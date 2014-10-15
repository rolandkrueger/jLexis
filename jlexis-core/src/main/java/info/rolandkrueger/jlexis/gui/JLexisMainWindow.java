/*
 * LexisMainWindow.java
 * Created on 31.01.2007
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
package info.rolandkrueger.jlexis.gui;

import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.data.userprofile.UserProfile;
import info.rolandkrueger.jlexis.gui.actions.CreateNewQuizAction;
import info.rolandkrueger.jlexis.gui.actions.CreateNewUserProfileAction;
import info.rolandkrueger.jlexis.gui.actions.DeleteLearningUnitAction;
import info.rolandkrueger.jlexis.gui.actions.EditFileAction;
import info.rolandkrueger.jlexis.gui.actions.NewLearningUnitAction;
import info.rolandkrueger.jlexis.gui.actions.OpenLearningUnitAction;
import info.rolandkrueger.jlexis.gui.actions.QuitAction;
import info.rolandkrueger.jlexis.gui.actions.SelectUserProfileAction;
import info.rolandkrueger.jlexis.gui.actions.ShowLearningUnitManagerFrameAction;
import info.rolandkrueger.jlexis.gui.actions.UndoAction;
import info.rolandkrueger.jlexis.gui.dialogs.CreateNewUserProfileDialog;
import info.rolandkrueger.jlexis.gui.internalframes.EditLearningUnitInternalFrame;
import info.rolandkrueger.jlexis.managers.ConfigurationManager;
import info.rolandkrueger.jlexis.managers.JLexisGUIElementEnablingManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.UserProfileManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;
import info.rolandkrueger.roklib.ui.swing.StatusBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public class JLexisMainWindow extends JFrame implements WindowListener
{ 
  private static final long serialVersionUID = -3040456690949066114L;
  
  private UndoAction         mUndoAction;
  private StatusBar          mStatusBar;
  private JDesktopPane       mDesktop;
//  private CenterPanel        mMainPanel;
  private Map<LearningUnit, EditLearningUnitInternalFrame> mEditedLearningUnits;
  private JLexisGUIElementEnablingManager mVisibilityGroupManager;
  private int mStatusBarUserProfileSection;
  
  public JLexisMainWindow ()
  {
    super ("jLexis - v0.1a");
    mEditedLearningUnits = new HashMap<LearningUnit, EditLearningUnitInternalFrame> ();
    LearningUnitManager.getInstance ().setMainWindowReference (this);
    setLayout (new BorderLayout ());
        
    mVisibilityGroupManager = new JLexisGUIElementEnablingManager ();
    mDesktop = new JDesktopPane ();
    mDesktop.setBackground (new Color (0, 84, 166));
    add (mDesktop, BorderLayout.CENTER);
    
//    mMainPanel = new CenterPanel ();
//    add (mMainPanel, BorderLayout.CENTER);
    
//    mMainPanel.showEditPanel ();
    
//    add (new DefaultVocableInputPanel (new SwedishIPATextField ()), BorderLayout.CENTER);
//    add (new SwedishNounInputPanel (), BorderLayout.CENTER);
    
    JToolBar toolbar = new JToolBar (JToolBar.HORIZONTAL);
    QuitAction                         quitAction = new QuitAction ();
    NewLearningUnitAction              newFileAction  = new NewLearningUnitAction  ();
    OpenLearningUnitAction             openLearningUnitAction = new OpenLearningUnitAction ();
    DeleteLearningUnitAction           deleteLearningUnitAction = new DeleteLearningUnitAction ();
    CreateNewUserProfileAction         createUserProfileAction = new CreateNewUserProfileAction ();
    SelectUserProfileAction            selectUserProfileAction = UserProfileManager.getInstance ().getSelectUserProfileAction ();
    ShowLearningUnitManagerFrameAction learningUnitAction = LearningUnitManager.getInstance ().getShowLearningUnitManagerFrameAction ();
    EditFileAction                     editFileAction = new EditFileAction ();
    CreateNewQuizAction                createQuizAction = new CreateNewQuizAction ();

    editFileAction.setEnabled (false);
    mUndoAction = new UndoAction ();
    mUndoAction.setEnabled (false);
    
    mVisibilityGroupManager.setDeleteLearningUnitAction (deleteLearningUnitAction);
    mVisibilityGroupManager.setEditFileAction           (editFileAction);
    mVisibilityGroupManager.setOpenLearningUnitAction   (openLearningUnitAction);
    mVisibilityGroupManager.setSelectUserProfileAction  (selectUserProfileAction);
    mVisibilityGroupManager.setCreateNewQuizAction      (createQuizAction);
    
    toolbar.add (quitAction);
    toolbar.add (newFileAction);
    toolbar.add (openLearningUnitAction);
    toolbar.add (editFileAction);
    toolbar.add (deleteLearningUnitAction);
    toolbar.add (mUndoAction);
    toolbar.add (createQuizAction);
    
    add (toolbar, BorderLayout.NORTH);
    
    JMenuBar menubar  = new JMenuBar ();
    // FIXME
//    JMenu    fileMenu = new JMenu    (I18NResources.getString (I18NResourceKeys.FILE_MENU_KEY));
    JMenu    fileMenu = new JMenu    ("File");
    
    fileMenu.add (newFileAction);
    fileMenu.add (openLearningUnitAction);
    fileMenu.add (deleteLearningUnitAction);
    fileMenu.add (mUndoAction);
    fileMenu.addSeparator ();
    fileMenu.add (quitAction);
    menubar.add (fileMenu);
    
    //TODO: i18n
//    JMenu userMenu = new JMenu ("Profil");
    JMenu userMenu = new JMenu ("Profile");
    userMenu.add (createUserProfileAction);
    userMenu.add (selectUserProfileAction);
    menubar.add (userMenu);
    
    // TODO: I18N
//    JMenu viewMenu = new JMenu ("Ansicht");
    JMenu viewMenu = new JMenu ("View");
    viewMenu.add (learningUnitAction.getJCheckBoxMenuItem ());
    learningUnitAction.showFrame ();
    menubar.add (viewMenu);
    
    setJMenuBar (menubar);
    
    mStatusBar = new StatusBar (4000);
    mStatusBar.addElement ("jLexis", "");
    // TODO: I18N
//    mStatusBarUserProfileSection = mStatusBar.addElement ("(kein Profil aktiv)", "");
    mStatusBarUserProfileSection = mStatusBar.addElement ("(no active profile)", "");
    add (mStatusBar, BorderLayout.SOUTH);
    
    addWindowListener (this);
    addFrameToDesktop (LearningUnitManager.getInstance ().getLearningUnitManagerFrame ());
    setIconImage (JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.LEXIS_LOGO).getImage ());
  }
  
  public void setActiveUserProfile (UserProfile profile)
  {
    // TODO: I18N
//    String profileText = "(kein Profil aktiv)";
    String profileText = "(no active profile)";
    String titleText = "";
    if (profile != null)
    {
      profileText = "(" + profile.getProfileName () + ")";
      titleText = profileText;
    }
    setTitle ("Lexis " + titleText);
    mStatusBar.setElementText (mStatusBarUserProfileSection, profileText);
  }
  
  public void addFrameToDesktop (JInternalFrame frame)
  {
    mDesktop.add (frame);
  }
  
  public CreateNewUserProfileDialog getCreateNewUserProfileDialog ()
  {
    CreateNewUserProfileDialog dialog = new CreateNewUserProfileDialog (this);
    return dialog;
  }
  
  public JDesktopPane getDesktop ()
  {
    return mDesktop;
  }
  
  public JLexisGUIElementEnablingManager getVisibilityGroupManager ()
  {
    return mVisibilityGroupManager;
  }
    
  /**
   * Loads configuration data relevant for the main window from the configuration manager.
   */
  public void configureFromWorkspace ()
  {
    ConfigurationManager.getInstance ().getWorkspace ().configureMainWindow (this);
  }
  
  public StatusBar getStatusBar ()
  {
    return mStatusBar;
  }
    
  public void openLearningUnit (LearningUnit unitToOpen)
  {
    EditLearningUnitInternalFrame frame = new EditLearningUnitInternalFrame (unitToOpen);
    mEditedLearningUnits.put (unitToOpen, frame);
    addFrameToDesktop (frame);
  }
    
  public UndoAction getUndoAction ()
  {
    return mUndoAction;
  }

  public void windowClosing (WindowEvent e)
  {
    System.exit (0);
  }

  public void windowActivated (WindowEvent e) {}
  public void windowClosed (WindowEvent e) {}
  public void windowDeactivated (WindowEvent e) {}
  public void windowDeiconified (WindowEvent e) {}
  public void windowIconified (WindowEvent e) {}
  public void windowOpened (WindowEvent e) {}
}
  
  
