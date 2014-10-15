/*
 * Created on 17.03.2009 
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
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManagerCallbackInterface;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitSelectionPanel;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration;
import info.rolandkrueger.jlexis.quiz.generalquiztypes.GeneralQuizType;
import info.rolandkrueger.jlexis.quiz.manager.CreateQuizCallbackInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/*
 * @author Roland Krueger
 * @version $Id: CreateQuizPanel.java 126 2009-06-01 16:59:04Z roland $
 */
public class CreateQuizPanel extends JPanel implements LearningUnitManagerCallbackInterface
{
  private static final long serialVersionUID = -2657298662264258264L;

  private JSplitPane mainSplitPane = null;
  private JTable selectedQuizTypeTable = null;
  private JPanel selectUnitsTab = null;
  private JPanel optionsPanel = null;
  private JTabbedPane optionsTabbedPane = null;
  private JPanel addQuizBtnPanel = null;
  private JButton addQuizButton = null;
  private Map<LanguagePlugin, SelectQuizTypesPanel> availableLanguages;
  private CreateQuizCallbackInterface createQuizCallback;  //  @jve:decl-index=0:
  private SelectedQuizTypesTableModel tableModel;  //  @jve:decl-index=0:visual-constraint="737,-4"
  private SelectQuizTypesPanel generalQuizTypesPanel;  //  @jve:decl-index=0:visual-constraint="59,466"
  private JScrollPane tableScrollPane = null;  //  @jve:decl-index=0:visual-constraint="456,494"
  private JPanel btnPanel = null;
  private JButton startButton = null;
  private JButton cancelButton = null;
  private QuizConfigurationPanel quizConfigurationTab = null;
  private JPanel selectQuizTypeTab = null;
  private JTabbedPane tabbedPane = null;
  private LearningUnitSelectionPanel learningUnitSelectionPanel = null;

  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public GeneralQuizConfiguration getQuizConfiguration ()
  {
    return getQuizConfigurationTab ().getConfiguration ();
  }
  
  public void setCreateQuizCallback (CreateQuizCallbackInterface callback)
  {
    if (callback == null) throw new NullPointerException ("Callback is null.");
    createQuizCallback = callback;
  }
  
  public void reset ()
  {
    getOptionsTabbedPane ().removeAll ();
    //TODO: I18N
//    optionsTabbedPane.addTab("Allgemein", null, getGeneralQuizTypesPanel (), null);
    optionsTabbedPane.addTab("General", null, getGeneralQuizTypesPanel (), null);
  }
  
  public void addSelectedLearningUnits (List<LearningUnit> units)
  {
    for (LearningUnit unit : units)
    {
      for (Language language : unit.getLanguages ()) 
      {
        if (availableLanguages.containsKey (language.getLanguagePlugin ().getValue ()))
          continue;
        SelectQuizTypesPanel panel = new SelectQuizTypesPanel ();
        availableLanguages.put (language.getLanguagePlugin ().getValue (), panel);
        panel.setLanguage (language);
        getOptionsTabbedPane ().addTab (language.getLanguageName (), panel);
      }
    }
    createQuizCallback.addLearningUnits (units);
    tableModel.setQuizTypeConfigurations (createQuizCallback.getQuizConfigurations ());
    
    // Remove the selected units from the learning unit mixin configuration sot
    // that a learning unit which has been selected for a quiz cannot be 
    // simultaneously be mixed in the quiz.
    getQuizConfigurationTab ().getLearningUnitSelectionPanel ().removeLearningUnitsFromSelectable (units);    
  }
  
  private void addCurrentQuizTypeConfiguration ()
  {
    SelectQuizTypesPanel selectedPanel = (SelectQuizTypesPanel) getOptionsTabbedPane ().getSelectedComponent (); 
    AbstractQuizType currentQuizType = (selectedPanel).getSelectedQuizType ();
    createQuizCallback.addQuizTypeConfiguration (currentQuizType.getQuizTypeConfiguration (), selectedPanel.getLanguage ());
    tableModel.setQuizTypeConfigurations (createQuizCallback.getQuizConfigurations ());
    if (tableModel.getRowCount () == 0)
      getStartButton ().setEnabled (false);
    else
      getStartButton ().setEnabled (true);
  }
  
  public void addCancelButtonActionListener (ActionListener listener)
  {
    getCancelButton ().addActionListener (listener);
  }
  
  public void addStartButtonActionListener (ActionListener listener)
  {
    getStartButton ().addActionListener (listener);
  }
  
  @Override
  public void unitAdded (Object source, LearningUnit unit)
  {
    if (source == getLearningUnitSelectionPanel ())
      addSelectedLearningUnits (Arrays.asList (unit));
  }

  @Override
  public void unitRemoved (Object source, LearningUnit unit)
  {
  }

  @Override
  public void unitsAdded (Object source, List<LearningUnit> units)
  {
    if (source == getLearningUnitSelectionPanel ())
      addSelectedLearningUnits (units);
  }

