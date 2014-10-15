/*
 * Created on 18.03.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package info.rolandkrueger.jlexis.gui.containers;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.internalframes.learningunitmanager.UnitManagerLanguagePanel;
import info.rolandkrueger.jlexis.managers.JLexisGUIElementEnablingManager;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManagerCallbackInterface;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * @author Roland Krueger
 * @version $Id: LearningUnitManagerPanel.java 155 2009-10-23 06:22:31Z roland $
 */
public class LearningUnitManagerPanel extends JPanel implements 
  ChangeListener, ListSelectionListener, LearningUnitManagerCallbackInterface
{
  private static final long serialVersionUID = 6743453112715496060L;
  private JTabbedPane tabbedPane = null;
  private UnitManagerLanguagePanel              mSelectedTab;
  private Map<String, UnitManagerLanguagePanel> mLanguagePanels;
  private List<LearningUnit>                    mSelectedLearningUnits;
  private List<ChangeListener>                  mChangeListeners;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////

  public void unitsAdded (Object source, List<LearningUnit> units)
  {
    for (LearningUnit unit : units)
      unitAdded (this, unit);
  }
  
  public void unitsRemoved (Object source, List<LearningUnit> units)
  {
    for (LearningUnit unit : units)
      unitRemoved (this, unit);
  }
  
  @Override
  public void unitRemoved (Object source, LearningUnit unit)
  {
    for (UnitManagerLanguagePanel panel : mLanguagePanels.values ())
    {
      panel.removeLearningUnit (unit);
    }
  }
  
  @Override
  public void unitAdded (Object source, LearningUnit unit)
  {
    for (Language lang : unit.getLanguages ())
    {
      UnitManagerLanguagePanel panel = mLanguagePanels.get (lang.getLanguageName ());
      if (panel == null)
      {
        panel = new UnitManagerLanguagePanel ();
        panel.addListSelectionListener (this);
        mLanguagePanels.put (lang.getLanguageName (), panel);
        getTabbedPane ().add (lang.getLanguageName (), panel);
      }
      panel.addLearningUnit (unit);
    }
    getSelectedTab ();
  }
  
  public void addChangeListener (ChangeListener listener)
  {
    mChangeListeners.add (listener);
  }
  
  public void removeChangeListener (ChangeListener listener)
  {
    mChangeListeners.remove (listener);
  }
  
  private void notifyChangeListener ()
  {
    List<ChangeListener> list = new ArrayList<ChangeListener> (mChangeListeners);
    
    for (ChangeListener listener : list)
    {
      listener.stateChanged (new ChangeEvent (this));
    }
  }

  @Override
  public void stateChanged (ChangeEvent e)
  {
    getSelectedTab ();
    assert mSelectedTab != null;
    
    mSelectedTab.removeSelection ();
  }
  
  private void getSelectedTab ()
  {    
    mSelectedTab = mLanguagePanels.get (
        getTabbedPane ().getTitleAt (getTabbedPane ().getSelectedIndex ()));
  }
  
  @Override
  /**
   * Method is called when the list selection of the learning unit list 
   * changed. 
   */
  public void valueChanged (ListSelectionEvent e)
  {
    assert mSelectedTab != null;
    mSelectedLearningUnits = mSelectedTab.getSelectedListValues ();
    JLexisMain.getInstance ().getMainWindow ().getVisibilityGroupManager ().setGroupEnabled (
        JLexisGUIElementEnablingManager.LEARNING_UNIT_SELECTED_GROUP, ! mSelectedLearningUnits.isEmpty ());
    notifyChangeListener ();
  }
  
  /**
   * Returns the currently selected learning unit. If no selection is made, <code>null</code> is
   * returned.
   * 
   * @return the currently selected learning unit or <code>null</code> if no unit is currently
   *         selected
   */
  public List<LearningUnit> getSelectedLearningUnits ()
  {
    return mSelectedLearningUnits;
  }
  
  public boolean isSelectionMade ()
  {
    return ! mSelectedLearningUnits.isEmpty ();
  }
    
  @Override
  protected void finalize () throws Throwable
  {
    LearningUnitManager.getInstance ().removeListener (this);
    mChangeListeners.clear ();
    mLanguagePanels.clear ();
    super.finalize ();
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////

  /**
   * This is the default constructor
   */
  public LearningUnitManagerPanel ()
  {
    super ();
    initialize ();
  }

  /**
   * This method initializes this
   */
  private void initialize ()
  {
    this.setSize (534, 415);
    this.setLayout (new BorderLayout ());
    this.add (getTabbedPane (), BorderLayout.CENTER);
    mLanguagePanels = new HashMap<String, UnitManagerLanguagePanel> ();
    mChangeListeners = new ArrayList<ChangeListener> ();

    // register as observer with the learning unit manager so that we're
    // notified of changes in the set of available learning units
    LearningUnitManager.getInstance ().addListener (this);
    
    // add all learning units that are currently loaded
    for (LearningUnit unit : LearningUnitManager.getInstance ().getAllManagedLearningUnits ())
    {
      unitAdded (this, unit);
    }
  }
  
  /**
   * This method initializes tabbedPane 
   *  
   * @return javax.swing.JTabbedPane  
   */
  private JTabbedPane getTabbedPane ()
  {
    if (tabbedPane == null)
    {
      tabbedPane = new JTabbedPane ();
      tabbedPane.addChangeListener (this);
    }
    return tabbedPane;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
