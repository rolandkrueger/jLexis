/*
 * $Id: DBO_LanguagePlugin.java 41 2008-11-19 07:14:46Z roland $
 * Created on 15.11.2008
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
package info.rolandkrueger.jlexis.plugin;

public class DBO_LanguagePlugin
{
  private String mProviderURL;
  private String mPluginName;
  private String mVersionString;
  private int    mVersionNumber;
  private String mDescription;
  private String mIdentifier;
  private long   mId;
  
  public DBO_LanguagePlugin () {}
  
  public DBO_LanguagePlugin (String providerURL,
                             String pluginName,
                             String versionString,
                             int versionNumber,
                             String description,
                             String identifier)
  {
    mProviderURL   = providerURL;
    mPluginName    = pluginName;
    mVersionString = versionString;
    mVersionNumber = versionNumber;
    mDescription   = description;
    mIdentifier    = identifier;
  }
  
  public String getProviderURL ()
  {
    return mProviderURL;
  }
  
  public void setProviderURL (String providerURL)
  {
    mProviderURL = providerURL;
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
    return mVersionString;
  }
  
  public void setVersionString (String versionString)
  {
    mVersionString = versionString;
  }
  
  public int getVersionNumber ()
  {
    return mVersionNumber;
  }
  
  public void setVersionNumber (int versionNumber)
  {
    mVersionNumber = versionNumber;
  }
  
  public String getDescription ()
  {
    return mDescription;
  }
  
  public void setDescription (String description)
  {
    mDescription = description;
  }
  
  public String getIdentifier ()
  {
    return mIdentifier;
  }
  
  public void setIdentifier (String identifier)
  {
    mIdentifier = identifier;
  }
  
  @SuppressWarnings (value="all")
  private long getId ()
  {
    return mId;
  }

  @SuppressWarnings (value="all")
  private void setId (long id)
  {
    mId = id;
  }
  
  @Override
  public String toString ()
  {
    StringBuilder b = new StringBuilder ();
    b.append (DBO_LanguagePlugin.class.getName ()).append (": ");
    b.append (getPluginName ()).append (";");
    b.append (getIdentifier ()).append (";");
    b.append (getProviderURL ()).append (";");
    b.append (getVersionString ()).append (";");
    b.append (getVersionNumber ()).append (";");
    return b.toString ();
  }
}
