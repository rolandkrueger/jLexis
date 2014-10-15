/*
 * Created on 22.05.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package info.rolandkrueger.jlexis.quiz.createquiz;

import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitSelectionPanel;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.IncorrectAnswerRepetitionEnum;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.QuestionOrderEnum;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.QuestionRepetitionEnum;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.QuestionStrictness;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.ShowCorrectAnswer;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.WhenToRepeatIncorrectAnswer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * @author Roland Krueger
 * @version $Id: QuizConfigurationPanel.java 134 2009-07-06 19:33:01Z roland $
 */
public class QuizConfigurationPanel extends JPanel
{
  private static final long serialVersionUID = 3799517001452294530L;

  private JTabbedPane tabbedPane = null;  //  @jve:decl-index=0:visual-constraint="10,10"
  private JPanel orderConfigPanel = null;
  private JPanel repetitionConfigPanel = null;
  private JPanel mixinConfigPanel = null;
  private JPanel displayConfigPanel = null;
  private JPanel strictnessConfigPanel = null;
  private JPanel orderButtonsPanel = null;
  private JRadioButton correctOrderRB = null;
  private JRadioButton randomOrderRB = null;
  private JPanel repetitionHelpPanel = null;
  private JPanel questionRepetitionPanel = null;
  private JRadioButton fixedQuestionRepetitionsRB = null;
  private JSpinner fixedQuestionRepetitionSpinner = null;
  private JPanel fixedRepetitionSpinnerPanel = null;
  private JPanel spacerPanel = null;
  private JRadioButton oneQuestionRepetitionRB = null;
  private JRadioButton endlessQuestionRepetitionRB = null;
  private JPanel incorrectAnswerRepetitionPanel = null;
  private JRadioButton repeatWhenIncorrectFixedRB = null;
  private JPanel fixedIncorrectRepetitionSpinnerPanel = null;
  private JSpinner fixedIncorrectRepetitionSpinner = null;
  private JPanel spacerPanel2 = null;
  private JRadioButton oneIncorrectAnswerRepetitionRB = null;
  private JRadioButton noIncorrectAnswerRepetitionRB = null;
  private JPanel whenToRepeatIncorrectAnswerPanel = null;
  private JRadioButton repeatImmediatelyRB = null;
  private JRadioButton repeatLaterRB = null;
  private JPanel spacerPanel3 = null;
  private JPanel strictnessButtonsPanel = null;
  private JRadioButton oneAnswerSufficesRB = null;
  private JRadioButton allAnswersRequiredRB = null;
  private JPanel showCorrectAnswerPanel = null;
  private JRadioButton alwaysShowCorrectAnswerRB = null;
  private JRadioButton showCorrectAnswerOnlyWhenIncorrectRB = null;
  private JPanel showCorrectAnswerHelpPanel = null;
  private JPanel showAdditionalDataPanel = null;
  private JCheckBox showExampleCheckBox = null;
  private JCheckBox showCommentCheckBox = null;
  private JCheckBox enableMixinCheckBox = null;
  private JPanel mixinConfigurationPanel = null;
  private JPanel mixinConfigurationHelpPanel = null;
  private JPanel mixinPercentagePanel = null;
  private JSlider mixinPercentageSlider = null;
  private JPanel mixinPercentageSpacer = null;
  private JLabel percentageLabel = null;
  private JPanel selectMixinUnitsPanel = null;
  private JPanel selectMixinUnitsRBPanel = null;
  private JRadioButton mixinAllUnitsFromLanguageRB = null;
  private JRadioButton selectMixinUnitsRB = null;
  private JSpinner mixinPercentageTF = null;
  private LearningUnitSelectionPanel learningUnitSelectionPanel = null;
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  private GeneralQuizConfiguration quizConfiguration; 
  
  public void setConfiguration (GeneralQuizConfiguration configuration)
  {
    if (configuration == null)
      throw new NullPointerException ("Configuration object is null.");
    
    quizConfiguration = configuration;
    if (quizConfiguration.getOrder () == QuestionOrderEnum.CORRECT_ORDER)
      getCorrectOrderRB ().setSelected (true);
    if (quizConfiguration.getOrder () == QuestionOrderEnum.RANDOM)
      getRandomOrderRB ().setSelected (true);
    
    fixedQuestionRepetitionSpinner.setValue (quizConfiguration.getRepeatQuestionsCount ());
    if (quizConfiguration.getQuestionRepetition () == QuestionRepetitionEnum.FIXED)
      getFixedQuestionRepetitionsRB ().setSelected (true);
    if (quizConfiguration.getQuestionRepetition () == QuestionRepetitionEnum.ONCE)
      getOneQuestionRepetitionRB ().setSelected (true);
    if (quizConfiguration.getQuestionRepetition () == QuestionRepetitionEnum.ENDLESS)
      getEndlessQuestionRepetitionRB ().setSelected (true);
    
    fixedIncorrectRepetitionSpinner.setValue (quizConfiguration.getRepeatIncorrectAnswerCount ());
    if (quizConfiguration.getIncorrectAnswerRepetition () == IncorrectAnswerRepetitionEnum.FIXED)
      repeatWhenIncorrectFixedRB.setSelected (true);
    if (quizConfiguration.getIncorrectAnswerRepetition () == IncorrectAnswerRepetitionEnum.ONCE)
      oneIncorrectAnswerRepetitionRB.setSelected (true);
    if (quizConfiguration.getIncorrectAnswerRepetition () == IncorrectAnswerRepetitionEnum.NONE)
      noIncorrectAnswerRepetitionRB.setSelected (true);
    
    if (quizConfiguration.getWhenToRepeat () == WhenToRepeatIncorrectAnswer.IMMEDIATELY)
      getRepeatImmediatelyRB ().setSelected (true);
    else
      getRepeatLaterRB ().setSelected (true);
    
    getEnableMixinCheckBox ().setSelected (quizConfiguration.isDoMixinUnits ());
    mixinPercentageSlider.setValue (quizConfiguration.getMixinPercentage ());
    getMixinAllUnitsFromLanguageRB ().setSelected (quizConfiguration.isMixinAllUnits ());
    if (quizConfiguration.getShowCorrectAnswer () == ShowCorrectAnswer.ALWAYS)
      getAlwaysShowCorrectAnswerRB ().setSelected (true);
    else
      getShowCorrectAnswerOnlyWhenIncorrectRB ().setSelected (true);
    getShowCommentCheckBox ().setSelected (quizConfiguration.isShowComments ());
    getShowExampleCheckBox ().setSelected (quizConfiguration.isShowExample ());
    if (quizConfiguration.getQuestionStrictness () == QuestionStrictness.SOME)
      getOneAnswerSufficesRB ().setSelected (true);
    else
      getAllAnswersRequiredRB ().setSelected (true);
  }
  
