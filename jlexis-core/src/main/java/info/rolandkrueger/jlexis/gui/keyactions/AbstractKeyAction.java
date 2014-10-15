/*
 * AbstractKeyAction.java
 * Created on 26.04.2007
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
package info.rolandkrueger.jlexis.gui.keyactions;

import info.rolandkrueger.jlexis.gui.actions.JLexisAbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public abstract class AbstractKeyAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = 3800672343589331812L;
  private String          mIdentifier;
  private int             mKeyModifier;
  private int             mKeyID;
  
  public AbstractKeyAction (String name, Icon icon, 
      String identifier, int keyModifier, int keyID)
  {
    super (name, icon);
    init (identifier, keyModifier, keyID);
  }
  
  public AbstractKeyAction (String name, 
      String identifier, int keyModifier, int keyID)  
  {
    super (name);
    init (identifier, keyModifier, keyID);
  }
  
  public AbstractKeyAction (String identifier, int keyModifier, int keyID)  
  {
    super ("");
    init (identifier, keyModifier, keyID);
  }
  
  public AbstractKeyAction (String identifier)
  {
    this (identifier, identifier, -1, -1);
  }
  
  private void init (String identifier, int keyModifier, int keyID)
  {
    mIdentifier  = identifier;
    mKeyModifier = keyModifier;
    mKeyID       = keyID;
  }
  
  public void actionPerformed ()
  {
    actionPerformed (new ActionEvent (this, 0, ""));
  }
  
  public String getActionIdentifier ()
  {
    return mIdentifier;
  }

  public int getKeyID ()
  {
    return mKeyID;
  }

  public void setKeyID (int keyID)
  {
    mKeyID = keyID;
  }

  public int getKeyModifier ()
  {
    return mKeyModifier;
  }

  public void setKeyModifier (int keyModifier)
  {
    mKeyModifier = keyModifier;
  }
}
