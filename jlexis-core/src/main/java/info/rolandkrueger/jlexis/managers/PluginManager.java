/*
 * PluginLoader.java
 * Created on 30.01.2007
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
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.plugin.DBO_LanguagePlugin;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.jlexis.plugin.nativelanguage.NativeLanguagePlugin;
import info.rolandkrueger.roklib.util.ApplicationError;
import info.rolandkrueger.roklib.util.ApplicationError.ErrorType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * {@link PluginManager} is responsible for loading all language plugin files which are found in
 * the plugin directory. The main method of this class is {@link PluginManager#loadPlugins()}. 
 * This method loads the plugins and provides a list of {@link LanguagePlugin} objects containing
 * all plugins that have been successfully loaded.
 * 
 * @author Roland Krueger
 */
public class PluginManager
{
  public final static String VERSION_KEY     = "Version";
  public final static String PLUGIN_KEY      = "PluginMainClass";
  public final static String NAME_KEY        = "PluginName";
  public final static String DESCRIPTION_KEY = "Description";
  public final static String IDENTIFIER_KEY  = "PluginIdentifier";
  private JLexisMain mMain;
  
  private HashMap<String, LanguagePlugin> mPlugins;
  private LanguagePlugin                  mDefaultPlugin;
  private LanguagePlugin                  mNativeLanguagePlugin;  
  
  public PluginManager (JLexisMain main)
  {
    assert main != null;
    mMain = main;
    mPlugins = new HashMap<String, LanguagePlugin> ();
    mNativeLanguagePlugin = new NativeLanguagePlugin ();
  }
  
  /**
   * <p>
   * Loads all language plugins that can be found in the plugin folder. The path to this folder can
   * be configured by the user. The current configuration is obtained from the
   * {@link ConfigurationManager}.
   * </p>
   * <p>
   * The loaded plugin objects are stored in an internal hash map and can be queried with {@link
   * PluginManager#getLoadedPlugins()} and {@link PluginManager#getPluginFor(String)}. 
   * </p>
   */
  public void loadPlugins ()
  {
    mPlugins.clear ();
    // add the native language plugin
    mPlugins.put (mNativeLanguagePlugin.getIdentifier (), mNativeLanguagePlugin);

    File pathToPluginDir = ConfigurationManager.getInstance ().getWorkspace ().getPluginDirectory ();
    if (pathToPluginDir == null)
    {
      JLexisMain.getInstance ().reportError (new ApplicationError (String.format ("Plugin directory not defined."),
          ErrorType.WARNING));
      return;
    }
    // TODO: use logger
    System.out.println ((String.format ("Loading plugins from directory %s.", 
        pathToPluginDir.getAbsolutePath ())));

    // the absolute path to the plugin directory is now stored in 'pathToPluginDir'
    File[] files = pathToPluginDir.listFiles ();
    if (files == null)
    {
      // the plugin directory as configured in the user preferences could not be found. Ask the
      // user to provide a valid path to such a directory.

      mMain.fatalError (String.format ("Unable to find plugin directory: %s", 
          pathToPluginDir.getAbsolutePath ()));
    }

    for (File file : files)
    {
      if (file.getAbsolutePath ().endsWith (".jar"))
      {
        LanguagePlugin plugin = loadPlugin (file);
        if (plugin != null) 
        {
          if (pluginAlreadyLoaded (plugin))
          {
            // TODO: better error handling
            mMain.addLogMessage ("Plugin " + plugin.getIdentifier () + " already loaded. Skipping...");
          } else
          {
            plugin.registerUserInputIdentifiers ();
            mPlugins.put (plugin.getIdentifier (), plugin);
            if (plugin.isDefaultPlugin ()) mDefaultPlugin = plugin;
          }
        }
      }
    }
  }
  
  public LanguagePlugin getPluginFor (Language language)
  {
    LanguagePlugin result = null;
    
    for (LanguagePlugin plugin : mPlugins.values ())
    {
      if (plugin.getIdentifier ().equals (language.getPluginIdentifier ()))
      {
        if (result != null && plugin.getVersionNumber () < result.getVersionNumber ())
          continue;

        result = plugin;
      }
    }
    return result;
  }
  
  private boolean pluginAlreadyLoaded (LanguagePlugin plugin)
  {
    for (LanguagePlugin loadedPlugin : mPlugins.values ())
    {
      if (loadedPlugin.equals (plugin))
        return true;
    }
    return false;
  }

