/*
 * $Id: LearningUnitInfoPanel.java 72 2009-03-06 18:13:41Z roland $ Created on 14.12.2008 Copyright 2007 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version. jLexis is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with jLexis;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package info.rolandkrueger.jlexis.gui.internalframes.learningunitmanager;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class LearningUnitInfoPanel extends JPanel
{
  private static final long serialVersionUID = -5319368087178100747L;
  private JLabel nameLabel = null;
  private JTextField nameTextField = null;
  private JLabel nativeLanguageLabel = null;
  private JTextField nativeLanguageTF = null;
  private JLabel languagesLabel = null;
  private JScrollPane languagesScrollPane = null;
  private JList languagesList = null;
  private JLabel descriptionLabel = null;
  private JScrollPane descriptionScrollPane = null;
  private JTextPane descriptionTextPane = null;
  private DefaultListModel listModel;
  private JLabel sizeLabel = null;
  private JLabel sizeValueLabel = null;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public void setLearningUnit (LearningUnit unit)
  {
    getNameTextField       ().setText (unit.getName           ());
    getNativeLanguageTF    ().setText (unit.getNativeLanguage ().getLanguageName ());
    getDescriptionTextPane ().setText (unit.getDescription    ());
    listModel = new DefaultListModel ();
    for (Language lang : unit.getLanguages ())
    {
      listModel.addElement (lang.getLanguageName ());
    }
    getLanguagesList ().setModel (listModel);
    sizeValueLabel.setText (String.valueOf (unit.getSize ()));
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * This is the default constructor
   */
  public LearningUnitInfoPanel ()
  {
    super ();
    initialize ();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
    gridBagConstraints21.gridx = 1;
    gridBagConstraints21.anchor = GridBagConstraints.WEST;
    gridBagConstraints21.insets = new Insets(5, 5, 0, 0);
    gridBagConstraints21.gridy = 2;
    sizeValueLabel = new JLabel();
    sizeValueLabel.setText("0");
    GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
    gridBagConstraints11.gridx = 0;
    gridBagConstraints11.anchor = GridBagConstraints.EAST;
    gridBagConstraints11.insets = new Insets(5, 5, 0, 5);
    gridBagConstraints11.gridy = 2;
    sizeLabel = new JLabel();
    // TODO: I18N
//    sizeLabel.setText("Anzahl Vokabeln:");
    sizeLabel.setText("Vocabulary count:");
    GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
    gridBagConstraints7.fill = GridBagConstraints.BOTH;
    gridBagConstraints7.gridy = 4;
    gridBagConstraints7.weightx = 1.0;
    gridBagConstraints7.weighty = 2.0;
    gridBagConstraints7.insets = new Insets(5, 5, 10, 5);
    gridBagConstraints7.gridx = 1;
    GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
    gridBagConstraints6.gridx = 0;
    gridBagConstraints6.anchor = GridBagConstraints.NORTHEAST;
    gridBagConstraints6.insets = new Insets(5, 5, 0, 5);
    gridBagConstraints6.gridy = 4;
    descriptionLabel = new JLabel();
    // TODO: I18N
//    descriptionLabel.setText("Beschreibung:");
    descriptionLabel.setText("Description:");
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
    gridBagConstraints5.gridy = 3;
    gridBagConstraints5.weightx = 1.0;
    gridBagConstraints5.weighty = 0.5;
    gridBagConstraints5.anchor = GridBagConstraints.WEST;
    gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
    gridBagConstraints5.gridwidth = 1;
    gridBagConstraints5.gridx = 1;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.gridx = 0;
    gridBagConstraints4.anchor = GridBagConstraints.NORTHEAST;
    gridBagConstraints4.insets = new Insets(5, 5, 0, 5);
    gridBagConstraints4.gridy = 3;
    languagesLabel = new JLabel();
    // TODO: I18N
//    languagesLabel.setText("Fremdsprachen:");
    languagesLabel.setText("Foreign languages:");
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.fill = GridBagConstraints.BOTH;
    gridBagConstraints3.gridy = 1;
    gridBagConstraints3.weightx = 1.0;
    gridBagConstraints3.insets = new Insets(5, 5, 0, 5);
    gridBagConstraints3.gridx = 1;
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.anchor = GridBagConstraints.EAST;
    gridBagConstraints2.ipadx = 0;
    gridBagConstraints2.ipady = 0;
    gridBagConstraints2.insets = new Insets(5, 5, 0, 5);
    gridBagConstraints2.gridy = 1;
    nativeLanguageLabel = new JLabel();
    // TODO: I18N
//    nativeLanguageLabel.setText("Muttersprache:");
    nativeLanguageLabel.setText("Mother tongue:");
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.fill = GridBagConstraints.BOTH;
    gridBagConstraints1.gridy = 0;
    gridBagConstraints1.weightx = 1.0;
    gridBagConstraints1.insets = new Insets(10, 5, 0, 5);
    gridBagConstraints1.ipady = 0;
    gridBagConstraints1.gridx = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.insets = new Insets(10, 5, 0, 5);
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.ipady = 0;
    gridBagConstraints.gridy = 0;
    nameLabel = new JLabel();
    // I18N
//    nameLabel.setText("Name der Lerneinheit:");
    nameLabel.setText("Learning unit's name:");
    this.setSize (478, 355);
    this.setLayout (new GridBagLayout ());
    this.add(nameLabel, gridBagConstraints);
    this.add(getNameTextField(), gridBagConstraints1);
    this.add(nativeLanguageLabel, gridBagConstraints2);
    this.add(getNativeLanguageTF(), gridBagConstraints3);
    this.add(languagesLabel, gridBagConstraints4);
    this.add(getLanguagesScrollPane(), gridBagConstraints5);
    this.add(descriptionLabel, gridBagConstraints6);
    this.add(getDescriptionScrollPane(), gridBagConstraints7);
    this.add(sizeLabel, gridBagConstraints11);
    this.add(sizeValueLabel, gridBagConstraints21);
  }

  /**
   * This method initializes nameTextField	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getNameTextField ()
  {
    if (nameTextField == null)
    {
      nameTextField = new JTextField ();
      nameTextField.setEditable(false);
    }
    return nameTextField;
  }

  /**
   * This method initializes nativeLanguageTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getNativeLanguageTF ()
  {
    if (nativeLanguageTF == null)
    {
      nativeLanguageTF = new JTextField ();
      nativeLanguageTF.setEditable(false);
    }
    return nativeLanguageTF;
  }

  /**
   * This method initializes languagesScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getLanguagesScrollPane ()
  {
    if (languagesScrollPane == null)
    {
      languagesScrollPane = new JScrollPane ();
      languagesScrollPane.setMaximumSize(new Dimension(150, 32767));
      languagesScrollPane.setPreferredSize(new Dimension(150, 131));
      languagesScrollPane.setMinimumSize(new Dimension(150, 50));
      languagesScrollPane.setViewportView(getLanguagesList());
    }
    return languagesScrollPane;
  }

  /**
   * This method initializes languagesList	
   * 	
   * @return javax.swing.JList	
   */
  private JList getLanguagesList ()
  {
    if (languagesList == null)
    {
      languagesList = new JList ();
    }
    return languagesList;
  }

  /**
   * This method initializes descriptionScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getDescriptionScrollPane ()
  {
    if (descriptionScrollPane == null)
    {
      descriptionScrollPane = new JScrollPane ();
      descriptionScrollPane.setViewportView(getDescriptionTextPane());
    }
    return descriptionScrollPane;
  }

  /**
   * This method initializes descriptionTextPane	
   * 	
   * @return javax.swing.JTextPane	
   */
  private JTextPane getDescriptionTextPane ()
  {
    if (descriptionTextPane == null)
    {
      descriptionTextPane = new JTextPane ();
      descriptionTextPane.setEditable(false);
    }
    return descriptionTextPane;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
