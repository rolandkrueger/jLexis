/*
 * LexisMain.java
 * Created on 29.01.2007
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
package info.rolandkrueger.jlexis;

import info.rolandkrueger.jlexis.commands.UndoableCommand;
import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.configuration.JLexisWorkspace;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.data.userprofile.UserProfile;
import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.gui.JLexisMainWindow;
import info.rolandkrueger.jlexis.managers.ConfigurationManager;
import info.rolandkrueger.jlexis.managers.JLexisPersistenceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.PluginManager;
import info.rolandkrueger.jlexis.managers.UserProfileManager;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.roklib.ui.swing.ErrorMessageViewer;
import info.rolandkrueger.roklib.util.ApplicationError;
import info.rolandkrueger.roklib.util.ApplicationMessageHandler;
import info.rolandkrueger.roklib.util.ApplicationError.ErrorType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Main class for jLexis. Here, the startup and shutdown procedures are managed.
 * 
 * @author Roland Krueger
 */
public class JLexisMain implements ApplicationMessageHandler
{
  private static JLexisMain sInstance;
  
  private PluginManager             mPluginManager;
  private JLexisMainWindow           mMainWindow;
  private List<UndoableCommand>     mUserActions;
  
  public static void main (String[] args) throws Exception
  {
    getInstance ().startUp ();
  }
  
  private JLexisMain () 
  {    
    mUserActions = new ArrayList<UndoableCommand>     ();
    mPluginManager = new PluginManager (this);
  }
  
  public synchronized static JLexisMain getInstance ()
  {
    if (sInstance == null) sInstance = new JLexisMain ();
    return sInstance;
  }
  
