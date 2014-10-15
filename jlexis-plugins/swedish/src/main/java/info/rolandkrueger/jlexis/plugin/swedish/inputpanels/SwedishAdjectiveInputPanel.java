/*
 * Created on 20.11.2009.
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
package info.rolandkrueger.jlexis.plugin.swedish.inputpanels;

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.UserInputInterface;
import info.rolandkrueger.jlexis.gui.components.VocableTextField;
import info.rolandkrueger.jlexis.gui.containers.AbstractVocableInputPanel;
import info.rolandkrueger.jlexis.gui.containers.standardinputpanels.StandardAdjectiveInputPanel;
import info.rolandkrueger.jlexis.gui.containers.standardinputpanels.StandardInputFieldsPanel;
import info.rolandkrueger.jlexis.plugin.swedish.userinput.SwedishAdjectiveUserInput;
import info.rolandkrueger.jlexis.plugin.swedish.wordtypes.SwedishAdjectiveWordType;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwedishAdjectiveInputPanel extends AbstractVocableInputPanel
{
  private static final long serialVersionUID = -2062017935665463311L;
  private static final SwedishAdjectiveUserInput sExpectedInput = new SwedishAdjectiveUserInput ();
  
  private JPanel neutrumPluralPanel = null;
  private JLabel neutrumLabel = null;
  private JLabel pluralLabel = null;
  private VocableTextField neutrumTF = null;
  private VocableTextField pluralTF = null;
  private JPanel spacerPanel = null;
  private JPanel upperPanel = null;
  private StandardAdjectiveInputPanel standardAdjectiveInputPanel = null;
  private StandardInputFieldsPanel standardInputFieldsPanel = null;

  /**
   * This is the default constructor
   */
  public SwedishAdjectiveInputPanel ()
  {
    super (new SwedishAdjectiveWordType ());
    initialize ();
  }

  @Override
  public void clearInput ()
  {
    standardAdjectiveInputPanel.clearInput ();
    standardInputFieldsPanel.clearInput ();
    getNeutrumTF ().clearInput ();
    getPluralTF ().clearInput ();
  }

  @Override
  public AbstractUserInput getCurrentUserInput ()
  {
    SwedishAdjectiveUserInput input = new SwedishAdjectiveUserInput ();
    standardAdjectiveInputPanel.getCurrentUserInput (input.getStandardAdjectiveUserInput ());
    standardInputFieldsPanel.getCurrentUserInput (input);
    input.setNeutrumTerm (getNeutrumTF ().getText ());
    input.setPluralTerm (getPluralTF ().getText ());
    return input;
  }

  @Override
  public boolean isUserInputEmpty ()
  {
    return standardAdjectiveInputPanel.isUserInputEmpty () && 
           standardInputFieldsPanel.isUserInputEmpty () && 
           getNeutrumTF ().isUserInputEmpty () && 
           getPluralTF ().isUserInputEmpty ();
  }

  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.addAll (getStandardAdjectiveInputPanel ().getComponentsInFocusTraversalOrder ());
    result.addAll (getStandardInputFieldsPanel ().getComponentsInFocusTraversalOrder ());
    return result;
  }
  
  @Override
  public void setUserInput (UserInputInterface input)
  {
    if ( ! sExpectedInput.isTypeCorrect (input))
      throw new IllegalStateException ("Passed wrong user input object. Expected " + SwedishAdjectiveUserInput.class.getName ()
          + " but was " + input.getClass ().getName ());

    SwedishAdjectiveUserInput swedishInput = (SwedishAdjectiveUserInput) input;
    standardAdjectiveInputPanel.setUserInput (swedishInput.getStandardAdjectiveUserInput ());
    standardInputFieldsPanel.setUserInput (swedishInput);
    getNeutrumTF ().setText (swedishInput.getUserEnteredNeutrumTerm ());
    getPluralTF ().setText (swedishInput.getUserEnteredPluralTerm ());
  }
  
  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (538, 324);
    this.setLayout (new BorderLayout());
    this.add(getUpperPanel(), BorderLayout.NORTH);
    this.add(getStandardInputFieldsPanel(), BorderLayout.CENTER);
  }

  /**
   * This method initializes neutrumPluralPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getNeutrumPluralPanel ()
  {
    if (neutrumPluralPanel == null)
    {
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.weighty = 10.0;
      gridBagConstraints4.gridy = 2;
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints3.gridy = 1;
      gridBagConstraints3.weightx = 1.0;
      gridBagConstraints3.insets = new Insets(3, 7, 3, 7);
      gridBagConstraints3.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints3.gridx = 1;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints2.gridy = 0;
      gridBagConstraints2.weightx = 1.0;
      gridBagConstraints2.insets = new Insets(3, 7, 3, 7);
      gridBagConstraints2.gridx = 1;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.EAST;
      gridBagConstraints1.weighty = 0.0;
      gridBagConstraints1.gridy = 1;
      pluralLabel = new JLabel();
      // TODO: I18N
//      pluralLabel.setText("Pluralform:");
      pluralLabel.setText("Plural form:");
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = GridBagConstraints.EAST;
      gridBagConstraints.insets = new Insets(0, 5, 0, 0);
      gridBagConstraints.gridy = 0;
      neutrumLabel = new JLabel();
      // TODO: I18N
//      neutrumLabel.setText("Neutrumform:");
      neutrumLabel.setText("Neuter form:");
      neutrumPluralPanel = new JPanel ();
      neutrumPluralPanel.setLayout(new GridBagLayout());
      neutrumPluralPanel.add(neutrumLabel, gridBagConstraints);
      neutrumPluralPanel.add(pluralLabel, gridBagConstraints1);
      neutrumPluralPanel.add(getNeutrumTF(), gridBagConstraints2);
      neutrumPluralPanel.add(getPluralTF(), gridBagConstraints3);
      neutrumPluralPanel.add(getSpacerPanel(), gridBagConstraints4);
    }
    return neutrumPluralPanel;
  }

  /**
   * This method initializes neutrumTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private VocableTextField getNeutrumTF ()
  {
    if (neutrumTF == null)
    {
      neutrumTF = new VocableTextField ();
    }
    return neutrumTF;
  }

  /**
   * This method initializes pluralTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private VocableTextField getPluralTF ()
  {
    if (pluralTF == null)
    {
      pluralTF = new VocableTextField ();
    }
    return pluralTF;
  }

  /**
   * This method initializes spacerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSpacerPanel ()
  {
    if (spacerPanel == null)
    {
      spacerPanel = new JPanel ();
      spacerPanel.setLayout(new GridBagLayout());
    }
    return spacerPanel;
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
      upperPanel = new JPanel ();
      upperPanel.setLayout(new BorderLayout());
      upperPanel.add(getStandardAdjectiveInputPanel(), BorderLayout.NORTH);
      upperPanel.add(getNeutrumPluralPanel(), BorderLayout.CENTER);
    }
    return upperPanel;
  }

  /**
   * This method initializes standardAdjectiveInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.standardinputpanels.StandardAdjectiveInputPanel	
   */
  private StandardAdjectiveInputPanel getStandardAdjectiveInputPanel ()
  {
    if (standardAdjectiveInputPanel == null)
    {
      standardAdjectiveInputPanel = new StandardAdjectiveInputPanel ();
    }
    return standardAdjectiveInputPanel;
  }

  /**
   * This method initializes standardInputFieldsPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.standardinputpanels.StandardInputFieldsPanel	
   */
  private StandardInputFieldsPanel getStandardInputFieldsPanel ()
  {
    if (standardInputFieldsPanel == null)
    {
      standardInputFieldsPanel = new StandardInputFieldsPanel ();
    }
    return standardInputFieldsPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