  public GeneralQuizConfiguration getConfiguration ()
  {
    if (quizConfiguration == null)
      quizConfiguration = new GeneralQuizConfiguration ();
    
    // set ordering of the questions
    if (getCorrectOrderRB ().isSelected ())
      quizConfiguration.setOrder (QuestionOrderEnum.CORRECT_ORDER);
    if (getRandomOrderRB ().isSelected ())
      quizConfiguration.setOrder (QuestionOrderEnum.RANDOM);
    
    // set repetitions of the questions
    quizConfiguration.setRepeatQuestionsCount (Integer.valueOf (fixedQuestionRepetitionSpinner.getValue ().toString ()));
    if (getFixedQuestionRepetitionsRB ().isSelected ())
      quizConfiguration.setQuestionRepetition (QuestionRepetitionEnum.FIXED);
    if (getOneQuestionRepetitionRB ().isSelected ())    
      quizConfiguration.setQuestionRepetition (QuestionRepetitionEnum.ONCE);
    if (getEndlessQuestionRepetitionRB ().isSelected ())
      quizConfiguration.setQuestionRepetition (QuestionRepetitionEnum.ENDLESS);
    
    // set repetitions of incorrectly answered questions
    quizConfiguration.setRepeatIncorrectAnswerCount (Integer.valueOf (fixedIncorrectRepetitionSpinner.getValue ().toString ()));
    if (repeatWhenIncorrectFixedRB.isSelected ())
      quizConfiguration.setIncorrectAnswerRepetition (IncorrectAnswerRepetitionEnum.FIXED);
    if (oneIncorrectAnswerRepetitionRB.isSelected ())
      quizConfiguration.setIncorrectAnswerRepetition (IncorrectAnswerRepetitionEnum.ONCE);
    if (noIncorrectAnswerRepetitionRB.isSelected ())
      quizConfiguration.setIncorrectAnswerRepetition (IncorrectAnswerRepetitionEnum.NONE);
    
    // set the point of time when to repeat incorrectly answered questions
    if (repeatImmediatelyRB.isSelected ())
      quizConfiguration.setWhenToRepeat (WhenToRepeatIncorrectAnswer.IMMEDIATELY);
    else
      quizConfiguration.setWhenToRepeat (WhenToRepeatIncorrectAnswer.LATER);
    
    // set the mixed in learning units
    quizConfiguration.setDoMixinUnits (getEnableMixinCheckBox ().isSelected ());
    quizConfiguration.setMixinPercentage (mixinPercentageSlider.getValue ());
    quizConfiguration.setMixinAllUnits (getMixinAllUnitsFromLanguageRB ().isSelected ());
    quizConfiguration.setMixinUnits (new ArrayList<LearningUnit> (
        getLearningUnitSelectionPanel ().getSelectedLearningUnits ()));
    
    // set the display options    
    if (getAlwaysShowCorrectAnswerRB ().isSelected ())
      quizConfiguration.setShowCorrectAnswer (ShowCorrectAnswer.ALWAYS);
    else
      quizConfiguration.setShowCorrectAnswer (ShowCorrectAnswer.ONLY_WHEN_INCORRECT);
    quizConfiguration.setShowComments (getShowCommentCheckBox ().isSelected ());
    quizConfiguration.setShowExample (getShowExampleCheckBox ().isSelected ());
    
    // set the question strictness
    if (getOneAnswerSufficesRB ().isSelected ())
      quizConfiguration.setQuestionStrictness (QuestionStrictness.SOME);
    else
      quizConfiguration.setQuestionStrictness (QuestionStrictness.ALL);
      
    return quizConfiguration;
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * This is the default constructor
   */
  public QuizConfigurationPanel ()
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
    this.setSize (373, 355);
    this.setLayout (new BorderLayout());
    this.add(getTabbedPane(), BorderLayout.CENTER);
  }

