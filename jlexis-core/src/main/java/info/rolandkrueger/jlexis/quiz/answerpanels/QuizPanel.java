/*
 * Created on 05.04.2009 
 *  
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package info.rolandkrueger.jlexis.quiz.answerpanels;

import info.rolandkrueger.jlexis.data.guidata.VocableDetailedViewModel;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.managers.answerpanelmanager.AnswerPanelHandle;
import info.rolandkrueger.jlexis.misc.JLexisUtils;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration;
import info.rolandkrueger.jlexis.util.TernaryLogicValues;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/*
 * @author Roland Krueger
 * @version $Id: QuizPanel.java 204 2009-12-17 15:20:16Z roland $
 */
public class QuizPanel extends JPanel
{
  private static final long serialVersionUID = -7446178511804634445L;
  private static final String OK_CANCEL_BUTTON_PANEL = "OK_CANCEL_BUTTON_PANEL";  //  @jve:decl-index=0:
  private static final String FINISH_BUTTON_PANEL = "FINISH_BUTTON_PANEL";  
  private static final String CONTINUE_BUTTON_PANEL = "CONTINUE_BUTTON_PANEL";
  private static final String EMPTY_PANEL = "EMPTY_PANEL";
  
  private JPanel upperPanel = null;
  private JPanel answerPanel = null;
  private JPanel okCancelBtnPanel = null;
  private JButton okButton = null;
  private ActionListener actionListener;
  private JPanel questionPanel = null;
  private JScrollPane questionPanelScrollPane = null;
  private JEditorPane quizQuestionView = null;
  private JPanel middlePanel = null;
  private JPanel progressBarPanel = null;
  private JProgressBar progressBar = null;
  private JSplitPane mainSplitPane = null;
  private JButton cancelButton = null;
  private JPanel finishBtnPanel = null;
  private JButton finishButton = null;
  private JPanel buttonPanel = null;  //  @jve:decl-index=0:visual-constraint="350,306"
  private JPanel continueBtnPanel = null;  //  @jve:decl-index=0:visual-constraint="314,215"
  private JButton continueButton = null;
  private QuestionRenderer questionRenderer;
  private CardLayout btnPanelCardLayout;
  private CardLayout answerPanelCardLayout;
  private Set<AnswerPanelHandle> answerPanelsOnCardLayout;
  private AbstractQuizQuestion currentQuestion;
  private GeneralQuizConfiguration currentConfig;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
    
  public void setCorrectAnswersPercentage (int total, int correct)
  {
    if (correct > total)
      throw new IllegalArgumentException ("Number of correct answers cannot be greater than " +
      		"the total number of questions.");
    
    float percentageFloat = ((float) correct / (float) total) * 100.0f;
    int percentage = Math.round (percentageFloat);
    getProgressBar ().setValue (percentage);
    // TODO: I18N
//    getProgressBar ().setString (String.format ("%d von %d Fragen richtig beantwortet (%d%%)",
//        correct, total, percentage));
    getProgressBar ().setString (String.format ("%d of %d questions answered correctly(%d%%)",
        correct, total, percentage));
  }
  
  public void setQuestion (AbstractQuizQuestion question, Vocable lastQuestionsAnswer, 
      TernaryLogicValues lastAnswerCorrect, String lastGivenAnswer, GeneralQuizConfiguration config)
  {
    if (question == null || config == null)
      throw new NullPointerException ("Question object and/or quiz configuration object is null.");
    
    System.out.println (lastAnswerCorrect);
    
    questionRenderer = new QuestionRenderer ();
    questionRenderer.setLastGivenAnswer (lastGivenAnswer);
    questionRenderer.setLastAnswerCorrectness (lastAnswerCorrect);
    questionRenderer.setQuestion (question);      
    questionRenderer.setSolutionVocable (lastQuestionsAnswer);      
    
    currentQuestion = question;
    currentConfig = config;
    
    if (lastQuestionsAnswer != null)
    {
      btnPanelCardLayout.show (getButtonPanel (), CONTINUE_BUTTON_PANEL);
      getContinueButton ().requestFocusInWindow ();
      answerPanelCardLayout.show (answerPanel, EMPTY_PANEL);
    } else
    {
      question.getAnswerPanel ().requestFocusForInputElement ();
      btnPanelCardLayout.show (getButtonPanel (), OK_CANCEL_BUTTON_PANEL);
      showCurrentQuestion ();
    }
    getQuizQuestionView ().setText (questionRenderer.getRenderResult ());
  }
  
