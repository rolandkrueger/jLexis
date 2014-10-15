/*
 * EditVocabularyPanel.java
 * Created on 25.03.2007
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
package info.rolandkrueger.jlexis.gui.containers;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.commands.ResetInputCommand;
import info.rolandkrueger.jlexis.controllers.EditVocabularyControllerInterface;
import info.rolandkrueger.jlexis.controllers.EditVocabularyViewInterface;
import info.rolandkrueger.jlexis.data.guidata.VocableDetailedViewModel;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.gui.PopupListener;
import info.rolandkrueger.jlexis.gui.actions.RemoveSelectedVocableAction;
import info.rolandkrueger.jlexis.gui.keyactions.NextVocableAction;
import info.rolandkrueger.jlexis.gui.keyactions.SwitchInputPanelTabAction;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * 
 * $Id: EditVocabularyPanel.java 201 2009-12-14 17:55:13Z roland $
 * @author Roland Krueger
 */
public class EditVocabularyPanel extends JPanel implements EditVocabularyViewInterface, ListSelectionListener
{
  private static final long serialVersionUID = 7661075667623749605L;

  private JSplitPane jSplitPane = null;
  private JPanel jPanel = null;
  private JButton resetBtn = null;
  private JButton nextBtn = null;
  private JButton deleteBtn = null;
  private JSplitPane jSplitPane1 = null;
  private JEditorPane detailedView = null;
  private VocableInputTabbedPane vocableInputTabbedPane = null;
  private JScrollPane jScrollPane = null;
  private JScrollPane jScrollPane1 = null;
  private JTable overviewTable = null;
  private OverviewTableModel mTableModel;
  private HashMap<Language, VocableInputTab> mLanguageTabMapping;
  private EditVocabularyControllerInterface mController;
  private int mEditedIndex = -1;
  private JPopupMenu popup;
  private VocableDetailedViewModel mDetailedViewModel;
  private JScrollPane inputPanelScrollPane = null;
  private SwitchInputPanelTabAction mSwitchInputPanelTabAction;
  
  /// User defined methods - START
  
  public SwitchInputPanelTabAction getSwitchInputPanelTabAction ()
  {
    if (mSwitchInputPanelTabAction == null)
    {      
      mSwitchInputPanelTabAction = new SwitchInputPanelTabAction (getVocableInputTabbedPane ());
    }
    return mSwitchInputPanelTabAction;
  }
  
  public void setNextVocableAction (NextVocableAction action)
  {
    getNextBtn ().addActionListener (action);
  }
  
  public void removeAllLanguageInputAreas ()
  {
    getVocableInputTabbedPane ().removeAll ();
    mLanguageTabMapping.clear     ();
  }
  
  public void clearAllUserInput ()
  {
    getVocableInputTabbedPane ().clearAllInput ();
  }
  
  public int getSelectedVocableIndex ()
  {
    return overviewTable.getSelectedRow ();
  }
  
  public void addLanguage (Language language)
  {
    VocableInputTab languageTab = new VocableInputTab (language);
    languageTab.setVisible (true);
    getVocableInputTabbedPane ().addPanel (languageTab, language.getLanguageName ());
    mLanguageTabMapping.put (language, languageTab);
  }
  
  public void setController (EditVocabularyControllerInterface controller)
  {
    mController = controller;
    final Action removeVocableAction = new RemoveSelectedVocableAction (mController); 
    removeVocableAction.setEnabled (false);
    deleteBtn.setAction (removeVocableAction);
    popup = new JPopupMenu ();
    popup.add (removeVocableAction);
    overviewTable.addMouseListener (new PopupListener (popup));
    overviewTable.addMouseListener(new MouseAdapter()
    {
      public void mousePressed (MouseEvent e)
      {
        if (SwingUtilities.isRightMouseButton(e))
        {
          // get the coordinates of the mouse click
          Point p = e.getPoint();
     
          // get the row index that contains that coordinate
          int rowNumber = overviewTable.rowAtPoint(p);
     
          // Get the ListSelectionModel of the JTable
          ListSelectionModel model = overviewTable.getSelectionModel();
     
          // set the selected interval of rows. Using the "rowNumber"
          // variable for the beginning and end selects only that one row.
          model.setSelectionInterval(rowNumber, rowNumber);
          if (isIndexBlindRow (rowNumber)) removeVocableAction.setEnabled (false);
        }
      }
    });
  }
  
