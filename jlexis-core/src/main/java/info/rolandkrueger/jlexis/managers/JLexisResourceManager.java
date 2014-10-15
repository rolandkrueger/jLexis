/*
 * ResourceManager.java
 * Created on 15.02.2007
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
import info.rolandkrueger.roklib.util.helper.CheckForNull;
import info.rolandkrueger.roklib.util.resources.ResourceHandle;
import info.rolandkrueger.roklib.util.resources.ResourceHandlingManager;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.MissingResourceException;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

public class JLexisResourceManager
{
  private final static JLexisResourceManager sInstance = new JLexisResourceManager ();
  
  private static HashMap<String, ImageIcon> sIcons;
  
  public enum ResourcesEnums {QUIT_ICON, 
                              NEW_FILE_ICON, 
                              OPEN_LEARNING_UNIT_ICON,
                              EDIT_FILE_ICON,
                              UNDO_ICON,
                              SWEDISH_FLAG,
                              EMPTY, 
                              DELETE_UNIT_ICON,
                              REMOVE_SELECTED_VOCABLE_ICON,
                              EDIT_ICON,
                              BRICKS_ICON,
                              ADD_USER_PROFILE_ICON,
                              SELECT_USER_PROFILE_ICON,
                              LEXIS_LOGO};
                              
  private final static String FONT_FILENAME          = "DejaVuSerif.ttf";
  private final static String DEFAULT_PROPS_FILENAME = "Lexis.properties.xml";
  
  private static ResourceHandle sFontResourceHandle; 
  private static ResourceHandle sDefaultPropertiesResourceHandle; 
  private boolean               mImagesLoaded = false;
  
  static
  {
    // register all needed resources with the resource manager
    ResourceHandlingManager resMgr = ResourceHandlingManager.instance ();
    resMgr.setResourceLocation ("data");
    sIcons = new HashMap<String, ImageIcon> ();
    ResourceHandle handle;
    try
    {
      Toolkit toolkit = new JWindow ().getToolkit ();
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/door_in.png");      
      if (handle != null) sIcons.put (ResourcesEnums.QUIT_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/book_add.png");
      if (handle != null) sIcons.put (ResourcesEnums.NEW_FILE_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/book_open.png");
      if (handle != null) sIcons.put (ResourcesEnums.OPEN_LEARNING_UNIT_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/book_edit.png");
      if (handle != null) sIcons.put (ResourcesEnums.EDIT_FILE_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/sweden.png");
      if (handle != null) sIcons.put (ResourcesEnums.SWEDISH_FLAG.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/empty.png");
      if (handle != null) sIcons.put (ResourcesEnums.EMPTY.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/undo.png");
      if (handle != null) sIcons.put (ResourcesEnums.UNDO_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/delete.png");
      if (handle != null) sIcons.put (ResourcesEnums.DELETE_UNIT_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/pencil_delete.png");
      if (handle != null) sIcons.put (ResourcesEnums.REMOVE_SELECTED_VOCABLE_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/pencil.png");
      if (handle != null) sIcons.put (ResourcesEnums.EDIT_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/bricks.png");
      if (handle != null) sIcons.put (ResourcesEnums.BRICKS_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/user_add.png");
      if (handle != null) sIcons.put (ResourcesEnums.ADD_USER_PROFILE_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/user.png");
      if (handle != null) sIcons.put (ResourcesEnums.SELECT_USER_PROFILE_ICON.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      handle = resMgr.registerResource (JLexisResourceManager.class, "icons/Lexis-logo.png");
      if (handle != null) sIcons.put (ResourcesEnums.LEXIS_LOGO.toString (), new ImageIcon (toolkit.createImage (
          resMgr.getResourceURL (handle))));
      
      sFontResourceHandle = resMgr.registerResource (JLexisResourceManager.class, FONT_FILENAME);
      sDefaultPropertiesResourceHandle =resMgr.registerResource (JLexisResourceManager.class, DEFAULT_PROPS_FILENAME);
    } catch (IOException ioExc)
    {
      // TODO Auto-generated catch block
      ioExc.printStackTrace ();
    }
  }
    
  private JLexisResourceManager () 
  {    
  }
  
  public synchronized static JLexisResourceManager getInstance ()
  {
    return sInstance;
  }
  
  public InputStream getFontInputStream () throws IOException
  {
    return ResourceHandlingManager.instance ().getResourceData (sFontResourceHandle);
  }
  
  public InputStream getDefaultPropertiesFileInputStream () throws IOException
  {
    return ResourceHandlingManager.instance ().getResourceData (sDefaultPropertiesResourceHandle);  
  }
  
  public void initialize ()
  {
    ResourceHandlingManager resMgr = ResourceHandlingManager.instance ();
    if (resMgr.areResourcesMissing ())
      JLexisMain.getInstance ().fatalError ("There are some resources missing: " + 
          resMgr.getMissingResourcesAsString ());
    
    mImagesLoaded = true;
  }
  
  public String registerIconResource (Class<?> clazz, String iconPath) throws IOException
  {
    CheckForNull.check (clazz, iconPath);
    String iconIdentifier = clazz.getCanonicalName () + iconPath;
    if (sIcons.containsKey (iconIdentifier))
      throw new IllegalArgumentException ("This icon resource has already been registered for the given class.");
    ResourceHandle handle = ResourceHandlingManager.instance ().registerResource (clazz, iconPath);
    if (ResourceHandlingManager.instance ().areResourcesMissing ())
      throw new IllegalStateException (ResourceHandlingManager.instance ().getMissingResourcesAsString ());
    sIcons.put (iconIdentifier, new ImageIcon (new JWindow ().getToolkit ().createImage (
        ResourceHandlingManager.instance ().getResourceURL (handle))));
    return iconIdentifier;
  }
  
  public ImageIcon getIcon (String iconIdentifier)
  {
    CheckForNull.check (iconIdentifier);

    if ( ! mImagesLoaded) initialize ();
    
    ImageIcon result = sIcons.get (iconIdentifier.toString ());
    
    if (result == null) throw new MissingResourceException (
        String.format ("Unable to provide icon resource with key '%s'. Resource was not loaded." +
        		" Register icon resource with registerIconResource() first.", iconIdentifier.toString ()), 
        		ImageIcon.class.getName (), iconIdentifier.toString ());
    
    return result;
  }
  
  public ImageIcon getIcon (ResourcesEnums resourceName)
  {
    return getIcon (resourceName.toString ());
  }
  
}
