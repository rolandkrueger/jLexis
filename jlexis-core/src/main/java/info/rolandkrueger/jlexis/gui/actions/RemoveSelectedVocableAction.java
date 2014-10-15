/*
 * $Id: RemoveSelectedVocableAction.java 72 2009-03-06 18:13:41Z roland $
 * Created on 06.03.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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

import info.rolandkrueger.jlexis.controllers.EditVocabularyControllerInterface;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;

import java.awt.event.ActionEvent;

import javax.swing.Action;

public class RemoveSelectedVocableAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = 7177969145479331601L;
  private EditVocabularyControllerInterface mEditVocabularyController;
  
  public RemoveSelectedVocableAction (EditVocabularyControllerInterface editVocabularyController)
  {
    // TODO I18N
    super ("Remove vocable", 
        JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.REMOVE_SELECTED_VOCABLE_ICON));
//    super ("Vokabel entfernen", 
//        LexisResourceManager.getInstance ().getIcon (ResourcesEnums.REMOVE_SELECTED_VOCABLE_ICON));
    if (editVocabularyController == null)    
      throw new NullPointerException ("Controller is null.");
    
    mEditVocabularyController = editVocabularyController;
    putValue (Action.SHORT_DESCRIPTION, "Selektierte Vokabel entfernen");
  }

  @Override
  public void actionPerformed (ActionEvent e)
  {
    mEditVocabularyController.removeSelectedVocable ();
  }
}
