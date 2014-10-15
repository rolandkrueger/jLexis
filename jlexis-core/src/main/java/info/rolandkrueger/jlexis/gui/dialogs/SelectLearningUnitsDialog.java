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
package info.rolandkrueger.jlexis.gui.dialogs;

import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.containers.LearningUnitManagerPanel;
import info.rolandkrueger.jlexis.gui.datamodels.JLexisListModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Roland Krueger
 * @version $Id: SelectLearningUnitsDialog.java 126 2009-06-01 16:59:04Z roland $
 */
public class SelectLearningUnitsDialog extends JDialog implements ChangeListener
{
  private static final long serialVersionUID = 997330921787987582L;
  private JPanel selectedUnitsPanel = null;
  private JScrollPane selectedUnitsScrollPane = null;
  private JList selectedUnitsList = null;
  private JPanel contentPanel = null;
  private JPanel mainPanel = null;
  private JPanel buttonPanel = null;
  private JButton addButton = null;
  private JButton removeButton = null;
  private JPanel confirmBtnPanel = null;
  private JButton okButton = null;
  private JButton cancelButton = null;
  private LearningUnitManagerPanel learningUnitManagerPanel = null;
  private JPanel lowerPanel = null;
  private JPanel selectedUnitsListPanel = null;
  private JSplitPane mainSplitPane = null;
  private JLexisListModel<LearningUnit> selectedUnitsListModel;
  private boolean isSelectionMade = false;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
    
  @Override
  public void stateChanged (ChangeEvent e)
  {
    if (getLearningUnitManagerPanel ().getSelectedLearningUnits () == null)
    {
      getAddButton ().setEnabled (false);
    } else
    {
      getAddButton ().setEnabled (true);
    }
  }
  
  public List<LearningUnit> getSelectedLearningUnits ()
  {
    return selectedUnitsListModel.getData ();
  }
  
  public void setSelectedLearningUnits (List<LearningUnit> selectedUnits)
  {
    // remove all units from the learning unit manager panel which are already selected
    getLearningUnitManagerPanel ().unitsRemoved (this, selectedUnits);      
  }
  
  public void removeLearningUnitsFromSelection (List<LearningUnit> units)
  {
    selectedUnitsListModel.remove (units);
  }
  
  public void removeLearningUnitsFromSelectable (List<LearningUnit> units)
  {
    selectedUnitsListModel.remove (units);
    getLearningUnitManagerPanel ().unitsRemoved (this, units);
  }
    
