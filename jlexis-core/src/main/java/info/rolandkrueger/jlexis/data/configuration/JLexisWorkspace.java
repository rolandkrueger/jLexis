/*
 * $Id: LexisWorkspace.java 205 2009-12-17 17:25:22Z roland $
 * Created on 04.01.2008
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
package info.rolandkrueger.jlexis.data.configuration;

import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.JFrame;

public class JLexisWorkspace
{
  private static final String DEFAULT_ROOT_REPLACEMENT      = "--";
  private static final String DEFAULT_ROOT_MARKER           = "|";
  private static final int    DEFAULT_MAX_UNDOS             = 50;
  private final static File   DEFAULT_DATABASE_FILE         = new File ("database/defaultDB");
  private static final File   DEFAULT_PLUGIN_DIRECTORY      = new File ("plugins");
  private static final float  DEFAULT_APPLICATION_FONT_SIZE = 13.0f;
  private static final int    DEFAULT_MAIN_WINDOW_WIDTH     = 800;
  private static final int    DEFAULT_MAIN_WINDOW_HEIGHT    = 600;

  private String mRootMarker;
  private String mRootReplacement;
  private int    mMaxUndos;
  private String mNativeLanguageName;
  private File   mPluginDirectory;
  private File   mDatabaseFile;
  private float  mApplicationFontSize;
  private JFrame mMainWindow;
 
  public JLexisWorkspace ()
  {
    mRootReplacement     = DEFAULT_ROOT_REPLACEMENT;
    mRootMarker          = DEFAULT_ROOT_MARKER;
    mMaxUndos            = DEFAULT_MAX_UNDOS;
    mNativeLanguageName  = "";
    mPluginDirectory     = DEFAULT_PLUGIN_DIRECTORY;
    mDatabaseFile        = DEFAULT_DATABASE_FILE;
    mApplicationFontSize = DEFAULT_APPLICATION_FONT_SIZE;
    mMainWindow = new JFrame ();
    mMainWindow.setBounds (new Rectangle (0, 0, 
        DEFAULT_MAIN_WINDOW_WIDTH, DEFAULT_MAIN_WINDOW_HEIGHT));
  }
  
  public File getPluginDirectory ()
  {
    return mPluginDirectory;
  }
  
  public String getRootReplacement ()
  {
    return mRootReplacement;
  }
    
  public File getDatabaseFile ()
  {
    return mDatabaseFile;
  }
  
  public void setDatabaseFile (File databaseFile)
  {
    CheckForNull.check (databaseFile);
  }
  
  public void setRootReplacement (String rootReplacement)
  {
    CheckForNull.check (rootReplacement);
    mRootReplacement= rootReplacement;
  }
  
  public String getRootMarker ()
  {
    return mRootMarker;
  }
  
  public void setRootMarker (String rootMarker)
  {
    CheckForNull.check (rootMarker);
    mRootMarker = rootMarker;
  }
  
  public void setMaxUndos (int maxUndos)
  {
    if (maxUndos < 0) throw new IllegalArgumentException ("must not be < 0");
    mMaxUndos = maxUndos;
  }
  
  public int getMaxUndos ()
  {
    return mMaxUndos;
  }
  
  public void setApplicationFontSize (float size)
  {
    if (size < 0.0f) throw new IllegalArgumentException ("application font size must not be < 0");
    mApplicationFontSize = size;
  }
  
  public float getApplicationFontSize ()
  {
    return mApplicationFontSize;
  }
  
  public void setMainWindow (JFrame mainWindow)
  {
    CheckForNull.check (mainWindow);
    mMainWindow = mainWindow;
  }
  
  public JFrame getMainWindow ()
  {
    return mMainWindow;
  }
  
  public void configureMainWindow (Component mainWindow)
  {
    mainWindow.setBounds (mMainWindow.getBounds ());
  }
  
  public String getNativeLanguage ()
  {
    return mNativeLanguageName;
  }
  
  public void setNativeLanguage (String nativeLanguage)
  {
    CheckForNull.check (nativeLanguage);
    mNativeLanguageName = nativeLanguage;
  }
}
