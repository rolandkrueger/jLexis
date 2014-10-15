/*
 * NewFileDialogUI.java
 * Created on 11.03.2007
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

import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.languages.LanguageFactory;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.SelectionListener;
import info.rolandkrueger.jlexis.gui.components.SelectValuesFromListPanelUI;
import info.rolandkrueger.jlexis.managers.ConfigurationManager;
import info.rolandkrueger.jlexis.managers.JLexisPersistenceManager;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.roklib.ui.swing.JDialogClosableWithESC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class NewLearningUnitDialogUI extends JDialogClosableWithESC implements ActionListener, 
                  SelectionListener<Language>
{
  private static final long serialVersionUID = -3102586763302562640L;
  private JPanel jPanel = null;
  private JPanel filenamePanel = null;
  private JPanel jPanel4 = null;
  private JTextField unitNameTF = null;
  private JPanel nativeLanguagePanel = null;
  private JPanel jPanel7 = null;
  private JPanel jPanel8 = null;
  private JPanel commentPanel = null;
  private JScrollPane jScrollPane = null;
  private JTextArea descriptionTF = null;
  private JPanel jPanel2 = null;
  private JButton okBtn = null;
  private JButton cancelBtn = null;
  private JPanel middlePanel = null;
  private SelectValuesFromListPanelUI<Language> selectValuesFromListPanel = null;
  private JTextField nativeLanguageTF = null;
  
  /**
   * @param owner
   */
  public NewLearningUnitDialogUI (Frame owner)
  {
    super (owner);
    initialize ();
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public void actionPerformed (ActionEvent event)
  {
    if (event.getSource () == cancelBtn)
    {
      // cancel button pressed
      cancelDialog ();
    } else if (event.getSource () == okBtn)
    {
      // ok button clicked: check the input
      if (selectValuesFromListPanel.getSelectedValues ().size () == 0)
      {
        JOptionPane.showMessageDialog (this, 
            I18NResources.getString (I18NResourceKeys.ERROR_MSG_SELECT_LANGUAGE_TEXT), 
            I18NResources.getString (I18NResourceKeys.ERROR_MSG_SELECT_LANGUAGE_TITLE),
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      
      if (unitNameTF.getText ().equals (""))
      {
        JOptionPane.showMessageDialog (this, 
            "Geben Sie bitte einen Namen f\u00FCr die Lerneinheit an.", 
            "Fehlende Eingabe",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      
      // check if a learning unit with the given name does already exist
      if (LearningUnitManager.getInstance ().doesLearningUnitExistWithName (unitNameTF.getText ()))
      {
        JOptionPane.showMessageDialog (this, "Eine Lerneinheit mit dem angegebenen Namen existiert bereits.", 
            "Ung\u00FCltiger Name", JOptionPane.ERROR_MESSAGE);
        return;
      }
      
      // check if a native language was provided
      if (nativeLanguageTF.getText ().equals (""))
      {
        // TODO: i18n
        JOptionPane.showMessageDialog (this, "Bitte geben Sie die Muttersprache an.", 
            "Fehlende Eingabe", JOptionPane.ERROR_MESSAGE);
        return;
      }
      
      // pass the given data to the main class
      LearningUnit newUnit = new LearningUnit ();
      newUnit.setDescription (descriptionTF.getText ());
      newUnit.setName (unitNameTF.getText ());
      
      ConfigurationManager.getInstance ().getWorkspace ().setNativeLanguage (nativeLanguageTF.getText ());
      newUnit.setNativeLanguageName (nativeLanguageTF.getText ());
      newUnit.setLanguages (selectValuesFromListPanel.getSelectedValues ());
      
      JLexisPersistenceManager.getInstance ().saveObject (newUnit);
      clearInput ();
      LearningUnitManager.getInstance ().addLearningUnit (newUnit);
      
      setVisible (false);
    }
  }
  
  @Override
  public void valueSelected (Language value)
  {
    if (value.getLanguagePlugin ().getValue ().isDefaultPlugin ())
    {
      // if the user has chosen a default language plugin, ask her to provide a language name
      // and add the plugin again to the list of selectable plugins. Default plugins can
      // be selected more than once.
      // TODO: i18n
      String input = "";
      while (input.length () == 0)
      {
        input = JOptionPane.showInputDialog (this, "Geben Sie bitte die gewünschte Sprache an.", "Sprache auswählen",
            JOptionPane.QUESTION_MESSAGE);
      }
      value.setLanguageName (input);

      selectValuesFromListPanel.addSelectableValue (LanguageFactory.getInstance ().getLanguageFor (
          value.getLanguagePlugin ().getValue ()));
    }
  }
  
  @Override
  public void valueRemoved (Language value)
  {
    if (value.getLanguagePlugin ().getValue ().isDefaultPlugin ())
      selectValuesFromListPanel.removeSelectableValue (value);
  }
  
  @Override
  protected void cancelDialog ()
  {
    reset ();
    setVisible (false);
  }
  
  private void reset ()
  {
    unitNameTF.setText    ("");
    descriptionTF.setText ("");
    selectValuesFromListPanel.reset ();
  }
  
  private void setLabels ()
  {
    // FIXME:
//    setTitle              (I18NResources.getString (I18NResourceKeys.NEW_FILE_DIALOG_TITLE));
    setTitle              ("Create new learning unit");
    okBtn.setText         (I18NResources.getString (I18NResourceKeys.OK_BTN));
//    cancelBtn.setText     (I18NResources.getString (I18NResourceKeys.CANCEL_BTN)); 
    cancelBtn.setText     ("Cancel"); 
    
//    selectValuesFromListPanel.setFromListLabel (I18NResources.getString (I18NResourceKeys.SELECT_FOREIGN_LANGUAGE) + ":");
    selectValuesFromListPanel.setFromListLabel ("Select your \nforeign languages:");
//    selectValuesFromListPanel.setToListLabel   (I18NResources.getString (I18NResourceKeys.SELECTED_LANGUAGE) + ":"); 
    selectValuesFromListPanel.setToListLabel   ("Selected language:"); 

//    setTitledBorder (nativeLanguagePanel, I18NResources.getString (I18NResourceKeys.SELECT_NATIVE_LANGUAGE) + ":");
    setTitledBorder (nativeLanguagePanel, "Select your mother tongue:");
//    setTitledBorder (filenamePanel,       I18NResources.getString (I18NResourceKeys.FILE_NAME_LABEL) + ":");
    setTitledBorder (filenamePanel, "Learning unit's name:");
//    setTitledBorder (commentPanel,        I18NResources.getString (I18NResourceKeys.COMMENT_KEY) + ":");
    setTitledBorder (commentPanel, "Comment:");
  }
  
  private void setTitledBorder (JPanel panel, String newTitle)
  {
    panel.setBorder (BorderFactory.createTitledBorder(null, 
        newTitle, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
  }
  
  public void clearInput ()
  {
    unitNameTF.setText   ("");
    descriptionTF.setText ("");
    selectValuesFromListPanel.reset ();
  }
  
  public void setSelectableLanguages (Collection<LanguagePlugin> selectableValues)
  {
    List<Language> langList = new ArrayList<Language> ();
    for (LanguagePlugin plugin : selectableValues)
    {
      langList.add (LanguageFactory.getInstance ().getLanguageFor (plugin));
    }
    selectValuesFromListPanel.setSelectableValues (langList);
  }
  
  public class NewFileData 
  {
    private String               mComment;
    private File                 mFile;
    private List<LanguagePlugin> mSelectedLanguages;
    private String               mNativeLanguage;
    
    private NewFileData () {}

    public String getComment ()
    {
      return mComment;
    }

    private void setComment (String comment)
    {
      mComment = comment;
    }

    public File getFile ()
    {
      return mFile;
    }

    private void setFile (File file)
    {
      mFile = file;
    }

    public String getNativeLanguage ()
    {
      return mNativeLanguage;
    }

    private void setNativeLanguage (String nativeLanguage)
    {
      mNativeLanguage = nativeLanguage;
    }

    public List<LanguagePlugin> getSelectedLanguages ()
    {
      return mSelectedLanguages;
    }

    private void setSelectedLanguages (List<LanguagePlugin> selectedLanguages)
    {
      mSelectedLanguages = selectedLanguages;
    }
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (648, 450);
    this.setContentPane(getJPanel());
    setLabels ();
    cancelBtn.addActionListener (this);
    okBtn.addActionListener     (this);
    getSelectValuesFromListPanel ().addSelectionListener (this);
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
      jPanel = new JPanel ();
      jPanel.setLayout(new BorderLayout());
      jPanel.add(getFilenamePanel(), BorderLayout.NORTH);
      jPanel.add(getJPanel8(), BorderLayout.SOUTH);
      jPanel.add(getMiddlePanel(), BorderLayout.CENTER);
    }
    return jPanel;
  }

  /**
   * This method initializes filenamePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getFilenamePanel ()
  {
    if (filenamePanel == null)
    {
      filenamePanel = new JPanel ();
      filenamePanel.setLayout(new BoxLayout(getFilenamePanel(), BoxLayout.X_AXIS));
      // TODO: I18N
//      filenamePanel.setBorder(BorderFactory.createTitledBorder(null, "Datei:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      filenamePanel.setBorder(BorderFactory.createTitledBorder(null, "File:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      filenamePanel.add(getJPanel4(), null);
    }
    return filenamePanel;
  }

  /**
   * This method initializes jPanel4	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel4 ()
  {
    if (jPanel4 == null)
    {
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 4;
      gridBagConstraints3.gridwidth = 2;
      gridBagConstraints3.weightx = 0.5;
      gridBagConstraints3.gridy = 0;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 2;
      gridBagConstraints1.gridwidth = 3;
      gridBagConstraints1.weightx = 1.0;
      gridBagConstraints1.gridy = 0;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.ipadx = 150;
      gridBagConstraints.gridwidth = 4;
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.ipady = 0;
      gridBagConstraints.insets = new Insets(0, 5, 5, 10);
      gridBagConstraints.gridx = 0;
      jPanel4 = new JPanel ();
      jPanel4.setLayout(new GridBagLayout());
      jPanel4.add(getUnitNameTF(), gridBagConstraints);
    }
    return jPanel4;
  }

  /**
   * This method initializes editFilenameTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getUnitNameTF ()
  {
    if (unitNameTF == null)
    {
      unitNameTF = new JTextField ();
    }
    return unitNameTF;
  }

  /**
   * This method initializes nativeLanguagePanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getNativeLanguagePanel ()
  {
    if (nativeLanguagePanel == null)
    {
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.fill = GridBagConstraints.BOTH;
      gridBagConstraints2.gridy = 0;
      gridBagConstraints2.weightx = 1.0;
      gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints2.ipadx = 0;
      gridBagConstraints2.gridx = 6;
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 6;
      gridBagConstraints6.weightx = 0.7;
      gridBagConstraints6.gridy = 1;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.gridx = 1;
      gridBagConstraints5.gridy = 0;
      nativeLanguagePanel = new JPanel ();
      nativeLanguagePanel.setLayout(new GridBagLayout());
      // TODO: I18N
//      nativeLanguagePanel.setBorder(BorderFactory.createTitledBorder(null, "Muttersprache:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      nativeLanguagePanel.setBorder(BorderFactory.createTitledBorder(null, "Mother tongue:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      nativeLanguagePanel.add(getJPanel7(), gridBagConstraints6);
      nativeLanguagePanel.add(getNativeLanguageTF(), gridBagConstraints2);
    }
    return nativeLanguagePanel;
  }

  /**
   * This method initializes jPanel7	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel7 ()
  {
    if (jPanel7 == null)
    {
      jPanel7 = new JPanel ();
      jPanel7.setLayout(new GridBagLayout());
    }
    return jPanel7;
  }

  /**
   * This method initializes jPanel8	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel8 ()
  {
    if (jPanel8 == null)
    {
      jPanel8 = new JPanel ();
      jPanel8.setLayout(new BorderLayout());
      jPanel8.add(getNativeLanguagePanel(), BorderLayout.NORTH);
      jPanel8.add(getJPanel2(), BorderLayout.SOUTH);
    }
    return jPanel8;
  }

  /**
   * This method initializes commentPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getCommentPanel ()
  {
    if (commentPanel == null)
    {
      commentPanel = new JPanel ();
      commentPanel.setLayout(new BorderLayout());
      // TODO: I18N
//      commentPanel.setBorder(BorderFactory.createTitledBorder(null, "Kommentar:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      commentPanel.setBorder(BorderFactory.createTitledBorder(null, "Comment:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
      commentPanel.add(getJScrollPane(), BorderLayout.CENTER);
    }
    return commentPanel;
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
      jScrollPane.setViewportView(getCommentTextField());
    }
    return jScrollPane;
  }

  /**
   * This method initializes commentTextField	
   * 	
   * @return javax.swing.JTextArea	
   */
  private JTextArea getCommentTextField ()
  {
    if (descriptionTF == null)
    {
      descriptionTF = new JTextArea ();
      descriptionTF.setWrapStyleWord(true);
      descriptionTF.setLineWrap(true);
    }
    return descriptionTF;
  }

  /**
   * This method initializes jPanel2	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getJPanel2 ()
  {
    if (jPanel2 == null)
    {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      jPanel2 = new JPanel ();
      jPanel2.setLayout(flowLayout);
      jPanel2.add(getOkBtn(), null);
      jPanel2.add(getCancelBtn(), null);
    }
    return jPanel2;
  }

  /**
   * This method initializes okBtn	
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
   * This method initializes cancelBtn	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getCancelBtn ()
  {
    if (cancelBtn == null)
    {
      cancelBtn = new JButton ();
      // TODO: I18N
//      cancelBtn.setText("Abbrechen");
      cancelBtn.setText("Cancel");
    }
    return cancelBtn;
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
      GridLayout gridLayout3 = new GridLayout();
      gridLayout3.setRows(1);
      gridLayout3.setVgap(0);
      gridLayout3.setHgap(5);
      gridLayout3.setColumns(2);
      middlePanel = new JPanel ();
      middlePanel.setLayout(gridLayout3);
      middlePanel.add(getCommentPanel(), null);
      middlePanel.add(getSelectValuesFromListPanel(), null);
    }
    return middlePanel;
  }

  /**
   * This method initializes selectValuesFromListPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.SelectValuesFromListPanelUI	
   */
  private SelectValuesFromListPanelUI<Language> getSelectValuesFromListPanel ()
  {
    if (selectValuesFromListPanel == null)
    {
      selectValuesFromListPanel = new SelectValuesFromListPanelUI<Language> ();
    }
    return selectValuesFromListPanel;
  }

  /**
   * This method initializes nativeLanguageTF	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getNativeLanguageTF ()
  {
    if (nativeLanguageTF == null)
    {
      // initialize the text field with the native language that is provided by 
      // the Workspace object, i.e. the language the user has chosen last
      nativeLanguageTF = new JTextField (ConfigurationManager.getInstance ().getWorkspace ().getNativeLanguage ());
    }
    return nativeLanguageTF;
  }
}  //  @jve:decl-index=0:visual-constraint="30,35"
