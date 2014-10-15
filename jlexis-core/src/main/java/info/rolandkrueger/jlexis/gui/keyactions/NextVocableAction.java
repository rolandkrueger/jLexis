/*
 * NextVocableAction.java
 * Created on 06.05.2007
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
 * 
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
package info.rolandkrueger.jlexis.gui.keyactions;

import info.rolandkrueger.jlexis.controllers.EditVocabularyControllerInterface;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.gui.dialogs.ChooseNextWordTypeDialog;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class NextVocableAction extends AbstractKeyAction
{
  private static final long serialVersionUID = -6181985979750379185L;
  private final static String sIdentifier = "keyaction.nextVocable";
  private EditVocabularyControllerInterface mEditController;
  private ChooseNextWordTypeDialog mDialog;
  
  public NextVocableAction (EditVocabularyControllerInterface controller)
  {
    super (sIdentifier, InputEvent.CTRL_MASK, 'n' - 'a' + 1);
    mEditController = controller;
  }  
  
  public void actionPerformed (ActionEvent e)
  {
    if (mDialog == null)
    {
      mDialog = new ChooseNextWordTypeDialog (getMain ());
      for (Language language: mEditController.getLanguagesOfEditedLearningUnit ())
      {
        LanguagePlugin plugin = language.getLanguagePlugin ().getValue ();
        if (plugin == null)
        {
          // TODO: error handling
          continue;
        }
        for (final AbstractWordType wordType : plugin.getWordTypes ())
        {
          mDialog.addWordType (plugin, wordType);
        }
      }
      mDialog.addActionListener (new Listener ());
    }
    mDialog.setVisible (true);
  }
  
  private class Listener implements ActionListener
  {
    public void actionPerformed (ActionEvent e)
    {
      mEditController.editNewVocable (mDialog.getSelectedWordType ());
      mDialog.setVisible (false);
    }
  }
}