  public void setEditedLearningUnit (LearningUnit unit)
  {
    mTableModel = new OverviewTableModel (unit);
    overviewTable.setModel (mTableModel);
    overviewTable.doLayout ();
  }
  
  public void editNewVocable (AbstractWordType nextWordType)
  {
    // show the corresponding panels
    for (Language language : mLanguageTabMapping.keySet ())
    {
      VocableInputTab tab = mLanguageTabMapping.get (language);
      tab.show (language.getLanguagePlugin ().getValue ().getCorrespondingWordTypeFor (nextWordType));
    }
    getVocableInputTabbedPane ().setSelectedIndex (0);
    mDetailedViewModel.clear ();
    getDetailedView ().setText (mDetailedViewModel.getDetailedText ());
  }
  
  public void setCurrentlyEditedVocable (Vocable vocable)
  {
    for (Language language : mLanguageTabMapping.keySet ())
    {
      VocableInputTab tab = mLanguageTabMapping.get (language);
      tab.show (vocable.getVariantWordType (language));
      tab.getTopmost ().setUserInput (vocable.getVariantInput (language));      
    }
    mDetailedViewModel.setVocable (vocable);
    getDetailedView ().setText (mDetailedViewModel.getDetailedText ());
  }
  
  public Vocable getCurrentInput ()
  {
    Vocable result = new Vocable ();
    for (Language language : mLanguageTabMapping.keySet ())
    {
      result.addVariant (language, mLanguageTabMapping.get (language).getTopmost ().getCorrespondingWordType (),
          mLanguageTabMapping.get (language).getTopmost ().getCurrentUserInput ());
    }
    return result;
  }
  
  public void updateView ()
  {
    mTableModel.update ();
  }
  
  public void setEditedIndex (int index)
  {
    mEditedIndex = index;
    mTableModel.setExtraRowVisible (mEditedIndex == -1);
  }
  
  @Override
  public void valueChanged (ListSelectionEvent e)
  {
    int index = getSelectedVocableIndex ();
    if (index < 0 || isIndexBlindRow (index))
    {
      deleteBtn.getAction ().setEnabled (false);
    } else
    {
      mDetailedViewModel.setVocable (mController.getVocableAtIndex (getSelectedVocableIndex()));
      deleteBtn.getAction ().setEnabled (true);
    }
    getDetailedView ().setText (mDetailedViewModel.getDetailedText ());
  }
  
  private boolean isIndexBlindRow (int index)
  {
    return index == mTableModel.getRowCount () - 1 && mTableModel.mExtraRowForNewVocableVisible;
  }
    
  /// User defined methods - END  
  