  private void showCurrentQuestion ()
  {
    Language sourceLanguage = currentQuestion.getSourceLanguage ();
    if (currentConfig.isShowComments () && sourceLanguage != null)
    {
      questionRenderer.setComment (currentQuestion.getVocable ().getVariantInput (sourceLanguage).getComment ());
    } 
    if (currentConfig.isShowExample () && sourceLanguage != null)
    {
      questionRenderer.setExample (currentQuestion.getVocable ().getVariantInput (sourceLanguage).getExample ());
    }
    
    currentQuestion.getAnswerPanel ().reset ();
    
    if ( ! answerPanelsOnCardLayout.contains ((currentQuestion.getAnswerPanelHandle ())))
    {
      getAnswerPanel ().add (currentQuestion.getAnswerPanel (), currentQuestion.getAnswerPanelHandle ().toString ());
      answerPanelsOnCardLayout.add (currentQuestion.getAnswerPanelHandle ());
    }
    answerPanelCardLayout.show (getAnswerPanel (), currentQuestion.getAnswerPanelHandle ().toString ());
    
    if (actionListener != null)
    {
      currentQuestion.getAnswerPanel ().setCheckAnswerActionListener (actionListener);
    }
  }
  
  private void displayNextQuestion ()
  {
    questionRenderer.setSolutionVocable (null);
    questionRenderer.setLastGivenAnswer (null);
    questionRenderer.setLastAnswerCorrectness (null);
    questionRenderer.setComment (null);
    questionRenderer.setExample (null);
    btnPanelCardLayout.show (getButtonPanel (), OK_CANCEL_BUTTON_PANEL);
    getQuizQuestionView ().setText (questionRenderer.getRenderResult ());
    showCurrentQuestion ();
  }
  
  public void setFinalQuestionsResult (Vocable lastQuestionsAnswer, TernaryLogicValues lastAnswerCorrect,
      String lastGivenAnswer, GeneralQuizConfiguration config)
  {
    questionRenderer = new QuestionRenderer ();
    questionRenderer.setLastGivenAnswer (lastGivenAnswer);
    questionRenderer.setLastAnswerCorrectness (lastAnswerCorrect);
    questionRenderer.setSolutionVocable (lastQuestionsAnswer);
    getQuizQuestionView ().setText (questionRenderer.getRenderResult ());
    btnPanelCardLayout.show (getButtonPanel (), FINISH_BUTTON_PANEL);
    answerPanelCardLayout.show (answerPanel, EMPTY_PANEL);
  }
  
  public void setOkButtonActionListener (ActionListener listener)
  {
    if (listener == null)
      throw new NullPointerException ("Action listener is null");
    actionListener = listener;
    getOkButton ().addActionListener (listener);
  }
  
  public void addCancelButtonActionListener (ActionListener listener)
  {
    if (listener == null)
      throw new NullPointerException ("Action listener is null");
    getCancelButton ().addActionListener (listener);
  }
  
  public void addFinishButtonActionListener (ActionListener listener)
  {
    if (listener == null)
      throw new NullPointerException ("Action listener is null");
    getFinishButton ().addActionListener (listener);
  }
  
  private class QuestionRenderer 
  {
    private String mRenderResult;
    private AbstractQuizQuestion mQuestion;
    private TernaryLogicValues mLastAnswerCorrect;
    private Vocable mSolution;
    private String mLastGivenAnswer;
    private String mComment;
    private String mExample;
    
    public void setComment (String comment)
    {
      if (comment == null || comment.trim ().equals (""))
        mComment = null;
      else
        mComment = comment;
    }
    
    public void setExample (String example)
    {
      if (example == null || example.trim ().equals (""))
        mExample = null;
      else
        mExample = example;
    }
    
    public void setQuestion (AbstractQuizQuestion question)
    {
      mQuestion = question;
    }
    
    public void setLastGivenAnswer (String lastGivenAnswer)
    {
      mLastGivenAnswer = lastGivenAnswer;
    }

    public void setLastAnswerCorrectness (TernaryLogicValues wasCorrectOrIncorrect)
    {
      mLastAnswerCorrect = wasCorrectOrIncorrect;  
    }
    
    public void setSolutionVocable (Vocable solution)
    {
      mSolution = solution;
    }
    
