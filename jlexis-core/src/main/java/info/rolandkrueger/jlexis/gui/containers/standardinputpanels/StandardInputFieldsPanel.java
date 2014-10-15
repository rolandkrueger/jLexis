/*
 * StandardInputFieldsPanel.java
 * Created on 15.04.2007
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
package info.rolandkrueger.jlexis.gui.containers.standardinputpanels;

import info.rolandkrueger.jlexis.data.vocable.standarduserinput.AbstractStandardUserInput;
import info.rolandkrueger.jlexis.gui.components.JLexisIPATextField;
import info.rolandkrueger.jlexis.gui.components.JLexisTextArea;
import info.rolandkrueger.jlexis.gui.components.VocableTextField;
import info.rolandkrueger.jlexis.gui.containers.AbstractPanelWithCustomFocusTraversalPolicy;
import info.rolandkrueger.jlexis.gui.containers.TitledBorderPanel;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

public class StandardInputFieldsPanel extends AbstractPanelWithCustomFocusTraversalPolicy
{  
  private static final long serialVersionUID = 5044353190397801479L;
  private JPanel phoneticsInputPanel = null;
  private TitledBorderPanel exampleInputPanel = null;
  private TitledBorderPanel commentInputPanel = null;
  private JPanel pronunciationFilePanel = null;
  private VocableTextField pronunciationTextField = null;
  private JScrollPane jScrollPane = null;
  private JScrollPane jScrollPane1 = null;
  private JLexisIPATextField phoneticsTextField = null;
  private JLexisTextArea exampleArea = null;
  private JLexisTextArea commentArea = null;
  private JPanel jPanel = null;
  private JPanel jPanel1 = null;
  private JButton findPronunciationFileBtn = null;
  private JLabel pronunciationFileIndicatorIcon = null;
  private JPanel centerPanel = null;
  private boolean mForNativeLanguage;
  private JLabel phoneticsInputLabel = null;
  private JLabel pronunciationLabel = null;

  /**
   * This is the default constructor
   */
  public StandardInputFieldsPanel ()
  {
    this (false, null);
  }
  
  /// User defined methods - START
  public boolean isUserInputEmpty ()
  {
    return getExampleArea ().getText ().trim ().length () == 0 &&
           getCommentArea ().getText ().trim ().length () == 0 &&
           getPronunciationTextField ().isUserInputEmpty ();
  }
  
  public void getCurrentUserInput (AbstractStandardUserInput userInput)
  {
    CheckForNull.check (userInput);
    userInput.setComment (getCommentArea ().getText ().trim ());
    userInput.setExample (getExampleArea ().getText ().trim ());
    userInput.setPhonetics (getPhoneticsTextField ().getText ().trim ());
    userInput.setPronunciation (getPronunciationTextField ().getText ().trim ());
  }
  
  public void setUserInput (AbstractStandardUserInput userInput)
  {
    CheckForNull.check (userInput);
    getCommentArea ().setText (userInput.getComment ());
    getExampleArea ().setText (userInput.getExample ());
    getPhoneticsTextField ().setText (userInput.getPhonetics ());
    getPronunciationTextField ().setText (userInput.getPronunciation ());    
  }
  
  public StandardInputFieldsPanel (JLexisIPATextField phoneticsTextfield)
  {
    this (false, phoneticsTextfield);
  }
  
  public StandardInputFieldsPanel (boolean forNativeLanguage, JLexisIPATextField phoneticsTextfield)
  {
    super ();
    mForNativeLanguage = forNativeLanguage;
    phoneticsTextField = phoneticsTextfield;
    initialize ();
  }
  
  public void clearInput ()
  {
    getCommentArea ().setText            ("");
    getExampleArea ().setText            ("");
    getPhoneticsTextField ().setText     ("");
    getPronunciationTextField ().setText ("");
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.add (getPhoneticsInputPanel ());
    result.add (getCommentArea ());
    result.add (getExampleArea ());
    // TODO: enable the pronuncitation textfield if this feature is implemented
//    result.add (getPronunciationTextField ());
    return result;
  }
  /// User defined methods - END

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.anchor = GridBagConstraints.CENTER;
    gridBagConstraints.gridx = -1;
    gridBagConstraints.gridy = -1;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 0;
    gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints3.weightx = 0.0;
    gridBagConstraints3.weighty = 0.0;
    gridBagConstraints3.gridy = 0;
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.fill = GridBagConstraints.BOTH;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.weighty = 1.0;
    gridBagConstraints2.gridy = 1;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints1.gridy = 2;
    this.setLayout(new GridBagLayout());
    this.setSize (669, 180);
    this.setMinimumSize(new Dimension(153, 0));
    this.setPreferredSize(new Dimension(15, 10));
    this.add(getCenterPanel(), gridBagConstraints2);
    if ( ! mForNativeLanguage)
    {
      this.add(getPronunciationFilePanel(), gridBagConstraints1);
      this.add(getPhoneticsInputPanel(), gridBagConstraints3);      
    }
  }

  /**
   * This method initializes phoneticsInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.TitledBorderPanel	
   */
  private JPanel getPhoneticsInputPanel ()
  {
    if (phoneticsInputPanel == null)
    {
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 0;
      gridBagConstraints6.insets = new Insets(0, 10, 0, 15);
      gridBagConstraints6.gridy = 0;
      phoneticsInputLabel = new JLabel();
      // TODO: I18N
//      phoneticsInputLabel.setText("Lautschrift:");
      phoneticsInputLabel.setText("Phonetics:");
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.fill = GridBagConstraints.BOTH;
      gridBagConstraints5.gridy = 0;
      gridBagConstraints5.ipadx = 528;
      gridBagConstraints5.weightx = 1.0;
      gridBagConstraints5.gridx = 1;
      phoneticsInputPanel = new JPanel ();
      phoneticsInputPanel.setLayout(new GridBagLayout());
      phoneticsInputPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      phoneticsInputPanel.add(getPhoneticsTextField(), gridBagConstraints5);
      phoneticsInputPanel.add(phoneticsInputLabel, gridBagConstraints6);
    }
    return phoneticsInputPanel;
  }

  /**
   * This method initializes exampleInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.TitledBorderPanel	
   */
  private TitledBorderPanel getExampleInputPanel ()
  {
    if (exampleInputPanel == null)
    {
      exampleInputPanel = new TitledBorderPanel ();
      // TODO: I18N
//      exampleInputPanel.setTitle("Beispiel:");
      exampleInputPanel.setTitle("Example:");
      exampleInputPanel.setPreferredSize(new Dimension(331, 20));
      exampleInputPanel.setMinimumSize(new Dimension(32, 20));
      exampleInputPanel.add(getJScrollPane1(), BorderLayout.CENTER);
    }
    return exampleInputPanel;
  }

  /**
   * This method initializes commentInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.TitledBorderPanel	
   */
  private TitledBorderPanel getCommentInputPanel ()
  {
    if (commentInputPanel == null)
    {
      commentInputPanel = new TitledBorderPanel ();
      // TODO: I18N
//      commentInputPanel.setTitle("Kommentar:");
      commentInputPanel.setTitle("Comment:");
      commentInputPanel.setPreferredSize(new Dimension(331, 20));
      commentInputPanel.setMinimumSize(new Dimension(32, 20));
      commentInputPanel.add(getJScrollPane(), BorderLayout.CENTER);
    }
    return commentInputPanel;
  }

  /**
   * This method initializes pronunciationFilePanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.TitledBorderPanel	
   */
  private JPanel getPronunciationFilePanel ()
  {
    if (pronunciationFilePanel == null)
    {
      GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
      gridBagConstraints10.gridx = 0;
      gridBagConstraints10.insets = new Insets(0, 10, 0, 15);
      gridBagConstraints10.gridy = 0;
      pronunciationLabel = new JLabel();
      // TODO: I18N
//      pronunciationLabel.setText("Aussprachedatei:");
      pronunciationLabel.setText("Pronunciation file:");
      GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
      gridBagConstraints9.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints9.gridy = 0;
      gridBagConstraints9.fill = GridBagConstraints.BOTH;
      gridBagConstraints9.anchor = GridBagConstraints.CENTER;
      gridBagConstraints9.weightx = 1.0;
      gridBagConstraints9.gridx = 1;
      pronunciationFilePanel = new JPanel ();
      pronunciationFilePanel.setLayout(new GridBagLayout());
      pronunciationFilePanel.add(getJPanel(), gridBagConstraints9);
      pronunciationFilePanel.add(pronunciationLabel, gridBagConstraints10);
    }
    return pronunciationFilePanel;
  }

  /**
   * This method initializes pronunciationTextField	
   * 	
   * @return info.rolandkrueger.lexis.gui.VocableTextField	
   */
  private VocableTextField getPronunciationTextField ()
  {
    if (pronunciationTextField == null)
    {
      pronunciationTextField = new VocableTextField ();
      pronunciationTextField.setMinimumSize(new Dimension(4, 20));
      pronunciationTextField.setPreferredSize(new Dimension(200, 20));
    }
    return pronunciationTextField;
  }

  /**
   * This method initializes jScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getJScrollPane ()
  {
    if (jScrollPane == null)
    {
      jScrollPane = new JScrollPane ();
      jScrollPane.setViewportView(getCommentArea());
    }
    return jScrollPane;
  }

  /**
   * This method initializes jScrollPane1	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getJScrollPane1 ()
  {
    if (jScrollPane1 == null)
    {
      jScrollPane1 = new JScrollPane ();
      jScrollPane1.setViewportView(getExampleArea());
    }
    return jScrollPane1;
  }

  /**
   * This method initializes phoneticsTextField	
   * 	
   * @return info.rolandkrueger.lexis.gui.phoneticsTextField	
   */
  private JLexisIPATextField getPhoneticsTextField ()
  {
    if (phoneticsTextField == null)
    {
      phoneticsTextField = new JLexisIPATextField ();
      phoneticsTextField.setMinimumSize (new Dimension(125, 22));
    }
    return phoneticsTextField;
  }

  /**
   * This method initializes exampleArea	
   * 	
   * @return info.rolandkrueger.lexis.gui.LexisTextArea	
   */
  private JLexisTextArea getExampleArea ()
  {
    if (exampleArea == null)
    {
      exampleArea = new JLexisTextArea ();
      exampleArea.setLineWrap(true);
      exampleArea.setSize(new Dimension(318, 20));
    }
    return exampleArea;
  }

  /**
   * This method initializes commentArea	
   * 	
   * @return info.rolandkrueger.lexis.gui.LexisTextArea	
   */
  private JLexisTextArea getCommentArea ()
  {
    if (commentArea == null)
    {
      commentArea = new JLexisTextArea ();
      commentArea.setLineWrap(true);
      commentArea.setSize(new Dimension(318, 20));
    }
    return commentArea;
  }

  /**
   * This method initializes jPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel ()
  {
    if (jPanel == null)
    {
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints4.gridx = -1;
      gridBagConstraints4.gridy = -1;
      gridBagConstraints4.weightx = 1.0;
      gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.gridx = 1;
      gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints7.gridy = 0;
      jPanel = new JPanel ();
      jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      jPanel.setLayout(new GridBagLayout());
      jPanel.add(getPronunciationTextField(), gridBagConstraints4);
      jPanel.add(getJPanel1(), gridBagConstraints7);
    }
    return jPanel;
  }

  /**
   * This method initializes jPanel1	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel1 ()
  {
    if (jPanel1 == null)
    {
      pronunciationFileIndicatorIcon = new JLabel();
      pronunciationFileIndicatorIcon.setText("");
      jPanel1 = new JPanel ();
      jPanel1.setLayout(new FlowLayout());
      jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      jPanel1.add(getFindPronunciationFileBtn(), null);
      jPanel1.add(pronunciationFileIndicatorIcon, null);
    }
    return jPanel1;
  }

  /**
   * This method initializes findPronunciationFileBtn	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getFindPronunciationFileBtn ()
  {
    if (findPronunciationFileBtn == null)
    {
      findPronunciationFileBtn = new JButton ();
      findPronunciationFileBtn.setEnabled (false);
      // TODO: I18N
//      findPronunciationFileBtn.setText("Durchsuchen...");
      findPronunciationFileBtn.setText("Browse...");
      findPronunciationFileBtn.setPreferredSize(new Dimension(119, 20));
    }
    return findPronunciationFileBtn;
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
      GridLayout gridLayout = new GridLayout();
      gridLayout.setRows(1);
      gridLayout.setColumns(2);
      centerPanel = new JPanel ();
      centerPanel.setMinimumSize(new Dimension(64, 20));
      centerPanel.setLayout(gridLayout);
      centerPanel.add(getCommentInputPanel(), null);
      centerPanel.add(getExampleInputPanel(), null);
    }
    return centerPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="22,35"
