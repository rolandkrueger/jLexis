/*
 * Created on 01.12.2009
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
package info.rolandkrueger.jlexis.plugin.english.inputpanels;

import info.rolandkrueger.jlexis.gui.components.VocableTextField;
import info.rolandkrueger.jlexis.gui.containers.AbstractPanelWithCustomFocusTraversalPolicy;
import info.rolandkrueger.jlexis.gui.containers.standardinputpanels.StandardInputFieldsPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VerbInputPanel extends AbstractPanelWithCustomFocusTraversalPolicy
{
  private static final long serialVersionUID = -4987219127820709456L;
  
  private JPanel upperPanel = null;
  private JLabel infinitiveLabel = null;
  private JLabel pastTenseLabel = null;
  private JLabel pastParticipleLabel = null;
  private VocableTextField infinitiveTF = null;
  private VocableTextField pastTenseTF = null;
  private VocableTextField pastParticipleTF = null;

  private JCheckBox irregularCheckBox = null;

  private StandardInputFieldsPanel standardInputFieldsPanel = null;

  /**
   * This is the default constructor
   */
  public VerbInputPanel ()
  {
    super ();
    initialize ();
  }

  public void clearInput ()
  {
    getStandardInputFieldsPanel ().clearInput ();
    getIrregularCheckBox ().setSelected (false);
    getInfinitiveTF ().setText ("");
    getPastTenseTF ().setText ("");
    getPastParticipleTF ().setText ("");
  }
  
  public boolean isUserInputEmpty ()
  {
    return getInfinitiveTF ().isUserInputEmpty () && 
           getPastTenseTF ().isUserInputEmpty () &&
           getPastParticipleTF ().isUserInputEmpty ();
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.add (getInfinitiveTF ());
    result.add (getPastTenseTF ());
    result.add (getPastParticipleTF ());
    result.addAll (getStandardInputFieldsPanel ().getComponentsInFocusTraversalOrder ());
    return result;
  }
  
  public String getInfinitive ()
  {
    return getInfinitiveTF ().getText ();
  }
  
  public String getPastTense ()
  {
    return getPastTenseTF ().getText ();
  }
  
  public String getPastParticiple ()
  {
    return getPastParticipleTF ().getText ();
  }
  
  public boolean isIrregular ()
  {
    return getIrregularCheckBox ().isSelected ();
  }
  
  public void setInfinitive (String infinitive)
  {
    getInfinitiveTF ().setText (infinitive);
  }
  
  public void setPastTense (String pastTense)
  {
    getPastTenseTF ().setText (pastTense);
  }
  
  public void setPastParticiple (String pastParticiple)
  {
    getPastParticipleTF ().setText (pastParticiple);
  }
  
  public void setIrregular (boolean irregular)
  {
    getIrregularCheckBox ().setSelected (irregular);
  }
  
  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (396, 257);
    this.setLayout (new BorderLayout());
    this.add(getUpperPanel(), BorderLayout.NORTH);
    this.add(getStandardInputFieldsPanel(), BorderLayout.CENTER);
  }

  /**
   * This method initializes upperPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getUpperPanel ()
  {
    if (upperPanel == null)
    {
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.gridx = 1;
      gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints11.weighty = 10.0;
      gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints11.gridy = 3;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints5.gridy = 2;
      gridBagConstraints5.weightx = 10.0;
      gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints5.insets = new Insets(5, 0, 3, 0);
      gridBagConstraints5.gridx = 1;
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints4.gridy = 1;
      gridBagConstraints4.weightx = 10.0;
      gridBagConstraints4.insets = new Insets(5, 0, 0, 0);
      gridBagConstraints4.gridx = 1;
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints3.gridy = 0;
      gridBagConstraints3.weightx = 10.0;
      gridBagConstraints3.insets = new Insets(3, 0, 0, 0);
      gridBagConstraints3.gridx = 1;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.weighty = 0.0;
      gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints2.insets = new Insets(3, 0, 0, 0);
      gridBagConstraints2.gridy = 2;
      pastParticipleLabel = new JLabel();
      pastParticipleLabel.setText("Past participle:");
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.WEST;
      gridBagConstraints1.weightx = 0.2;
      gridBagConstraints1.weighty = 1.0;
      gridBagConstraints1.gridy = 1;
      pastTenseLabel = new JLabel();
      pastTenseLabel.setText("Past tense:");
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.insets = new Insets(7, 0, 0, 5);
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.weighty = 1.0;
      gridBagConstraints.gridy = 0;
      infinitiveLabel = new JLabel();
      infinitiveLabel.setText("Infinitive:");
      upperPanel = new JPanel ();
      upperPanel.setLayout(new GridBagLayout());
      upperPanel.add(infinitiveLabel, gridBagConstraints);
      upperPanel.add(pastTenseLabel, gridBagConstraints1);
      upperPanel.add(pastParticipleLabel, gridBagConstraints2);
      upperPanel.add(getInfinitiveTF(), gridBagConstraints3);
      upperPanel.add(getPastTenseTF(), gridBagConstraints4);
      upperPanel.add(getPastParticipleTF(), gridBagConstraints5);
      upperPanel.add(getIrregularCheckBox(), gridBagConstraints11);
    }
    return upperPanel;
  }

  /**
   * This method initializes infinitiveTF	
   * 	
   * @return info.rolandkrueger.lexis.gui.components.VocableTextField	
   */
  private VocableTextField getInfinitiveTF ()
  {
    if (infinitiveTF == null)
    {
      infinitiveTF = new VocableTextField ();
    }
    return infinitiveTF;
  }

  /**
   * This method initializes pastTenseTF	
   * 	
   * @return info.rolandkrueger.lexis.gui.components.VocableTextField	
   */
  private VocableTextField getPastTenseTF ()
  {
    if (pastTenseTF == null)
    {
      pastTenseTF = new VocableTextField ();
    }
    return pastTenseTF;
  }

  /**
   * This method initializes pastParticipleTF	
   * 	
   * @return info.rolandkrueger.lexis.gui.components.VocableTextField	
   */
  private VocableTextField getPastParticipleTF ()
  {
    if (pastParticipleTF == null)
    {
      pastParticipleTF = new VocableTextField ();
    }
    return pastParticipleTF;
  }

  /**
   * This method initializes irregularCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getIrregularCheckBox ()
  {
    if (irregularCheckBox == null)
    {
      irregularCheckBox = new JCheckBox ();
      irregularCheckBox.setText("Irregular");
    }
    return irregularCheckBox;
  }

  /**
   * This method initializes standardInputFieldsPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.standardinputpanels.StandardInputFieldsPanel	
   */
  public StandardInputFieldsPanel getStandardInputFieldsPanel ()
  {
    if (standardInputFieldsPanel == null)
    {
      standardInputFieldsPanel = new StandardInputFieldsPanel (new EnglishIPATextfield ());
    }
    return standardInputFieldsPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
