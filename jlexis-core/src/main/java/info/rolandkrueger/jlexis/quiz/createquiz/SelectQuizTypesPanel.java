/*
 * Created on 22.03.2009 
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
package info.rolandkrueger.jlexis.quiz.createquiz;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.gui.datamodels.JLexisListModel;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * @author Roland Krueger
 * @version $Id: SelectQuizTypesPanel.java 125 2009-05-30 08:58:51Z roland $
 */
public class SelectQuizTypesPanel extends JPanel implements ListSelectionListener
{

  private static final long serialVersionUID = 1L;
  private JPanel quizTypesListPanel = null;
  private JScrollPane quizTypesListScrollPane = null;
  private JList quizTypesList = null;
  private Language language;
  private JLexisListModel<AbstractQuizType> listDataModel;
  private JPanel optionsPanel = null;
  private CardLayout optionsPanelLayout;
  private AbstractQuizType selectedQuizType;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public void setLanguage (Language language)
  {
    if (language == null)    
      throw new NullPointerException ("Language is null");
    this.language = language;
    for (AbstractQuizType quizType : language.getLanguagePlugin ().getValue ().getQuizTypes ())
    {
      addQuizType (quizType);
    }
  }
  
  public void addQuizType (AbstractQuizType quizType)
  {
    listDataModel.add (quizType);
    JScrollPane scroller = new JScrollPane ();
    scroller.setViewportView (quizType.getConfigurationPanel ());
    optionsPanel.add (scroller, quizType.getName ());    
    selectFirstQuizType ();
  }
  
  private void selectFirstQuizType ()
  {
    getQuizTypesList ().setSelectedIndex (0);
    selectedQuizType = listDataModel.get (0);    
  }
  
  public Language getLanguage ()
  {
    return language;
  }
  
  public AbstractQuizType getSelectedQuizType ()
  {
    return selectedQuizType;
  }
  
  @Override
  public void valueChanged (ListSelectionEvent e)
  {
    if (quizTypesList.getSelectedIndex () == -1) return;
    selectedQuizType = listDataModel.get (quizTypesList.getSelectedIndex ());
    optionsPanelLayout.show (optionsPanel, selectedQuizType.getName ());
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  public SelectQuizTypesPanel ()
  {
    super ();
    initialize ();
  }

  private void initialize ()
  {
    this.setSize (565, 351);
    this.setLayout (new BorderLayout());
    this.add(getQuizTypesListPanel(), BorderLayout.WEST);
    this.add(getOptionsPanel(), BorderLayout.CENTER);
  }

  private JPanel getQuizTypesListPanel ()
  {
    if (quizTypesListPanel == null)
    {
      quizTypesListPanel = new JPanel ();
      quizTypesListPanel.setLayout(new BorderLayout());
      // TODO: I18N
//      quizTypesListPanel.setBorder(BorderFactory.createTitledBorder(null, "Abfragetypen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      quizTypesListPanel.setBorder(BorderFactory.createTitledBorder(null, "Quiz types", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      quizTypesListPanel.setPreferredSize(new Dimension(200, 157));
      quizTypesListPanel.add(getQuizTypesListScrollPane(), BorderLayout.CENTER);
    }
    return quizTypesListPanel;
  }

  private JScrollPane getQuizTypesListScrollPane ()
  {
    if (quizTypesListScrollPane == null)
    {
      quizTypesListScrollPane = new JScrollPane ();
      quizTypesListScrollPane.setViewportView(getQuizTypesList());
    }
    return quizTypesListScrollPane;
  }

  private JList getQuizTypesList ()
  {
    if (quizTypesList == null)
    {
      listDataModel = new JLexisListModel<AbstractQuizType> ();
      quizTypesList = new JList (listDataModel);
      quizTypesList.addListSelectionListener (this);
      quizTypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    return quizTypesList;
  }

  private JPanel getOptionsPanel ()
  {
    if (optionsPanel == null)
    {
      optionsPanel = new JPanel ();
      optionsPanelLayout = new CardLayout();
      optionsPanel.setLayout(optionsPanelLayout);
      // TODO: I18N
//      optionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Optionen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      optionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    }
    return optionsPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
