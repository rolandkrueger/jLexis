/*
 * LanguagePlugin.java
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
package info.rolandkrueger.jlexis.plugin;

import info.rolandkrueger.jlexis.data.DefaultWordType;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

public abstract class LanguagePlugin
{
  private   Map<String, AbstractWordType> mWordTypes;
  protected Locale  mLocale;
  private   String  mPluginName;
  private   String  mVersionString;
  private   int     mVersionNumber;
  private   String  mDescription;
  private   String  mIdentifier;
  private   String  mProviderURL;
  private   Integer mHashCode;
  
  public LanguagePlugin () 
  {
    mProviderURL = "";
  }
  
  public    abstract String getLanguageName ();
  public    abstract Icon   getIcon         ();
  protected abstract AbstractWordType getCorrespondingWordTypeForImpl (AbstractWordType wordType);
  public    abstract AbstractWordType getDefaultWordType ();
  public    abstract List<AbstractQuizType> getQuizTypes ();
  public    abstract List<Set<String>> getAbbreviationAlternatives ();
  
  /**
   * A default language plugin can be used for every language. It has no language specific features
   * and is thus less flexible when it comes to handling the characteristics of a language. Since a
   * default language plugin is independent of a specific language, no language name can be assigned
   * to the plugin. Therefore, if the default plugin is used by the user, the desired language name
   * is queried from her by jLexis.
   * 
   * @return <code>true</code> if this plugin is a default plugin
   */
  public abstract boolean isDefaultPlugin ();
  
  public void setLocale (Locale locale) 
  {
    mLocale = locale;
  }
  
  protected void registerWordType (AbstractWordType wordType)
  {
    // lazy instantiation of the word types list
    if (mWordTypes == null)
    {
      mWordTypes = new HashMap<String, AbstractWordType> ();
    }
    AbstractWordType testIfAlreadyRegistered = mWordTypes.get (wordType.getIdentifier ());
    if (testIfAlreadyRegistered != null)
      throw new IllegalArgumentException ("This word type has already been registered.");
    
    mWordTypes.put (wordType.getIdentifier (), wordType);
  }
  
  public void registerUserInputIdentifiers ()
  {
    if (mWordTypes == null) return;
    for (AbstractWordType wordType : mWordTypes.values ())
    {
      wordType.registerUserInputIdentifiers ();
    }
  }
  
  public Collection<AbstractWordType> getWordTypes ()
  {
    // if no word types have been registered for this plugin by a subclass, add at least
    // the default word type
    if (mWordTypes == null || mWordTypes.size () == 0)
    {
      mWordTypes = new HashMap<String, AbstractWordType> ();
      // TODO: I18N
      AbstractWordType defaultWordType = new DefaultWordType ("allg. Wort"); 
      mWordTypes.put (defaultWordType.getIdentifier (), defaultWordType);      
    }
    return mWordTypes.values ();
  }
  
  public AbstractWordType getWordTypeFor (String identifier)
  {
    return mWordTypes.get (identifier);
  }

  public String getDescription ()
  {
    return mDescription;
  }

  public void setDescription (String description)
  {
    mDescription = description;
  }

  public String getPluginName ()
  {
    return mPluginName;
  }

  public void setPluginName (String pluginName)
  {
    mPluginName = pluginName;
  }

  public String getVersionString ()
  {
    if (mVersionString == null || mVersionString.equals (""))
      return String.valueOf (mVersionNumber);
    return mVersionString;
  }

  public void setVersionString (String version)
  {
    mVersionString = version;
  }
  
  public String toString ()
  {
    return getLanguageName ();
  }

  public AbstractWordType getCorrespondingWordTypeFor (AbstractWordType wordType)
  {
    AbstractWordType result = getCorrespondingWordTypeForImpl (wordType);
    if (result == null)
    {
      return getDefaultWordType ();
    }
    return result;
  }

  public String getIdentifier ()
  {
    if (mIdentifier == null)
      throw new IllegalStateException ("Identifier for LanguagePlugin hasn't been set yet.");
    return mIdentifier;
  }

  public void setIdentifier (String identifier)
  {
    mHashCode = null;
    mIdentifier = identifier;
  }

  public int getVersionNumber ()
  {
    mHashCode = null;
    return mVersionNumber;
  }

  public void setVersionNumber (int versionNumber)
  {
    mVersionNumber = versionNumber;
  }

  /**
   * Returns the URL of the plugin's provider. This is the person or organization that maintains and
   * releases the language plugin.
   * 
   * @return The URL of the plugins originator.
   */
  public String getProviderURL ()
  {
    return mProviderURL;
  }

  /**
   * Sets the URL of the plugin's provider.
   * 
   * @param providerURL a URL string
   */
  public void setProviderURL (String providerURL)
  {
    if (providerURL == null) throw new NullPointerException ("Provider URL is null.");
    mProviderURL = providerURL;
  }
  
  public DBO_LanguagePlugin getDatabaseObject ()
  {
    return new DBO_LanguagePlugin (getProviderURL (),
                                   getPluginName (), 
                                   getVersionString (), 
                                   getVersionNumber (), 
                                   getDescription (), 
                                   getIdentifier ());
  }
  
  @Override
  public final int hashCode ()
  {
    if (mHashCode == null)
    {
      mHashCode = new String (getIdentifier () + getVersionNumber ()).hashCode (); 
    }
    return mHashCode;
  }
  
  @Override
  public final boolean equals (Object obj)
  {
    if (obj == this) return true;
    if ( ! (obj instanceof LanguagePlugin))
      return false;
    LanguagePlugin other = (LanguagePlugin) obj;
    if (other.mIdentifier.equals (mIdentifier) && other.mVersionNumber == mVersionNumber)
      return true;
    
    return false;
  }
}
