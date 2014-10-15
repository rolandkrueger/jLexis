/*
 * ConfigurationManager.java Created on 05.04.2007 Copyright 2007 Roland Krueger
 * (www.rolandkrueger.info) This file is part of jLexis. jLexis is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version.
 * jLexis is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU General Public
 * License along with jLexis; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 */
package info.rolandkrueger.jlexis.deprecated;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.gui.JLexisMainWindow;
import info.rolandkrueger.jlexis.gui.keyactions.AbstractKeyAction;
import info.rolandkrueger.jlexis.misc.JLexisUtils;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Deprecated
public class ConfigurationManager extends Observable
{
  private final static String               USER_PROPS_FILENAME    = "userProperties.xml";
  private final static String               DEFAULT_PROPS_FILENAME = "defaultProperties.xml";
  private final static String               FONT_FILENAME          = "DejaVuSans.ttf";
  // configuration keys
  private final static String               ROOT_REPLACEMENT       = "rootReplacement";
  private final static String               ROOT_MARKER            = "rootMarker";
  private final static String               APPLICATION_FONT_SIZE  = "appFontSize";
  private final static String               MAX_UNDOS              = "maxUndos";
  private final static String               MAIN_WINDOW_HEIGHT     = "MainWindowHeight";
  private final static String               MAIN_WINDOW_WIDTH      = "MainWindowWidth";
  private final static String               MAIN_WINDOW_XPOS       = "MainWindowXPos";
  private final static String               MAIN_WINDOW_YPOS       = "MainWindowYPos";
  private final static String               DIALOG_WINDOW_HEIGHT   = "DialogWindowHeight";
  private final static String               DIALOG_WINDOW_WIDTH    = "DialogWindowWidth";
  private final static String               DIALOG_WINDOW_XPOS     = "DialogWindowXPos";
  private final static String               DIALOG_WINDOW_YPOS     = "DialogWindowYPos";
  private final static String               FILE_CHOOSER_DIRECTORY = "FileChooserStartDir";

  private final static ConfigurationManager sInstance              = new ConfigurationManager ();
  private List<AbstractKeyAction>           mKeyActions;
  private Map<String, String>               mConfigMapping;
  private List<String>                      mOpenVocabularyFiles;

  public static ConfigurationManager getInstance ()
  {
    return sInstance;
  }

  private ConfigurationManager ()
  {
    mKeyActions = new ArrayList<AbstractKeyAction> ();
    mConfigMapping = new TreeMap<String, String> ();
    mOpenVocabularyFiles = new ArrayList<String> ();

//    try
//    {
//      ResourceManager.getInstance ().registerResource (ConfigurationManager.class, DEFAULT_PROPS_FILENAME);
//      ResourceManager.getInstance ().registerResource (LexisMain.class, FONT_FILENAME);
//    } catch (IOException e)
//    {
//      // TODO: error handling
//      e.printStackTrace ();
//    }

    // load defaults (load these data from a default file later)
    mConfigMapping.put (ROOT_REPLACEMENT, "--");
    mConfigMapping.put (ROOT_MARKER, "|");
    mConfigMapping.put (APPLICATION_FONT_SIZE, "13.0");
    mConfigMapping.put (MAX_UNDOS, "50");
    mConfigMapping.put (MAIN_WINDOW_HEIGHT, "450");
    mConfigMapping.put (MAIN_WINDOW_WIDTH, "600");
    mConfigMapping.put (MAIN_WINDOW_XPOS, "0");
    mConfigMapping.put (MAIN_WINDOW_YPOS, "0");

    loadFromFile ();
  }

  public void setFileChooserStartDirectory (String directory)
  {
    mConfigMapping.put (FILE_CHOOSER_DIRECTORY, directory);
  }

  public String getFileChooserStartDirectory ()
  {
    return mConfigMapping.get (FILE_CHOOSER_DIRECTORY);
  }

  public String getRootReplacement ()
  {
    return mConfigMapping.get (ROOT_REPLACEMENT);
  }

