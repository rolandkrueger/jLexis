/*
 * $Id: UnitManagerLanguagePanel.java 121 2009-05-26 19:24:36Z roland $ Created on 24.11.2008 Copyright 2007 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version. jLexis is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with jLexis;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package info.rolandkrueger.jlexis.gui.internalframes.learningunitmanager;

import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.datamodels.JLexisListModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UnitManagerLanguagePanel extends JPanel implements ListSelectionListener
{
  private static final long serialVersionUID = 1L;
  private JSplitPane jSplitPane = null;
  private JScrollPane unitListScrollPane = null;
  private JList unitList = null;
  private JPanel infoPanel = null;
  private JPanel unitListPanel = null;
  private JPanel buttonPanel = null;
  private JButton sortButton = null;
  private JLexisListModel<LearningUnit> listModel;
  private JPanel emptyInfoPanel = null;  //  @jve:decl-index=0:visual-constraint="532,9"
  private JLabel emptyInfoLabel = null;
    
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public void addLearningUnit (LearningUnit unit)
  {
    assert listModel != null;
    listModel.add (unit);    
  }
  
  public void removeLearningUnit (LearningUnit learningUnit)
  {
    listModel.remove (learningUnit);
  }
  
  public void removeSelection ()
  {
    getUnitList ().removeSelectionInterval (0, listModel.getSize ());
  }
  
  public void addListSelectionListener (ListSelectionListener listener)
  {
    getUnitList ().addListSelectionListener (listener);
  }

  public List<LearningUnit> getSelectedListValues ()
  {
    if (unitList.isSelectionEmpty ()) return Collections.emptyList ();
    return listModel.getDataForIndices (unitList.getSelectedIndices ());
  }
  
  @Override
  public void valueChanged (ListSelectionEvent e)
  {
    List<LearningUnit> selectedUnits = getSelectedListValues ();
    if (selectedUnits.isEmpty ())
    {
      // no learning unit is currently selected
      setInfoPanel (getEmptyInfoPanel ());
    } else
    {
      // show the details of the selected learning unit on the info panel on the right
      LearningUnitInfoPanel infoPanel = new LearningUnitInfoPanel ();
      infoPanel.setLearningUnit (selectedUnits.get (0));
      setInfoPanel (infoPanel);
    }
  }
  
  private void setInfoPanel (JPanel newPanel)
  {
    getInfoPanel ().removeAll ();
    getInfoPanel ().add (newPanel, BorderLayout.CENTER);
    revalidate ();
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * This is the default constructor
   */
  public UnitManagerLanguagePanel ()
  {
    super ();
    initialize ();
  }

  private void initialize ()
  {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.gridx = 0;
    this.setSize (504, 317);
    this.setLayout (new GridBagLayout ());
    this.add(getJSplitPane(), gridBagConstraints);
    addListSelectionListener (this);
    setInfoPanel (getEmptyInfoPanel ());
  }

  private JSplitPane getJSplitPane ()
  {
    if (jSplitPane == null)
    {
      jSplitPane = new JSplitPane ();
      jSplitPane.setPreferredSize(new Dimension(214, 28));
      jSplitPane.setRightComponent(getInfoPanel());
      jSplitPane.setLeftComponent(getUnitListPanel());
      jSplitPane.setDividerLocation(197);
    }
    return jSplitPane;
  }

  private JScrollPane getUnitListScrollPane ()
  {
    if (unitListScrollPane == null)
    {
      unitListScrollPane = new JScrollPane ();
      unitListScrollPane.setViewportView(getUnitList());
    }
    return unitListScrollPane;
  }

  private JList getUnitList ()
  {
    if (unitList == null)
    {
      listModel = new JLexisListModel<LearningUnit> ();
      unitList = new JList(listModel);
      unitList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    return unitList;
  }

  private JPanel getInfoPanel ()
  {
    if (infoPanel == null)
    {
      infoPanel = new JPanel ();
      infoPanel.setLayout(new BorderLayout());
    }
    return infoPanel;
  }

  private JPanel getUnitListPanel ()
  {
    if (unitListPanel == null)
    {
      unitListPanel = new JPanel ();
      unitListPanel.setLayout(new BorderLayout());
      unitListPanel.add(getUnitListScrollPane(), BorderLayout.CENTER);
      unitListPanel.add(getButtonPanel(), BorderLayout.NORTH);
    }
    return unitListPanel;
  }

  private JPanel getButtonPanel ()
  {
    if (buttonPanel == null)
    {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      buttonPanel = new JPanel ();
      buttonPanel.setLayout(flowLayout);
//      buttonPanel.add(getSortButton(), null);
    }
    return buttonPanel;
  }

  private JButton getSortButton ()
  {
    if (sortButton == null)
    {
      sortButton = new JButton ();
      sortButton.setText("sortieren");
      sortButton.setActionCommand("");
    }
    return sortButton;
  }

  private JPanel getEmptyInfoPanel ()
  {
    if (emptyInfoPanel == null)
    {
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.gridy = 0;
      emptyInfoLabel = new JLabel();
      // TODO: i18n
      emptyInfoLabel.setText("Keine Lerneinheit ausgew\u00e4hlt");
      emptyInfoPanel = new JPanel ();
      emptyInfoPanel.setLayout(new GridBagLayout());
      emptyInfoPanel.setSize(new Dimension(165, 132));
      emptyInfoPanel.add(emptyInfoLabel, gridBagConstraints1);
    }
    return emptyInfoPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