    public String getRenderResult ()
    {
      // TODO: I18N
      String showAnswerCorrectString = "";
      if (mLastAnswerCorrect != null && mLastAnswerCorrect != TernaryLogicValues.UNKNOWN)
      {
        if (mLastAnswerCorrect == TernaryLogicValues.TRUE)
//          showAnswerCorrectString = "<br/><hr size=\"1\" /><font color=\"green\" size=\"+1\"><b>Richtig!</b></font><hr size=\"1\" /><br/>";
          showAnswerCorrectString = "<br/><hr size=\"1\" /><font color=\"green\" size=\"+1\"><b>Correct!</b></font><hr size=\"1\" /><br/>";
        else
        {
//          String text = "<font color=\"red\" size=\"+1\"><b>Das war leider falsch.</b></font>";
          String text = "<font color=\"red\" size=\"+1\"><b>Sorry, that was not correct.</b></font>";
          if (mLastGivenAnswer != null && ! mLastGivenAnswer.equals (""))
          {
//            text = String.format ("<b><font color=\"red\" size=\"+1\">Die Antwort</font><br/>" +
//                "<font size=\"+1\">\"%s\"</font><br/>" +
//                "<font color=\"red\" size=\"+1\">war leider falsch.</font></b>", 
//                LexisUtils.convertHTMLEntities (mLastGivenAnswer));
            text = String.format ("<b><font color=\"red\" size=\"+1\">The answer</font><br/>" +
            		"<font size=\"+1\">\"%s\"</font><br/>" +
            		"<font color=\"red\" size=\"+1\">was not correct.</font></b>", 
            		JLexisUtils.convertHTMLEntities (mLastGivenAnswer));
          }
          
          showAnswerCorrectString = String.format ("<br/><hr size=\"1\" />" +
          		"%s<hr size=\"1\" /><br/>", text);
        }
      }
      
      String solution = "";
      if (mSolution != null)
      {
        VocableDetailedViewModel viewModel = new VocableDetailedViewModel ();
        viewModel.setVocable (mSolution);
//        solution = String.format ("<hr size=\"1\" /><h2>L\u00f6sung der letzten Frage</h2>%s", 
//            viewModel.getDetailedText ());
        solution = String.format ("<hr size=\"1\" /><h2>Solution for the previous question</h2>%s", 
            viewModel.getDetailedText ());
      }
      
      String question = "";
      if (mQuestion != null && mSolution == null)
      {
        question = String.format ("<h2>%s</h2><b><font color=\"blue\" size=\"+1\">%s</font></b><br/>", mQuestion.getQuestionText (),
            mQuestion.getTextToTranslate ());
      }
      String comment = "";
      String example = "";
      if (mComment != null)
        // TODO: I18N
//        comment = String.format ("<br/><b>Kommentar:</b> %s", mComment);
      comment = String.format ("<br/><b>Comment:</b> %s", mComment);
      if (mExample != null)
//        example = String.format ("<br/><b>Beispiel:</b> %s", mExample);
        example = String.format ("<br/><b>Example:</b> %s", mExample);
      
      mRenderResult = String.format ("<html><center>%s%s%s%s%s</center></html>", showAnswerCorrectString, question, comment, example, solution);
      return mRenderResult;
    }
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////

  public QuizPanel ()
  {
    super ();
    initialize ();
  }

  private void initialize ()
  {
    this.setSize (269, 294);
    this.setLayout (new BorderLayout());
    this.add(getButtonPanel(), BorderLayout.SOUTH);
    this.add(getMainSplitPane(), BorderLayout.CENTER);
  }

  private JPanel getUpperPanel ()
  {
    if (upperPanel == null)
    {
      upperPanel = new JPanel ();
      upperPanel.setLayout(new BorderLayout());
      upperPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      upperPanel.setPreferredSize(new Dimension(100, 100));
      upperPanel.add(getQuestionPanel(), BorderLayout.CENTER);
    }
    return upperPanel;
  }

