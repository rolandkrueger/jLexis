/*
 * SwedishNounInputPanel.java
 * Created on 05.04.2007
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
import info.rolandkrueger.jlexis.data.vocable.standarduserinput.AbstractStandardUserInput;
import info.rolandkrueger.jlexis.gui.components.VocableTextField;
import info.rolandkrueger.jlexis.gui.containers.AbstractVocableInputPanel;
import info.rolandkrueger.jlexis.gui.containers.TitledBorderPanel;
import info.rolandkrueger.jlexis.gui.containers.standardinputpanels.StandardInputFieldsPanel;
import info.rolandkrueger.jlexis.plugin.swedish.userinput.SwedishNounUserInput;
import info.rolandkrueger.jlexis.plugin.swedish.wordtypes.SwedishNounWordType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class SwedishNounInputPanel extends AbstractVocableInputPanel
{  
  private static final long serialVersionUID = 4790229380754166679L;
  private static final SwedishNounUserInput sExpectedInput = new SwedishNounUserInput ();
  private JPanel jPanel = null;
  private JRadioButton utrumRB = null;
  private JRadioButton neutrumRB = null;
  private JPanel jPanel1 = null;
  private JRadioButton group1RB = null;
  private JRadioButton group3RB = null;
  private JRadioButton group2RB = null;
  private JRadioButton group4RB = null;
  private JRadioButton group5RB = null;
  private JPanel jPanel2 = null;
  private TitledBorderPanel titledBorderPanel = null;
  private TitledBorderPanel titledBorderPanel1 = null;
  private VocableTextField indefiniteSingularTF = null;
  private VocableTextField definiteSingularTF = null;
  private VocableTextField indefinitePluralTF = null;
  private VocableTextField definitePluralTF = null;
  private TitledBorderPanel titledBorderPanel2 = null;
  private TitledBorderPanel titledBorderPanel3 = null;
  private JCheckBox irregularNounCB = null;
  private StandardInputFieldsPanel standardInputFieldsPanel = null;
  private ButtonGroup mGenderButtonGroup;
  private ButtonGroup mGroupButtonGroup;
  
  /// User defined methods - START
  @Override
  public boolean isUserInputEmpty ()
  {
    return standardInputFieldsPanel.isUserInputEmpty () &&
           indefinitePluralTF.isUserInputEmpty () &&
           indefiniteSingularTF.isUserInputEmpty () &&
           definitePluralTF.isUserInputEmpty () &&
           definiteSingularTF.isUserInputEmpty ();
  }
  
  @Override
  public void clearInput ()
  {
    assert mGenderButtonGroup != null && mGroupButtonGroup != null;
    standardInputFieldsPanel.clearInput ();
    getDefinitePluralTF     ().setText ("");
    getDefiniteSingularTF   ().setText ("");
    getIndefinitePluralTF   ().setText ("");
    getIndefiniteSingularTF ().setText ("");
    getIrregularNounCB ().setSelected (false);
    getUtrumRB         ().setSelected (false);
    getNeutrumRB       ().setSelected (false);
    getGroup1RB        ().setSelected (false);
    getGroup2RB        ().setSelected (false);
    getGroup3RB        ().setSelected (false);
    getGroup4RB        ().setSelected (false);
    getGroup5RB        ().setSelected (false);
  }

  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.add (getIndefiniteSingularTF ());
    result.add (getDefiniteSingularTF ());
    result.add (getIndefinitePluralTF ());
    result.add (getDefinitePluralTF ());
    result.addAll (getStandardInputFieldsPanel ().getComponentsInFocusTraversalOrder ());
    return result;
  }
  
  @Override
  public AbstractUserInput getCurrentUserInput ()
  {
    SwedishNounUserInput input = new SwedishNounUserInput ();
    input.addUserData (SwedishNounUserInput.DEFINITE_PLURAL_KEY, getDefinitePluralTF ().getText ());
    input.addUserData (SwedishNounUserInput.DEFINITE_SINGULAR_KEY, getDefiniteSingularTF ().getText ());
    input.addUserData (SwedishNounUserInput.INDEFINITE_SINGULAR_KEY, getIndefiniteSingularTF ().getText ());
    input.addUserData (SwedishNounUserInput.INDEFINITE_PLURAL_KEY, getIndefinitePluralTF ().getText ());
    standardInputFieldsPanel.getCurrentUserInput (input);
    
    SwedishNounUserInput.Gender gender;
    if (getUtrumRB ().isSelected ()) gender = SwedishNounUserInput.Gender.UTRUM;
    else gender = SwedishNounUserInput.Gender.NEUTRUM;
    input.setGender (gender);

    SwedishNounUserInput.Group group = SwedishNounUserInput.Group.GROUP1;
    if (getGroup2RB ().isSelected ()) group = SwedishNounUserInput.Group.GROUP2;
    if (getGroup3RB ().isSelected ()) group = SwedishNounUserInput.Group.GROUP3;
    if (getGroup4RB ().isSelected ()) group = SwedishNounUserInput.Group.GROUP4;
    if (getGroup5RB ().isSelected ()) group = SwedishNounUserInput.Group.GROUP5;
    input.setNounGroup (group);
    
    input.setIrregular (getIrregularNounCB ().isSelected ());
    
    return input;
  }

  @Override
  public void setUserInput (UserInputInterface input)
  {
    if ( ! sExpectedInput.isTypeCorrect (input))
      throw new IllegalStateException ("Passed wrong user input object. Expected " + SwedishNounUserInput.class.getName ()
          + " but was " + input.getClass ().getName ());
    
    SwedishNounUserInput data = (SwedishNounUserInput) input;
    getDefinitePluralTF ().setText (data.getUserData (SwedishNounUserInput.DEFINITE_PLURAL_KEY).getUserEnteredTerm ());
    getDefiniteSingularTF ().setText (data.getUserData (SwedishNounUserInput.DEFINITE_SINGULAR_KEY).getUserEnteredTerm ());
    getIndefiniteSingularTF ().setText (data.getUserData (SwedishNounUserInput.INDEFINITE_SINGULAR_KEY).getUserEnteredTerm ());
    getIndefinitePluralTF ().setText (data.getUserData (SwedishNounUserInput.INDEFINITE_PLURAL_KEY).getUserEnteredTerm ());
    standardInputFieldsPanel.setUserInput ((AbstractStandardUserInput) input);
    
    SwedishNounUserInput.Group  group  = data.getGroup  ();
    SwedishNounUserInput.Gender gender = data.getGender ();
    
    if (group != null)
    {
      if (group == SwedishNounUserInput.Group.GROUP1) getGroup1RB ().setSelected (true);
      if (group == SwedishNounUserInput.Group.GROUP2) getGroup2RB ().setSelected (true);
      if (group == SwedishNounUserInput.Group.GROUP3) getGroup3RB ().setSelected (true);
      if (group == SwedishNounUserInput.Group.GROUP4) getGroup4RB ().setSelected (true);
      if (group == SwedishNounUserInput.Group.GROUP5) getGroup5RB ().setSelected (true);
    }
    
    if (gender != null)
    {
      if (gender == SwedishNounUserInput.Gender.UTRUM) getUtrumRB ().setSelected (true);
      else getNeutrumRB ().setSelected (true);
    }
    
    getIrregularNounCB ().setSelected (data.getIrregular ());
  }
  
  /// User defined methods - END
  
  /**
   * This is the default constructor
   */
  public SwedishNounInputPanel ()
  {
    super (new SwedishNounWordType ());
    initialize ();
  }
  
  public SwedishNounInputPanel (SwedishNounWordType wordType)
  {
    super (wordType);
    initialize ();
  }
  

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.fill = GridBagConstraints.BOTH;
    gridBagConstraints2.gridwidth = 3;
    gridBagConstraints2.gridheight = 2;
    gridBagConstraints2.weighty = 1.0;
    gridBagConstraints2.gridy = 2;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 0;
    gridBagConstraints3.weightx = 0.75;
    gridBagConstraints3.fill = GridBagConstraints.BOTH;
    gridBagConstraints3.gridheight = 2;
    gridBagConstraints3.anchor = GridBagConstraints.NORTH;
    gridBagConstraints3.gridy = 0;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 1;
    gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints1.gridwidth = 1;
    gridBagConstraints1.weightx = 0.25;
    gridBagConstraints1.gridy = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.weightx = 0.25;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridy = 0;