  /**
   * This is the default constructor
   */
  public EditVocabularyPanel ()
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
    mLanguageTabMapping = new HashMap<Language, VocableInputTab> ();
    this.setLayout (new BorderLayout());
    this.setSize(new Dimension(403, 303));
    this.add(getJPanel(), BorderLayout.SOUTH);
    this.add(getJSplitPane(), BorderLayout.CENTER);
    mDetailedViewModel = new VocableDetailedViewModel ();
  }

  /**
   * This method initializes jSplitPane	
   * 	
   * @return javax.swing.JSplitPane	
   */
  private JSplitPane getJSplitPane ()
  {
    if (jSplitPane == null)
    {
      jSplitPane = new JSplitPane ();
      jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
      jSplitPane.setOneTouchExpandable(true);
      jSplitPane.setContinuousLayout(true);
      jSplitPane.setResizeWeight(1.0D);
      jSplitPane.setTopComponent(getJSplitPane1());
      jSplitPane.setBottomComponent(getInputPanelScrollPane());
      jSplitPane.setDividerLocation(230);
    }
    return jSplitPane;
  }

  /**
   * This method initializes jPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel ()
  {
    if (jPanel == null)
    {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      jPanel = new JPanel ();
      jPanel.setLayout(flowLayout);
      jPanel.add(getDeleteBtn(), null);
      jPanel.add(getResetBtn(), null);
      jPanel.add(getNextBtn(), null);
    }
    return jPanel;
  }

  /**
   * This method initializes resetBtn	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getResetBtn ()
  {
    if (resetBtn == null)
    {
      resetBtn = new JButton ();
   // TODO: I18N
//      resetBtn.setText("Zur\u00FCcksetzen");
      resetBtn.setText("Reset");
      resetBtn.addActionListener (new ActionListener () {
        public void actionPerformed (ActionEvent e)
        {
          AbstractVocableInputPanel panel = getVocableInputTabbedPane ().getTopmostPanel ();
          if (panel.getCurrentUserInput ().isEmpty ()) return;
          List<AbstractVocableInputPanel> panels = new LinkedList<AbstractVocableInputPanel> ();
          panels.add(panel);
          JLexisMain.getInstance ().addUserAction (new ResetInputCommand (panels));
        }});
    }
    return resetBtn;
  }

  /**
   * This method initializes nextBtn	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getNextBtn ()
  {
    if (nextBtn == null)
    {
      nextBtn = new JButton ();
      // TODO: I18N
//      nextBtn.setText("Neue Vokabel >>");
      nextBtn.setText("New vocable >>");
      nextBtn.setMnemonic ('n');
      nextBtn.addMouseListener (new MouseListener () 
      {
        public void mousePressed (MouseEvent e)
        {
          final JPopupMenu menu = new JPopupMenu ();
          for (Language language : mController.getLanguagesOfEditedLearningUnit ())
          {
            LanguagePlugin plugin = language.getLanguagePlugin ().getValue ();
            if (language == null)
            {
              // TODO: error handling
              continue;
            }
            for (final AbstractWordType wordType : plugin.getWordTypes ())
            {
              JMenuItem item = new JMenuItem (String.format ("(%s) %s",
                  language.getLanguageName (), wordType.getName ()), plugin.getIcon ());
              item.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent e)
                {
                  mController.editNewVocable (wordType);
                  menu.setVisible (false);
                }});
              menu.add (item);
            }
          }
          menu.show (nextBtn, e.getX (), e.getY ());
        }
        public void mouseEntered (MouseEvent e) {}
        public void mouseExited (MouseEvent e) {}
        public void mouseClicked (MouseEvent e) {}
        public void mouseReleased (MouseEvent e) {}
      });
    }
    return nextBtn;
  }

  /**
   * This method initializes deleteBtn	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getDeleteBtn ()
  {
    if (deleteBtn == null)
    {
      deleteBtn = new JButton ();
    }
    return deleteBtn;
  }

  /**
   * This method initializes jSplitPane1	
   * 	
   * @return javax.swing.JSplitPane	
   */
  private JSplitPane getJSplitPane1 ()
  {
    if (jSplitPane1 == null)
    {
      jSplitPane1 = new JSplitPane ();
      jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
      jSplitPane1.setMinimumSize(new Dimension(24, 100));
      jSplitPane1.setPreferredSize(new Dimension(11, 300));
      jSplitPane1.setDoubleBuffered(true);
      jSplitPane1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      jSplitPane1.setOneTouchExpandable(true);
      jSplitPane1.setResizeWeight(1.0D);
      jSplitPane1.setBottomComponent(getJScrollPane());
      jSplitPane1.setTopComponent(getOverviewTable());
      jSplitPane1.setDividerLocation(100);
    }
    return jSplitPane1;
  }

  /**
   * This method initializes detailedView	
   * 	
   * @return javax.swing.JEditorPane	
   */
  private JEditorPane getDetailedView ()
  {
    if (detailedView == null)
    {
      detailedView = new JEditorPane ();
      detailedView.setContentType("text/html");
      detailedView.setDoubleBuffered(true);
      detailedView.setEditable(false);
    }
    return detailedView;
  }

  /**
   * This method initializes inputAreaTabbedPane	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  public VocableInputTabbedPane getVocableInputTabbedPane ()
  {
    if (vocableInputTabbedPane == null)
    {
      vocableInputTabbedPane = new VocableInputTabbedPane ();
      vocableInputTabbedPane.setSize(new Dimension(39, 23));
    }
    return vocableInputTabbedPane;
  }

  /**
   * This method initializes jScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getJScrollPane ()
  {
    if (jScrollPane == null)
    {
      jScrollPane = new JScrollPane ();
      jScrollPane.setViewportView(getDetailedView());
    }
    return jScrollPane;
  }

  /**
   * This method initializes overviewTable	
   * 	
   * @return javax.swing.JTable	
   */
  private JScrollPane getOverviewTable ()
  {
    if (jScrollPane1 == null)
    {
      jScrollPane1 = new JScrollPane ();
      overviewTable = new JTable (){
        private static final long serialVersionUID = 3758770017563737816L;
        private DefaultTableCellRenderer defaultRenderer   = new DefaultTableCellRenderer ();
        private DefaultTableCellRenderer editedRowRenderer = new DefaultTableCellRenderer ();
        private Color editedRowColor = new Color (230, 110, 57);
        @Override
        public TableCellRenderer getCellRenderer (int row, int column)
        {
          return new TableCellRenderer () {
            public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
              if (row == mEditedIndex || (mEditedIndex == -1 && row == getModel ().getRowCount () -1))
              {
                Component editedRow = editedRowRenderer.getTableCellRendererComponent (
                    table, value, false, false, row, column);
                if (isSelected)
                  editedRow.setBackground (Color.red);
                else
                  editedRow.setBackground (editedRowColor);
                return editedRow;
              } else                
              return defaultRenderer.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
            }};
        }
      };
      overviewTable.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
      overviewTable.getSelectionModel ().addListSelectionListener (this);
      overviewTable.addMouseListener (new MouseListener () {
        public void mouseClicked (MouseEvent e)
        {
          if (e.getClickCount () == 2)
          {
            // the user double-clicked on a table row. Select this row for editing 
            int selectedRow = overviewTable.getSelectedRow ();
            if (selectedRow == -1 || isIndexBlindRow (selectedRow)) return;
            mController.setSelectedVocable (selectedRow);
          }
        }
        public void mouseEntered (MouseEvent e) {}
        public void mouseExited (MouseEvent e) {}
        public void mousePressed (MouseEvent e) {}
        public void mouseReleased (MouseEvent e) {}
      });

      jScrollPane1.setViewportView (overviewTable);
    }
    return jScrollPane1;
  }
  
  /// User defined classes - START
  
  private class OverviewTableModel implements TableModel
  {
    private List<Language>           mLanguages;
    private List<TableModelListener> mTableModelListeners;
    private LearningUnit             mLearningUnit;
    private boolean                  mExtraRowForNewVocableVisible = false;

    public OverviewTableModel (LearningUnit unit)
    {
      mLanguages = new ArrayList<Language> ();
      
      // add the plugin for the native language
      mLanguages.add (unit.getNativeLanguage ());
      
      for (Language language : unit.getLanguages ())
      {
        mLanguages.add (language);
      }
      mTableModelListeners = new LinkedList<TableModelListener> ();
      mLearningUnit = unit;
    }
    
    public void update ()
    {
      List<TableModelListener> listeners = new LinkedList<TableModelListener> (mTableModelListeners);
      for (TableModelListener listener : listeners)
      {
        listener.tableChanged (new TableModelEvent (this));
      }      
    }
    
    public void setExtraRowVisible (boolean visible)
    {
      mExtraRowForNewVocableVisible = visible;
      update ();
    }

    public void addTableModelListener (TableModelListener l)
    {
      mTableModelListeners.add (l);      
    }

    public Class<?> getColumnClass (int columnIndex)
    {
      return String.class;
    }

    public int getColumnCount ()
    {
      return mLanguages.size ();
    }

    public String getColumnName (int columnIndex)
    {
      return mLanguages.get (columnIndex).getLanguageName ();
    }

    public int getRowCount ()
    {
      int result = mLearningUnit.getSize ();
      if (mExtraRowForNewVocableVisible) result++;
      return result;
    }

    public Object getValueAt (int rowIndex, int columnIndex)
    {
      if (rowIndex >= mLearningUnit.getSize ())
      {
        return "";
      }
      // TODO: assure that mLanguages.get (columnIndex).getLanguagePlugin () != null
      return mLearningUnit.getVocableAt (rowIndex).getVariantInput (
          mLanguages.get (columnIndex)).getShortVersion ();
    }

    public boolean isCellEditable (int rowIndex, int columnIndex)
    {
      return false;
    }

    public void removeTableModelListener (TableModelListener l)
    {
      mTableModelListeners.remove (l);
    }

    public void setValueAt (Object aValue, int rowIndex, int columnIndex)
    {
      throw new UnsupportedOperationException ("Setting data not supported.");
    }
  }
  /// User defined classes - END

  /**
   * This method initializes inputPanelScrollPane	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getInputPanelScrollPane ()
  {
    if (inputPanelScrollPane == null)
    {
      inputPanelScrollPane = new JScrollPane ();
      inputPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      inputPanelScrollPane.setViewportView(getVocableInputTabbedPane());
    }
    return inputPanelScrollPane;
  }  
}  //  @jve:decl-index=0:visual-constraint="10,10"
