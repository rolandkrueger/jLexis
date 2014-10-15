/*
 * NewFileAction.java
 * Created on 15.02.2007
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
package info.rolandkrueger.jlexis.gui.actions;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.gui.dialogs.NewLearningUnitDialogUI;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;

public class NewLearningUnitAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = -4992313929096103720L;
  private NewLearningUnitDialogUI mNewLearningUnitDialog;
  
  public NewLearningUnitAction ()
  {
    super (I18NResources.getString (I18NResourceKeys.NEW_FILE_KEY),
           JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.NEW_FILE_ICON));
    // TODO I18N
    putValue (Action.SHORT_DESCRIPTION, "Neue Lerneinheit anlegen.");
    
    mNewLearningUnitDialog = new NewLearningUnitDialogUI (JLexisMain.getInstance ().getMainWindow ());
  }
  
  public void actionPerformed (ActionEvent event)
  {
    mNewLearningUnitDialog.setSelectableLanguages (JLexisMain.getInstance ().getPlugins ());
    mNewLearningUnitDialog.setVisible (true);
  }
  
  @Deprecated
  public void createNewFile (NewLearningUnitDialogUI.NewFileData data)
  {
    // check the input
    if (data.getSelectedLanguages ().size () == 0)
    {
      JOptionPane.showMessageDialog (mNewLearningUnitDialog, 
          I18NResources.getString (I18NResourceKeys.ERROR_MSG_SELECT_LANGUAGE_TEXT), 
          I18NResources.getString (I18NResourceKeys.ERROR_MSG_SELECT_LANGUAGE_TITLE),
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    
    if (data.getFile ().exists ())
    {
      int answer = JOptionPane.showConfirmDialog (mNewLearningUnitDialog, 
          I18NResources.getString (I18NResourceKeys.WARNING_MSG_OVERWRITE_FILE_TEXT), 
          I18NResources.getString (I18NResourceKeys.WARNING_MSG_OVERWRITE_FILE_TITLE), 
          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      switch (answer)
      {
        case JOptionPane.YES_OPTION:
          // do nothing
          break;
        case JOptionPane.NO_OPTION:
          return;

        case JOptionPane.CANCEL_OPTION:
          mNewLearningUnitDialog.clearInput ();
          mNewLearningUnitDialog.setVisible (false);
          return;
      }
    }
    
    if (data.getFile ().getName ().equals (".lex") || 
        (data.getFile ().getParentFile () != null && 
        ! data.getFile ().getParentFile ().exists ()))
    {
      // the parent path of the specified file does not exist       
      JOptionPane.showMessageDialog (mNewLearningUnitDialog, 
          I18NResources.getString (I18NResourceKeys.ERROR_MSG_INVALID_FILENAME_TEXT), 
          I18NResources.getString (I18NResourceKeys.ERROR_MSG_INVALID_FILENAME_TITLE),
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    
//    LexisVocabularyFile file = new LexisVocabularyFile (data.getSelectedLanguages (),
//        data.getFile (), data.getComment (), LexisMain.getInstance ().getLexisVersion ());
//    LexisMain.getInstance ().addVocabularyFileForEditing (file);
    mNewLearningUnitDialog.clearInput ();
    mNewLearningUnitDialog.setVisible (false);
  }
}