  /**
   * This method initializes tabbedPane	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  private JTabbedPane getTabbedPane ()
  {
    if (tabbedPane == null)
    {
      tabbedPane = new JTabbedPane ();
      tabbedPane.setTabPlacement(JTabbedPane.TOP);
      tabbedPane.setSize(new Dimension(372, 337));
      // TODO: I18N
//      tabbedPane.addTab("Abfragereihenfolge", null, getOrderConfigPanel(), null);
//      tabbedPane.addTab("Wiederholungen", null, getRepetitionConfigPanel(), null);
//      tabbedPane.addTab("Untermischungen", null, getMixinConfigPanel(), null);
//      tabbedPane.addTab("Anzeige", null, getDisplayConfigPanel(), null);
//      tabbedPane.addTab("Abfragestrenge", null, getStrictnessConfigPanel(), "");
      tabbedPane.addTab("Testing order", null, getOrderConfigPanel(), null);
      tabbedPane.addTab("Repetitions", null, getRepetitionConfigPanel(), null);
      tabbedPane.addTab("Mix-ins", null, getMixinConfigPanel(), null);
      tabbedPane.addTab("Display", null, getDisplayConfigPanel(), null);
      tabbedPane.addTab("Test strictness", null, getStrictnessConfigPanel(), "");
    }
    return tabbedPane;
  }

  /**
   * This method initializes orderConfigPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getOrderConfigPanel ()
  {
    if (orderConfigPanel == null)
    {
      orderConfigPanel = new JPanel ();
      orderConfigPanel.setLayout(new BorderLayout());
      orderConfigPanel.add(getOrderButtonsPanel(), BorderLayout.NORTH);
    }
    return orderConfigPanel;
  }

  /**
   * This method initializes repetitionConfigPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getRepetitionConfigPanel ()
  {
    if (repetitionConfigPanel == null)
    {
      repetitionConfigPanel = new JPanel ();
      repetitionConfigPanel.setLayout(new BorderLayout());
      repetitionConfigPanel.add(getRepetitionHelpPanel(), BorderLayout.NORTH);
    }
    return repetitionConfigPanel;
  }

  /**
   * This method initializes mixinConfigPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMixinConfigPanel ()
  {
    if (mixinConfigPanel == null)
    {
      mixinConfigPanel = new JPanel ();
      mixinConfigPanel.setLayout(new BorderLayout());
      mixinConfigPanel.add(getEnableMixinCheckBox(), BorderLayout.NORTH);
      mixinConfigPanel.add(getMixinConfigurationPanel(), BorderLayout.CENTER);
    }
    return mixinConfigPanel;
  }

  /**
   * This method initializes displayConfigPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getDisplayConfigPanel ()
  {
    if (displayConfigPanel == null)
    {
      displayConfigPanel = new JPanel ();
      displayConfigPanel.setLayout(new BorderLayout());
      displayConfigPanel.add(getShowCorrectAnswerHelpPanel(), BorderLayout.NORTH);
    }
    return displayConfigPanel;
  }

  /**
   * This method initializes strictnessConfigPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getStrictnessConfigPanel ()
  {
    if (strictnessConfigPanel == null)
    {
      strictnessConfigPanel = new JPanel ();
      strictnessConfigPanel.setLayout(new BorderLayout());
      strictnessConfigPanel.add(getStrictnessButtonsPanel(), BorderLayout.NORTH);
    }
    return strictnessConfigPanel;
  }

  /**
   * This method initializes orderButtonsPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getOrderButtonsPanel ()
  {
    if (orderButtonsPanel == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      ButtonGroup group = new ButtonGroup ();
      group.add (getCorrectOrderRB());
      group.add (getRandomOrderRB ());
      orderButtonsPanel = new JPanel ();
      orderButtonsPanel.setLayout(gridLayout);
      orderButtonsPanel.add(getCorrectOrderRB(), null);
      orderButtonsPanel.add(getRandomOrderRB(), null);
    }
    return orderButtonsPanel;
  }

  /**
   * This method initializes correctOrderRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getCorrectOrderRB ()
  {
    if (correctOrderRB == null)
    {
      correctOrderRB = new JRadioButton ();
      // TODO: I18N
//      correctOrderRB.setText("der Reihe nach");
      correctOrderRB.setText("sequential");
      correctOrderRB.setSelected (true);
    }
    return correctOrderRB;
  }

  /**
   * This method initializes randomOrderRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getRandomOrderRB ()
  {
    if (randomOrderRB == null)
    {
      randomOrderRB = new JRadioButton ();
      // TODO: I18N
//      randomOrderRB.setText("durcheinander");
      randomOrderRB.setText("randomized");
    }
    return randomOrderRB;
  }

  /**
   * This method initializes repetitionHelpPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getRepetitionHelpPanel ()
  {
    if (repetitionHelpPanel == null)
    {
      GridLayout gridLayout2 = new GridLayout();
      gridLayout2.setColumns(1);
      gridLayout2.setRows(0);
      repetitionHelpPanel = new JPanel ();
      repetitionHelpPanel.setLayout(gridLayout2);
      repetitionHelpPanel.add(getQuestionRepetitionPanel(), null);
      repetitionHelpPanel.add(getIncorrectAnswerRepetitionPanel(), null);
      repetitionHelpPanel.add(getWhenToRepeatIncorrectAnswerPanel(), null);
    }
    return repetitionHelpPanel;
  }

  /**
   * This method initializes questionRepetitionPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getQuestionRepetitionPanel ()
  {
    if (questionRepetitionPanel == null)
    {
      GridLayout gridLayout1 = new GridLayout();
      gridLayout1.setColumns(1);
      gridLayout1.setRows(0);
      questionRepetitionPanel = new JPanel ();
      // TODO: I18N
//      questionRepetitionPanel.setBorder(BorderFactory.createTitledBorder(null, "Wiederholung der Fragen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      questionRepetitionPanel.setBorder(BorderFactory.createTitledBorder(null, "Question repetition", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      questionRepetitionPanel.setLayout(gridLayout1);
      ButtonGroup group = new ButtonGroup ();
      group.add (getFixedQuestionRepetitionsRB ());
      group.add (getOneQuestionRepetitionRB ());
      group.add (getEndlessQuestionRepetitionRB ());
      questionRepetitionPanel.add(getFixedQuestionRepetitionsRB(), null);
      questionRepetitionPanel.add(getFixedRepetitionSpinnerPanel(), null);
      questionRepetitionPanel.add(getOneQuestionRepetitionRB(), null);
      questionRepetitionPanel.add(getEndlessQuestionRepetitionRB(), null);
    }
    return questionRepetitionPanel;
  }

  /**
   * This method initializes fixedQuestionRepetitionsRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getFixedQuestionRepetitionsRB ()
  {
    if (fixedQuestionRepetitionsRB == null)
    {
      fixedQuestionRepetitionsRB = new JRadioButton ();
      // TODO: I18N
//      fixedQuestionRepetitionsRB.setText("feste Anzahl");
      fixedQuestionRepetitionsRB.setText("fixed amount");
      fixedQuestionRepetitionsRB.setSelected(true);
      
      fixedQuestionRepetitionsRB.addItemListener (new ItemListener ()
      {
        @Override
        public void itemStateChanged (ItemEvent e)
        {
          if (e.getStateChange () == ItemEvent.SELECTED)
          {
            fixedQuestionRepetitionSpinner.setEnabled (true);
          } else
          {
            fixedQuestionRepetitionSpinner.setEnabled (false);
          }
        }
      });
    }
    return fixedQuestionRepetitionsRB;
  }

  /**
   * This method initializes fixedQuestionRepetitionSpinner	
   * 	
   * @return javax.swing.JSpinner	
   */
  private JSpinner getFixedQuestionRepetitionSpinner ()
  {
    if (fixedQuestionRepetitionSpinner == null)
    {
      fixedQuestionRepetitionSpinner = new JSpinner ();
      fixedQuestionRepetitionSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
    }
    return fixedQuestionRepetitionSpinner;
  }

