/*
 * $Id: LanguageFactory.java 69 2009-03-05 19:44:11Z roland $
 * Created on 05.03.2009
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
package info.rolandkrueger.jlexis.data.languages;

import info.rolandkrueger.jlexis.managers.JLexisPersistenceManager;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public class LanguageFactory
{
  private static LanguageFactory sInstance;
  private Map<String, Language> mAvailableLanguages;
  
  @SuppressWarnings("unchecked")
  public static LanguageFactory getInstance ()
  {
    if (sInstance == null)
    {
      sInstance = new LanguageFactory ();
      Session session = JLexisPersistenceManager.getInstance ().getSession ();
      List result = session.createQuery ("from Language l").list ();
      sInstance.mAvailableLanguages = new HashMap<String, Language> ();
      for (Object obj : result)
      {
        Language lang = (Language) obj;
        sInstance.mAvailableLanguages.put (lang.getLanguageName (), lang);
      }
    }
    
    return sInstance;
  }
  
  public Language getLanguageFor (String languageName)
  {
    return mAvailableLanguages.get (languageName);
  }
  
  public Language getLanguageFor (LanguagePlugin plugin)
  {
    for (Language language : mAvailableLanguages.values ())
    {
      if (language.getPluginIdentifier ().equals (plugin.getIdentifier ()) &&
          language.getPluginVersion ().equals (plugin.getVersionNumber ()))
      {
        return language;
      }
    }
    
    return new Language (plugin);
  }
}
