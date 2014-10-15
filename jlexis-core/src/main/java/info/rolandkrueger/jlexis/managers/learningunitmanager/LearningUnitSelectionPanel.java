/*
 * Created on 27.05.2009. Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info). All rights
 * reserved. This file is part of Anteeo.
 */
package info.rolandkrueger.jlexis.managers.learningunitmanager;

import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.dialogs.SelectLearningUnitsDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * @author Roland Krueger
 * @version $Id: LearningUnitSelectionPanel.java 126 2009-06-01 16:59:04Z roland $
 */
public class LearningUnitSelectionPanel extends JPanel
{
  private static final long serialVersionUID = -3504082763121671712L;

  private JPanel buttonPanel = null;
  private JButton addButton = null;
  private JButton removeButton = null;
  private JPanel unitOverviewPanel = null;
  private JScrollPane unitOverviewScrollPane = null;
  private JTable selectedUnitsTable = null;
  private LearningUnitTableModel selectedUnitsTableModel;
  private LearningUnitManagerCallbackHandler mCallbackHandler;
  private SelectLearningUnitsDialog selectionDialog;

  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public void setEnabled (boolean enabled)
  {
    addButton.setEnabled (enabled);
    removeButton.setEnabled (enabled);
    selectedUnitsTable.setEnabled (enabled);
  }
  
  private void learningUnitsAdded (List<LearningUnit> units)
  {
    selectedUnitsTableModel.addLearningUnits (units);
    mCallbackHandler.fireUnitsAdded (units);
  }
  
  public void addLearningUnitManagerCallback (LearningUnitManagerCallbackInterface listener)
  {
    mCallbackHandler.addLearningUnitManagerCallback (listener);
  }

  public void removeLearningUnitManagerCallback (LearningUnitManagerCallbackInterface listener)
  {
    mCallbackHandler.removeLearningUnitManagerCallback (listener);
  }
  
  public List<LearningUnit> getSelectedLearningUnits ()
  {
    return selectedUnitsTableModel.getData ();
  }
  
  public void removeLearningUnitsFromSelectable (List<LearningUnit> units)
  {
    selectionDialog.removeLearningUnitsFromSelectable (units);
  } 

  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////

  /**
   * This is the default constructor
   */
  public LearningUnitSelectionPanel ()
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
    this.setSize (492, 435);
    this.setLayout (new BorderLayout());
    this.add(getButtonPanel(), BorderLayout.NORTH);
    this.add(getUnitOverviewPanel(), BorderLayout.CENTER);
    mCallbackHandler = new LearningUnitManagerCallbackHandler (this);
    selectionDialog = new SelectLearningUnitsDialog ();
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
//      addButton.setText("Hinzuf\u00FCgen ...");
      addButton.setText("Add ...");
      addButton.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          selectionDialog.removeLearningUnitsFromSelection (selectionDialog.getSelectedLearningUnits ());
          selectionDialog.setVisible (true);
          if (selectionDialog.isSelectionMade ())
            learningUnitsAdded (selectionDialog.getSelectedLearningUnits ());
        }});
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
    }
    return removeButton;
  }

  /**
   * This method initializes unitOverviewPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getUnitOverviewPanel ()
  {
    if (unitOverviewPanel == null)
    {
      unitOverviewPanel = new JPanel ();
      unitOverviewPanel.setLayout(new BorderLayout());
      unitOverviewPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
      unitOverviewPanel.add(getUnitOverviewScrollPane(), java.awt.BorderLayout.CENTER);
    }
    return unitOverviewPanel;
  }

  /**
   * This method initializes unitOverviewScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getUnitOverviewScrollPane ()
  {
    if (unitOverviewScrollPane == null)
    {
      unitOverviewScrollPane = new JScrollPane ();
      unitOverviewScrollPane.setViewportView(getSelectedUnitsTable());
    }
    return unitOverviewScrollPane;
  }

  /**
   * This method initializes selectedUnitsTable	
   * 	
   * @return javax.swing.JTable	
   */
  private JTable getSelectedUnitsTable ()
  {
    if (selectedUnitsTable == null)
    {
      selectedUnitsTable = new JTable ();
      selectedUnitsTableModel = new LearningUnitTableModel();
      selectedUnitsTable.setModel(selectedUnitsTableModel);
    }
    return selectedUnitsTable;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