  /**
   * This method initializes fixedRepetitionSpinnerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getFixedRepetitionSpinnerPanel ()
  {
    if (fixedRepetitionSpinnerPanel == null)
    {
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 1;
      gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints1.weightx = 0.5;
      gridBagConstraints1.gridy = 0;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints.gridwidth = 1;
      gridBagConstraints.anchor = GridBagConstraints.CENTER;
      gridBagConstraints.weightx = 0.1;
      gridBagConstraints.insets = new Insets(0, 10, 0, 10);
      gridBagConstraints.gridy = 0;
      fixedRepetitionSpinnerPanel = new JPanel ();
      fixedRepetitionSpinnerPanel.setLayout(new GridBagLayout());
      fixedRepetitionSpinnerPanel.add(getFixedQuestionRepetitionSpinner(), gridBagConstraints);
      fixedRepetitionSpinnerPanel.add(getSpacerPanel(), gridBagConstraints1);
    }
    return fixedRepetitionSpinnerPanel;
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
   * This method initializes oneQuestionRepetitionRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getOneQuestionRepetitionRB ()
  {
    if (oneQuestionRepetitionRB == null)
    {
      oneQuestionRepetitionRB = new JRadioButton ();
      // TODO: I18N
//      oneQuestionRepetitionRB.setText("einmal");
      oneQuestionRepetitionRB.setText("once");
    }
    return oneQuestionRepetitionRB;
  }

  /**
   * This method initializes endlessQuestionRepetitionRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getEndlessQuestionRepetitionRB ()
  {
    if (endlessQuestionRepetitionRB == null)
    {
      endlessQuestionRepetitionRB = new JRadioButton ();
      // TODO: I18N
//      endlessQuestionRepetitionRB.setText("endlos");
      endlessQuestionRepetitionRB.setText("endless");
    }
    return endlessQuestionRepetitionRB;
  }

  /**
   * This method initializes incorrectAnswerRepetitionPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getIncorrectAnswerRepetitionPanel ()
  {
    if (incorrectAnswerRepetitionPanel == null)
    {
      GridLayout gridLayout3 = new GridLayout();
      gridLayout3.setColumns(1);
      gridLayout3.setRows(0);
      incorrectAnswerRepetitionPanel = new JPanel ();
      // TODO: I18N
//      incorrectAnswerRepetitionPanel.setBorder(BorderFactory.createTitledBorder(null, "Wiederholung bei falscher Antwort", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      incorrectAnswerRepetitionPanel.setBorder(BorderFactory.createTitledBorder(null, "Repeat when answer was incorrect", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      incorrectAnswerRepetitionPanel.setLayout(gridLayout3);
      ButtonGroup group = new ButtonGroup ();
      group.add (getRepeatWhenIncorrectFixedRB ());
      group.add (getOneIncorrectAnswerRepetitionRB ());
      group.add (getNoIncorrectAnswerRepetitionRB ());
      incorrectAnswerRepetitionPanel.add(getRepeatWhenIncorrectFixedRB(), null);
      incorrectAnswerRepetitionPanel.add(getFixedIncorrectRepetitionSpinnerPanel(), null);
      incorrectAnswerRepetitionPanel.add(getOneIncorrectAnswerRepetitionRB(), null);
      incorrectAnswerRepetitionPanel.add(getNoIncorrectAnswerRepetitionRB(), null);
    }
    return incorrectAnswerRepetitionPanel;
  }

  /**
   * This method initializes repeatWhenIncorrectFixedRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getRepeatWhenIncorrectFixedRB ()
  {
    if (repeatWhenIncorrectFixedRB == null)
    {
      repeatWhenIncorrectFixedRB = new JRadioButton ();
      // TODO: I18N
//      repeatWhenIncorrectFixedRB.setText("feste Anzahl von Versuchen");
      repeatWhenIncorrectFixedRB.setText("fixed number of attempts");
      repeatWhenIncorrectFixedRB.setSelected(true);
      repeatWhenIncorrectFixedRB.addItemListener (new ItemListener ()
      {
        @Override
        public void itemStateChanged (ItemEvent e)
        {
          if (e.getStateChange () == ItemEvent.SELECTED)
          {
            fixedIncorrectRepetitionSpinner.setEnabled (true);
          } else
          {
            fixedIncorrectRepetitionSpinner.setEnabled (false);
          }
        }
      });
    }
    return repeatWhenIncorrectFixedRB;
  }

  /**
   * This method initializes fixedIncorrectRepetitionSpinnerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getFixedIncorrectRepetitionSpinnerPanel ()
  {
    if (fixedIncorrectRepetitionSpinnerPanel == null)
    {
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 1;
      gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints3.weightx = 0.5;
      gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
      gridBagConstraints3.gridy = 0;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.weightx = 0.1;
      gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints2.insets = new Insets(0, 10, 0, 10);
      gridBagConstraints2.gridy = 0;
      fixedIncorrectRepetitionSpinnerPanel = new JPanel ();
      fixedIncorrectRepetitionSpinnerPanel.setLayout(new GridBagLayout());
      fixedIncorrectRepetitionSpinnerPanel.add(getFixedIncorrectRepetitionSpinner(), gridBagConstraints2);
      fixedIncorrectRepetitionSpinnerPanel.add(getSpacerPanel2(), gridBagConstraints3);
    }
    return fixedIncorrectRepetitionSpinnerPanel;
  }

  /**
   * This method initializes fixedIncorrectRepetitionSpinner	
   * 	
   * @return javax.swing.JSpinner	
   */
  private JSpinner getFixedIncorrectRepetitionSpinner ()
  {
    if (fixedIncorrectRepetitionSpinner == null)
    {
      fixedIncorrectRepetitionSpinner = new JSpinner ();
      fixedIncorrectRepetitionSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
    }
    return fixedIncorrectRepetitionSpinner;
  }

