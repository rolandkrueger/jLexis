/*
 * $Id: LearningUnitManager.java 125 2009-05-30 08:58:51Z roland $
 * Created on 02.12.2008
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
package info.rolandkrueger.jlexis.managers.learningunitmanager;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.JLexisMainWindow;
import info.rolandkrueger.jlexis.gui.actions.ShowLearningUnitManagerFrameAction;
import info.rolandkrueger.jlexis.gui.internalframes.EditLearningUnitInternalFrame;
import info.rolandkrueger.jlexis.gui.internalframes.learningunitmanager.LearningUnitManagerFrame;
import info.rolandkrueger.jlexis.managers.JLexisPersistenceManager;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;

import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

public class LearningUnitManager
{
  private final static LearningUnitManager sInstance = new LearningUnitManager ();
  
  private JLexisMainWindow                    mMainWindow;
  private LearningUnitManagerFrame           mGUI;
  private ShowLearningUnitManagerFrameAction mShowFrameAction;
  private Map<String, LearningUnit>          mLearningUnits;
  private LearningUnitManagerCallbackHandler mCallbackHandler;
    
  private LearningUnitManager () 
  {
    mLearningUnits   = new HashMap<String, LearningUnit>      ();
    mCallbackHandler = new LearningUnitManagerCallbackHandler (this);
  }
  
  public static LearningUnitManager getInstance ()
  {
    return sInstance;
  }
  
  public void addListener (LearningUnitManagerCallbackInterface listener)
  {
    mCallbackHandler.addLearningUnitManagerCallback (listener);
  }
  
  public void removeListener (LearningUnitManagerCallbackInterface listener)
  {
    mCallbackHandler.removeLearningUnitManagerCallback (listener);
  }
  
  public void setMainWindowReference (JLexisMainWindow mainWindow)
  {
    assert mainWindow != null;
    mMainWindow = mainWindow;
    mShowFrameAction = new ShowLearningUnitManagerFrameAction (getLearningUnitManagerFrame ());
  }
  
  public ShowLearningUnitManagerFrameAction getShowLearningUnitManagerFrameAction ()
  {
    if (mMainWindow == null)
      throw new IllegalStateException ("Reference to main window object hasn't been set yet.");
    return mShowFrameAction;
  }

  public void addLearningUnit (LearningUnit newUnit)
  {
    mLearningUnits.put    (newUnit.getName (), newUnit);
    mCallbackHandler.fireLearningUnitAdded (newUnit);
  }
  
  public Collection<LearningUnit> getAllManagedLearningUnits ()
  {
    return Collections.unmodifiableCollection (mLearningUnits.values ());
  }
  
  public LearningUnitManagerFrame getLearningUnitManagerFrame ()
  {
    if (mGUI == null)
      mGUI = new LearningUnitManagerFrame ();
      
    return mGUI;
  }

  public void removeLearningUnit (LearningUnit learningUnit)
  {
    if ( ! doesLearningUnitExistWithName (learningUnit.getName ()))
      throw new IllegalArgumentException ("Cannot remove learning unit " + learningUnit + ". Such a " +
      		"unit is not managed by this manager.");
    
    mLearningUnits.remove   (learningUnit.getName ());
    mCallbackHandler.fireLearningUnitRemoved (learningUnit);
  }
  
  @SuppressWarnings("unchecked")
  public void loadLearningUnits ()
  {
    Session session = JLexisPersistenceManager.getInstance ().getSession ();
    Query qry = session.createQuery ("FROM LearningUnit");
    List<LearningUnit> units = qry.list ();
    for (LearningUnit unit : units)
    {
      addLearningUnit (unit);
    }
    updateLanguagePluginInformationForUnits ();
  }
  
  public boolean doesLearningUnitExistWithName (String name)
  {
    return mLearningUnits.containsKey (name);
  }
  
  public void openSelectedLearningUnitForEditing ()
  {
    List<LearningUnit> unitsToOpen = getLearningUnitManagerFrame ().getSelectedLearningUnits ();

    for (LearningUnit unitToOpen : unitsToOpen)
    {
      // check if the unit is already open for editing
      if (unitToOpen.isOpenForEditing ())
      {
        EditLearningUnitInternalFrame frame = unitToOpen.getEditFrame ();
        // bring edit frame to foreground
        try
        {
          frame.setSelected (true);
        } catch (PropertyVetoException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      } else
      {
        EditLearningUnitInternalFrame frame = new EditLearningUnitInternalFrame (unitToOpen);
        JLexisMain.getInstance ().getMainWindow ().addFrameToDesktop (frame);
        unitToOpen.openForEditing (frame);
        frame.setVisible (true); 
      }
    }
  }

  public void updateLanguagePluginInformationForUnits ()
  {
    for (LearningUnit unit : mLearningUnits.values ())
    {
      for (Language lang : unit.getLanguages ())
      {
        LanguagePlugin plugin = JLexisMain.getInstance ().getPluginManager ().getPluginFor (lang);
        lang.setLanguagePlugin (plugin);
      }
    }
  }
}
