/*
 * StandardAdjectiveInputPanel.java
 * Created on 20.11.2009
 * 
 * Copyright Roland Krueger (www.rolandkrueger.info)
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

import info.rolandkrueger.jlexis.data.vocable.standarduserinput.StandardAdjectiveUserInputDataHandler;
import info.rolandkrueger.jlexis.gui.containers.AbstractPanelWithCustomFocusTraversalPolicy;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public class StandardAdjectiveInputPanel extends AbstractPanelWithCustomFocusTraversalPolicy
{
  private static final long serialVersionUID = 1650597619060023086L;
 
  private JPanel propertiesPanel = null;
  private JCheckBox notComparableCheckBox = null;
  private JCheckBox irregularCheckBox = null;
  private JPanel propertiesLayoutPanel = null;
  private JPanel inputPanel = null;
  private JPanel inputLayoutPanel = null;
  private JLabel positiveLabel = null;
  private JLabel comparativeLabel = null;
  private JLabel superlativeLabel = null;
  private JTextField positiveTF = null;
  private JTextField comparativeTF = null;
  private JTextField superlativeTF = null;
  private JPanel spacerPanel = null;
  private boolean mDeactivateComparativesWhenNotAvailable = false;
  
  public StandardAdjectiveInputPanel ()
  {
    super ();
    initialize ();
  }
  
  // USER DEFINED METHODS - START
  
  public void getCurrentUserInput (StandardAdjectiveUserInputDataHandler result)
  {
    CheckForNull.check        (result);
    result.setPositive        (getPositive ());
    result.setComparative     (getComparative());
    result.setSuperlative     (getSuperlative ());
    result.setNotComparable (isNotComparable ());
    result.setIrregular     (result.isIrregular ());
  }
  
  public void setUserInput (StandardAdjectiveUserInputDataHandler input)
  {
    CheckForNull.check (input);
    setPositive        (input.getPositive ());
    setComparative     (input.getComparative ());
    setSuperlative     (input.getSuperlative ());
    setNotComparable   (input.isNotComparable ());
    setIrregular       (input.isIrregular ());
  }
  
  public boolean isUserInputEmpty ()
  {
    return getPositiveTF ().getText ().trim ().length () == 0 &&
           getComparativeTF ().getText ().trim ().length () == 0 &&
           getSuperlativeTF ().getText ().trim ().length () == 0;
  }
  
  public void clearInput ()
  {
    setPositive ("");
    setComparative ("");
    setSuperlative ("");
    setIrregular (false);
    setNotComparable (false);
  }
  
  public void deactivateComparativesWhenNotAvailable (boolean deactivate)
  {
    mDeactivateComparativesWhenNotAvailable = deactivate;
  }
    
  public String getPositive ()
  {
    return getPositiveTF ().getText ();
  }
  
  public String getComparative ()
  {
    return getComparativeTF ().getText ();
  }
  
  public String getSuperlative ()
  {
    return getSuperlativeTF ().getText ();
  }
  
  public void setPositive (String positive)
  {
    getPositiveTF ().setText (positive);
  }
  
  public void setComparative (String comparative)
  {
    getComparativeTF ().setText (comparative);
  }
  
  public void setSuperlative (String superlative)
  {
    getSuperlativeTF ().setText (superlative);
  }
  
  public boolean isNotComparable ()
  {
    return getNotComparableCheckBox ().isSelected ();
  }

  public boolean isIrregular ()
  {
    return getIrregularCheckBox ().isSelected ();
  }
  
  public void setNotComparable (boolean notComparable)
  {
    getNotComparableCheckBox ().setSelected (notComparable);    
  }
  
  public void setIrregular (boolean irregular)
  {
    getIrregularCheckBox ().setSelected (irregular);
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.add (getPositiveTF ());
    result.add (getComparativeTF ());
    result.add (getSuperlativeTF ());
    return result;
  }
  
  // USER DEFINED METHODS - END

  private void initialize ()
  {
    this.setSize (415, 200);
    this.setLayout (new BorderLayout());
    this.add(getPropertiesLayoutPanel(), BorderLayout.EAST);
    this.add(getInputLayoutPanel(), BorderLayout.CENTER);
  }

  private JPanel getPropertiesPanel ()
  {
    if (propertiesPanel == null)
    {
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
      gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints1.weighty = 10.0;
      gridBagConstraints1.gridy = 1;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 0.0;
      gridBagConstraints.gridy = 0;
      propertiesPanel = new JPanel ();
      propertiesPanel.setLayout(new GridBagLayout());
      // TODO: I18N
//      propertiesPanel.setBorder(BorderFactory.createTitledBorder(null, "Eigenschaften", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      propertiesPanel.setBorder(BorderFactory.createTitledBorder(null, "Properties", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      propertiesPanel.add(getNotComparableCheckBox(), gridBagConstraints);
      propertiesPanel.add(getIrregularCheckBox(), gridBagConstraints1);
    }
    return propertiesPanel;
  }

  private JCheckBox getNotComparableCheckBox ()
  {
    if (notComparableCheckBox == null)
    {
      notComparableCheckBox = new JCheckBox ();
      // TODO: I18N
//      notComparableCheckBox.setText("nicht steigerbar");
      notComparableCheckBox.setText("not comparable");
      notComparableCheckBox.addChangeListener (new ChangeListener()
      {
        @Override
        public void stateChanged (ChangeEvent event)
        {
          if (notComparableCheckBox.isSelected ()) getIrregularCheckBox ().setSelected (false);
          if ( ! mDeactivateComparativesWhenNotAvailable) return;
          getComparativeTF ().setEnabled ( ! notComparableCheckBox.isSelected ());
          getSuperlativeTF ().setEnabled ( ! notComparableCheckBox.isSelected ());
        }
      });
    }
    return notComparableCheckBox;
  }

  private JCheckBox getIrregularCheckBox ()
  {
    if (irregularCheckBox == null)
    {
      irregularCheckBox = new JCheckBox ();
      // TODO: I18N
//      irregularCheckBox.setText("unregelm\u00E4\u00DFig");
      irregularCheckBox.setText("irregular");
      irregularCheckBox.addChangeListener (new ChangeListener()
      {
        @Override
        public void stateChanged (ChangeEvent event)
        {
          if (irregularCheckBox.isSelected ()) getNotComparableCheckBox ().setSelected (false);
          if ( ! mDeactivateComparativesWhenNotAvailable) return;
          getComparativeTF ().setEnabled (irregularCheckBox.isSelected ());
          getSuperlativeTF ().setEnabled (irregularCheckBox.isSelected ());
        }
      });
    }
    return irregularCheckBox;
  }

  private JPanel getPropertiesLayoutPanel ()
  {
    if (propertiesLayoutPanel == null)
    {
      propertiesLayoutPanel = new JPanel ();
      propertiesLayoutPanel.setLayout(new BorderLayout());
      propertiesLayoutPanel.add(getPropertiesPanel(), BorderLayout.NORTH);
    }
    return propertiesLayoutPanel;
  }

  private JPanel getInputPanel ()
  {
    if (inputPanel == null)
    {
      GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
      gridBagConstraints8.gridx = 0;
      gridBagConstraints8.weighty = 10.0;
      gridBagConstraints8.gridy = 3;
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints7.gridy = 2;
      gridBagConstraints7.weightx = 1.0;
      gridBagConstraints7.insets = new Insets(3, 7, 3, 7);
      gridBagConstraints7.anchor = GridBagConstraints.NORTH;
      gridBagConstraints7.gridx = 2;
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints6.gridy = 1;
      gridBagConstraints6.weightx = 1.0;
      gridBagConstraints6.insets = new Insets(3, 7, 3, 7);
      gridBagConstraints6.gridx = 2;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints5.gridy = 0;
      gridBagConstraints5.weightx = 1.0;
      gridBagConstraints5.insets = new Insets(3, 7, 3, 7);
      gridBagConstraints5.anchor = GridBagConstraints.NORTH;
      gridBagConstraints5.gridx = 2;
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.anchor = GridBagConstraints.EAST;
      gridBagConstraints4.weighty = 0.0;
      gridBagConstraints4.insets = new Insets(0, 7, 0, 0);
      gridBagConstraints4.gridy = 2;
      superlativeLabel = new JLabel();
      // TODO: I18N
//      superlativeLabel.setText("Superlativ:");
      superlativeLabel.setText("Superlative:");
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.anchor = GridBagConstraints.EAST;
      gridBagConstraints3.weighty = 0.0;
      gridBagConstraints3.insets = new Insets(0, 7, 0, 0);
      gridBagConstraints3.gridy = 1;
      comparativeLabel = new JLabel();
      // TODO: I18N
//      comparativeLabel.setText("Komparativ:");
      comparativeLabel.setText("Comparative:");
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.anchor = GridBagConstraints.EAST;
      gridBagConstraints2.weighty = 0.0;
      gridBagConstraints2.insets = new Insets(0, 7, 0, 0);
      gridBagConstraints2.gridy = 0;
      positiveLabel = new JLabel();
      // TODO: I18N
//      positiveLabel.setText("Positiv:");
      positiveLabel.setText("Positive:");
      inputPanel = new JPanel ();
      inputPanel.setLayout(new GridBagLayout());
      inputPanel.add(positiveLabel, gridBagConstraints2);
      inputPanel.add(comparativeLabel, gridBagConstraints3);
      inputPanel.add(superlativeLabel, gridBagConstraints4);
      inputPanel.add(getPositiveTF(), gridBagConstraints5);
      inputPanel.add(getComparativeTF(), gridBagConstraints6);
      inputPanel.add(getSuperlativeTF(), gridBagConstraints7);
      inputPanel.add(getSpacerPanel(), gridBagConstraints8);
    }
    return inputPanel;
  }

  private JPanel getInputLayoutPanel ()
  {
    if (inputLayoutPanel == null)
    {
      inputLayoutPanel = new JPanel ();
      inputLayoutPanel.setLayout(new BorderLayout());
      inputLayoutPanel.add(getInputPanel(), BorderLayout.NORTH);
    }
    return inputLayoutPanel;
  }

  private JTextField getPositiveTF ()
  {
    if (positiveTF == null)
    {
      positiveTF = new JTextField ();
    }
    return positiveTF;
  }

  private JTextField getComparativeTF ()
  {
    if (comparativeTF == null)
    {
      comparativeTF = new JTextField ();
    }
    return comparativeTF;
  }

  private JTextField getSuperlativeTF ()
  {
    if (superlativeTF == null)
    {
      superlativeTF = new JTextField ();
    }
    return superlativeTF;
  }

  private JPanel getSpacerPanel ()
  {
    if (spacerPanel == null)
    {
      spacerPanel = new JPanel ();
      spacerPanel.setLayout(new GridBagLayout());
    }
    return spacerPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
