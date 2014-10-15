/*
 * Created on 02.06.2009. 
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
package info.rolandkrueger.jlexis.quiz.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/*
 * *
 * @author Roland Krueger
 * @version $Id: QuizStatisticsDialog.java 127 2009-06-02 12:01:53Z roland $
 */
public class QuizStatisticsDialog extends JDialog
{
  private static final long serialVersionUID = -2232242284347389974L;
  private JPanel jContentPane = null;
  private JPanel buttonPanel = null;
  private JButton okButton = null;
  private JPanel statisticsPanel = null;
  private JLabel totalQuestionsLabel = null;
  private JLabel correctAnswersLabel = null;
  private JLabel incorrectAnswersLabel = null;
  private JLabel correctOnFirstTrialLabel = null;
  private JLabel totalQuestionsValueLabel = null;
  private JLabel correctAnswersValueLabel = null;
  private JLabel correctOnFirstTrialValueLabel = null;
  private JLabel incorrectAnswersValueLabel = null;
  public void setValues (int totalQuestions, int correctAnswers, int correctOnFirstTrial, int incorrect)
  {
    totalQuestionsValueLabel.setText (String.valueOf (totalQuestions));
    correctAnswersValueLabel.setText (String.valueOf (correctAnswers) + 
        getPercentage (correctAnswers, totalQuestions));
    correctOnFirstTrialValueLabel.setText (String.valueOf (correctOnFirstTrial) + 
        getPercentage (correctOnFirstTrial, totalQuestions));
    incorrectAnswersValueLabel.setText (String.valueOf (incorrect) + 
        getPercentage (incorrect, totalQuestions));
  }
  
  private String getPercentage (int fraction, int total)
  {
    if (total == 0) return "";
    float resultFloat = ((float) fraction / (float) total) * 100.0f;
    return String.format (" (%d %%)", Math.round (resultFloat));
  }
  
  public QuizStatisticsDialog (Frame owner)
  {
    super (owner);
    initialize ();
  }

  private void initialize ()
  {
    this.setSize (310, 281);
    this.setContentPane (getJContentPane ());
  }

  private JPanel getJContentPane ()
  {
    if (jContentPane == null)
    {
      jContentPane = new JPanel ();
      jContentPane.setLayout (new BorderLayout ());
      jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
      jContentPane.add(getStatisticsPanel(), BorderLayout.CENTER);
    }
    return jContentPane;
  }

  private JPanel getButtonPanel ()
  {
    if (buttonPanel == null)
    {
      buttonPanel = new JPanel ();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.add(getOkButton(), null);
    }
    return buttonPanel;
  }

  private JButton getOkButton ()
  {
    if (okButton == null)
    {
      okButton = new JButton ();
      okButton.setText("OK");
      okButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          dispose ();
        }});
    }
    return okButton;
  }

  private JPanel getStatisticsPanel ()
  {
    if (statisticsPanel == null)
    {
      GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
      gridBagConstraints21.gridx = 1;
      gridBagConstraints21.anchor = GridBagConstraints.WEST;
      gridBagConstraints21.gridy = 4;
      incorrectAnswersValueLabel = new JLabel();
      incorrectAnswersValueLabel.setText("");
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.gridx = 1;
      gridBagConstraints11.anchor = GridBagConstraints.WEST;
      gridBagConstraints11.gridy = 3;
      correctOnFirstTrialValueLabel = new JLabel();
      correctOnFirstTrialValueLabel.setText("");
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 1;
      gridBagConstraints6.anchor = GridBagConstraints.WEST;
      gridBagConstraints6.gridy = 1;
      correctAnswersValueLabel = new JLabel();
      correctAnswersValueLabel.setText("");
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.gridx = 1;
      gridBagConstraints5.anchor = GridBagConstraints.WEST;
      gridBagConstraints5.weightx = 1.0;
      gridBagConstraints5.gridy = 0;
      totalQuestionsValueLabel = new JLabel();
      totalQuestionsValueLabel.setText("");
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.anchor = GridBagConstraints.EAST;
      gridBagConstraints4.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints4.gridy = 6;
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.anchor = GridBagConstraints.EAST;
      gridBagConstraints3.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints3.gridy = 3;
      correctOnFirstTrialLabel = new JLabel();
//      correctOnFirstTrialLabel.setText("davon auf Anhieb richtig beantwortet:");
      correctOnFirstTrialLabel.setText("of which were answered correctly right away:");
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.anchor = GridBagConstraints.EAST;
      gridBagConstraints2.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints2.gridy = 4;
      incorrectAnswersLabel = new JLabel();
//      incorrectAnswersLabel.setText("falsch beantwortet:");
      incorrectAnswersLabel.setText("answered incorrectly:");
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = GridBagConstraints.EAST;
      gridBagConstraints1.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints1.gridy = 1;
      correctAnswersLabel = new JLabel();
//      correctAnswersLabel.setText("davon richtig beantwortet:");
      correctAnswersLabel.setText("of which were answered correctly:");
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = GridBagConstraints.EAST;
      gridBagConstraints.insets = new Insets(0, 0, 0, 10);
      gridBagConstraints.fill = GridBagConstraints.NONE;
      gridBagConstraints.weightx = 0.0;
      gridBagConstraints.gridy = 0;
      totalQuestionsLabel = new JLabel();
//      totalQuestionsLabel.setText("Gesamtanzahl Fragen:");
      totalQuestionsLabel.setText("Total number of questions:");
      statisticsPanel = new JPanel ();
      statisticsPanel.setLayout(new GridBagLayout());
      // TODO: I18N
//      statisticsPanel.setBorder(BorderFactory.createTitledBorder(null, "Antwortstatistik", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      statisticsPanel.setBorder(BorderFactory.createTitledBorder(null, "Quiz statistics", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      statisticsPanel.add(totalQuestionsLabel, gridBagConstraints);
      statisticsPanel.add(correctAnswersLabel, gridBagConstraints1);
      statisticsPanel.add(correctOnFirstTrialLabel, gridBagConstraints3);
      statisticsPanel.add(incorrectAnswersLabel, gridBagConstraints2);
      statisticsPanel.add(totalQuestionsValueLabel, gridBagConstraints5);
      statisticsPanel.add(correctAnswersValueLabel, gridBagConstraints6);
      statisticsPanel.add(correctOnFirstTrialValueLabel, gridBagConstraints11);
      statisticsPanel.add(incorrectAnswersValueLabel, gridBagConstraints21);
    }
    return statisticsPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
