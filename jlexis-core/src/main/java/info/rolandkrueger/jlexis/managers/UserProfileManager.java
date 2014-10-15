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
package info.rolandkrueger.jlexis.managers;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.data.userprofile.UserProfile;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.gui.actions.SelectUserProfileAction;
import info.rolandkrueger.jlexis.gui.dialogs.CreateNewUserProfileDialog;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Roland Krueger
 * @version $Id: UserProfileManager.java 159 2009-10-23 21:40:42Z roland $
 */
public class UserProfileManager
{
  private static UserProfileManager sInstance;
  
  private List<UserProfile> mUserProfiles;
  private UserProfile       mActiveUserProfile;
  private SelectUserProfileAction mSelectUserProfileAction;
  
  public UserProfileManager ()
  {
    mSelectUserProfileAction = new SelectUserProfileAction ();
  }
  
  public static UserProfileManager getInstance ()
  {
    if (sInstance == null)
    {
      sInstance = new UserProfileManager ();
    }
    
    return sInstance;
  }
  
  public SelectUserProfileAction getSelectUserProfileAction ()
  {
    return mSelectUserProfileAction;
  }
  
  @SuppressWarnings("unchecked")
  public List<UserProfile> getUserProfiles ()
  {
    if (mUserProfiles == null)
    {
      mUserProfiles = new ArrayList<UserProfile> ();
      Session session = JLexisPersistenceManager.getInstance ().getSession ();
      Query query = session.createQuery ("from UserProfile");
      mUserProfiles = query.list ();
    }
    JLexisMain.getInstance ().getMainWindow ().getVisibilityGroupManager (
        ).getUserProfileExistsCondition ().setValue ( ! mUserProfiles.isEmpty ());
    return mUserProfiles;
  }
  
  public int getUserProfilesCount ()
  {
    return getUserProfiles ().size ();
  }
  
  public void selectUserProfile ()
  {
    mSelectUserProfileAction.actionPerformed (null);
  }
  
  public UserProfile createNewUserProfile ()
  {
    final CreateNewUserProfileDialog dialog = JLexisMain.getInstance ().getMainWindow ().getCreateNewUserProfileDialog ();
    OKButtonActionListener okButtonActionListener = new OKButtonActionListener (dialog);
    dialog.addOKButtonActionListener (okButtonActionListener);
    dialog.addCancelButtonActionListener (new ActionListener () {
      @Override
      public void actionPerformed (ActionEvent e)
      {
        dialog.setVisible (false);
      }});
    dialog.setVisible (true);
    
    getUserProfilesCount ();
    return okButtonActionListener.getUserProfile ();
  }
  
  public void deleteUserProfile (UserProfile profile)
  {
    JLexisPersistenceManager.getInstance ().deleteObject (profile);
    
    //TODO remove all vocabulary statistics for this profile
  }
  
  public UserProfile getActiveUserProfile ()
  {
    return mActiveUserProfile;
  }
  
  public void setActiveUserProfile (UserProfile profile)
  {
    CheckForNull.check (profile);
    mActiveUserProfile = profile;
    JLexisMain.getInstance ().getMainWindow ().getVisibilityGroupManager (
        ).getUserProfileSelectedCondition ().setValue (true);
    JLexisMain.getInstance ().getMainWindow ().setActiveUserProfile (profile);
  }
  
  public void increaseAnswerCountForVocable (Vocable vocable, boolean answeredCorrectly)
  {
    if (mActiveUserProfile == null)
      throw new IllegalStateException ("Can't increase answer count. No active user profile set.");
    // TODO
  }
  
  private static class OKButtonActionListener implements ActionListener 
  {
    private CreateNewUserProfileDialog mDialog;
    private UserProfile mProfile = null;
    
    public OKButtonActionListener (CreateNewUserProfileDialog dialog)
    {
      mDialog = dialog;
    }
    
    @Override
    public void actionPerformed (ActionEvent e)
    {
      if (mDialog.getProfileName ().trim ().equals (""))
      {
        // TODO: i18n
        JOptionPane.showMessageDialog (null, "Es muss ein Profilname angegeben werden.", "Fehler!", JOptionPane.ERROR_MESSAGE);
        return;
      }
      mProfile = new UserProfile ();
      mProfile.setComment (mDialog.getProfileComment ());
      mProfile.setProfileName (mDialog.getProfileName ());
      mProfile.setCreationDate (new Date ());
      
      JLexisPersistenceManager.getInstance ().saveObject (mProfile);
      mDialog.setVisible (false);
    }
    
    public UserProfile getUserProfile ()
    {
      return mProfile;
    }
  }
}
