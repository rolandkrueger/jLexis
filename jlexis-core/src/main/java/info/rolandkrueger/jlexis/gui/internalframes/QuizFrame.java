/*
 * Created on 05.04.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package info.rolandkrueger.jlexis.gui.internalframes;

import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.quiz.answerpanels.QuizPanel;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration;
import info.rolandkrueger.jlexis.util.TernaryLogicValues;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/*
 * @author Roland Krueger
 * @version $Id: QuizFrame.java 131 2009-07-03 15:49:12Z roland $
 */
public class QuizFrame extends JLexisInternalFrame
{
  private static final long serialVersionUID = 1994475352977019610L;

  private JPanel jContentPane = null;
  private QuizPanel quizPanel = null;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public void setCorrectAnswersPercentage (int total, int correct)
  {
    quizPanel.setCorrectAnswersPercentage (total, correct);  
  }
  
  public void setQuestion (AbstractQuizQuestion question, Vocable lastQuestionsAnswer, 
      TernaryLogicValues lastAnswerCorrect, String lastGivenAnswer, GeneralQuizConfiguration config)
  {
    getQuizPanel ().setQuestion (question, lastQuestionsAnswer, lastAnswerCorrect, lastGivenAnswer, config); 
  }
  
  public void setFinalQuestionsResult (Vocable lastQuestionsAnswer, 
      TernaryLogicValues lastAnswerCorrect, String lastGivenAnswer, GeneralQuizConfiguration config)
  {
    getQuizPanel ().setFinalQuestionsResult (lastQuestionsAnswer, lastAnswerCorrect, lastGivenAnswer, config);     
  }
  
  public void setOkButtonActionListener (ActionListener listener)
  {
    getQuizPanel ().setOkButtonActionListener (listener);
  }
  
  public void addCancelButtonActionListener (ActionListener listener)
  {
    getQuizPanel ().addCancelButtonActionListener (listener);
  }
  
  public void addFinishButtonActionListener (ActionListener listener)
  {
    getQuizPanel ().addFinishButtonActionListener (listener);
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * This is the default constructor
   */
  public QuizFrame ()
  {
    super ();
    initialize ();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (458, 486);
    this.setContentPane (getJContentPane ());
    setMaximizable(true);
    setResizable(true);
    setClosable(true);
    setIconifiable(true);
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane ()
  {
    if (jContentPane == null)
    {
      jContentPane = new JPanel ();
      jContentPane.setLayout (new BorderLayout ());
      jContentPane.add(getQuizPanel(), BorderLayout.CENTER);
    }
    return jContentPane;
  }

  /**
   * This method initializes quizPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.QuizPanel	
   */
  private QuizPanel getQuizPanel ()
  {
    if (quizPanel == null)
    {
      quizPanel = new QuizPanel ();
    }
    return quizPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
