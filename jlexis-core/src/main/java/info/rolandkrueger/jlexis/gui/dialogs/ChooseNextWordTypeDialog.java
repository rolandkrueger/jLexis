/*
 * ChooseNextWordTypeDialog.java
 * Created on 06.05.2007
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
package info.rolandkrueger.jlexis.gui.dialogs;

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.roklib.ui.swing.JDialogClosableWithESC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

public class ChooseNextWordTypeDialog extends JDialogClosableWithESC
{
  private static final long serialVersionUID = 1214407470575923832L;
  private JPanel jContentPane     = null;
  private JPanel jPanel = null;
  private JButton cancelBtn = null;
  private JButton okBtn = null;
  private JPanel jPanel1 = null;
  private JScrollPane jScrollPane = null;
  private JList jList = null;
  private ListModel mListModel;

  // USER ADDED METHODS - START
  
  public void addWordType (LanguagePlugin plugin, AbstractWordType wordType)
  {
    mListModel.add (plugin, wordType);
  }
  
  public AbstractWordType getSelectedWordType ()
  {
    return mListModel.get (jList.getSelectedIndex ());
  }
  
  /* (non-Javadoc)
   * @see info.rolandkrueger.roklib.ui.swing.JDialogClosableWithESC#setVisible(boolean)
   */
  @Override
  public void setVisible (boolean visible)
  {
    getJList ().requestFocusInWindow ();
    super.setVisible (visible);
  }
  
  public void addActionListener (final ActionListener listener)
  {
    getOkBtn ().addActionListener (listener);
//    getJList ().addKeyListener (new KeyListener () {
//      public void keyPressed (KeyEvent e)
//      {
//        if (e.getKeyCode () == KeyEvent.VK_ENTER)
//          listener.actionPerformed (new ActionEvent (getOkBtn (), ActionEvent.ACTION_PERFORMED, 
//              getOkBtn ().getText ()));
//        if (e.getKeyCode () == KeyEvent.VK_ESCAPE)
//          cancelDialog ();
//      }
//      public void keyReleased (KeyEvent e) {}
//      public void keyTyped (KeyEvent e)    {}});
    
    getJContentPane ().getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (
        KeyStroke.getKeyStroke ("ENTER"), "select");
    getJContentPane ().getActionMap ().put ("select", new AbstractAction()
    {
      private static final long serialVersionUID = -424381076749670625L;

      @Override
      public void actionPerformed (ActionEvent e)
      {
        listener.actionPerformed (new ActionEvent (getOkBtn (), ActionEvent.ACTION_PERFORMED, 
            getOkBtn ().getText ()));
      }
    });
  }
  
  @Override
  public void cancelDialog ()
  {    
    setVisible (false);
  }
  
  public void clearList ()
  {
    mListModel.clear ();
  }
  
  private class ListModel extends AbstractListModel
  {
    private static final long serialVersionUID = 7873598709786560147L;
    private List<DataPair> mData;
    
    public ListModel ()
    {
      mData = new ArrayList<DataPair> ();
    }
    
    public Object getElementAt (int index)
    {
      return String.format ("%s - %s", mData.get (index).mPlugin.getLanguageName (), 
          mData.get (index).mWordType.getName ());
    }

    public int getSize ()
    {
      return mData.size ();
    }
    
    public void add (LanguagePlugin plugin, AbstractWordType wordType)
    {
      mData.add (new DataPair (plugin, wordType));
      fireContentsChanged (this, 0, getSize ());
    }
    
    public void clear ()
    {
      mData.clear ();
    }
    
    public AbstractWordType get (int index)
    {
      return mData.get (index).mWordType;
    }
    
    private class DataPair
    {
      public DataPair (LanguagePlugin plugin, AbstractWordType wordType)
      {
        mPlugin   = plugin;
        mWordType = wordType;
      }
      
      private LanguagePlugin   mPlugin;
      private AbstractWordType mWordType;
    }
  }
  
  // USER ADDED METHODS - END
  
  /**
   * @param owner
   */
  public ChooseNextWordTypeDialog (Frame owner)
  {
    // TODO I18N
//    super (owner, "Neue Vokabel", true);
    super (owner, "New vocable", true);
    initialize ();
    getJList ().requestFocusInWindow ();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    mListModel = new ListModel ();
    this.setSize (335, 352);
    this.setContentPane (getJContentPane ());
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane ()
  {
    if (jContentPane == null)
    {
      jContentPane = new JPanel ();
      jContentPane.setLayout (new BorderLayout ());
      jContentPane.add(getJPanel(), BorderLayout.SOUTH);
      jContentPane.add(getJPanel1(), BorderLayout.CENTER);
      jContentPane.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (
          KeyStroke.getKeyStroke ("ESCAPE"), "cancel");
      jContentPane.getActionMap ().put ("cancel", new AbstractAction()
      {
        private static final long serialVersionUID = 1564321898761486L;
        @Override
        public void actionPerformed (ActionEvent e)
        {
          cancelDialog (); 
        }
      });      
    }
    return jContentPane;
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
      jPanel.add(getCancelBtn(), null);
      jPanel.add(getOkBtn(), null);
    }
    return jPanel;
  }

  /**
   * This method initializes jButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getCancelBtn ()
  {
    if (cancelBtn == null)
    {
      cancelBtn = new JButton ();
      cancelBtn.setText("Abbrechen");
      cancelBtn.addActionListener (new ActionListener () {
        public void actionPerformed (ActionEvent e)
        {
          cancelDialog ();
        }});
    }
    return cancelBtn;
  }

  /**
   * This method initializes jButton1	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getOkBtn ()
  {
    if (okBtn == null)
    {
      okBtn = new JButton ();
      okBtn.setText("OK");
    }
    return okBtn;
  }

  /**
   * This method initializes jPanel1	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel1 ()
  {
    if (jPanel1 == null)
    {
      jPanel1 = new JPanel ();
      jPanel1.setLayout(new BorderLayout());
      jPanel1.setBorder(BorderFactory.createTitledBorder(null, "N\u00E4chste Vokabel:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
    }
    return jPanel1;
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
      jScrollPane.setViewportView(getJList());
    }
    return jScrollPane;
  }

  /**
   * This method initializes jList	
   * 	
   * @return javax.swing.JList	
   */
  private JList getJList ()
  {
    if (jList == null)
    {
      assert mListModel != null;
      jList = new JList (mListModel);
      jList.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
      jList.getSelectionModel ().setSelectionInterval (0, 0);
    }
    return jList;
  }

}  //  @jve:decl-index=0:visual-constraint="33,21"
