/*
 * Created on 01.04.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package info.rolandkrueger.jlexis.quiz.answerpanels;

import info.rolandkrueger.jlexis.gui.containers.AbstractAnswerPanel;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion.QuizAnswerType;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/*
 * @author Roland Krueger
 * @version $Id: MultipleChoiceAnswerPanel.java 204 2009-12-17 15:20:16Z roland $
 */
public class MultipleChoiceAnswerPanel extends AbstractAnswerPanel implements ActionListener
{
  private static final long serialVersionUID = -1810987506356746174L;
  
  private static final String RADIO_BTN_PANEL = "RADIO_BTN_PANEL";  //  @jve:decl-index=0:
  private static final String CHECKBOX_BTN_PANEL = "CHECKBOX_BTN_PANEL";  //  @jve:decl-index=0:
  
  private List<JButton>       radioButtons;  //  @jve:decl-index=0:
  private List<JToggleButton> checkboxButtons;  //  @jve:decl-index=0:
  private QuizAnswerType answerType;
  private int numberOfChoices = 0;
  private CardLayout cardLayout;
  private JPanel radioBtnPanel = null;
  private JPanel checkboxBtnPanel = null;
  private int selectedIndex = -1;
  private ActionListener actionListener;
  
  /**
   * This is the default constructor
   */
  public MultipleChoiceAnswerPanel ()
  {
    super ();
    initialize ();
  }
  
  public MultipleChoiceAnswerPanel (int numberOfChoices)
  {
    this ();
    if (numberOfChoices < 2)
      throw new IllegalArgumentException ("Number of choices must not be less than 2.");
    
    this.numberOfChoices = numberOfChoices;
    
    for (int i = 0; i < numberOfChoices; i++)
    {
      JCheckBox checkbox = new JCheckBox ();
      checkboxButtons.add (checkbox);
      getCheckboxBtnPanel ().add (checkbox);
      
      JButton radioBtn = new JButton ();
      radioBtn.addActionListener (this);
      radioButtons.add (radioBtn);
      getRadioBtnPanel ().add (radioBtn);
    }
  }
  
  @Override
  public void actionPerformed (ActionEvent e)
  {
    for (int i = 0; i < numberOfChoices; i++)
    {
      if (e.getSource () == radioButtons.get (i))
      {
        selectedIndex = i;
        if (actionListener != null)
          actionListener.actionPerformed (e);
        return;        
      }
    }
  }

  @Override
  protected void requestFocusForInputElementImpl ()
  {
    // nothing to do here
  }
  
  @Override
  public void addCheckAnswerActionListenerImpl (ActionListener listener)
  {
    actionListener = listener;
  }

  @Override
  protected void removeCheckAnswerActionListener (ActionListener listener)
  {    
    for (JButton button : radioButtons)    
    {
      button.removeActionListener (listener);
    }    
  }
  
  @Override
  public void reset ()
  {
    for (JToggleButton button : checkboxButtons)
    {
      button.setSelected (false);
    }
  }
  
  public void setChoices (List<String> choices, QuizAnswerType answerType)
  {
    if (answerType != QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION &&
        answerType != QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION)
      throw new IllegalArgumentException ("Answer type must be one of the multiple choice values.");
    
    if (choices.size () != numberOfChoices)
      throw new IllegalArgumentException ("Mismatch between size of choice label list and predefined " +
      		"number of choices.");
    
    if (answerType == QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION)
      cardLayout.show (this, CHECKBOX_BTN_PANEL);
    else
      cardLayout.show (this, RADIO_BTN_PANEL);
    
    this.answerType = answerType;

    for (int i = 0; i < numberOfChoices; i++)
    {
      checkboxButtons.get (i).setText (choices.get (i)); 
      radioButtons.get    (i).setText (choices.get (i)); 
    }
  }
  
  public List<Integer> getSelectedIndices ()
  {
    List<Integer> result = new ArrayList<Integer> ();
    
    if (answerType == QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION)
    {
      for (int i = 0; i < checkboxButtons.size (); ++i)
      {
        JToggleButton btn = checkboxButtons.get (i);
        if (btn.isSelected ()) result.add (i);
      }
    } else
    {
      result.add (selectedIndex);
    }
    
    return result;
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    radioButtons    = new ArrayList<JButton> ();
    checkboxButtons = new ArrayList<JToggleButton> ();
    this.cardLayout = new CardLayout ();
    this.setLayout(cardLayout);
    this.setSize (300, 200);
    this.add(getRadioBtnPanel(), RADIO_BTN_PANEL);
    this.add(getCheckboxBtnPanel(), CHECKBOX_BTN_PANEL);
  }

  /**
   * This method initializes radioBtnPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getRadioBtnPanel ()
  {
    if (radioBtnPanel == null)
    {
      GridLayout gridLayout1 = new GridLayout();
      gridLayout1.setColumns(1);
      gridLayout1.setHgap(7);
      gridLayout1.setVgap(7);
      gridLayout1.setRows(0);
      radioBtnPanel = new JPanel ();
      radioBtnPanel.setName("radioBtnPanel");
      radioBtnPanel.setLayout(gridLayout1);
    }
    return radioBtnPanel;
  }

  /**
   * This method initializes checkboxBtnPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getCheckboxBtnPanel ()
  {
    if (checkboxBtnPanel == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      checkboxBtnPanel = new JPanel ();
      checkboxBtnPanel.setName("checkboxBtnPanel");
      checkboxBtnPanel.setLayout(gridLayout);
    }
    return checkboxBtnPanel;
  }
}