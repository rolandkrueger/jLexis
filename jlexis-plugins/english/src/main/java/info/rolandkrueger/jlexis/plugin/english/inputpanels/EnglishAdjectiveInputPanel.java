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
package info.rolandkrueger.jlexis.plugin.english.inputpanels;

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.DefaultUserInput;
import info.rolandkrueger.jlexis.data.vocable.UserInputInterface;
import info.rolandkrueger.jlexis.gui.containers.AbstractVocableInputPanel;
import info.rolandkrueger.jlexis.gui.containers.standardinputpanels.StandardAdjectiveInputPanel;
import info.rolandkrueger.jlexis.plugin.english.EnglishLanguagePlugin;
import info.rolandkrueger.jlexis.plugin.english.userinput.EnglishAdjectiveUserInput;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTabbedPane;

public class EnglishAdjectiveInputPanel extends AbstractVocableInputPanel
{
  private static final long serialVersionUID = 7578296197126978999L;
  private static final EnglishAdjectiveUserInput sExpectedInput = new EnglishAdjectiveUserInput ();  //  @jve:decl-index=0:
  
  private JTabbedPane beaeTabbedPane = null;
  private AdjectiveInputPanel beAdjectiveInputPanel = null;
  private AdjectiveInputPanel aeAdjectiveInputPanel = null;
  private EnglishLanguagePlugin mPlugin;
  
  public EnglishAdjectiveInputPanel (AbstractWordType correspondingWordType)
  {
    super (correspondingWordType);
		initialize();
  }

  public void setPlugin (EnglishLanguagePlugin plugin)
  {
    CheckForNull.check (plugin);
    mPlugin = plugin;
    getBeaeTabbedPane ().setIconAt (0, mPlugin.getIcon ());
    getBeaeTabbedPane ().setIconAt (1, mPlugin.getUSAFlagIcon ());
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.addAll (beAdjectiveInputPanel.getComponentsInFocusTraversalOrder ());
    result.addAll (aeAdjectiveInputPanel.getComponentsInFocusTraversalOrder ());
    return result;
  }
    
  /**
   * This method initializes this
   * 
   */
  private void initialize()
  {
    this.setLayout(new BorderLayout());
    this.setSize(new Dimension(477, 309));
    this.add(getBeaeTabbedPane(), BorderLayout.CENTER);
  }

  @Override
  public void clearInput ()
  {
    getBeAdjectiveInputPanel ().clearInput ();
    getAeAdjectiveInputPanel ().clearInput ();
  }

  @Override
  public AbstractUserInput getCurrentUserInput ()
  {
    EnglishAdjectiveUserInput result = new EnglishAdjectiveUserInput ();
    DefaultUserInput defaultUserInput = new DefaultUserInput ();
    getBeAdjectiveInputPanel ().getStandardInputFieldsPanel ().getCurrentUserInput (defaultUserInput);
    result.setBritishStandardValues (defaultUserInput);
    getAeAdjectiveInputPanel ().getStandardInputFieldsPanel ().getCurrentUserInput (defaultUserInput);
    result.setAmericanStandardValues (defaultUserInput);
    StandardAdjectiveInputPanel adjPanelBE = getBeAdjectiveInputPanel ().getStandardAdjectiveInputPanel ();
    StandardAdjectiveInputPanel adjPanelAE = getAeAdjectiveInputPanel ().getStandardAdjectiveInputPanel ();
    result.setPositiveBE (adjPanelBE.getPositive ());
    result.setPositiveAE (adjPanelAE.getPositive ());
    result.setComparativeBE (adjPanelBE.getComparative ());
    result.setComparativeAE (adjPanelAE.getComparative ());
    result.setSuperlativeBE (adjPanelBE.getSuperlative ());
    result.setSuperlativeAE (adjPanelAE.getSuperlative ());
    result.setIrregularBE (adjPanelBE.isIrregular ());
    result.setIrregularAE (adjPanelAE.isIrregular ());
    result.setNotComparableBE (adjPanelBE.isNotComparable ());
    result.setNotComparableAE (adjPanelAE.isNotComparable ());
    result.setAdjectiveUsageBE (getBeAdjectiveInputPanel ().getAdjectiveUsage ());
    result.setAdjectiveUsageAE (getAeAdjectiveInputPanel ().getAdjectiveUsage ());
    return result;
  }

