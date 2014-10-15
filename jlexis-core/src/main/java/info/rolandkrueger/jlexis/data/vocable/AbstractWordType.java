/*
 * AbstractWordType.java
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
package info.rolandkrueger.jlexis.data.vocable;

import info.rolandkrueger.jlexis.gui.containers.AbstractVocableInputPanel;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

public abstract class AbstractWordType
{
  private String            mName;
  private String            mIdentifier;
  private AbstractUserInput mUserInputPrototype;
  
  protected AbstractWordType (String name, String identifier, AbstractUserInput userInputForThisWordType)
  {
    if (identifier == null || identifier.equals (""))
      throw new IllegalArgumentException ("Identifier must not be null or the empty string.");
    CheckForNull.check (name, userInputForThisWordType);
    
    mName               = name;
    mIdentifier         = identifier;
    mUserInputPrototype = userInputForThisWordType;
  }
  
  public abstract AbstractVocableInputPanel getInputPanel ();
  public abstract WordTypeEnum getWordTypeEnum ();
  
  public final AbstractUserInput createNewUserInputObject ()
  {
    AbstractUserInput result = mUserInputPrototype.createNewUserInputObject ();
    result.init ();
    return result;
  }
  
  public final String getUserInputIdentifier ()
  {
    return mUserInputPrototype.getUserInputIdentifier ();
  }
  
  public final String getName ()
  {
    return mName;
  }
  
  public final String getIdentifier ()
  {
    return mIdentifier;
  }
  
  public void registerUserInputIdentifiers ()
  {
    mUserInputPrototype.registerUserInputIdentifiers ();
  }
  
  @Override
  public int hashCode ()
  {
    return mIdentifier.hashCode ();
  }
  
  @Override
  public boolean equals (Object obj)
  {
    if (obj == null) return false;
    if (obj == this) return true;
    if (obj instanceof AbstractWordType)
    {
      AbstractWordType other = (AbstractWordType) obj;
      return mIdentifier.equals (other.mIdentifier);
    }
    return false;
  }
}