  public String getRootMarker ()
  {
    return mConfigMapping.get (ROOT_MARKER);
  }

  public void addKeyAction (AbstractKeyAction action)
  {
    mKeyActions.add (action);
  }

  public Font getApplicationFont ()
  {
    Font font = null;
    try
    {
      font = Font
          .createFont (Font.TRUETYPE_FONT, JLexisMain.class.getResourceAsStream ("../../../data/DejaVuSans.ttf"));
    } catch (FontFormatException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace ();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace ();
    }
    font = font.deriveFont (getApplicationFontSize ());

    return font;
  }

  public float getApplicationFontSize ()
  {
    return getFloat (APPLICATION_FONT_SIZE);
  }

  public void setApplicationFontSize (float newSize)
  {
    mConfigMapping.put (APPLICATION_FONT_SIZE, String.format ("%d", newSize));
    setChanged ();
    notifyObservers ();
  }

  public int getMaxUndos ()
  {
    return getInteger (MAX_UNDOS);
  }

  public List<AbstractKeyAction> getKeyBindings ()
  {
    return mKeyActions;
  }

  public void setBoundsForMainWindow (JLexisMainWindow mainWindow)
  {
    Rectangle rect = new Rectangle (getInteger (MAIN_WINDOW_XPOS), getInteger (MAIN_WINDOW_YPOS),
        getInteger (MAIN_WINDOW_WIDTH), getInteger (MAIN_WINDOW_HEIGHT));
    mainWindow.setBounds (rect);
  }

  public void storeMainWindowBounds (JLexisMainWindow mainWindow)
  {
    mConfigMapping.put (MAIN_WINDOW_XPOS, String.valueOf (mainWindow.getX ()));
    mConfigMapping.put (MAIN_WINDOW_YPOS, String.valueOf (mainWindow.getY ()));
    mConfigMapping.put (MAIN_WINDOW_WIDTH, String.valueOf (mainWindow.getWidth ()));
    mConfigMapping.put (MAIN_WINDOW_HEIGHT, String.valueOf (mainWindow.getHeight ()));
  }

  public List<String> getOpenVocabularyFiles ()
  {
    return mOpenVocabularyFiles;
  }

  public void storeDialogWindowBounds (Dialog dialog, String id)
  {
    mConfigMapping.put (DIALOG_WINDOW_XPOS + id, String.valueOf (dialog.getX ()));
    mConfigMapping.put (DIALOG_WINDOW_YPOS + id, String.valueOf (dialog.getY ()));
    mConfigMapping.put (DIALOG_WINDOW_WIDTH + id, String.valueOf (dialog.getWidth ()));
    mConfigMapping.put (DIALOG_WINDOW_HEIGHT + id, String.valueOf (dialog.getHeight ()));
  }

  public void setBoundsForDialogWindow (Dialog dialog, String id)
  {
    Rectangle rect = new Rectangle (getInteger (DIALOG_WINDOW_XPOS + id), getInteger (DIALOG_WINDOW_YPOS + id),
        getInteger (DIALOG_WINDOW_WIDTH + id), getInteger (DIALOG_WINDOW_HEIGHT + id));
    dialog.setBounds (rect);
  }

  private File getBaseDir ()
  {
    File baseDir = JLexisUtils.getAppBaseDir ();
    if (baseDir == null) baseDir = new File (".");
    return baseDir;
  }