  @Override
  public boolean isUserInputEmpty ()
  {
    return getBeAdjectiveInputPanel ().isUserInputEmpty () &&
      getAeAdjectiveInputPanel ().isUserInputEmpty ();
  }

  @Override
  public void setUserInput (UserInputInterface input)
  {
    CheckForNull.check (input);
    if ( ! sExpectedInput.isTypeCorrect (input))
      throw new IllegalStateException ("Passed wrong user input object. Expected " + EnglishAdjectiveUserInput.class.getName ()
          + " but was " + input.getClass ().getName ());
    EnglishAdjectiveUserInput englishInput = (EnglishAdjectiveUserInput) input;
    
    StandardAdjectiveInputPanel adjPanelBE = getBeAdjectiveInputPanel ().getStandardAdjectiveInputPanel ();
    StandardAdjectiveInputPanel adjPanelAE = getAeAdjectiveInputPanel ().getStandardAdjectiveInputPanel ();
    adjPanelBE.setPositive (englishInput.getPositiveBE ());
    adjPanelAE.setPositive (englishInput.getPositiveAE ());
    adjPanelBE.setComparative (englishInput.getComparativeBE ());
    adjPanelAE.setComparative (englishInput.getComparativeAE ());
    adjPanelBE.setSuperlative (englishInput.getSuperlativeBE ());
    adjPanelAE.setSuperlative (englishInput.getSuperlativeAE ());
    adjPanelBE.setIrregular (englishInput.isIrregularBE ());
    adjPanelAE.setIrregular (englishInput.isIrregularAE ());
    adjPanelBE.setNotComparable (englishInput.isNotComparableBE ());
    adjPanelAE.setNotComparable (englishInput.isNotComparableAE ());
    beAdjectiveInputPanel.setAdjectiveUsage (englishInput.getAdjectiveUsageBE ());
    aeAdjectiveInputPanel.setAdjectiveUsage (englishInput.getAdjectiveUsageAE ());
    beAdjectiveInputPanel.getStandardInputFieldsPanel ().setUserInput (englishInput.getBritishStandardValues ());
    aeAdjectiveInputPanel.getStandardInputFieldsPanel ().setUserInput (englishInput.getAmericanStandardValues ());    
  }

  /**
   * This method initializes beaeTabbedPane	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  private JTabbedPane getBeaeTabbedPane ()
  {
    if (beaeTabbedPane == null)
    {
      beaeTabbedPane = new JTabbedPane ();
      beaeTabbedPane.addTab("British English", null, getBeAdjectiveInputPanel(), null);
      beaeTabbedPane.addTab("American English", null, getAeAdjectiveInputPanel(), null);
    }
    return beaeTabbedPane;
  }

  /**
   * This method initializes beAdjectiveInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.plugin.english.inputpanels.AdjectiveInputPanel	
   */
  private AdjectiveInputPanel getBeAdjectiveInputPanel ()
  {
    if (beAdjectiveInputPanel == null)
    {
      beAdjectiveInputPanel = new AdjectiveInputPanel ();
    }
    return beAdjectiveInputPanel;
  }

  /**
   * This method initializes aeAdjectiveInputPanel	
   * 	
   * @return info.rolandkrueger.lexis.plugin.english.inputpanels.AdjectiveInputPanel	
   */
  private AdjectiveInputPanel getAeAdjectiveInputPanel ()
  {
    if (aeAdjectiveInputPanel == null)
    {
      aeAdjectiveInputPanel = new AdjectiveInputPanel ();
    }
    return aeAdjectiveInputPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
