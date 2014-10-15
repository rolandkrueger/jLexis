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
package info.rolandkrueger.jlexis.gui.dialogs;

import info.rolandkrueger.jlexis.data.userprofile.UserProfile;
import info.rolandkrueger.jlexis.gui.datamodels.JLexisListModel;
import info.rolandkrueger.jlexis.managers.UserProfileManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * *
 * @author Roland Krueger
 * @version $Id: SelectUserProfileDialog.java 204 2009-12-17 15:20:16Z roland $
 */
public class SelectUserProfileDialog extends JDialog implements ListSelectionListener
{
  private static final long serialVersionUID = 1803038371122246459L;

  private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="38,411"
  private JPanel buttonPanel = null;
  private JButton okButton = null;
  private JButton cancelButton = null;
  private JScrollPane listScrollPane = null;
  private JList userProfileList = null;
  private JPanel mainPanel = null;
  private JPanel infoPanel = null;
  private JLexisListModel<UserProfile> mUserProfileListModel;
  private JSplitPane splitPane = null;
  private UserProfile mSelectedUserProfile = null;

  /**
   * @param owner
   */
  public SelectUserProfileDialog (Frame owner)
  {
    super (owner);
    initialize ();
  }
  
  // USER ADDED METHODS - START
  
  @Override
  public void setVisible (boolean visible)
  {
    if (visible)
    {
      mUserProfileListModel.clear ();
      mUserProfileListModel.add (UserProfileManager.getInstance ().getUserProfiles ());      
    }
    
    super.setVisible (visible);
  }
  
  @Override
  public void valueChanged (ListSelectionEvent e)
  {
    okButton.setEnabled (userProfileList.getSelectedIndex () > -1);
  }
  
  public UserProfile getSelectedProfile ()
  {
    return mSelectedUserProfile;
  }
  
  public void addActionListener (ActionListener listener)
  {
    getOkButton ().addActionListener (listener);
  }
  
  public void removeActionListener (ActionListener listener)
  {
    getOkButton ().removeActionListener (listener);
  }
  
  // USER ADDED METHODS - END

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    mUserProfileListModel = new JLexisListModel<UserProfile> ();
    this.setSize (529, 376);
    this.setContentPane(getJContentPane());
    // i18n
    this.setTitle ("Benutzerprofil ausw\u00e4hlen");
    this.setModal (true);
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane ()
  {
    if (jContentPane == null)
    {
      jContentPane = new JPanel ();
      jContentPane.setLayout (new BorderLayout ());
      jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
      jContentPane.add(getMainPanel(), BorderLayout.CENTER);
    }
    return jContentPane;
  }

  /**
   * This method initializes buttonPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getButtonPanel ()
  {
    if (buttonPanel == null)
    {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      buttonPanel = new JPanel ();
      buttonPanel.setLayout(flowLayout);
      buttonPanel.add(getOkButton(), null);
      buttonPanel.add(getCancelButton(), null);
    }
    return buttonPanel;
  }

  /**
   * This method initializes okButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getOkButton ()
  {
    if (okButton == null)
    {
      okButton = new JButton ();
      okButton.setText("Ok");
      okButton.setEnabled (false);
      okButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          mSelectedUserProfile = mUserProfileListModel.get (userProfileList.getSelectedIndex ());
          setVisible (false);
        }});
    }
    return okButton;
  }

  /**
   * This method initializes cancelButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getCancelButton ()
  {
    if (cancelButton == null)
    {
      cancelButton = new JButton ();
      cancelButton.setText("Abbrechen");
      cancelButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          mSelectedUserProfile = null;
          setVisible (false);
        }});
    }
    return cancelButton;
  }

  /**
   * This method initializes listScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getListScrollPane ()
  {
    if (listScrollPane == null)
    {
      listScrollPane = new JScrollPane ();
      listScrollPane.setViewportView(getUserProfileList());
    }
    return listScrollPane;
  }

  /**
   * This method initializes userProfileList	
   * 	
   * @return javax.swing.JList	
   */
  private JList getUserProfileList ()
  {
    if (userProfileList == null)
    {
      userProfileList = new JList (mUserProfileListModel);
      userProfileList.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
      userProfileList.addListSelectionListener (this);
    }
    return userProfileList;
  }

  /**
   * This method initializes mainPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMainPanel ()
  {
    if (mainPanel == null)
    {
      mainPanel = new JPanel ();
      mainPanel.setLayout(new BorderLayout());
      mainPanel.add(getSplitPane(), BorderLayout.CENTER);
    }
    return mainPanel;
  }

  /**
   * This method initializes infoPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getInfoPanel ()
  {
    if (infoPanel == null)
    {
      infoPanel = new JPanel ();
      infoPanel.setLayout(new GridBagLayout());
    }
    return infoPanel;
  }

  /**
   * This method initializes splitPane	
   * 	
   * @return javax.swing.JSplitPane	
   */
  private JSplitPane getSplitPane ()
  {
    if (splitPane == null)
    {
      splitPane = new JSplitPane ();
      splitPane.setLeftComponent(getListScrollPane());
      splitPane.setRightComponent(getInfoPanel());
      splitPane.setDividerLocation (250);
    }
    return splitPane;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
