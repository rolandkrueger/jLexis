/*
 * Created on 03.10.2009.
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package info.rolandkrueger.jlexis.gui.actions;

import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.UserProfileManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;

import java.awt.event.ActionEvent;

import javax.swing.Action;

/**
 * @author Roland Krueger
 * @version $Id: CreateNewUserProfileAction.java 145 2009-10-16 07:30:20Z roland $
 */
public class CreateNewUserProfileAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = -8909938172838290301L;

  public CreateNewUserProfileAction ()
  {
    // TODO I18N
    super ("Neues Benutzerprofil anlegen",
        JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.ADD_USER_PROFILE_ICON));
    putValue (Action.SHORT_DESCRIPTION, "Ein neues Benutzerprofil anlegen.");
  }
  
  @Override
  public void actionPerformed (ActionEvent e)
  {
    UserProfileManager.getInstance ().createNewUserProfile ();
  }
}