  private LanguagePlugin loadPlugin (File file)
  {
    JarInputStream jarInputStream;
    try
    {
      jarInputStream = new JarInputStream (new FileInputStream (file));
    } catch (FileNotFoundException e)
    {
      log (String.format ("Unable to load plugin file %s: file not found.", file.getAbsolutePath ()));
      return null;
    } catch (IOException e)
    {
      log (String.format ("Unable to load plugin file %s: I/O error.", file.getAbsolutePath ()));
      return null;
    }
    
    String pluginName = "[n/a]", version = "[n/a]", mainClass = null, description = "", identifier = null;
    
    Manifest manifest = jarInputStream.getManifest ();
    if (manifest == null)
    {
      log (String.format ("Unable to load plugin file %s: manifest file not found in plugin file.", file.getAbsolutePath ()));
      return null;
    }
    
    Map<String, Attributes> entries = manifest.getEntries ();
    for (String key : entries.keySet ())
    {
      // TODO check if all values are present
      Attributes attributes = entries.get (key);
      pluginName  = attributes.getValue (NAME_KEY);
      version     = attributes.getValue (VERSION_KEY);
      mainClass   = attributes.getValue (PLUGIN_KEY);
      description = attributes.getValue (DESCRIPTION_KEY);
      identifier  = attributes.getValue (IDENTIFIER_KEY);
      if (mainClass != null) break;
    }
    if (mainClass == null) 
    {
      log (String.format ("Unable to load plugin file %s: unable to find main class in plugin manifest file.", 
          file.getAbsolutePath ()));
      return null;
    }
    
    URL url[] = new URL[1];
    try
    {
      url[0] = file.toURL ();
    } catch (MalformedURLException e)
    {
      log (String.format ("Unable to load plugin file %s: unable to obtain URL to plugin file.", 
          file.getAbsolutePath ()));
      return null;
    }
    
    URLClassLoader classLoader = new URLClassLoader (url);
    Class<?>       pluginClass;
    LanguagePlugin plugin;
    try
    {
      pluginClass = classLoader.loadClass (mainClass);
      plugin      = (LanguagePlugin) pluginClass.newInstance ();
    } catch (ClassNotFoundException cnfExc)
    {
      log (String.format ("Unable to load plugin file %s: class instantiation failed: class not found.", 
          file.getAbsolutePath ()));
      return null;
    } catch (IllegalAccessException iaExc)
    {
      log (String.format ("Unable to load plugin file %s: class instantiation failed: access violation.", 
          file.getAbsolutePath ()));
      return null;
    } catch (InstantiationException iExc)
    {
      log (String.format ("Unable to load plugin file %s: class instantiation failed.", 
          file.getAbsolutePath ()));
      return null;
    }
    
    plugin.setDescription   (description);
    plugin.setPluginName    (pluginName);
    plugin.setVersionString (version);
    plugin.setIdentifier    (identifier);
    log (String.format ("Successfully loaded language plugin with the name '%s'.", pluginName));
    
    return plugin;
  }
  
  private void log (String msg)
  {
    mMain.addLogMessage (String.format ("%s %s", "[Plugin loader]", msg));
  }
  
  /**
   * Provides the {@link LanguagePlugin} object which is identified by the given identifier.
   * 
   * @param pluginIdentifier
   *          an identifier that identifies the required language plugin
   * @return a {@link LanguagePlugin} with the specified identifier
   * @throws IllegalArgumentException if no such plugin is available
   */
  public LanguagePlugin getPluginFor (String pluginIdentifier)
  {
    LanguagePlugin result = mPlugins.get (pluginIdentifier);
    if (result == null)
      throw new IllegalArgumentException (String.format ("LanguagePlugin with identifier %s is unknown."));

    return result;
  }
  
  /**
   * Provides a {@link Collection} of all {@link LanguagePlugin}s that have successfully been
   * loaded.
   * 
   * @return a list of the loaded language plugins
   */
  public Collection<LanguagePlugin> getLoadedPlugins ()
  {
    return new ArrayList<LanguagePlugin> (mPlugins.values ());
  }
  
  /**
   * Checks if a {@link LanguagePlugin} with the given identifier has been loaded and is available
   * via {@link PluginManager#getLoadedPlugins()}
   * 
   * @param pluginIdentifier
   *          an identifier for a {@link LanguagePlugin}
   * @return true if such a plugin is available
   */
  public boolean isPluginAvailable (String pluginIdentifier)
  {
    return mPlugins.containsKey (pluginIdentifier);
  }
  
  /**
   * Persists the information of all currently loaded plugins into the database.
   */
  @SuppressWarnings("unchecked")
  public void saveLanguagePluginInfosToDB ()
  {
    Session session = JLexisPersistenceManager.getInstance ().getSession ();
    Transaction tx = session.beginTransaction ();
    
    Query q = JLexisPersistenceManager.getInstance ().getSession ().createQuery ("FROM DBO_LanguagePlugin");
    List<DBO_LanguagePlugin> pluginDBOs = q.list ();
    for (DBO_LanguagePlugin p : pluginDBOs)
    {
      session.delete (p);
    }
    
    for (LanguagePlugin plugin : getLoadedPlugins ())
    {
      session.saveOrUpdate (plugin.getDatabaseObject ());
    }
    
    tx.commit ();
  }

  /**
   * Provides the {@link LanguagePlugin} which allows the user to edit her own native language. 
   * Such a plugin does for example provide no way to enter phonetics or example sentences. 
   * 
   * @return a {@link LanguagePlugin} for editing the user's mother tongue
   */
  public LanguagePlugin getNativeLanguagePlugin ()
  {
    return mNativeLanguagePlugin;
  }

  public LanguagePlugin getDefaultPlugin ()
  {
    return mDefaultPlugin;
  }
}
