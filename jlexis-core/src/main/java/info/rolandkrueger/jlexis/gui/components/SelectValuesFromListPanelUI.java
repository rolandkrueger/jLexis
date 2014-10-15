/*
 * SelectValuesFromListPanel.java
 * Created on 18.03.2007
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
 * 
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
package info.rolandkrueger.jlexis.gui.components;

import info.rolandkrueger.jlexis.gui.SelectionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class SelectValuesFromListPanelUI<T> extends JPanel implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private JPanel leftPanel = null;
  private JScrollPane leftScrollPane = null;
  private JList fromList = null;
  private JPanel middlePanel = null;
  private JPanel dummyPanel = null;
  private JPanel addRemoveButtonPanel = null;
  private JPanel addBtnPanel = null;
  private JButton addButton = null;
  private JPanel removeBtnPanel = null;
  private JButton removeButton = null;
  private JPanel rightPanel = null;
  private JScrollPane rightScrollPane = null;
  private JList toList = null;
  
  private String               fromListLabel = "From:";  //  @jve:decl-index=0:
  private String               toListLabel   = "To:";  //  @jve:decl-index=0:
  private InternalListModel<T> fromListModel;
  private InternalListModel<T> toListModel;
  private Collection<T>        selectableValues;
  private List<SelectionListener<T>> mSelectionListeners;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public SelectValuesFromListPanelUI (String fromListHeadline, String toListHeadline)
  {
    super ();
    this.fromListLabel = fromListHeadline;
    this.toListLabel   = toListHeadline;
    initialize ();
  }
  
  public SelectValuesFromListPanelUI (String fromListHeadline, 
                                      String toListHeadline,
                                      List<T> selectableValues)
  {
    super ();
    this.fromListLabel = fromListHeadline;
    this.toListLabel   = toListHeadline;
    
    initialize          ();
    setSelectableValues (selectableValues);
  }
  
  /**
   * Adds a {@link SelectionListener} to this object. {@link SelectionListener}s will get notified
   * which values have been selected from the list.
   * 
   * @param listener
   *          a {@link SelectionListener}
   */
  public void addSelectionListener (SelectionListener<T> listener)
  {
    mSelectionListeners.add (listener);
  }
  
  /**
   * Removes a {@link SelectionListener}.
   * 
   * @param listener
   *          a {@link SelectionListener}
   */
  public void removeSelectionListener (SelectionListener<T> listener)
  {
    mSelectionListeners.remove (listener);
  }
  
  /**
   * Removes all registered {@link SelectionListener}s.
   */
  public void clearSelectionListeners ()
  {
    mSelectionListeners.clear ();
  }
  
  private void notifyListenersOfSelection (T selectedValue)
  {
    List<SelectionListener<T>> copyList = new ArrayList<SelectionListener<T>> (mSelectionListeners);
    for (SelectionListener<T> listener : copyList)
    {
      listener.valueSelected (selectedValue);
    }
  }
  
  private void notifyListenersOfRemoval (T removedValue)
  {
    List<SelectionListener<T>> copyList = new ArrayList<SelectionListener<T>> (mSelectionListeners);
    for (SelectionListener<T> listener : copyList)
    {
      listener.valueRemoved (removedValue);
    }
  }
  
  public List<T> getSelectedValues ()
  {
    return toListModel.getAllElements ();
  }
  
  public void setSelectableValues (Collection<T> values)
  {
    assert fromListModel != null;
    selectableValues = values;
    fromListModel = new InternalListModel<T> (values);
    fromList.setModel (fromListModel);
    clearSelection ();
  }
  
  public void addSelectableValue (T value)
  {
    selectableValues.add (value);
    fromListModel.addElement (value);
  }
  
  public void removeSelectableValue (T value)
  {
    selectableValues.remove (value);
    fromListModel.removeElement (value);
  }
  
  public void clearSelection ()
  {
    toListModel.clear ();
  }
  
  private void copySelection (JList fromList, JList toList, 
      InternalListModel<T> fromListModel, InternalListModel<T> toListModel, boolean valuesRemoved)
  {
    if (fromListModel.getSize () == 0) return;

    int[] selected = fromList.getSelectedIndices ();
    List<T> selectedItems = fromListModel.removeElements (selected);
    
    if ( ! valuesRemoved)
    {
      for (T item : selectedItems)
      {
        // notify all selection listeners that values have been selected
        notifyListenersOfSelection (item);
      }
    }
    
    toListModel.addElements (selectedItems);
    fromList.setSelectedIndices (new int[0]);

    if (valuesRemoved)
    {
      for (T item : selectedItems)
      {
        // notify all selection listeners that values have been removed
        notifyListenersOfRemoval (item);
      }
    }
  }
  
  public void actionPerformed (ActionEvent event)
  {
    if (event.getSource () == addButton)
    {
      copySelection (fromList, toList, fromListModel, toListModel, false);
    } else if (event.getSource () == removeButton)
    {
      copySelection (toList, fromList, toListModel, fromListModel, true);
    }
    else
    {
      throw new IllegalStateException ("Unhandled ActionEvent!");
    }
  }
  
  public void setFromListLabel (String label)
  {
    setTitledBorder (getLeftPanel (), label);
  }
  
  public void setToListLabel (String label)
  {
    setTitledBorder (getRightPanel (), label);
  }
  
  public void reset ()
  {
    assert selectableValues != null;
    toListModel.clear ();
    setSelectableValues (selectableValues);
  }
  
  private void setTitledBorder (JPanel panel, String newTitle)
  {
    panel.setBorder(BorderFactory.createTitledBorder(null, newTitle, 
        TitledBorder.DEFAULT_JUSTIFICATION, 
        TitledBorder.DEFAULT_POSITION, 
        new Font("Dialog", Font.BOLD, 12),
        new Color(51, 51, 51)));
  }
  
  private class InternalListModel<E> extends AbstractListModel
  {
    private static final long serialVersionUID = 3106421765264654056L;
   
    private List<T> mData;
    
    /**
     * Copy constructor.
     * @param other Other {@link InternalListModel} to be copied.
     */
    public InternalListModel (InternalListModel<T> other)
    {
      this (other.getAllElements ());
    }

    public void removeElement (T value)
    {
      mData.remove (value);      
    }

    public InternalListModel ()
    {
      mData   = new ArrayList<T> ();
    }
   
    public InternalListModel (Collection<T> data)
    {
      this (); 
      mData = new ArrayList<T> (data);
    }
    
    public List<T> getElements (int[] indices)
    {
      List<T> result = new ArrayList<T> ();
      for (int i =0; i < indices.length; ++i)
      {
        result.add (mData.get (indices[i]));
      }
      return result;
    }    
    
    public List<T> getAllElements ()
    {
      return mData;
    }
    
    public int getSize ()
    {
      return mData.size ();
    }
    
    public void addElements (List<T> newElements)
    {
      for (T element : newElements)
      {
        mData.add (element);
      }
      fireContentsChanged (this, 0, mData.size ());
    }
    
    public void addElement (T element)
    {
      mData.add (element);
      fireContentsChanged (this, 0, mData.size ());
    }
    
    public List<T> removeElements (int[] indices)
    {
      List<T> result = new ArrayList<T> ();
      for (int i = 0; i < indices.length; ++i)
      {
        // subtracting i in the following step is important since the list is becoming smaller in each iteration!
        result.add (removeElementAt (indices[i] - i));
      }
      fireContentsChanged (this, 0, mData.size ());
      return result;
    }
    
    public void clear ()
    {
      mData.clear ();
      fireContentsChanged (this, 0, mData.size ());
    }

    private T removeElementAt (int index)
    {
      fireContentsChanged (this, 0, mData.size ());
      return mData.remove (index);
    }

    public T getElementAt (int index)
    {
      return mData.get (index);
    }
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --  
  ////////////////////////////////////////////////////////////
  
  /**
   * This is the default constructor
   */
  public SelectValuesFromListPanelUI ()
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
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 2;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.weighty = 1.0;
    gridBagConstraints2.fill = GridBagConstraints.BOTH;
    gridBagConstraints2.gridy = 0;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 1;
    gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
    gridBagConstraints1.gridy = 0;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.gridy = 0;
    this.setSize (646, 377);
    this.setLayout (new GridBagLayout ());
    this.add(getLeftPanel(), gridBagConstraints);
    this.add(getMiddlePanel(), gridBagConstraints1);
    this.add(getRightPanel(), gridBagConstraints2);
    
    setFromListLabel (fromListLabel);
    setToListLabel   (toListLabel);
    fromListModel  = new InternalListModel<T> ();
    toListModel = new InternalListModel<T> ();
    getToList ().setModel (toListModel);
    mSelectionListeners = new ArrayList<SelectionListener<T>> ();
  }

  /**
   * This method initializes leftPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getLeftPanel ()
  {
    if (leftPanel == null)
    {
      leftPanel = new JPanel ();
      leftPanel.setLayout(new BorderLayout());
      leftPanel.setBorder(BorderFactory.createTitledBorder(null, "<from>", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      leftPanel.add(getLeftScrollPane(), java.awt.BorderLayout.CENTER);
    }
    return leftPanel;
  }

  /**
   * This method initializes leftScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getLeftScrollPane ()
  {
    if (leftScrollPane == null)
    {
      leftScrollPane = new JScrollPane ();
      leftScrollPane.setViewportView(getFromList());
    }
    return leftScrollPane;
  }

  /**
   * This method initializes fromList	
   * 	
   * @return javax.swing.JList	
   */
  private JList getFromList ()
  {
    if (fromList == null)
    {
      fromList = new JList ();
    }
    return fromList;
  }

  /**
   * This method initializes middlePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMiddlePanel ()
  {
    if (middlePanel == null)
    {
      GridLayout gridLayout11 = new GridLayout();
      gridLayout11.setRows(3);
      middlePanel = new JPanel ();
      middlePanel.setLayout(gridLayout11);
      middlePanel.add(getDummyPanel(), null);
      middlePanel.add(getAddRemoveButtonPanel(), null);
    }
    return middlePanel;
  }

  /**
   * This method initializes dummyPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getDummyPanel ()
  {
    if (dummyPanel == null)
    {
      dummyPanel = new JPanel ();
      dummyPanel.setLayout(new GridBagLayout());
    }
    return dummyPanel;
  }

  /**
   * This method initializes addRemoveButtonPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getAddRemoveButtonPanel ()
  {
    if (addRemoveButtonPanel == null)
    {
      GridLayout gridLayout21 = new GridLayout();
      gridLayout21.setRows(2);
      gridLayout21.setVgap(10);
      gridLayout21.setHgap(0);
      addRemoveButtonPanel = new JPanel ();
      addRemoveButtonPanel.setLayout(gridLayout21);
      addRemoveButtonPanel.add(getAddBtnPanel(), null);
      addRemoveButtonPanel.add(getRemoveBtnPanel(), null);
    }
    return addRemoveButtonPanel;
  }

  /**
   * This method initializes addBtnPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getAddBtnPanel ()
  {
    if (addBtnPanel == null)
    {
      GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
      gridBagConstraints81.gridx = 0;
      gridBagConstraints81.gridy = 0;
      addBtnPanel = new JPanel ();
      addBtnPanel.setLayout(new GridBagLayout());
      addBtnPanel.add(getAddButton(), gridBagConstraints81);
    }
    return addBtnPanel;
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
      addButton.addActionListener (this);
      addButton.setText(">");
    }
    return addButton;
  }

  /**
   * This method initializes removeBtnPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getRemoveBtnPanel ()
  {
    if (removeBtnPanel == null)
    {
      GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
      gridBagConstraints71.gridx = 0;
      gridBagConstraints71.gridy = 0;
      removeBtnPanel = new JPanel ();
      removeBtnPanel.setLayout(new GridBagLayout());
      removeBtnPanel.add(getRemoveButton(), gridBagConstraints71);
    }
    return removeBtnPanel;
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
      removeButton.setText("<");
      removeButton.addActionListener (this);
    }
    return removeButton;
  }

  /**
   * This method initializes rightPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getRightPanel ()
  {
    if (rightPanel == null)
    {
      rightPanel = new JPanel ();
      rightPanel.setLayout(new BorderLayout());
      rightPanel.setBorder(BorderFactory.createTitledBorder(null, "<to>", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      rightPanel.add(getRightScrollPane(), java.awt.BorderLayout.CENTER);
    }
    return rightPanel;
  }

  /**
   * This method initializes rightScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getRightScrollPane ()
  {
    if (rightScrollPane == null)
    {
      rightScrollPane = new JScrollPane ();
      rightScrollPane.setViewportView(getToList());
    }
    return rightScrollPane;
  }

  /**
   * This method initializes toList	
   * 	
   * @return javax.swing.JList	
   */
  private JList getToList ()
  {
    if (toList == null)
    {
      toList = new JList ();
    }
    return toList;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