  /**
   * This method initializes spacerPanel2	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSpacerPanel2 ()
  {
    if (spacerPanel2 == null)
    {
      spacerPanel2 = new JPanel ();
      spacerPanel2.setLayout(new GridBagLayout());
    }
    return spacerPanel2;
  }

  /**
   * This method initializes oneIncorrectAnswerRepetitionRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getOneIncorrectAnswerRepetitionRB ()
  {
    if (oneIncorrectAnswerRepetitionRB == null)
    {
      oneIncorrectAnswerRepetitionRB = new JRadioButton ();
      // TODO: I18N
//      oneIncorrectAnswerRepetitionRB.setText("einmal");
      oneIncorrectAnswerRepetitionRB.setText("once");
    }
    return oneIncorrectAnswerRepetitionRB;
  }

  /**
   * This method initializes noIncorrectAnswerRepetitionRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getNoIncorrectAnswerRepetitionRB ()
  {
    if (noIncorrectAnswerRepetitionRB == null)
    {
      noIncorrectAnswerRepetitionRB = new JRadioButton ();
      // TODO: I18N
//      noIncorrectAnswerRepetitionRB.setText("keine Wiederholung");
      noIncorrectAnswerRepetitionRB.setText("no repetition");
    }
    return noIncorrectAnswerRepetitionRB;
  }

  /**
   * This method initializes whenToRepeatIncorrectAnswerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getWhenToRepeatIncorrectAnswerPanel ()
  {
    if (whenToRepeatIncorrectAnswerPanel == null)
    {
      GridLayout gridLayout4 = new GridLayout();
      gridLayout4.setColumns(1);
      gridLayout4.setRows(0);
      whenToRepeatIncorrectAnswerPanel = new JPanel ();
      whenToRepeatIncorrectAnswerPanel.setLayout(gridLayout4);
      ButtonGroup group = new ButtonGroup ();
      group.add (getRepeatImmediatelyRB ());
      group.add (getRepeatLaterRB ());
      // TODO: I18N
//      whenToRepeatIncorrectAnswerPanel.setBorder(BorderFactory.createTitledBorder(null, "Zeitpunkt der Wiederholung bei falscher Antwort", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      whenToRepeatIncorrectAnswerPanel.setBorder(BorderFactory.createTitledBorder(null, "When to repeat when answer was incorrect", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      whenToRepeatIncorrectAnswerPanel.add(getRepeatImmediatelyRB(), null);
      whenToRepeatIncorrectAnswerPanel.add(getRepeatLaterRB(), null);
      whenToRepeatIncorrectAnswerPanel.add(getSpacerPanel3(), null);
    }
    return whenToRepeatIncorrectAnswerPanel;
  }

  /**
   * This method initializes repeatImmediatelyRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getRepeatImmediatelyRB ()
  {
    if (repeatImmediatelyRB == null)
    {
      repeatImmediatelyRB = new JRadioButton ();
      // TODO: I18N
//      repeatImmediatelyRB.setText("sofort");
      repeatImmediatelyRB.setText("immediately");
      repeatImmediatelyRB.setSelected(true);
    }
    return repeatImmediatelyRB;
  }

  /**
   * This method initializes repeatLaterRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getRepeatLaterRB ()
  {
    if (repeatLaterRB == null)
    {
      repeatLaterRB = new JRadioButton ();
      // TODO: I18N
//      repeatLaterRB.setText("zu einem sp\u00E4teren Zeitpunkt");
      repeatLaterRB.setText("anytime later");
    }
    return repeatLaterRB;
  }

  /**
   * This method initializes spacerPanel3	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSpacerPanel3 ()
  {
    if (spacerPanel3 == null)
    {
      spacerPanel3 = new JPanel ();
      spacerPanel3.setLayout(new GridBagLayout());
    }
    return spacerPanel3;
  }

  /**
   * This method initializes strictnessButtonsPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getStrictnessButtonsPanel ()
  {
    if (strictnessButtonsPanel == null)
    {
      GridLayout gridLayout5 = new GridLayout();
      gridLayout5.setColumns(1);
      gridLayout5.setRows(0);
      strictnessButtonsPanel = new JPanel ();
      strictnessButtonsPanel.setLayout(gridLayout5);
      ButtonGroup group = new ButtonGroup ();
      group.add (getOneAnswerSufficesRB ());
      group.add (getAllAnswersRequiredRB ());
      strictnessButtonsPanel.add(getOneAnswerSufficesRB(), null);
      strictnessButtonsPanel.add(getAllAnswersRequiredRB(), null);
    }
    return strictnessButtonsPanel;
  }

  /**
   * This method initializes oneAnswerSufficesRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getOneAnswerSufficesRB ()
  {
    if (oneAnswerSufficesRB == null)
    {
      oneAnswerSufficesRB = new JRadioButton ();
      // TODO: I18N
//      oneAnswerSufficesRB.setText("einer der geforderten Begriffe gen\u00FCgt");
      oneAnswerSufficesRB.setText("one of the tested terms suffices");
    }
    return oneAnswerSufficesRB;
  }

  /**
   * This method initializes allAnswersRequiredRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getAllAnswersRequiredRB ()
  {
    if (allAnswersRequiredRB == null)
    {
      allAnswersRequiredRB = new JRadioButton ();
      // TODO: I18N
//      allAnswersRequiredRB.setText("alle geforderten Begriffe m\u00fcssen angegeben werden");
      allAnswersRequiredRB.setText("all tested terms have to be provided");
      allAnswersRequiredRB.setSelected(true);
    }
    return allAnswersRequiredRB;
  }

  /**
   * This method initializes showCorrectAnswerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getShowCorrectAnswerPanel ()
  {
    if (showCorrectAnswerPanel == null)
    {
      GridLayout gridLayout6 = new GridLayout();
      gridLayout6.setColumns(1);
      gridLayout6.setRows(0);
      showCorrectAnswerPanel = new JPanel ();
      // TODO: I18N
//      showCorrectAnswerPanel.setBorder(BorderFactory.createTitledBorder(null, "Anzeige der korrekten Antwort", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      showCorrectAnswerPanel.setBorder(BorderFactory.createTitledBorder(null, "Display of the correct answer", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      showCorrectAnswerPanel.setLayout(gridLayout6);
      ButtonGroup group = new ButtonGroup ();
      group.add (getAlwaysShowCorrectAnswerRB ());
      group.add (getShowCorrectAnswerOnlyWhenIncorrectRB ());
      showCorrectAnswerPanel.add(getAlwaysShowCorrectAnswerRB(), null);
      showCorrectAnswerPanel.add(getShowCorrectAnswerOnlyWhenIncorrectRB(), null);
    }
    return showCorrectAnswerPanel;
  }

  /**
   * This method initializes alwaysShowCorrectAnswerRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getAlwaysShowCorrectAnswerRB ()
  {
    if (alwaysShowCorrectAnswerRB == null)
    {
      alwaysShowCorrectAnswerRB = new JRadioButton ();
      // TODO: I18N      
//      alwaysShowCorrectAnswerRB.setText("immer");
      alwaysShowCorrectAnswerRB.setText("always");
    }
    return alwaysShowCorrectAnswerRB;
  }

  /**
   * This method initializes showCorrectAnswerOnlyWhenIncorrectRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getShowCorrectAnswerOnlyWhenIncorrectRB ()
  {
    if (showCorrectAnswerOnlyWhenIncorrectRB == null)
    {
      showCorrectAnswerOnlyWhenIncorrectRB = new JRadioButton ();
      // TODO: I18N
//      showCorrectAnswerOnlyWhenIncorrectRB.setText("nur bei falscher Antwort");
      showCorrectAnswerOnlyWhenIncorrectRB.setText("only when answer was incorrect");
      showCorrectAnswerOnlyWhenIncorrectRB.setSelected(true);
    }
    return showCorrectAnswerOnlyWhenIncorrectRB;
  }

  /**
   * This method initializes showCorrectAnswerHelpPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getShowCorrectAnswerHelpPanel ()
  {
    if (showCorrectAnswerHelpPanel == null)
    {
      GridLayout gridLayout7 = new GridLayout();
      gridLayout7.setColumns(1);
      gridLayout7.setRows(0);
      showCorrectAnswerHelpPanel = new JPanel ();
      showCorrectAnswerHelpPanel.setLayout(gridLayout7);
      showCorrectAnswerHelpPanel.add(getShowCorrectAnswerPanel(), null);
      showCorrectAnswerHelpPanel.add(getShowAdditionalDataPanel(), null);
    }
    return showCorrectAnswerHelpPanel;
  }

  /**
   * This method initializes showAdditionalDataPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getShowAdditionalDataPanel ()
  {
    if (showAdditionalDataPanel == null)
    {
      GridLayout gridLayout8 = new GridLayout();
      gridLayout8.setColumns(1);
      gridLayout8.setRows(0);
      showAdditionalDataPanel = new JPanel ();
      // TODO: I18N
//      showAdditionalDataPanel.setBorder(BorderFactory.createTitledBorder(null, "Anzeige von zus\u00e4tzlichen Informationen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      showAdditionalDataPanel.setBorder(BorderFactory.createTitledBorder(null, "Display of additional information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      showAdditionalDataPanel.setLayout(gridLayout8);
      showAdditionalDataPanel.add(getShowExampleCheckBox(), null);
      showAdditionalDataPanel.add(getShowCommentCheckBox(), null);
    }
    return showAdditionalDataPanel;
  }

  /**
   * This method initializes showExampleCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getShowExampleCheckBox ()
  {
    if (showExampleCheckBox == null)
    {
      showExampleCheckBox = new JCheckBox ();
      // TODO: I18N
//      showExampleCheckBox.setText("Beispiel");
      showExampleCheckBox.setText("Example");
      showExampleCheckBox.setSelected(true);
    }
    return showExampleCheckBox;
  }

  /**
   * This method initializes showCommentCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getShowCommentCheckBox ()
  {
    if (showCommentCheckBox == null)
    {
      showCommentCheckBox = new JCheckBox ();
      // TODO: I18N
//      showCommentCheckBox.setText("Kommentar");
      showCommentCheckBox.setText("Comment");
      showCommentCheckBox.setSelected(true);
    }
    return showCommentCheckBox;
  }

  /**
   * This method initializes enableMixinCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getEnableMixinCheckBox ()
  {
    if (enableMixinCheckBox == null)
    {
      enableMixinCheckBox = new JCheckBox ();
      enableMixinCheckBox.setSelected (false);
      // TODO: I18N
//      enableMixinCheckBox.setText("Einmischen von Vokabeln zulassen");
      enableMixinCheckBox.setText("Allow mix-in of vocabulary");
      enableMixinCheckBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
      enableMixinCheckBox.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          if (enableMixinCheckBox.isSelected ()) 
          {
            mixinPercentageSlider.setEnabled (true);
            mixinPercentageTF.setEnabled (true);
            selectMixinUnitsRB.setEnabled (true);
            mixinAllUnitsFromLanguageRB.setEnabled (true);
            learningUnitSelectionPanel.setEnabled (true);
          } else
          {
            mixinPercentageSlider.setEnabled (false);
            mixinPercentageTF.setEnabled (false);
            selectMixinUnitsRB.setEnabled (false);
            mixinAllUnitsFromLanguageRB.setEnabled (false);            
            learningUnitSelectionPanel.setEnabled (false);
          }
        }});
    }
    return enableMixinCheckBox;
  }

  /**
   * This method initializes mixinConfigurationPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMixinConfigurationPanel ()
  {
    if (mixinConfigurationPanel == null)
    {
      mixinConfigurationPanel = new JPanel ();
      mixinConfigurationPanel.setLayout(new BorderLayout());
      // TODO: I18N
//      mixinConfigurationPanel.setBorder(BorderFactory.createTitledBorder(null, "Einstellungen f\u00fcr das Untermischen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      mixinConfigurationPanel.setBorder(BorderFactory.createTitledBorder(null, "Mix-in settings", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      mixinConfigurationPanel.add(getMixinConfigurationHelpPanel(), BorderLayout.CENTER);
    }
    return mixinConfigurationPanel;
  }

  /**
   * This method initializes mixinConfigurationHelpPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMixinConfigurationHelpPanel ()
  {
    if (mixinConfigurationHelpPanel == null)
    {
      GridLayout gridLayout9 = new GridLayout();
      gridLayout9.setColumns(1);
      gridLayout9.setRows(0);
      mixinConfigurationHelpPanel = new JPanel ();
      mixinConfigurationHelpPanel.setLayout(gridLayout9);
      mixinConfigurationHelpPanel.add(getMixinPercentagePanel(), null);
      mixinConfigurationHelpPanel.add(getSelectMixinUnitsPanel(), null);
    }
    return mixinConfigurationHelpPanel;
  }

  /**
   * This method initializes mixinPercentagePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMixinPercentagePanel ()
  {
    if (mixinPercentagePanel == null)
    {
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.gridx = 0;
      gridBagConstraints5.weightx = 1.0;
      gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints5.insets = new Insets(0, 20, 0, 5);
      gridBagConstraints5.gridy = 1;
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.gridx = 1;
      gridBagConstraints7.gridwidth = 1;
      gridBagConstraints7.gridy = 1;
      percentageLabel = new JLabel();
      percentageLabel.setText("%");
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 2;
      gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints6.weightx = 3.0;
      gridBagConstraints6.gridwidth = 1;
      gridBagConstraints6.gridy = 1;
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.fill = GridBagConstraints.BOTH;
      gridBagConstraints4.gridy = 0;
      gridBagConstraints4.weightx = 1.0;
      gridBagConstraints4.gridwidth = 3;
      gridBagConstraints4.insets = new Insets(0, 10, 8, 10);
      gridBagConstraints4.gridx = 0;
      mixinPercentagePanel = new JPanel ();
      mixinPercentagePanel.setLayout(new GridBagLayout());
      // TODO: I18N
//      mixinPercentagePanel.setBorder(BorderFactory.createTitledBorder(null, "Anteil der Untermischungen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      mixinPercentagePanel.setBorder(BorderFactory.createTitledBorder(null, "Mix-in percentage", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      mixinPercentagePanel.add(getMixinPercentageSlider(), gridBagConstraints4);
      mixinPercentagePanel.add(getMixinPercentageSpacer(), gridBagConstraints6);
      mixinPercentagePanel.add(percentageLabel, gridBagConstraints7);
      mixinPercentagePanel.add(getMixinPercentageTF(), gridBagConstraints5);
    }
    return mixinPercentagePanel;
  }

  /**
   * This method initializes mixinPercentageSlider	
   * 	
   * @return javax.swing.JSlider	
   */
  private JSlider getMixinPercentageSlider ()
  {
    if (mixinPercentageSlider == null)
    {
      mixinPercentageSlider = new JSlider ();
      mixinPercentageSlider.setValue(25);
      mixinPercentageSlider.setMajorTickSpacing(10);
      mixinPercentageSlider.setPaintTicks(true);
      mixinPercentageSlider.setPaintLabels(true);
      mixinPercentageSlider.setEnabled(false);
      mixinPercentageSlider.setMinorTickSpacing(5);
      mixinPercentageSlider.addChangeListener (new ChangeListener () {
        @Override
        public void stateChanged (ChangeEvent e)
        {
          mixinPercentageTF.setValue (mixinPercentageSlider.getValue ());
        }});
    }
    return mixinPercentageSlider;
  }

