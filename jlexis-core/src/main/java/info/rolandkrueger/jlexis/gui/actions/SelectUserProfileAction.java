/*
 * Created on 07.10.2009.
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

import info.rolandkrueger.jlexis.data.userprofile.UserProfile;
import info.rolandkrueger.jlexis.gui.dialogs.SelectUserProfileDialog;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.UserProfileManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;

import java.awt.event.ActionEvent;

import javax.swing.Action;

/**
 * @author Roland Krueger
 * @version $Id: SelectUserProfileAction.java 156 2009-10-23 19:40:41Z roland $
 */
public class SelectUserProfileAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = -318143429046282260L;

  public SelectUserProfileAction ()
  {
    // TODO I18N
    super ("Benutzerprofil ausw\u00E4hlen",
        JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.SELECT_USER_PROFILE_ICON));
    putValue (Action.SHORT_DESCRIPTION, "Mit einem vorhandenen Benutzerprofil arbeiten.");
  }
  
  @Override
  public void actionPerformed (ActionEvent arg0)
  {
    SelectUserProfileDialog dialog = new SelectUserProfileDialog (getMain ());
    dialog.setVisible (true);
    UserProfile selectedProfile = dialog.getSelectedProfile ();
    if (selectedProfile == null) return;
    UserProfileManager.getInstance ().setActiveUserProfile (selectedProfile);
  }
}
