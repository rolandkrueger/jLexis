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
package info.rolandkrueger.jlexis.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * 
 * @author Roland Krueger
 * @version $Id: CreateNewUserProfileDialog.java 138 2009-10-16 06:45:04Z roland $
 */
public class CreateNewUserProfileDialog extends JDialog
{
  private static final long serialVersionUID = 4487028450072048253L;
  
  private JPanel jContentPane = null;
  private JPanel buttonPanel = null;
  private JButton okButton = null;
  private JButton cancelButton = null;
  private JPanel centerPanel = null;
  private JLabel profileNameLabel = null;
  private JLabel commentLabel = null;
  private JTextField profileNameTF = null;
  private JScrollPane commentScrollPane = null;
  private JTextPane commentTextPane = null;

  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- STAsRT --
  ////////////////////////////////////////////////////////////
  
  public String getProfileName ()
  {
    return profileNameTF.getText ();
  }
  
  public String getProfileComment ()
  {
    return commentTextPane.getText ();
  }
  
  public void addOKButtonActionListener (ActionListener listener)
  {
    okButton.addActionListener (listener);
  }
  
  public void addCancelButtonActionListener (ActionListener listener)
  {
    cancelButton.addActionListener (listener);
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * @param owner
   */
  public CreateNewUserProfileDialog (Frame owner)
  {
    super (owner);
    initialize ();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (381, 237);
    this.setContentPane(getJContentPane());
    this.setModal (true);
    // TODO: i18n
    this.setTitle ("Neues Benutzerprofil anlegen");
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
      jContentPane.add(getCenterPanel(), BorderLayout.CENTER);
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
    }
    return cancelButton;
  }

  /**
   * This method initializes centerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getCenterPanel ()
  {
    if (centerPanel == null)
    {
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.fill = GridBagConstraints.BOTH;
      gridBagConstraints11.gridy = 1;
      gridBagConstraints11.weightx = 1.0;
      gridBagConstraints11.weighty = 1.0;
      gridBagConstraints11.insets = new Insets(0, 0, 10, 0);
      gridBagConstraints11.gridx = 1;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints2.gridy = 0;
      gridBagConstraints2.weightx = 1.0;
      gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints2.gridx = 1;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.NORTH;
      gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
      gridBagConstraints1.gridy = 1;
      commentLabel = new JLabel();
      commentLabel.setText("Kommentar:");
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.insets = new Insets(10, 10, 10, 10);
      gridBagConstraints.gridy = 0;
      profileNameLabel = new JLabel();
      profileNameLabel.setText("Profilname:");
      centerPanel = new JPanel ();
      centerPanel.setLayout(new GridBagLayout());
      centerPanel.add(profileNameLabel, gridBagConstraints);
      centerPanel.add(commentLabel, gridBagConstraints1);
      centerPanel.add(getProfileNameTF(), gridBagConstraints2);
      centerPanel.add(getCommentScrollPane(), gridBagConstraints11);
    }
    return centerPanel;
  }

  /**
   * This method initializes profileNameTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getProfileNameTF ()
  {
    if (profileNameTF == null)
    {
      profileNameTF = new JTextField ();
    }
    return profileNameTF;
  }

  /**
   * This method initializes commentScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getCommentScrollPane ()
  {
    if (commentScrollPane == null)
    {
      commentScrollPane = new JScrollPane ();
      commentScrollPane.setViewportView(getCommentTextPane());
    }
    return commentScrollPane;
  }

  /**
   * This method initializes commentTextPane	
   * 	
   * @return javax.swing.JTextPane	
   */
  private JTextPane getCommentTextPane ()
  {
    if (commentTextPane == null)
    {
      commentTextPane = new JTextPane ();
    }
    return commentTextPane;
  }

}  //  @jve:decl-index=0:visual-constraint="30,16"