//    this.setSize (666, 539);
    this.setLayout (new GridBagLayout ());
    this.add(getJPanel(), gridBagConstraints);
    this.add(getJPanel1(), gridBagConstraints1);
    this.add(getJPanel2(), gridBagConstraints3);
    this.add(getStandardInputFieldsPanel(), gridBagConstraints2);
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
      jPanel = new JPanel ();
      jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
      // TODO: I18N  
//      jPanel.setBorder(BorderFactory.createTitledBorder(null, "Geschlecht:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      jPanel.setBorder(BorderFactory.createTitledBorder(null, "Gender:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      mGenderButtonGroup = new ButtonGroup ();
			jPanel.add(getUtrumRB(), null);
			jPanel.add(getNeutrumRB(), null);
      mGenderButtonGroup.add(getUtrumRB());
      mGenderButtonGroup.add(getNeutrumRB());
    }
    return jPanel;
  }

  /**
   * This method initializes utrumRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getUtrumRB ()
  {
    if (utrumRB == null)
    {
      utrumRB = new JRadioButton ();
      // TODO: I18N
//      utrumRB.setText("Utrum");
      utrumRB.setText("Common");
    }
    return utrumRB;
  }

  /**
   * This method initializes neutrumRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getNeutrumRB ()
  {
    if (neutrumRB == null)
    {
      neutrumRB = new JRadioButton ();
      // TODO: I18N
//      neutrumRB.setText("Neutrum");
      neutrumRB.setText("Neuter");
    }
    return neutrumRB;
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
      jPanel1 = new JPanel ();
      jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.Y_AXIS));
      // TODO: I18N
//      jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Gruppe:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Group:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      jPanel1.add(getGroup1RB(), null);
      jPanel1.add(getGroup2RB(), null);
      jPanel1.add(getGroup3RB(), null);
      jPanel1.add(getGroup4RB(), null);
      jPanel1.add(getGroup5RB(), null);
      jPanel1.add(getIrregularNounCB(), null);
      mGroupButtonGroup = new ButtonGroup ();
      mGroupButtonGroup.add (getGroup1RB ());
      mGroupButtonGroup.add (getGroup3RB ());
      mGroupButtonGroup.add (getGroup2RB ());
      mGroupButtonGroup.add (getGroup4RB ());
      mGroupButtonGroup.add (getGroup5RB ());
    }
    return jPanel1;
  }

  /**
   * This method initializes group1RB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getGroup1RB ()
  {
    if (group1RB == null)
    {
      group1RB = new JRadioButton ();
      group1RB.setText("1");
    }
    return group1RB;
  }

  /**
   * This method initializes group3RB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getGroup3RB ()
  {
    if (group3RB == null)
    {
      group3RB = new JRadioButton ();
      group3RB.setText("3");
    }
    return group3RB;
  }

  /**
   * This method initializes group2RB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getGroup2RB ()
  {
    if (group2RB == null)
    {
      group2RB = new JRadioButton ();
      group2RB.setText("2");
    }
    return group2RB;
  }

  /**
   * This method initializes group4RB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getGroup4RB ()
  {
    if (group4RB == null)
    {
      group4RB = new JRadioButton ();
      group4RB.setText("4");
    }
    return group4RB;
  }

  /**
   * This method initializes group5RB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getGroup5RB ()
  {
    if (group5RB == null)
    {
      group5RB = new JRadioButton ();
      group5RB.setText("5");
    }
    return group5RB;
  }

  /**
   * This method initializes jPanel2	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel2 ()
  {
    if (jPanel2 == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      jPanel2 = new JPanel ();
      jPanel2.setLayout(gridLayout);
      jPanel2.add(getTitledBorderPanel(), null);
      jPanel2.add(getTitledBorderPanel1(), null);
      jPanel2.add(getTitledBorderPanel2(), null);
      jPanel2.add(getTitledBorderPanel3(), null);
    }
    return jPanel2;
  }

  private TitledBorderPanel getTitledBorderPanel ()
  {
    if (titledBorderPanel == null)
    {
      titledBorderPanel = new TitledBorderPanel ();
      // TODO: I18N
//      titledBorderPanel.setTitle("Grundform:");
      titledBorderPanel.setTitle("Basic form:");
      titledBorderPanel.add(getIndefiniteSingularTF(), BorderLayout.NORTH);
    }
    return titledBorderPanel;
  }

  private VocableTextField getIndefiniteSingularTF ()
  {
    if (indefiniteSingularTF == null)
    {
      indefiniteSingularTF = new VocableTextField ();
    }
    return indefiniteSingularTF;
  }

  private TitledBorderPanel getTitledBorderPanel1 ()
  {
    if (titledBorderPanel1 == null)
    {
      titledBorderPanel1 = new TitledBorderPanel ();
      // TODO: I18N
//      titledBorderPanel1.setTitle("Singular, bestimmt:");
      titledBorderPanel1.setTitle("Definite singular:");
      titledBorderPanel1.add(getDefiniteSingularTF(), BorderLayout.NORTH);
    }
    return titledBorderPanel1;
  }

  private VocableTextField getDefiniteSingularTF ()
  {
    if (definiteSingularTF == null)
    {
      definiteSingularTF = new VocableTextField ();
    }
    return definiteSingularTF;
  }

  private TitledBorderPanel getTitledBorderPanel2 ()
  {
    if (titledBorderPanel2 == null)
    {
      titledBorderPanel2 = new TitledBorderPanel ();
      // TODO: I18N
//      titledBorderPanel2.setTitle("Plural, unbestimmt:");
      titledBorderPanel2.setTitle("Indefinite plural:");
      titledBorderPanel2.add(getIndefinitePluralTF(), BorderLayout.NORTH);
    }
    return titledBorderPanel2;
  }

  private VocableTextField getIndefinitePluralTF ()
  {
    if (indefinitePluralTF == null)
    {
      indefinitePluralTF = new VocableTextField ();
    }
    return indefinitePluralTF;
  }

  private TitledBorderPanel getTitledBorderPanel3 ()
  {
    if (titledBorderPanel3 == null)
    {
      titledBorderPanel3 = new TitledBorderPanel ();
      // TODO: I18N
//      titledBorderPanel3.setTitle("Plural, bestimmt:");
      titledBorderPanel3.setTitle("Definite plural:");
      titledBorderPanel3.add(getDefinitePluralTF(), BorderLayout.NORTH);
    }
    return titledBorderPanel3;
  }

  private VocableTextField getDefinitePluralTF ()
  {
    if (definitePluralTF == null)
    {
      definitePluralTF = new VocableTextField ();
    }
    return definitePluralTF;
  }

  private JCheckBox getIrregularNounCB ()
  {
    if (irregularNounCB == null)
    {
      irregularNounCB = new JCheckBox ();
      // TODO: I18N
//      irregularNounCB.setText("unregelmaessig");
      irregularNounCB.setText("irregular");
    }
    return irregularNounCB;
  }

  /**
   * This method initializes standardInputFieldsPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.StandardInputFieldsPanel	
   */
  private StandardInputFieldsPanel getStandardInputFieldsPanel ()
  {
    if (standardInputFieldsPanel == null)
    {
      standardInputFieldsPanel = new StandardInputFieldsPanel (new SwedishIPATextField ());
    }
    return standardInputFieldsPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
