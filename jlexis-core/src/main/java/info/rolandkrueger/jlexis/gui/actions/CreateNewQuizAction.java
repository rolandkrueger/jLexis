/*
 * Created on 30.03.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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

import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;
import info.rolandkrueger.jlexis.quiz.createquiz.CreateQuizDialog;
import info.rolandkrueger.jlexis.quiz.manager.QuizManager;

import java.awt.event.ActionEvent;

import javax.swing.Action;

/**
 * @author Roland Krueger
 * @version $Id: CreateNewQuizAction.java 121 2009-05-26 19:24:36Z roland $
 */
public class CreateNewQuizAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = -5607980458014331669L;

  public CreateNewQuizAction ()
  {
    //TODO:I18N
    super ("Neue Abfragerunde",
        JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.BRICKS_ICON));
    // TODO I18N
    putValue (Action.SHORT_DESCRIPTION, "Eine neue Fragerunde beginnen.");
  }
  
  @Override
  public void actionPerformed (ActionEvent e)
  {
    QuizManager manager = new QuizManager ();
    CreateQuizDialog dialog = new CreateQuizDialog ();
    dialog.setCreateQuizCallback (manager);
    dialog.setVisible (true);
  }
}