  /**
   * Starts up the application.
   */
  private void startUp () throws Exception
  {
    try
    {
      UIManager.setLookAndFeel ("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e)
    {
      UIManager.setLookAndFeel (UIManager.getCrossPlatformLookAndFeelClassName ());
    } 
    
    // initialize I18N resources 
//    I18NResources.getInstance ().init (this, Locale.getDefault ());
    I18NResources.getInstance ().init (this, new Locale ("en", "US"));
    mMainWindow = new JLexisMainWindow ();
    // load the user settings
    ConfigurationManager.getInstance ().loadWorkspace ();
    
    // initialize the resource manager
    JLexisResourceManager.getInstance ().initialize ();

//    while ( ! LexisPersistenceManager.getInstance ().isDatabaseFileReadable ())
//    {
//      LexisPersistenceManager.getInstance ().selectDatabase ();
//    }
    
    // open a database connection
    JLexisPersistenceManager.getInstance ().setUpDatabaseConnection ();
    
    // load all installed plugins
    mPluginManager.loadPlugins ();
    AbstractUserInput.synchronizeRegisteredKeysWithDatabase ();
    
    // the following is only for testing purposes:
//  mEditedFiles.add (new LexisVocabularyFile (mLanguagePlugins));
    // testing code end

    // add shutdown hook
    Runtime.getRuntime ().addShutdownHook (new Thread (new Runnable () {
      public void run ()
      {
        for (LearningUnit unit : LearningUnitManager.getInstance ().getAllManagedLearningUnits ())
        {
          if (unit.isOpenForEditing ())
          {
            unit.closeForEditing ();
          }
        }
        addLogMessage ("Exiting Lexis...");
        saveWorkspace ();
        JLexisPersistenceManager.getInstance ().flushSession ();
        mPluginManager.saveLanguagePluginInfosToDB ();
        // close database connection
        JLexisPersistenceManager.getInstance ().closeDatabaseConnection ();
      }}));

    
    mMainWindow.configureFromWorkspace ();
       
    // load all available learning units
    LearningUnitManager.getInstance ().loadLearningUnits ();
    mMainWindow.setVisible (true);
    
    if (UserProfileManager.getInstance ().getUserProfilesCount () == 0)
    {
      UserProfile profile = null;
      profile = UserProfileManager.getInstance ().createNewUserProfile ();        
      if (profile != null)
        UserProfileManager.getInstance ().setActiveUserProfile (profile);
    } else
    {
      // start with having the user select a user profile
      UserProfileManager.getInstance ().selectUserProfile ();
    }
  }
    
  public void addLogMessage (String msg)
  {
    System.out.println (msg);
  }
  
  public Collection<LanguagePlugin> getPlugins ()
  {
    return mPluginManager.getLoadedPlugins ();
  }
  
  public PluginManager getPluginManager ()
  {
    return mPluginManager;
  } 
  
  private void saveWorkspace ()
  {
    JLexisWorkspace workspace = ConfigurationManager.getInstance ().getWorkspace ();
    if (mMainWindow != null)
    {
      workspace.setMainWindow (mMainWindow);
    }    
    
    try
    {
      ConfigurationManager.getInstance ().saveWorkspace ();
    } catch (ParserConfigurationException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TransformerException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public UndoableCommand addUserAction (UndoableCommand action)
  {
    mUserActions.add (action);
    if (mUserActions.size () > ConfigurationManager.getInstance ().getWorkspace ().getMaxUndos ())
      mUserActions.remove (0);
    action.execute ();
    mMainWindow.getUndoAction ().setName (action.getDescription ());
    mMainWindow.getUndoAction ().setEnabled (true);
    return action;
  }
  
  public void undoLastUserAction ()
  {
    mUserActions.remove (mUserActions.size () - 1).undo ();
    if (mUserActions.size () > 0)
    {
      UndoableCommand current = mUserActions.get (mUserActions.size () - 1);
      mMainWindow.getUndoAction ().setName (current.getDescription ());
    } else
    {
      mMainWindow.getUndoAction ().setDefaultName ();
      mMainWindow.getUndoAction ().setEnabled (false);
    }
  }

  public String getLexisVersion ()
  {
    return "0.1a";
  }

  /**
   * Returns the plugin with the given identifier. Only one of the loaded plugins can be returned.
   * 
   * @param pluginIdentifier
   *          the identifier of a plugin
   * @return one of the loaded plugins that is specified by the given identifier or
   *         <code>null</code> if no such plugin is available.
   */
  public LanguagePlugin getPluginFor (String pluginIdentifier)
  {    
    return mPluginManager.getPluginFor (pluginIdentifier);
  }
  
  /**
   * If some severe error condition occurs from which the application cannot recover this method
   * can be called. It will open an error dialog and shut down the application after the user has
   * clicked OK.
   */
  public void fatalError (String message)
  {
    ErrorMessageViewer errorDialog = new ErrorMessageViewer (mMainWindow, "Error!", message);
    errorDialog.addActionListener (new ActionListener () {
      public void actionPerformed (ActionEvent e)
      {
        System.exit (1);        
      }});
    errorDialog.setVisible (true);
  }

  public JLexisMainWindow getMainWindow ()
  {
    return mMainWindow;
  }

//  @Deprecated
//  public void addVocabularyFileForEditing (LexisVocabularyFile file)
//  {
//    mMainWindow.addVocabularyFileForEditing (file);
//  }

  public ApplicationMessageHandler getApplicationMessageHandler ()
  {
    return this;
  }
  
  @Override
  public void infoMessage (String message)
  {
    // TODO: i18n
    JOptionPane.showMessageDialog (mMainWindow, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void reportError (ApplicationError error)
  {
    // TODO: i18n
    String title = "Information";
    int msgType = JOptionPane.INFORMATION_MESSAGE;
    if (error.getType () == ErrorType.WARNING)
    {
      msgType = JOptionPane.WARNING_MESSAGE;
      title = "Hinweis";      
    } else if (error.getType () == ErrorType.SEVERE)
    {
      msgType = JOptionPane.ERROR_MESSAGE;       
      title = "Fehler";
    }
    JOptionPane.showMessageDialog (mMainWindow, error.getDescription (), title, msgType);
  }
}