  private JPanel getAnswerPanel ()
  {
    if (answerPanel == null)
    {
      answerPanel = new JPanel ();
      answerPanelCardLayout = new CardLayout ();
      answerPanelsOnCardLayout = new HashSet<AnswerPanelHandle> ();
      answerPanel.setLayout(answerPanelCardLayout);
      answerPanel.add (new JPanel (), EMPTY_PANEL);
      // TODO: I18N
//      answerPanel.setBorder(BorderFactory.createTitledBorder(null, "Antwort", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      answerPanel.setBorder(BorderFactory.createTitledBorder(null, "Your answer", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    }
    return answerPanel;
  }

  private JPanel getOkCancelBtnPanel ()
  {
    if (okCancelBtnPanel == null)
    {
      okCancelBtnPanel = new JPanel ();
      okCancelBtnPanel.setLayout(new FlowLayout());
      okCancelBtnPanel.add(getOkButton(), null);
      okCancelBtnPanel.add(getCancelButton(), null);
    }
    return okCancelBtnPanel;
  }

  private JButton getOkButton ()
  {
    if (okButton == null)
    {
      okButton = new JButton ();
      okButton.setText("   Ok   ");
    }
    return okButton;
  }

  private JPanel getQuestionPanel ()
  {
    if (questionPanel == null)
    {
      questionPanel = new JPanel ();
      questionPanel.setLayout(new BorderLayout());
      questionPanel.add(getQuestionPanelScrollPane(), BorderLayout.CENTER);
    }
    return questionPanel;
  }

  private JScrollPane getQuestionPanelScrollPane ()
  {
    if (questionPanelScrollPane == null)
    {
      questionPanelScrollPane = new JScrollPane ();
      questionPanelScrollPane.setViewportView(getQuizQuestionView());
    }
    return questionPanelScrollPane;
  }

  private JEditorPane getQuizQuestionView ()
  {
    if (quizQuestionView == null)
    {
      quizQuestionView = new JEditorPane ();
      quizQuestionView.setContentType("text/html");
      quizQuestionView.setDoubleBuffered(true);
      quizQuestionView.setEditable(false);
    }
    return quizQuestionView;
  }

  private JPanel getMiddlePanel ()
  {
    if (middlePanel == null)
    {
      middlePanel = new JPanel ();
      middlePanel.setLayout(new BorderLayout());
      middlePanel.add(getProgressBarPanel(), BorderLayout.SOUTH);
      middlePanel.add(getAnswerPanel(), BorderLayout.CENTER);
    }
    return middlePanel;
  }

  private JPanel getProgressBarPanel ()
  {
    if (progressBarPanel == null)
    {
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.insets = new Insets(10, 10, 10, 10);
      gridBagConstraints.gridy = 0;
      progressBarPanel = new JPanel ();
      progressBarPanel.setLayout(new GridBagLayout());
      progressBarPanel.add(getProgressBar(), gridBagConstraints);
    }
    return progressBarPanel;
  }

  private JProgressBar getProgressBar ()
  {
    if (progressBar == null)
    {
      progressBar = new JProgressBar ();
      progressBar.setMaximum (100);
      progressBar.setMinimum (0);
      progressBar.setStringPainted(true);
      progressBar.setValue (0);
      progressBar.setString ("");
    }
    return progressBar;
  }

  private JSplitPane getMainSplitPane ()
  {
    if (mainSplitPane == null)
    {
      mainSplitPane = new JSplitPane ();
      mainSplitPane.setLeftComponent(getUpperPanel());
      mainSplitPane.setRightComponent(getMiddlePanel());
      mainSplitPane.setOrientation (JSplitPane.VERTICAL_SPLIT);
      mainSplitPane.setResizeWeight (1.0D);
    }
    return mainSplitPane;
  }

  private JButton getCancelButton ()
  {
    if (cancelButton == null)
    {
      cancelButton = new JButton ();
      // TODO: I18N
//      cancelButton.setText("Abbrechen");
      cancelButton.setText("Cancel");
    }
    return cancelButton;
  }

  private JPanel getFinishBtnPanel ()
  {
    if (finishBtnPanel == null)
    {
      finishBtnPanel = new JPanel ();
      finishBtnPanel.setLayout(new FlowLayout());
      finishBtnPanel.setName("finishBtnPanel");
      finishBtnPanel.add(getFinishButton(), null);
    }
    return finishBtnPanel;
  }

  private JButton getFinishButton ()
  {
    if (finishButton == null)
    {
      finishButton = new JButton ();
      // TODO: I18N
//      finishButton.setText("Beenden");
      finishButton.setText("Quit");
    }
    return finishButton;
  }

  private JPanel getButtonPanel ()
  {
    if (buttonPanel == null)
    {
      buttonPanel = new JPanel ();
      btnPanelCardLayout = new CardLayout();
      buttonPanel.setLayout(btnPanelCardLayout);
      buttonPanel.setSize(new Dimension(265, 65));
      buttonPanel.add(getFinishBtnPanel(), FINISH_BUTTON_PANEL);
      buttonPanel.add(getOkCancelBtnPanel (), OK_CANCEL_BUTTON_PANEL);
      buttonPanel.add(getContinueBtnPanel (), CONTINUE_BUTTON_PANEL);
      btnPanelCardLayout.show (buttonPanel, OK_CANCEL_BUTTON_PANEL);
    }
    return buttonPanel;
  }

  private JPanel getContinueBtnPanel ()
  {
    if (continueBtnPanel == null)
    {
      continueBtnPanel = new JPanel ();
      continueBtnPanel.setLayout(new FlowLayout());
      continueBtnPanel.setSize(new Dimension(256, 73));
      continueBtnPanel.add(getContinueButton(), null);
    }
    return continueBtnPanel;
  }

  private JButton getContinueButton ()
  {
    if (continueButton == null)
    {
      continueButton = new JButton ();
      // TODO: I18N
//      continueButton.setText("Weiter");
      continueButton.setText("Continue");
      continueButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          displayNextQuestion ();
        }});
    }
    return continueButton;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"




