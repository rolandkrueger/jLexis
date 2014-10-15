/*
 * DefaultVocableInputPanel.java
 * Created on 29.01.2007
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
package info.rolandkrueger.jlexis.gui.containers;

import info.rolandkrueger.jlexis.data.DefaultWordType;
import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.DefaultUserInput;
import info.rolandkrueger.jlexis.data.vocable.UserInputInterface;
import info.rolandkrueger.jlexis.data.vocable.standarduserinput.AbstractStandardUserInput;
import info.rolandkrueger.jlexis.gui.components.JLexisIPATextField;
import info.rolandkrueger.jlexis.gui.components.VocableTextField;
import info.rolandkrueger.jlexis.gui.containers.standardinputpanels.StandardInputFieldsPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class DefaultVocableInputPanel extends AbstractVocableInputPanel
{
  private static final long serialVersionUID = -5566687682572763186L;
  private static final DefaultUserInput sExpectedInput = new DefaultUserInput (); 
  
  private VocableTextField mTermTextField;
  private StandardInputFieldsPanel mStandardFieldsPanel;
  
  public DefaultVocableInputPanel (JLexisIPATextField ipaTextField, AbstractWordType wordType)
  {
    this (false, ipaTextField, wordType);
  }
  
  public DefaultVocableInputPanel (boolean nativeLanguagePanel, JLexisIPATextField ipaTextField, AbstractWordType wordType)
  {
    super (wordType);
    setLayout (new BorderLayout ());
    
    mTermTextField  = new VocableTextField ();
    mStandardFieldsPanel = new StandardInputFieldsPanel (nativeLanguagePanel, ipaTextField);
    // I18N
//    add (new TitledBorderPanel (I18NResources.getString (I18NResourceKeys.TERM_KEY) + ":", mTermTextField), BorderLayout.NORTH);
    add (new TitledBorderPanel ("Term:", mTermTextField), BorderLayout.NORTH);
    add (mStandardFieldsPanel, BorderLayout.CENTER);
    setMinimumSize(new Dimension(125, 20));
    setPreferredSize(new Dimension(125, 20));
  }
  
  public DefaultVocableInputPanel (boolean nativeLanguagePanel, AbstractWordType wordType)
  {
    this (nativeLanguagePanel, new JLexisIPATextField (), wordType);
  }
  
  public DefaultVocableInputPanel ()
  {
    this (false, new DefaultWordType ("N/A"));
  }
  
  @Override
  public List<Component> getComponentsInFocusTraversalOrder ()
  {
    List<Component> result = new LinkedList<Component> ();
    result.add    (mTermTextField);
    result.addAll (mStandardFieldsPanel.getComponentsInFocusTraversalOrder ());
    return result;
  }

  @Override
  public void clearInput ()
  {
    mTermTextField.setText     ("");
    mStandardFieldsPanel.clearInput ();
  }

  @Override
  public AbstractUserInput getCurrentUserInput ()
  {
    DefaultUserInput result = new DefaultUserInput ();
    result.addUserData (DefaultUserInput.TERM_KEY, mTermTextField.getText ());
    mStandardFieldsPanel.getCurrentUserInput (result);
    return result;
  }

  @Override
  public void setUserInput (UserInputInterface input)
  {
    if ( ! sExpectedInput.isTypeCorrect (input))
      throw new IllegalArgumentException (String.format ("The given %s is not of type %s.",
          AbstractUserInput.class.getName (), DefaultUserInput.class.getName ()));
    
    mTermTextField.setText (input.getUserData (DefaultUserInput.TERM_KEY).getUserEnteredTerm ());    
    mStandardFieldsPanel.setUserInput ((AbstractStandardUserInput) input);
  }

  @Override
  public boolean isUserInputEmpty ()
  {
    return mTermTextField.isUserInputEmpty () && mStandardFieldsPanel.isUserInputEmpty ();
  }
}
