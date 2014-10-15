/*
 * I18NResources.java
 * Created on 02.02.2007
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
package info.rolandkrueger.jlexis.data;

import info.rolandkrueger.jlexis.JLexisMain;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class I18NResources
{
  private final static I18NResources sInstance = new I18NResources ();
  private final static String RESOURCE_BASE_NAME = "data.i18nResources";
  
  // keys
  public enum I18NResourceKeys {
                               BROWSE_KEY,
                               COMMENT_KEY, 
                               EXAMPLE_KEY, 
                               FILE_MENU_KEY,
                               OPEN_FILE_KEY,
                               EDIT_FILE_KEY,
                               UNDO_DEFAULT_KEY,
                               NEW_FILE_KEY,
                               NEW_FILE_LABEL_KEY,
                               TERM_KEY, 
                               PHONETICS_KEY,
                               QUIT_PROGRAM_KEY,
                               OK_BTN,
                               CANCEL_BTN,
                               SELECT_NATIVE_LANGUAGE,
                               SELECT_FOREIGN_LANGUAGE,
                               SELECTED_LANGUAGE,
                               FILE_NAME_LABEL,
                               NEW_FILE_DIALOG_TITLE,
                               ERROR_MSG_SELECT_LANGUAGE_TITLE,
                               ERROR_MSG_SELECT_LANGUAGE_TEXT,
                               ERROR_MSG_INVALID_FILENAME_TITLE,
                               ERROR_MSG_INVALID_FILENAME_TEXT,
                               WARNING_MSG_OVERWRITE_FILE_TITLE,
                               WARNING_MSG_OVERWRITE_FILE_TEXT,
                               DELETE_LEARNING_UNIT_KEY
                               };
  
  private boolean        mIsInitialized = false;
  private JLexisMain      mMain;
  private ResourceBundle mResourceBundle;
  
  private I18NResources () {}
  
  public static I18NResources getInstance ()
  { 
    return sInstance;    
  }
  
  public void init (JLexisMain main, Locale locale)
  {
    mMain = main;
    try 
    {
      mResourceBundle = ResourceBundle.getBundle (RESOURCE_BASE_NAME, locale);
    } catch (MissingResourceException mrExc) {
      // TODO: this unrecoverable error condition must be handled by stopping the application
      log ("Unable to load resource bundles for internationalization!");
      return;
    }
  
    mIsInitialized = true;
  }
  
  public ResourceBundle getResourceBundle ()
  {
    if ( ! mIsInitialized) throw new IllegalStateException ("This class hasn't been initialized yet. Call " +
        I18NResources.class.getName () + ".init() first.");
    assert mResourceBundle != null;
    return mResourceBundle;
  }
  
  private void log (String msg)
  {
    mMain.addLogMessage (String.format ("[I18N resources] %s", msg));
  }
  
  public static String getString (I18NResourceKeys key)
  {
    if ( ! sInstance.mIsInitialized) 
      throw new IllegalStateException ("This class hasn't been initialized yet. Call " +
        I18NResources.class.getName () + ".init() first.");
    assert sInstance.mResourceBundle != null;
    
    return sInstance.mResourceBundle.getString (key.toString ());
  }
}