  public void saveToFile ()
  {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance ();
    DocumentBuilder builder;
    Document document;

    try
    {
      builder = documentBuilderFactory.newDocumentBuilder ();
      document = builder.newDocument ();
      Element root = document.createElement ("lexisconfig");

      document.appendChild (root);

      // save configuration data
      for (String key : mConfigMapping.keySet ())
      {
        String value = mConfigMapping.get (key);
        Element option = document.createElement ("option");
        option.setAttribute ("key", key);
        option.setAttribute ("value", value);
        root.appendChild (option);
      }

      // save the names of the vocabulary files that are currently open
      Element vocFiles = document.createElement ("vocfiles");
      for (String filePath : mOpenVocabularyFiles)
      {
        Element fileElement = document.createElement ("editedvocfile");
        fileElement.setAttribute ("path", filePath);
        vocFiles.appendChild (fileElement);
      }
      root.appendChild (vocFiles);

      Transformer transformer = TransformerFactory.newInstance ().newTransformer ();
      transformer.setOutputProperty (OutputKeys.INDENT, "YES");
      DOMSource source = new DOMSource (document);
      FileOutputStream os = new FileOutputStream (new File (getBaseDir (), USER_PROPS_FILENAME));
      StreamResult result = new StreamResult (os);
      transformer.transform (source, result);
    } catch (ParserConfigurationException e1)
    {
      // TODO: error handling
      e1.printStackTrace ();
    } catch (TransformerConfigurationException e)
    {
      // TODO: error handling
      e.printStackTrace ();
    } catch (TransformerFactoryConfigurationError e)
    {
      // TODO: error handling
      e.printStackTrace ();
    } catch (FileNotFoundException e)
    {
      // TODO: error handling
      e.printStackTrace ();
    } catch (TransformerException e)
    {
      // TODO: error handling
      e.printStackTrace ();
    }
  }

  private int getInteger (String forKey)
  {
    try
    {
      return Integer.valueOf (mConfigMapping.get (forKey));
    } catch (NumberFormatException nfExc)
    {
      // TODO: error handling
      nfExc.printStackTrace ();
      return Integer.MIN_VALUE;
    }
  }

  private float getFloat (String forKey)
  {
    try
    {
      return Float.valueOf (mConfigMapping.get (forKey));
    } catch (NumberFormatException nfExc)
    {
      // TODO: error handling
      nfExc.printStackTrace ();
      return Float.NaN;
    }
  }

  private void loadFromFile ()
  {
//    mOpenVocabularyFiles.clear ();
//
//    Reader source = null;
//    File inputFile = new File (getBaseDir (), USER_PROPS_FILENAME);
//    if (!inputFile.canRead ())
//    {
//      try
//      {
//        source = new InputStreamReader (ResourceManager.getInstance ().getResource (DEFAULT_PROPS_FILENAME));
//      } catch (IOException e)
//      {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    } else
//      try
//      {
//        source = new FileReader (inputFile);
//      } catch (FileNotFoundException e)
//      {
//        e.printStackTrace (); /* TODO: error handling */
//      }
//    StAXDocumentRoot document = new StAXDocumentRoot (source);
//    document.assignHandler ("option", new OptionsParser ());
//    document.assignHandler ("editedvocfile", new OpenVocabularyFilesParser ());
//    try
//    {
//      document.parse ();
//    } catch (XMLStreamException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace ();
//    } catch (FactoryConfigurationError e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace ();
//    } catch (IOException e)
//    {
//      e.printStackTrace();
//    }
  }

//  private class OptionsParser extends DefaultStAXDocumentFragment
//  {
//    @Override
//    protected void processElement (DocumentFragmentIterator lockedIterator)
//    {
//      if (lockedIterator.isStartElement ()
//          && DocumentFragmentIterator.assembleNamespacePrefixedElementName (lockedIterator).equals ("option"))
//      {
//        mConfigMapping.put (lockedIterator.getAttributeByName ("key"), lockedIterator.getAttributeByName ("value"));
//      }
//    }
//  }
//
//  private class OpenVocabularyFilesParser extends DefaultStAXDocumentFragment
//  {
//    @Override
//    protected void processElement (DocumentFragmentIterator lockedIterator)
//    {
//      if (lockedIterator.isStartElement ()
//          && DocumentFragmentIterator.assembleNamespacePrefixedElementName (lockedIterator).equals ("editedvocfile"))
//      {
//        mOpenVocabularyFiles.add (lockedIterator.getAttributeByName ("path"));
//      }
//
//    }
//  }
}
