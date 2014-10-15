/*
 * OpenFileAction.java
 * Created on 28.03.2007
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

import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;

import java.awt.event.ActionEvent;

import javax.swing.Action;

public class OpenLearningUnitAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = 1067313845985398659L;

  public OpenLearningUnitAction ()
  {
    super (I18NResources.getString (I18NResourceKeys.OPEN_FILE_KEY),
           JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.OPEN_LEARNING_UNIT_ICON));
    // TODO I18N
    putValue (Action.SHORT_DESCRIPTION, "Lerneinheit \u00F6ffnen.");
  }
  
  @Override
  public void actionPerformed (ActionEvent e)
  {
    LearningUnitManager.getInstance ().openSelectedLearningUnitForEditing ();
  }
}
