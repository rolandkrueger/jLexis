/*
 * $Id: ShowLearningUnitManagerFrameAction.java 138 2009-10-16 06:45:04Z roland $
 * Created on 24.11.2008
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

import info.rolandkrueger.jlexis.gui.internalframes.learningunitmanager.LearningUnitManagerFrame;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

public class ShowLearningUnitManagerFrameAction extends JLexisAbstractAction
{
  private static final long serialVersionUID = 5809926132865951878L;
 
  private LearningUnitManagerFrame mUnitManager;
  private JCheckBoxMenuItem        mMenuItem;
  
  public ShowLearningUnitManagerFrameAction (LearningUnitManagerFrame gui)
  {
    // TODO: I18N
//    super ("Lerneinheiten");
    super ("Learning units");
    mUnitManager = gui;
    mMenuItem = new JCheckBoxMenuItem (this);
    mUnitManager.setCorrespondingMenuItem (mMenuItem);
  }

  @Override
  public void actionPerformed (ActionEvent e)
  {
    mUnitManager.setVisible (mMenuItem.getState ());
  }

  public JMenuItem getJCheckBoxMenuItem ()
  {
    return mMenuItem;
  }
    
  public void showFrame ()
  {
    mUnitManager.setVisible  (true);
    mMenuItem.setState (true);
  }
  
  public void hideFrame ()
  {
    mUnitManager.setVisible  (false);
    mMenuItem.setState (false);    
  }
}
