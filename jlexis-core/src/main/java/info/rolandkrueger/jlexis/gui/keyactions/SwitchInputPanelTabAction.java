/*
 * SwitchInputPanelTabAction.java
 * Created on 26.04.2007
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
package info.rolandkrueger.jlexis.gui.keyactions;

import info.rolandkrueger.jlexis.gui.containers.VocableInputTabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class SwitchInputPanelTabAction extends AbstractKeyAction
{
  private static final long serialVersionUID = 8470946308312162254L;

  private final static String sIdentifier = "keyaction.switchInputPanelTab";
  
  private VocableInputTabbedPane mTabbedPane;
  
  public SwitchInputPanelTabAction (VocableInputTabbedPane tabbedPane)
  {
    super (sIdentifier, InputEvent.CTRL_MASK, '\t');
    mTabbedPane = tabbedPane;
  }  

  public void actionPerformed (ActionEvent e)
  {
    mTabbedPane.showNextTab ();
  }
}
