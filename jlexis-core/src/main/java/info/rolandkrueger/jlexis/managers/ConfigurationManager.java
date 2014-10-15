/*
 * ConfigurationManager.java
 * Created on 05.04.2007
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
package info.rolandkrueger.jlexis.managers;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.data.configuration.JLexisWorkspace;
import info.rolandkrueger.jlexis.gui.keyactions.AbstractKeyAction;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class ConfigurationManager extends Observable
{
  public final static String USER_PROPS_FILENAME = "Lexis.properties.xml";
  public final static File   DATA_DIRECTORY      = new File ("data");
  public final static File   CONFIG_DIRECTORY    = new File (DATA_DIRECTORY, "config");
   
  private static ConfigurationManager sInstance;

  private List<AbstractKeyAction> mKeyActions;
  private File                    mPropertiesFile;  
  private JLexisWorkspace          mWorkspace;
  
  public static ConfigurationManager getInstance ()
  {
    if (sInstance == null)
    {
      sInstance = new ConfigurationManager ();
    }
    return sInstance;
  }
  
  private ConfigurationManager () 
  {
    mWorkspace      = new JLexisWorkspace               ();
    mKeyActions     = new ArrayList<AbstractKeyAction> ();
    mPropertiesFile = new File (CONFIG_DIRECTORY, USER_PROPS_FILENAME);
  }
  
  public String getDatabaseURL ()
  {
    return String.format ("jdbc:hsqldb:file:%s;shutdown=true", 
        mWorkspace.getDatabaseFile ().getAbsolutePath ());
  }
  
  public File getDatabaseFile ()
  {
    return mWorkspace.getDatabaseFile ();
  }
  
  public void setDatabaseFile (File databaseFile)
  {
    CheckForNull.check (databaseFile);
    mWorkspace.setDatabaseFile (databaseFile);
  }
  
  @Deprecated
  public void addKeyAction (AbstractKeyAction action)
  {
    mKeyActions.add (action);
  }
  
  public Font getApplicationFont ()
  {
    try
    {
      return Font.createFont (Font.TRUETYPE_FONT, 
          JLexisResourceManager.getInstance ().getFontInputStream ()).deriveFont (
              mWorkspace.getApplicationFontSize ());
    } catch (FontFormatException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
    return null;
  }
  
  public JLexisWorkspace getWorkspace ()
  {
    return mWorkspace;
  }

  public List<AbstractKeyAction> getKeyBindings ()
  {
    return mKeyActions;
  }
  
  public String getWordStemMarker ()
  {
    return "|";
  }
  
  public String getWordStemBeginMarker ()
  {
    return "<";
  }
  
  public String getWordStemEndMarker ()
  {
    return ">";
  }
  
  public String getWordStemPlaceholder ()
  {
    return "--";
  }
  
  public String getMandatoryTokenSplitChar ()
  {
    return ";";
  }
  
  public void setMandatoryTokenSplitChar (String splitChar)
  {
  }
  
  public String getOptionalTokenSplitChar ()
  {
    return ",";
  }
  
  public void setOptionalTokenSplitChar (String splitChar)
  {
  }

  public void loadWorkspace () 
  {
//    try
//    {
//      if (mPropertiesFile.exists ())
//      {
//        mWorkspace.setSource (new FileReader (mPropertiesFile));
//      } else
//      {
//        // TODO: i18n
//        ApplicationError error;
//         
//        if ( ! CONFIG_DIRECTORY.exists ())
//          error = new ApplicationError ("Konfigurationsverzeichnis " + CONFIG_DIRECTORY.getAbsolutePath ()
//              + " existiert nicht. Standardeinstellungen werden benutzt.", ErrorType.WARNING);
//        else
//          error = new ApplicationError ("Konfigurationsdatei " + mPropertiesFile.getAbsolutePath ()  
//              + " existiert nicht. Standardeinstellungen werden benutzt.", ErrorType.WARNING);
//        LexisMain.getInstance ().getApplicationMessageHandler ().reportError (error);
//        mWorkspace.setSource (new InputStreamReader 
//            (LexisResourceManager.getInstance ().getDefaultPropertiesFileInputStream ()));
//      }
//      mWorkspace.read ();
//    } catch (Exception e)
//    {
//      mWorkspace = new LexisWorkspace ();
//    }
  }
  
  public void saveWorkspace () throws IOException, 
                                      ParserConfigurationException, 
                                      TransformerException
  {
    if ( ! CONFIG_DIRECTORY.exists ())
    {
      // TODO: i18n + show this error message in a some window
      JLexisMain.getInstance ().addLogMessage ("Konfiguration kann nicht gespeichert werden. " +
          		"Konfigurationsverzeichnis " + CONFIG_DIRECTORY.getAbsolutePath () + " existiert nicht.");
      return;
    }
//    mWorkspace.setTarget (new FileWriter (mPropertiesFile));
//    mWorkspace.save      ();
  }

  
//  public float getApplicationFontSize ()
//  {
//    return getFloat (APPLICATION_FONT_SIZE);
//  }
  
//  public void setApplicationFontSize (float newSize)
//  {
//    mConfigMapping.put (APPLICATION_FONT_SIZE, String.format ("%d", newSize));
//    setChanged      ();
//    notifyObservers ();
//  }

//  public int getMaxUndos ()
//  {
//    return getInteger (MAX_UNDOS);
//  }
  
//  public void setBoundsForMainWindow (LexisMainWindow mainWindow)
//  {
//    Rectangle rect = new Rectangle (getInteger (MAIN_WINDOW_XPOS),
//                                    getInteger (MAIN_WINDOW_YPOS), 
//                                    getInteger (MAIN_WINDOW_WIDTH), 
//                                    getInteger (MAIN_WINDOW_HEIGHT));
//    mainWindow.setBounds (rect);
//  }
  
//  public void storeMainWindowBounds (LexisMainWindow mainWindow)
//  {
//    mConfigMapping.put (MAIN_WINDOW_XPOS,   String.valueOf (mainWindow.getX ()));
//    mConfigMapping.put (MAIN_WINDOW_YPOS,   String.valueOf (mainWindow.getY ()));
//    mConfigMapping.put (MAIN_WINDOW_WIDTH,  String.valueOf (mainWindow.getWidth ()));
//    mConfigMapping.put (MAIN_WINDOW_HEIGHT, String.valueOf (mainWindow.getHeight ()));
//  }
  
//  public List<String> getOpenVocabularyFiles ()
//  {
//    return mOpenVocabularyFiles;
//  }
  
//  public void setOpenVocabularyFiles (List<LexisVocabularyFile> files)
//  {
//    mOpenVocabularyFiles.clear ();
//    for (LexisVocabularyFile file : files)
//      mOpenVocabularyFiles.add (file.getFile ().getAbsolutePath ());
//  }
  
//  public void storeDialogWindowBounds (Dialog dialog, String id)
//  {
//    mConfigMapping.put (DIALOG_WINDOW_XPOS + id,   String.valueOf (dialog.getX ()));
//    mConfigMapping.put (DIALOG_WINDOW_YPOS + id,   String.valueOf (dialog.getY ()));
//    mConfigMapping.put (DIALOG_WINDOW_WIDTH + id,  String.valueOf (dialog.getWidth ()));
//    mConfigMapping.put (DIALOG_WINDOW_HEIGHT + id, String.valueOf (dialog.getHeight ()));
//  }
  
//  public void setBoundsForDialogWindow (Dialog dialog, String id)
//  {
//    Rectangle rect = new Rectangle (getInteger (DIALOG_WINDOW_XPOS + id),
//        getInteger (DIALOG_WINDOW_YPOS + id), 
//        getInteger (DIALOG_WINDOW_WIDTH + id), 
//        getInteger (DIALOG_WINDOW_HEIGHT + id));
//    dialog.setBounds (rect);
//  }
  
//  private File getBaseDir ()
//  {
//    File baseDir = LexisUtils.getAppBaseDir ();
//    if (baseDir == null) baseDir = new File (".");
//    return baseDir;
//  }
  
//  public void saveToFile ()
//  {
//    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance ();
//    DocumentBuilder        builder;
//    Document               document;
//    
//    try
//    {
//      builder  = documentBuilderFactory.newDocumentBuilder ();
//      document = builder.newDocument ();
//      Element root    = document.createElement ("lexisconfig");
//      
//      document.appendChild (root);
//      
//      // save configuration data
//      for (String key : mConfigMapping.keySet ())
//      {
//        String value = mConfigMapping.get (key);
//        Element option = document.createElement ("option");
//        option.setAttribute ("key", key);
//        option.setAttribute ("value", value);
//        root.appendChild (option);
//      }
//      
//      // save the names of the vocabulary files that are currently open
//      Element vocFiles = document.createElement ("vocfiles");
//      for (String filePath : mOpenVocabularyFiles)
//      {
//        Element fileElement = document.createElement ("editedvocfile");
//        fileElement.setAttribute ("path", filePath);
//        vocFiles.appendChild (fileElement);
//      }
//      root.appendChild (vocFiles);
//      
//      Transformer transformer = TransformerFactory.newInstance ().newTransformer ();
//      transformer.setOutputProperty (OutputKeys.INDENT, "YES");
//      DOMSource        source = new DOMSource (document);
//      FileOutputStream os     = new FileOutputStream (new File (getBaseDir (), USER_PROPS_FILENAME));
//      StreamResult     result = new StreamResult (os);
//      transformer.transform (source, result);
//    } catch (ParserConfigurationException e1)
//    {
//      // TODO: error handling
//      e1.printStackTrace();
//    } catch (TransformerConfigurationException e)
//    {
//      // TODO: error handling
//      e.printStackTrace();
//    } catch (TransformerFactoryConfigurationError e)
//    {
//      // TODO: error handling
//      e.printStackTrace();
//    } catch (FileNotFoundException e)
//    {
//      // TODO: error handling
//      e.printStackTrace();
//    } catch (TransformerException e)
//    {
//      // TODO: error handling
//      e.printStackTrace();
//    }
//  }
  
//  private int getInteger (String forKey)
//  {
//    try
//    {
//      return Integer.valueOf (mConfigMapping.get (forKey));
//    } catch (NumberFormatException nfExc)
//    {
//      // TODO: error handling
//      nfExc.printStackTrace ();
//      return Integer.MIN_VALUE;
//    }
//  }
  
//  private float getFloat (String forKey)
//  {
//    try
//    {
//      return Float.valueOf (mConfigMapping.get (forKey));
//    } catch (NumberFormatException nfExc)
//    {
//      // TODO: error handling
//      nfExc.printStackTrace ();
//      return Float.NaN;
//    }
//  }
  
//  private void loadFromFile ()
//  {
//    mOpenVocabularyFiles.clear ();
//
//    Reader source = null;
//    File inputFile = new File (getBaseDir (), USER_PROPS_FILENAME);
//    if ( ! inputFile.canRead ())
//    {
//      source = new InputStreamReader 
//      (ResourceManager.getInstance ().getResource (DEFAULT_PROPS_FILENAME));
//    } else
//      try
//    {
//        source = new FileReader (inputFile);
//    } catch (FileNotFoundException e) {e.printStackTrace (); /*TODO: error handling*/}
//    StAXDocumentRoot document = new StAXDocumentRoot (source);
//    document.assignHandler ("option", new OptionsParser ());
//    document.assignHandler ("editedvocfile", new OpenVocabularyFilesParser ());
//    try
//    {
//      document.parse ();
//    } catch (XMLStreamException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    } catch (FactoryConfigurationError e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }
}