  @Override
  public void unitsRemoved (Object source, List<LearningUnit> units)
  {
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////

  public CreateQuizPanel ()
  {
    super ();
    initialize ();
  }

  private void initialize ()
  {
    this.setSize (706, 511);
    this.setLayout (new BorderLayout());
    this.add(getTabbedPane(), BorderLayout.CENTER);
    this.add(getBtnPanel(), BorderLayout.SOUTH);
    availableLanguages = new HashMap<LanguagePlugin, SelectQuizTypesPanel> ();
  }

  private JSplitPane getMainSplitPane ()
  {
    if (mainSplitPane == null)
    {
      mainSplitPane = new JSplitPane ();
      mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
      mainSplitPane.setResizeWeight(0.0D);
      mainSplitPane.setBottomComponent(getTableScrollPane ());
      mainSplitPane.setTopComponent(getOptionsPanel());
      mainSplitPane.setDividerLocation(380);
    }
    return mainSplitPane;
  }

  private JTable getSelectedQuizTypeTable ()
  {
    if (selectedQuizTypeTable == null)
    {
      tableModel = new SelectedQuizTypesTableModel ();
      selectedQuizTypeTable = new JTable (tableModel);
    }
    return selectedQuizTypeTable;
  }

  private JPanel getSelectUnitsTab ()
  {
    if (selectUnitsTab == null)
    {
      BorderLayout borderLayout = new BorderLayout();
      borderLayout.setHgap(0);
      selectUnitsTab = new JPanel ();
      selectUnitsTab.setLayout(borderLayout);
      // TODO: I18N
//      selectUnitsTab.setBorder(BorderFactory.createTitledBorder(null, "Lerneinheiten", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      selectUnitsTab.setBorder(BorderFactory.createTitledBorder(null, "Learning units", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      selectUnitsTab.add(getLearningUnitSelectionPanel(), BorderLayout.CENTER);
    }
    return selectUnitsTab;
  }

  private JPanel getOptionsPanel ()
  {
    if (optionsPanel == null)
    {
      optionsPanel = new JPanel ();
      optionsPanel.setLayout(new BorderLayout());
      // TODO: I18N
//      optionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Optionen", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      optionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      optionsPanel.add(getOptionsTabbedPane(), BorderLayout.CENTER);
      optionsPanel.add(getAddQuizBtnPanel(), BorderLayout.SOUTH);
    }
    return optionsPanel;
  }

  private JTabbedPane getOptionsTabbedPane ()
  {
    if (optionsTabbedPane == null)
    {
      optionsTabbedPane = new JTabbedPane ();
      reset ();
    }
    return optionsTabbedPane;
  }
  
  private SelectQuizTypesPanel getGeneralQuizTypesPanel ()
  {
    if (generalQuizTypesPanel == null)
    {
      generalQuizTypesPanel = new SelectQuizTypesPanel ();
      generalQuizTypesPanel.addQuizType (new GeneralQuizType ());
    }
    return generalQuizTypesPanel;
  }

  private JPanel getAddQuizBtnPanel ()
  {
    if (addQuizBtnPanel == null)
    {
      addQuizBtnPanel = new JPanel ();
      addQuizBtnPanel.setLayout(new FlowLayout());
      addQuizBtnPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
      addQuizBtnPanel.add(getAddQuizButton(), null);
    }
    return addQuizBtnPanel;
  }

  private JButton getAddQuizButton ()
  {
    if (addQuizButton == null)
    {
      addQuizButton = new JButton ();
      // TODO: I18N
//      addQuizButton.setText("Hinzuf\u00FCgen");
      addQuizButton.setText("Add");
      addQuizButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          addCurrentQuizTypeConfiguration ();          
        }});
    }
    return addQuizButton;
  }

  private JScrollPane getTableScrollPane ()
  {
    if (tableScrollPane == null)
    {
      tableScrollPane = new JScrollPane ();
      tableScrollPane.setSize(new Dimension(185, 167));
      tableScrollPane.setViewportView(getSelectedQuizTypeTable());
    }
    return tableScrollPane;
  }

  private JPanel getBtnPanel ()
  {
    if (btnPanel == null)
    {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      flowLayout.setAlignment(FlowLayout.RIGHT);
      btnPanel = new JPanel ();
      btnPanel.setLayout(flowLayout);
      btnPanel.add(getCancelButton(), null);
      btnPanel.add(getStartButton(), null);
    }
    return btnPanel;
  }

  private JButton getStartButton ()
  {
    if (startButton == null)
    {
      startButton = new JButton ();
      // TODO: I18N
      startButton.setText("    Start    ");
      startButton.setEnabled (false);
    }
    return startButton;
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

  private QuizConfigurationPanel getQuizConfigurationTab ()
  {
    if (quizConfigurationTab == null)
    {
      quizConfigurationTab = new QuizConfigurationPanel ();
    }
    return quizConfigurationTab;
  }

  private JPanel getSelectQuizTypeTab ()
  {
    if (selectQuizTypeTab == null)
    {
      selectQuizTypeTab = new JPanel ();
      selectQuizTypeTab.setLayout(new BorderLayout());
      selectQuizTypeTab.add(getMainSplitPane(), BorderLayout.CENTER);
    }
    return selectQuizTypeTab;
  }

  private JTabbedPane getTabbedPane ()
  {
    if (tabbedPane == null)
    {
      tabbedPane = new JTabbedPane ();
      // TODO: I18N
//      tabbedPane.addTab("Lerneinheiten", null, getSelectUnitsTab(), null);
//      tabbedPane.addTab("Abfragearten", null, getSelectQuizTypeTab(), null);
//      tabbedPane.addTab("Einstellungen", null, getQuizConfigurationTab(), null);
      tabbedPane.addTab("Learning units", null, getSelectUnitsTab(), null);
      tabbedPane.addTab("Quiz types", null, getSelectQuizTypeTab(), null);
      tabbedPane.addTab("Settings", null, getQuizConfigurationTab(), null);
    }
    return tabbedPane;
  }

  private LearningUnitSelectionPanel getLearningUnitSelectionPanel ()
  {
    if (learningUnitSelectionPanel == null)
    {
      learningUnitSelectionPanel = new LearningUnitSelectionPanel ();
      learningUnitSelectionPanel.addLearningUnitManagerCallback (this);
    }
    return learningUnitSelectionPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="22,-63"