  /**
   * This method initializes mixinPercentageSpacer	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMixinPercentageSpacer ()
  {
    if (mixinPercentageSpacer == null)
    {
      mixinPercentageSpacer = new JPanel ();
      mixinPercentageSpacer.setLayout(new GridBagLayout());
    }
    return mixinPercentageSpacer;
  }

  /**
   * This method initializes selectMixinUnitsPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSelectMixinUnitsPanel ()
  {
    if (selectMixinUnitsPanel == null)
    {
      selectMixinUnitsPanel = new JPanel ();
      selectMixinUnitsPanel.setLayout(new BorderLayout());
      selectMixinUnitsPanel.add(getSelectMixinUnitsRBPanel(), BorderLayout.NORTH);
      selectMixinUnitsPanel.add(getLearningUnitSelectionPanel(), BorderLayout.CENTER);
    }
    return selectMixinUnitsPanel;
  }

  /**
   * This method initializes selectMixinUnitsRBPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSelectMixinUnitsRBPanel ()
  {
    if (selectMixinUnitsRBPanel == null)
    {
      GridLayout gridLayout10 = new GridLayout();
      gridLayout10.setColumns(1);
      gridLayout10.setRows(0);
      selectMixinUnitsRBPanel = new JPanel ();
      ButtonGroup group = new ButtonGroup ();
      group.add (getSelectMixinUnitsRB ());
      group.add (getMixinAllUnitsFromLanguageRB ());
      selectMixinUnitsRBPanel.setLayout(gridLayout10);
      selectMixinUnitsRBPanel.add(getSelectMixinUnitsRB(), null);
      selectMixinUnitsRBPanel.add(getMixinAllUnitsFromLanguageRB(), null);
    }
    return selectMixinUnitsRBPanel;
  }

  /**
   * This method initializes mixinAllUnitsFromLanguageRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getMixinAllUnitsFromLanguageRB ()
  {
    if (mixinAllUnitsFromLanguageRB == null)
    {
      mixinAllUnitsFromLanguageRB = new JRadioButton ();
      // TODO: I18N
//      mixinAllUnitsFromLanguageRB.setText("Alle Lerneinheiten der abzufragenden Sprachen verwenden");
      mixinAllUnitsFromLanguageRB.setText("Use all learning units of the languages to be tested");
      mixinAllUnitsFromLanguageRB.setEnabled(false);
      mixinAllUnitsFromLanguageRB.setSelected(true);
    }
    return mixinAllUnitsFromLanguageRB;
  }

  /**
   * This method initializes selectMixinUnitsRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getSelectMixinUnitsRB ()
  {
    if (selectMixinUnitsRB == null)
    {
      selectMixinUnitsRB = new JRadioButton ();
      // TODO: I18N
//      selectMixinUnitsRB.setText("Nur ausgew\u00e4hlte Lerneinheiten verwenden");
      selectMixinUnitsRB.setText("Only use selected learning units");
      selectMixinUnitsRB.setEnabled(false);
    }
    return selectMixinUnitsRB;
  }

  private JSpinner getMixinPercentageTF ()
  {
    if (mixinPercentageTF == null)
    {
      mixinPercentageTF = new JSpinner ();
      mixinPercentageTF.setModel(new SpinnerNumberModel(25, 1, 100, 1));
      mixinPercentageTF.setEnabled(false);
      mixinPercentageTF.addChangeListener (new ChangeListener () {
        @Override
        public void stateChanged (ChangeEvent e)
        {
          mixinPercentageSlider.setValue (Integer.valueOf (mixinPercentageTF.getValue ().toString ()));
        }});
    }
    return mixinPercentageTF;
  }

  public LearningUnitSelectionPanel getLearningUnitSelectionPanel ()
  {
    if (learningUnitSelectionPanel == null)
    {
      learningUnitSelectionPanel = new LearningUnitSelectionPanel ();
      learningUnitSelectionPanel.setEnabled(false);
    }
    return learningUnitSelectionPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
