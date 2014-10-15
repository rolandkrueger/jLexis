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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/*
 * @author Roland Krueger
 * @version $Id: TextualAnswerPanel.java 127 2009-06-02 12:01:53Z roland $
 */
public class TextualAnswerPanel extends AbstractAnswerPanel
{
  private static final long serialVersionUID = -6603813645909408099L;
  private JTextField answerTextField = null;

  /**
   * This is the default constructor
   */
  public TextualAnswerPanel ()
  {
    super ();
    initialize ();
  }  

  @Override
  protected void requestFocusForInputElementImpl ()
  {
    answerTextField.requestFocusInWindow ();
  }
  
  @Override
  public void addCheckAnswerActionListenerImpl (ActionListener listener)
  {
    getAnswerTextField ().addActionListener (listener);    
  }
  
  @Override
  protected void removeCheckAnswerActionListener (ActionListener listener)
  {
    getAnswerTextField ().removeActionListener (listener);    
  }
  
  @Override
  public void reset ()
  {
    getAnswerTextField ().setText ("");
  }
  
  public String getAnswerText ()
  {
    return answerTextField.getText ();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new Insets(5, 5, 5, 5);
    gridBagConstraints.gridx = 0;
    this.setSize (300, 58);
    this.setLayout (new GridBagLayout ());
    this.add(getAnswerTextField(), gridBagConstraints);
  }

  /**
   * This method initializes answerTextField	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getAnswerTextField ()
  {
    if (answerTextField == null)
    {
      answerTextField = new JTextField ();
      answerTextField.requestFocusInWindow ();
    }
    return answerTextField;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
