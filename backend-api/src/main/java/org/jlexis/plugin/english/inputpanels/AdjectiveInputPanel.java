/*
 * Created on 20.11.2009
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


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedList;

public class AdjectiveInputPanel extends AbstractPanelWithCustomFocusTraversalPolicy
{
  private static final long serialVersionUID = 1651706939558445308L;

  private JPanel centerPanel = null;
  private JPanel usagePanel = null;
  private JRadioButton unspecifiedRB = null;
  private JRadioButton onlyBeforeNounRB = null;
  private JRadioButton usuallyBeforeNounRB = null;
  private JRadioButton notBeforeNounRB = null;
  private JRadioButton notUsuallyBeforeNounRB = null;
  private JRadioButton afterNounRB = null;
  private StandardAdjectiveInputPanel standardAdjectiveInputPanel = null;
  private JPanel usageAuxPanel = null;
  private StandardInputFieldsPanel standardInputFieldsPanel = null;

  /**
   * This is the default constructor
   */
  public AdjectiveInputPanel ()
  {
    super ();
    initialize ();
  }

  public void clearInput ()
  {
    getStandardInputFieldsPanel ().clearInput ();
    getStandardAdjectiveInputPanel ().clearInput ();
    setAdjectiveUsage (AdjectiveUsage.UNSPECIFIED);
  }
  
  public boolean isUserInputEmpty ()
  {
    return getStandardInputFieldsPanel ().isUserInputEmpty () && 
           getStandardAdjectiveInputPanel ().isUserInputEmpty ();
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component>();
    result.addAll (standardAdjectiveInputPanel.getComponentsInFocusTraversalOrder ());
    result.addAll (standardInputFieldsPanel.getComponentsInFocusTraversalOrder ());
    return result;
  }
      
  public void setAdjectiveUsage (AdjectiveUsage usage)
  {
    switch (usage)
    {
    case ONLY_BEFORE_NOUN:
      getOnlyBeforeNounRB ().setSelected (true);
      break;
    case USUALLY_BEFORE_NOUN:
      getUsuallyBeforeNounRB ().setSelected (true);
      break;
    case NOT_BEFORE_NOUN:
      getNotBeforeNounRB ().setSelected (true);
      break;
    case NOT_USUALLY_BEFORE_NOUN:
      getNotUsuallyBeforeNounRB ().setSelected (true);
      break;
    case AFTER_NOUN:
      getAfterNounRB ().setSelected (true);
      break;
    case UNSPECIFIED:
      getUnspecifiedRB ().setSelected (true);
      break;
    }
  }
  
  public AdjectiveUsage getAdjectiveUsage ()
  {
    if (getOnlyBeforeNounRB ().isSelected ())
      return AdjectiveUsage.ONLY_BEFORE_NOUN;
    if (getUsuallyBeforeNounRB ().isSelected ())
      return AdjectiveUsage.USUALLY_BEFORE_NOUN;
    if (getNotBeforeNounRB ().isSelected ())
      return AdjectiveUsage.NOT_BEFORE_NOUN;
    if (getNotUsuallyBeforeNounRB ().isSelected ())
      return AdjectiveUsage.NOT_USUALLY_BEFORE_NOUN;
    if (getAfterNounRB ().isSelected ())
      return AdjectiveUsage.AFTER_NOUN;
    
    return AdjectiveUsage.UNSPECIFIED;
  }
  
  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (550, 344);
    this.setLayout (new BorderLayout());
    this.add(getCenterPanel(), BorderLayout.CENTER);
    this.add(getUsageAuxPanel(), BorderLayout.EAST);
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
      centerPanel = new JPanel ();
      centerPanel.setLayout(new BorderLayout());
      centerPanel.add(getStandardAdjectiveInputPanel(), BorderLayout.NORTH);
      centerPanel.add(getStandardInputFieldsPanel(), BorderLayout.CENTER);
    }
    return centerPanel;
  }

  /**
   * This method initializes usagePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getUsagePanel ()
  {
    if (usagePanel == null)
    {
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints4.weighty = 10.0;
      gridBagConstraints4.gridy = 5;
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.anchor = GridBagConstraints.WEST;
      gridBagConstraints3.gridy = 4;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.anchor = GridBagConstraints.WEST;
      gridBagConstraints2.gridy = 3;
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.gridx = 0;
      gridBagConstraints11.anchor = GridBagConstraints.WEST;
      gridBagConstraints11.gridy = 2;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.WEST;
      gridBagConstraints1.gridy = 1;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.gridy = 0;
      usagePanel = new JPanel ();
      usagePanel.setLayout(new GridBagLayout());
      usagePanel.setBorder(BorderFactory.createTitledBorder(null, "Usage", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      usagePanel.add(getUnspecifiedRB(), gridBagConstraints);
      usagePanel.add(getOnlyBeforeNounRB(), gridBagConstraints1);
      usagePanel.add(getUsuallyBeforeNounRB(), gridBagConstraints11);
      usagePanel.add(getNotBeforeNounRB(), gridBagConstraints2);
      usagePanel.add(getNotUsuallyBeforeNounRB(), gridBagConstraints3);
      usagePanel.add(getAfterNounRB(), gridBagConstraints4);
      ButtonGroup group = new ButtonGroup ();
      group.add (getUnspecifiedRB ());
      group.add (getOnlyBeforeNounRB ());
      group.add (getUsuallyBeforeNounRB ());
      group.add (getNotBeforeNounRB ());
      group.add (getNotUsuallyBeforeNounRB ());
      group.add (getAfterNounRB ());
      
    }
    return usagePanel;
  }

  /**
   * This method initializes unspecifiedRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getUnspecifiedRB ()
  {
    if (unspecifiedRB == null)
    {
      unspecifiedRB = new JRadioButton ();
      unspecifiedRB.setText("Unspecified");
    }
    return unspecifiedRB;
  }

  /**
   * This method initializes onlyBeforeNounRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getOnlyBeforeNounRB ()
  {
    if (onlyBeforeNounRB == null)
    {
      onlyBeforeNounRB = new JRadioButton ();
      onlyBeforeNounRB.setText("Only before noun");
    }
    return onlyBeforeNounRB;
  }

  /**
   * This method initializes usuallyBeforeNounRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getUsuallyBeforeNounRB ()
  {
    if (usuallyBeforeNounRB == null)
    {
      usuallyBeforeNounRB = new JRadioButton ();
      usuallyBeforeNounRB.setText("Usually before noun");
    }
    return usuallyBeforeNounRB;
  }

  /**
   * This method initializes notBeforeNounRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getNotBeforeNounRB ()
  {
    if (notBeforeNounRB == null)
    {
      notBeforeNounRB = new JRadioButton ();
      notBeforeNounRB.setText("Not before noun");
    }
    return notBeforeNounRB;
  }

  /**
   * This method initializes notUsuallyBeforeNounRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getNotUsuallyBeforeNounRB ()
  {
    if (notUsuallyBeforeNounRB == null)
    {
      notUsuallyBeforeNounRB = new JRadioButton ();
      notUsuallyBeforeNounRB.setText("Not usually before noun");
    }
    return notUsuallyBeforeNounRB;
  }

  /**
   * This method initializes afterNounRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getAfterNounRB ()
  {
    if (afterNounRB == null)
    {
      afterNounRB = new JRadioButton ();
      afterNounRB.setText("After noun");
    }
    return afterNounRB;
  }

  /**
   * This method initializes standardAdjectiveInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.standardinputpanels.StandardAdjectiveInputPanel	
   */
  public StandardAdjectiveInputPanel getStandardAdjectiveInputPanel ()
  {
    if (standardAdjectiveInputPanel == null)
    {
      standardAdjectiveInputPanel = new StandardAdjectiveInputPanel ();
    }
    return standardAdjectiveInputPanel;
  }

  /**
   * This method initializes usageAuxPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getUsageAuxPanel ()
  {
    if (usageAuxPanel == null)
    {
      usageAuxPanel = new JPanel ();
      usageAuxPanel.setLayout(new BorderLayout());
      usageAuxPanel.add(getUsagePanel(), BorderLayout.NORTH);
    }
    return usageAuxPanel;
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
      standardInputFieldsPanel = new StandardInputFieldsPanel (new info.rolandkrueger.jlexis.plugin.english.inputpanels.EnglishIPATextfield());
    }
    return standardInputFieldsPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
