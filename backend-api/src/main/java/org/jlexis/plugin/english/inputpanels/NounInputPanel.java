/*
 * Created on 14.11.2009
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
package org.jlexis.plugin.english.inputpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class NounInputPanel extends AbstractPanelWithCustomFocusTraversalPolicy
{
  private static final long serialVersionUID = 8931216658427271366L;

  private JPanel propertiesPanel = null;
  private JPanel countabilityPanel = null;
  private JPanel pluralTypePanel = null;
  private JRadioButton countableRB = null;
  private JRadioButton uncountableRB = null;
  private JRadioButton singularRB = null;
  private JRadioButton pluralRB = null;
  private JPanel irregularPluralPanel = null;
  private JCheckBox irregularPluralCheckBox = null;
  private JPanel textPanel = null;
  private JLabel nounSingularLabel = null;
  private JLabel nounPluralLabel = null;
  private JLabel pluralPhoneticsLabel = null;
  private JTextField nounSingularTF = null;
  private JTextField nounPluralTF = null;
  private EnglishIPATextfield pluralPhoneticsTF = null;
  private JPanel auxPanel = null;
  private StandardInputFieldsPanel standardInputFieldsPanel = null;
  private JRadioButton singularPluralUndefinedRB = null;
  private JRadioButton countabilityUnspecifiedRB = null;

  /**
   * This is the default constructor
   */
  public NounInputPanel ()
  {
    super ();
    initialize ();
    getIrregularPluralCheckBox ().setSelected (false);
  }
  
  // USER DEFINED METHODS - START
  
  public void clearInput ()
  {
    getNounSingularTF ().setText ("");
    getNounPluralTF ().setText ("");
    getPluralPhoneticsTF ().setText ("");
    getIrregularPluralCheckBox ().setSelected (false);
    setCountability (Countability.UNSPECIFIED);
    setSingularPluralType (SingularPluralForm.UNSPECIFIED);
    getStandardInputFieldsPanel ().clearInput ();
  }
  
  public boolean isUserInputEmpty ()
  {
    return getNounSingularTF ().getText ().trim ().equals ("") &&
      getNounPluralTF ().getText ().trim ().equals ("") &&
      getPluralPhoneticsTF ().getText ().trim ().equals ("");
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.add (getNounSingularTF ());
    result.add (getNounPluralTF ());
    result.add (getPluralPhoneticsTF ());
    result.addAll (getStandardInputFieldsPanel ().getComponentsInFocusTraversalOrder ());
    return result;
  }
  
  public Countability getCountability ()
  { 
    if (getUncountableRB ().isSelected ()) return Countability.UNCOUNTABLE; 
    if (getCountableRB ().isSelected ()) return Countability.COUNTABLE;
    return Countability.UNSPECIFIED;
  }
  
  public void setCountability (Countability countability)
  {
    switch (countability)
    { 
    case UNSPECIFIED:
      getCountabilityUnspecifiedRB ().setSelected (true);
      break;
    case COUNTABLE:
      getCountableRB ().setSelected (true);
      break;
    case UNCOUNTABLE:
      getUncountableRB ().setSelected (true);
      break;
    }
  }
  
  public SingularPluralForm getSingularPluralType ()
  {
    if (getPluralRB ().isSelected ()) return SingularPluralForm.PLURAL;
    if (getSingularRB ().isSelected ()) return SingularPluralForm.SINGULAR;
    return SingularPluralForm.UNSPECIFIED;
  }
  
  public void setSingularPluralType (SingularPluralForm singularPluralType)
  {
    switch (singularPluralType)
    {
    case UNSPECIFIED:
      getSingularPluralUndefinedRB ().setSelected (true);
      break;
    case PLURAL:
      getPluralRB ().setSelected (true);
    case SINGULAR:
      getSingularRB ().setSelected (true);
      break;
    }
  }

  public boolean hasIrregularPlural ()
  {
    return getIrregularPluralCheckBox ().isSelected ();
  }
  
  public void setIrregularPlural (boolean hasIrregularPlural)
  {
    getIrregularPluralCheckBox ().setSelected (hasIrregularPlural);
  }
  
  public String getNounSingular ()
  {
    return getNounSingularTF ().getText ();
  }
  
  public void setNounSingular (String nounSingular)
  {
    if (nounSingular == null) nounSingular = "";
    getNounSingularTF ().setText (nounSingular);
  }
  
  public String getNounPlural ()
  {
    return getNounPluralTF ().getText ();
  }
  
  public void setNounPlural (String nounPlural)
  {
    if (nounPlural == null) nounPlural = "";
    getNounPluralTF ().setText (nounPlural);
  }
  
  public String getPluralPhonetics ()
  {
    return getPluralPhoneticsTF ().getText ();
  }
  
  public void setPluralPhonetics (String pluralPhonetics)
  {
    if (pluralPhonetics == null) pluralPhonetics = "";
    getPluralPhoneticsTF ().setText (pluralPhonetics);
  }
  
  // USER DEFINED METHODS - END

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (462, 243);
    this.setLayout (new BorderLayout());
    this.add(getTextPanel(), BorderLayout.NORTH);
    this.add(getAuxPanel(), BorderLayout.CENTER);
  }

  /**
   * This method initializes propertiesPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getPropertiesPanel ()
  {
    if (propertiesPanel == null)
    {
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.gridx = 2;
      gridBagConstraints7.weighty = 1.0;
      gridBagConstraints7.weightx = 1.0;
      gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints7.anchor = GridBagConstraints.NORTH;
      gridBagConstraints7.gridheight = 1;
      gridBagConstraints7.ipady = 48;
      gridBagConstraints7.gridy = 0;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.gridx = 1;
      gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints5.weightx = 1.0;
      gridBagConstraints5.weighty = 1.0;
      gridBagConstraints5.anchor = GridBagConstraints.NORTH;
      gridBagConstraints5.gridy = 0;
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints4.weightx = 1.0;
      gridBagConstraints4.weighty = 1.0;
      gridBagConstraints4.anchor = GridBagConstraints.NORTH;
      gridBagConstraints4.gridy = 0;
      propertiesPanel = new JPanel ();
      propertiesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      propertiesPanel.setLayout(new GridBagLayout());
      propertiesPanel.add(getCountabilityPanel(), gridBagConstraints4);
      propertiesPanel.add(getPluralTypePanel(), gridBagConstraints5);
      propertiesPanel.add(getIrregularPluralPanel(), gridBagConstraints7);
    }
    return propertiesPanel;
  }

  /**
   * This method initializes countabilityPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getCountabilityPanel ()
  {
    if (countabilityPanel == null)
    {
      GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
      gridBagConstraints15.gridx = 0;
      gridBagConstraints15.anchor = GridBagConstraints.WEST;
      gridBagConstraints15.gridy = 0;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.WEST;
      gridBagConstraints1.fill = GridBagConstraints.BOTH;
      gridBagConstraints1.gridwidth = 1;
      gridBagConstraints1.gridheight = 1;
      gridBagConstraints1.weightx = 1.0;
      gridBagConstraints1.weighty = 0.0;
      gridBagConstraints1.gridy = 2;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.fill = GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 0.0;
      gridBagConstraints.gridy = 1;
      countabilityPanel = new JPanel ();
      countabilityPanel.setLayout(new GridBagLayout());
      countabilityPanel.setBorder(BorderFactory.createTitledBorder(null, "Countability", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      countabilityPanel.add(getCountableRB(), gridBagConstraints);
      countabilityPanel.add(getUncountableRB(), gridBagConstraints1);
      countabilityPanel.add(getCountabilityUnspecifiedRB(), gridBagConstraints15);
      ButtonGroup group = new ButtonGroup ();
      group.add (getCountableRB());
      group.add (getUncountableRB ());
      group.add (getCountabilityUnspecifiedRB ());
    }
    return countabilityPanel;
  }

  /**
   * This method initializes pluralTypePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getPluralTypePanel ()
  {
    if (pluralTypePanel == null)
    {
      GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
      gridBagConstraints14.gridx = 0;
      gridBagConstraints14.anchor = GridBagConstraints.WEST;
      gridBagConstraints14.gridy = 0;
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.anchor = GridBagConstraints.WEST;
      gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints3.weightx = 1.0;
      gridBagConstraints3.gridy = 2;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.anchor = GridBagConstraints.WEST;
      gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints2.weightx = 1.0;
      gridBagConstraints2.gridy = 1;
      pluralTypePanel = new JPanel ();
      pluralTypePanel.setLayout(new GridBagLayout());
      pluralTypePanel.setBorder(BorderFactory.createTitledBorder(null, "Plural/Singular", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      pluralTypePanel.add(getSingularRB(), gridBagConstraints2);
      pluralTypePanel.add(getPluralRB(), gridBagConstraints3);
      pluralTypePanel.add(getSingularPluralUndefinedRB(), gridBagConstraints14);
      ButtonGroup group = new ButtonGroup ();
      group.add (getSingularPluralUndefinedRB());
      group.add (getPluralRB ());
      group.add (getSingularRB ());
    }
    return pluralTypePanel;
  }

  /**
   * This method initializes countableRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getCountableRB ()
  {
    if (countableRB == null)
    {
      countableRB = new JRadioButton ();
      countableRB.setText("Countable");
    }
    return countableRB;
  }

  /**
   * This method initializes uncountableRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getUncountableRB ()
  {
    if (uncountableRB == null)
    {
      uncountableRB = new JRadioButton ();
      uncountableRB.setText("Uncountable");
    }
    return uncountableRB;
  }

  /**
   * This method initializes singularRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getSingularRB ()
  {
    if (singularRB == null)
    {
      singularRB = new JRadioButton ();
      singularRB.setText("Singular");
    }
    return singularRB;
  }

  /**
   * This method initializes pluralRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getPluralRB ()
  {
    if (pluralRB == null)
    {
      pluralRB = new JRadioButton ();
      pluralRB.setText("Plural");
    }
    return pluralRB;
  }

  /**
   * This method initializes irregularPluralPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getIrregularPluralPanel ()
  {
    if (irregularPluralPanel == null)
    {
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 0;
      gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints6.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints6.weightx = 1.0;
      gridBagConstraints6.weighty = 10.0;
      gridBagConstraints6.gridy = 0;
      irregularPluralPanel = new JPanel ();
      irregularPluralPanel.setLayout(new GridBagLayout());
      irregularPluralPanel.setBorder(BorderFactory.createTitledBorder(null, "Plural", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      irregularPluralPanel.add(getIrregularPluralCheckBox(), gridBagConstraints6);
    }
    return irregularPluralPanel;
  }

  /**
   * This method initializes irregularPluralCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getIrregularPluralCheckBox ()
  {
    if (irregularPluralCheckBox == null)
    {
      irregularPluralCheckBox = new JCheckBox ();
      irregularPluralCheckBox.setText("irregular Plural");
    }
    return irregularPluralCheckBox;
  }

  /**
   * This method initializes textPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getTextPanel ()
  {
    if (textPanel == null)
    {
      GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
      gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints13.gridy = 2;
      gridBagConstraints13.weightx = 0.0;
      gridBagConstraints13.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints13.anchor = GridBagConstraints.WEST;
      gridBagConstraints13.weighty = 1.0;
      gridBagConstraints13.gridwidth = 1;
      gridBagConstraints13.gridx = 2;
      GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
      gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints12.gridy = 1;
      gridBagConstraints12.weightx = 1.0;
      gridBagConstraints12.insets = new Insets(7, 0, 7, 10);
      gridBagConstraints12.ipadx = 0;
      gridBagConstraints12.ipady = 0;
      gridBagConstraints12.anchor = GridBagConstraints.NORTH;
      gridBagConstraints12.gridx = 2;
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints11.gridy = 0;
      gridBagConstraints11.weightx = 10.0;
      gridBagConstraints11.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints11.anchor = GridBagConstraints.NORTH;
      gridBagConstraints11.gridx = 2;
      GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
      gridBagConstraints10.gridx = 0;
      gridBagConstraints10.anchor = GridBagConstraints.NORTHEAST;
      gridBagConstraints10.weightx = 1.0;
      gridBagConstraints10.insets = new Insets(3, 0, 0, 5);
      gridBagConstraints10.weighty = 100.0;
      gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints10.gridy = 2;
      pluralPhoneticsLabel = new JLabel();
      pluralPhoneticsLabel.setText("Plural phonetics:");
      GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
      gridBagConstraints9.gridx = 0;
      gridBagConstraints9.anchor = GridBagConstraints.NORTHEAST;
      gridBagConstraints9.weightx = 1.0;
      gridBagConstraints9.insets = new Insets(7, 0, 0, 5);
      gridBagConstraints9.weighty = 1.0;
      gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints9.gridy = 1;
      nounPluralLabel = new JLabel();
      nounPluralLabel.setText("Plural:");
      GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
      gridBagConstraints8.gridx = 0;
      gridBagConstraints8.anchor = GridBagConstraints.NORTHEAST;
      gridBagConstraints8.weightx = 1.0;
      gridBagConstraints8.insets = new Insets(7, 0, 0, 5);
      gridBagConstraints8.weighty = 1.0;
      gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints8.gridy = 0;
      nounSingularLabel = new JLabel();
      nounSingularLabel.setText("Singular:");
      textPanel = new JPanel ();
      textPanel.setLayout(new GridBagLayout());
      textPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
      textPanel.add(nounSingularLabel, gridBagConstraints8);
      textPanel.add(nounPluralLabel, gridBagConstraints9);
      textPanel.add(pluralPhoneticsLabel, gridBagConstraints10);
      textPanel.add(getNounSingularTF(), gridBagConstraints11);
      textPanel.add(getNounPluralTF(), gridBagConstraints12);
      textPanel.add(getPluralPhoneticsTF(), gridBagConstraints13);
    }
    return textPanel;
  }

  /**
   * This method initializes nounSingularTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getNounSingularTF ()
  {
    if (nounSingularTF == null)
    {
      nounSingularTF = new JTextField ();
    }
    return nounSingularTF;
  }

  /**
   * This method initializes nounPluralTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getNounPluralTF ()
  {
    if (nounPluralTF == null)
    {
      nounPluralTF = new JTextField ();
    }
    return nounPluralTF;
  }

  /**
   * This method initializes pluralPhoneticsTF	
   * 	
   * @return info.rolandkrueger.lexis.plugin.english.inputpanels.EnglishIPATextfield	
   */
  private EnglishIPATextfield getPluralPhoneticsTF ()
  {
    if (pluralPhoneticsTF == null)
    {
      pluralPhoneticsTF = new EnglishIPATextfield ();
    }
    return pluralPhoneticsTF;
  }

  /**
   * This method initializes auxPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getAuxPanel ()
  {
    if (auxPanel == null)
    {
      auxPanel = new JPanel ();
      auxPanel.setLayout(new BorderLayout());
      auxPanel.add(getPropertiesPanel(), BorderLayout.NORTH);
      auxPanel.add(getStandardInputFieldsPanel(), BorderLayout.CENTER);
    }
    return auxPanel;
  }

  /**
   * This method initializes standardInputFieldsPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.StandardInputFieldsPanel	
   */
  public StandardInputFieldsPanel getStandardInputFieldsPanel ()
  {
    if (standardInputFieldsPanel == null)
    {
      standardInputFieldsPanel = new StandardInputFieldsPanel (new EnglishIPATextfield ());
    }
    return standardInputFieldsPanel;
  }

  /**
   * This method initializes singularPluralUndefinedRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getSingularPluralUndefinedRB ()
  {
    if (singularPluralUndefinedRB == null)
    {
      singularPluralUndefinedRB = new JRadioButton ();
      singularPluralUndefinedRB.setText("Unspecified");
      singularPluralUndefinedRB.setSelected (true);
    }
    return singularPluralUndefinedRB;
  }

  /**
   * This method initializes countabilityUnspecifiedRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getCountabilityUnspecifiedRB ()
  {
    if (countabilityUnspecifiedRB == null)
    {
      countabilityUnspecifiedRB = new JRadioButton ();
      countabilityUnspecifiedRB.setText("Unspecified");
      countabilityUnspecifiedRB.setSelected (true);
    }
    return countabilityUnspecifiedRB;
  }

}  //  @jve:decl-index=0:visual-constraint="19,21"
