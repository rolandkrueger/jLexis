/*
 * $Id: DeleteLearningUnitAction.java 155 2009-10-23 06:22:31Z roland $
 * Created on 02.12.2008
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

import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;

public class DeleteLearningUnitAction extends JLexisAbstractAction
{  
  private static final long serialVersionUID = -4154630393745625681L;

  public DeleteLearningUnitAction ()
  {
    super (I18NResources.getString (I18NResourceKeys.DELETE_LEARNING_UNIT_KEY),
        JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.DELETE_UNIT_ICON));
    // TODO I18N
    putValue (Action.SHORT_DESCRIPTION, "Eine Lerneinheit löschen.");
    setEnabled (false);
  }
  
  @Override
  public void actionPerformed (ActionEvent e)
  {    
    List<LearningUnit> unitsToDelete = LearningUnitManager.getInstance ().getLearningUnitManagerFrame ().getSelectedLearningUnits ();
    // TODO I18N
    for (LearningUnit unitToDelete : unitsToDelete)
    {
      int selection = JOptionPane.showConfirmDialog (getMain (), "<html><center>Wollen Sie die Lerneinheit<br/>" +
          unitToDelete.getName () +
          "<br>wirklich löschen?</center></html>", "Achtung!", JOptionPane.YES_NO_OPTION);
      if (selection == JOptionPane.NO_OPTION)
        continue;

      unitToDelete.delete ();
    }
  }
}