  public boolean isSelectionMade ()
  {
    return isSelectionMade;
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * This method initializes
   */
  public SelectLearningUnitsDialog ()
  {
    super ();
    initialize ();
  }

  /**
   * This method initializes this
   */
  private void initialize ()
  {
    setResizable(true);
    this.setModal(true);
    
    this.setSize(new Dimension(714, 423));
    this.setContentPane(getContentPanel());
    // TODO: I18N
//    this.setTitle ("Lerneinheiten ausw\u00E4hlen");
    this.setTitle ("Select a learing unit");
    this.setDefaultCloseOperation (DISPOSE_ON_CLOSE);
  }

  /**
   * This method initializes selectedUnitsPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSelectedUnitsPanel ()
  {
    if (selectedUnitsPanel == null)
    {
      selectedUnitsPanel = new JPanel ();
      selectedUnitsPanel.setLayout(new BorderLayout());
      selectedUnitsPanel.add(getButtonPanel(), BorderLayout.NORTH);
      selectedUnitsPanel.add(getSelectedUnitsListPanel(), BorderLayout.CENTER);
    }
    return selectedUnitsPanel;
  }

  /**
   * This method initializes selectedUnitsScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getSelectedUnitsScrollPane ()
  {
    if (selectedUnitsScrollPane == null)
    {
      selectedUnitsScrollPane = new JScrollPane ();
      selectedUnitsScrollPane.setViewportView(getSelectedUnitsList());
    }
    return selectedUnitsScrollPane;
  }

  /**
   * This method initializes selectedUnitsList	
   * 	
   * @return javax.swing.JList	
   */
  private JList getSelectedUnitsList ()
  {
    if (selectedUnitsList == null)
    {
      selectedUnitsListModel = new JLexisListModel<LearningUnit> ();
      selectedUnitsList = new JList (selectedUnitsListModel);
      selectedUnitsList.addListSelectionListener (new ListSelectionListener () {
        @Override
        public void valueChanged (ListSelectionEvent e)
        {
          if (selectedUnitsList.getSelectedIndex () == -1)          
            getRemoveButton ().setEnabled (false);
          else
            getRemoveButton ().setEnabled (true);
        }});
    }
    return selectedUnitsList;
  }

  /**
   * This method initializes contentPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getContentPanel ()
  {
    if (contentPanel == null)
    {
      contentPanel = new JPanel ();
      contentPanel.setLayout(new BorderLayout());
      contentPanel.add(getMainPanel(), BorderLayout.CENTER);
    }
    return contentPanel;
  }

  /**
   * This method initializes mainPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMainPanel ()
  {
    if (mainPanel == null)
    {
      mainPanel = new JPanel ();
      mainPanel.setLayout(new BorderLayout());
      mainPanel.add(getMainSplitPane(), BorderLayout.CENTER);
    }
    return mainPanel;
  }

  /**
   * This method initializes buttonPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getButtonPanel ()
  {
    if (buttonPanel == null)
    {
      buttonPanel = new JPanel ();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.add(getAddButton(), null);
      buttonPanel.add(getRemoveButton(), null);
    }
    return buttonPanel;
  }

  /**
   * This method initializes addButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getAddButton ()
  {
    if (addButton == null)
    {
      addButton = new JButton ();
      // TODO: I18N
//      addButton.setText("Hinzuf\u00fcgen");
      addButton.setText("Add");
      addButton.addActionListener (new ActionListener ()
      {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          List<LearningUnit> selectedUnits = getLearningUnitManagerPanel ().getSelectedLearningUnits ();
          assert selectedUnits != null;
          selectedUnitsListModel.add (selectedUnits);
          getLearningUnitManagerPanel ().unitsRemoved (this, selectedUnits);
          getOkButton ().setEnabled (true);
        }
      });
    }
    return addButton;
  }

  /**
   * This method initializes removeButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getRemoveButton ()
  {
    if (removeButton == null)
    {
      removeButton = new JButton ();
      // TODO: I18N
//      removeButton.setText("Entfernen");
      removeButton.setText("Remove");
      removeButton.setEnabled (false);
      removeButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          int[] selectedIndices = getSelectedUnitsList ().getSelectedIndices ();
          List<LearningUnit> removedUnits = selectedUnitsListModel.remove (selectedIndices);
          getLearningUnitManagerPanel ().unitsAdded (this, removedUnits);
          if (selectedUnitsListModel.isEmpty ()) getOkButton ().setEnabled (false);
          getSelectedUnitsList ().removeSelectionInterval (0, selectedUnitsListModel.getSize ());
        }});
    }
    return removeButton;
  }

  /**
   * This method initializes confirmBtnPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getConfirmBtnPanel ()
  {
    if (confirmBtnPanel == null)
    {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      confirmBtnPanel = new JPanel ();
      confirmBtnPanel.setLayout(flowLayout);
      confirmBtnPanel.add(getCancelButton(), null);
      confirmBtnPanel.add(getOkButton(), null);
    }
    return confirmBtnPanel;
  }

  /**
   * This method initializes okButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getOkButton ()
  {
    if (okButton == null)
    {
      okButton = new JButton ();
      // TODO: I18N
      okButton.setText("      Ok      ");
      okButton.setEnabled (false);
      okButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          isSelectionMade = true;
          setVisible (false);
        }});
    }
    return okButton;
  }

  /**
   * This method initializes cancelButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getCancelButton ()
  {
    if (cancelButton == null)
    {
      cancelButton = new JButton ();
      // TODO: I18N
//      cancelButton.setText("Abbrechen");
      cancelButton.setText("Cancel");
      cancelButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          isSelectionMade = false;
          // close dialog
          setVisible (false);
        }});
    }
    return cancelButton;
  }

  /**
   * This method initializes learningUnitManagerPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.LearningUnitManagerPanel	
   */
  private LearningUnitManagerPanel getLearningUnitManagerPanel ()
  {
    if (learningUnitManagerPanel == null)
    {
      learningUnitManagerPanel = new LearningUnitManagerPanel ();
      learningUnitManagerPanel.addChangeListener (this);
    }
    return learningUnitManagerPanel;
  }

  /**
   * This method initializes lowerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getLowerPanel ()
  {
    if (lowerPanel == null)
    {
      lowerPanel = new JPanel ();
      lowerPanel.setLayout(new BorderLayout());
      lowerPanel.add(getConfirmBtnPanel(), BorderLayout.SOUTH);
      lowerPanel.add(getSelectedUnitsPanel(), BorderLayout.CENTER);
    }
    return lowerPanel;
  }

  /**
   * This method initializes selectedUnitsListPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getSelectedUnitsListPanel ()
  {
    if (selectedUnitsListPanel == null)
    {
      selectedUnitsListPanel = new JPanel ();
      selectedUnitsListPanel.setLayout(new BorderLayout());
      // TODO: I18N
//      selectedUnitsListPanel.setBorder(BorderFactory.createTitledBorder(null, "Ausgew\u00e4hlte Lerneinheiten", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      selectedUnitsListPanel.setBorder(BorderFactory.createTitledBorder(null, "Selected learning units", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      selectedUnitsListPanel.add(getSelectedUnitsScrollPane(), BorderLayout.CENTER);
    }
    return selectedUnitsListPanel;
  }

  /**
   * This method initializes mainSplitPane	
   * 	
   * @return javax.swing.JSplitPane	
   */
  private JSplitPane getMainSplitPane ()
  {
    if (mainSplitPane == null)
    {
      mainSplitPane = new JSplitPane ();
      mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
      mainSplitPane.setDividerLocation(200);
      mainSplitPane.setBottomComponent(getLowerPanel());
      mainSplitPane.setTopComponent(getLearningUnitManagerPanel());
    }
    return mainSplitPane;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
