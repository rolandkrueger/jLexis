/*
 * $Id: EditVocabularyController.java 203 2009-12-16 16:24:28Z roland $
 * Created on 05.03.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
 * 
 * This file is part of jLexis.
 *
 * jLexis is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * jLexis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jLexis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package info.rolandkrueger.jlexis.controllers;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.commands.RemoveVocableCommand;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.gui.keyactions.NextVocableAction;

import java.util.List;

public class EditVocabularyController implements EditVocabularyControllerInterface
{
  private LearningUnit                mEditedUnit;
  private EditVocabularyViewInterface mEditView;
  private int                         mEditedIndex;
  
  public EditVocabularyController (EditVocabularyViewInterface view)
  {
    if (view == null)
      throw new NullPointerException ("View is null.");
    mEditView = view;
    mEditView.setController (this);
    mEditedIndex = -1;
    
    view.setNextVocableAction (new NextVocableAction (this));
  }
  
  @Override
  public void setSelectedVocable (int index)
  {
    if (index >= mEditedUnit.getSize () || index < 0)
      throw new ArrayIndexOutOfBoundsException (String.format ("Index is not within the range " +
      		"of the size of the current learning unit: %d", index));
    
    // save the current input
    saveInput ();
    mEditedIndex = index;
    mEditView.setCurrentlyEditedVocable (mEditedUnit.getVocableAt (index));
    mEditView.setEditedIndex (mEditedIndex);
  }
  
  public void setEditedLearningUnit (LearningUnit unit)
  {
    if (unit == null)  
      throw new NullPointerException ("Learning unit is null.");
    
    mEditedUnit = unit;
    
    mEditView.removeAllLanguageInputAreas ();
    mEditView.addLanguage (mEditedUnit.getNativeLanguage ());
    for (Language language : mEditedUnit.getLanguages ())
    {
      mEditView.addLanguage (language);      
    }
    
    mEditView.setEditedLearningUnit (mEditedUnit);
    mEditView.setEditedIndex (-1);
  }
  
  public void saveInput ()
  {
    saveInput (true);
  }
  
  public void saveInput (boolean updateView)
  {
    Vocable input = mEditView.getCurrentInput ();
    // if the vocable is empty, there's nothing to do
    if (input.isEmpty ()) return;
    
    if (mEditedIndex == -1)
    {
      mEditedUnit.addVocable (input);
    } else
    {
      assert mEditedIndex < mEditedUnit.getSize ();
      mEditedUnit.replaceVocable (input, mEditedIndex);
    }
    if (updateView)
    {      
      mEditView.clearAllUserInput ();
      mEditView.updateView ();
    }
  }
  
  @Override
  public void editNewVocable (AbstractWordType nextWordType)
  {
    saveInput ();
    mEditedIndex = -1;
    mEditView.editNewVocable (nextWordType);
    mEditView.setEditedIndex (mEditedIndex);
  }
  
  @Override
  public List<Language> getLanguagesOfEditedLearningUnit ()
  {
    return mEditedUnit.getLanguages ();
  }

  @Override
  public String getLearningUnitName ()
  {
    return mEditedUnit.getName ();
  }

  @Override
  public void removeVocable (Vocable vocable)
  {
    int index = mEditedUnit.indexOf (vocable);
    if (index < 0) 
      throw new IllegalArgumentException ("Given vocable is not contained in the current learning unit.");
    mEditedUnit.removeVocable (vocable);
    if (mEditedIndex == index)
    {
      mEditView.clearAllUserInput ();
      // the currently edited vocable is about to be removed
      if (mEditedUnit.getSize () == 0)
      {
        mEditedIndex = -1;
        mEditView.editNewVocable (mEditedUnit.getLanguages ().get (0).getLanguagePlugin ().getValue ().getDefaultWordType ());
        mEditView.setEditedIndex (mEditedIndex);        
      } else
      {
        if (mEditedIndex == mEditedUnit.getSize ()) // is the last vocable in the unit being edited 
          mEditedIndex--;
        else 
          mEditedIndex++;
        
        mEditView.setCurrentlyEditedVocable (mEditedUnit.getVocableAt (mEditedIndex));
        mEditView.setEditedIndex (mEditedIndex);        
      }
    } else
    {
      if (mEditedIndex > index) 
      {
        mEditedIndex--;
        mEditView.setEditedIndex (mEditedIndex);        
      }
    }
    
    mEditView.updateView ();
  }
  
  @Override
  public void insertVocable (Vocable vocable, int index)
  {
    mEditedUnit.insertVocable (vocable, index);
    mEditView.updateView ();
  }

  @Override
  public Vocable getVocableAtIndex (int index)
  {
    return mEditedUnit.getVocableAt (index);
  }

  @Override
  public void removeSelectedVocable ()
  {
    int selectedIndex = mEditView.getSelectedVocableIndex (); 
    if (selectedIndex < 0 || (selectedIndex >= mEditedUnit.getSize ())) return;
    JLexisMain.getInstance ().addUserAction (
        new RemoveVocableCommand (this, getVocableAtIndex (selectedIndex), selectedIndex));
  }
}
