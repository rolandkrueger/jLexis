/*
 * LexisAbstractAction.java
 * Created on 31.01.2007
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
import info.rolandkrueger.jlexis.gui.JLexisMainWindow;
import info.rolandkrueger.roklib.util.groupvisibility.IVisibilityEnablingConfigurable;

import javax.swing.AbstractAction;
import javax.swing.Icon;

public abstract class JLexisAbstractAction extends AbstractAction implements IVisibilityEnablingConfigurable
{
  private static final long serialVersionUID = -6843991009387098463L;
 
  private JLexisMainWindow mMain;
  
  public JLexisAbstractAction (String name, Icon icon)
  {
    super (name, icon);
    mMain = JLexisMain.getInstance ().getMainWindow ();
  }
  
  public JLexisAbstractAction (String name)
  {
    super (name);
    mMain = JLexisMain.getInstance ().getMainWindow ();;
  }
  
  protected JLexisMainWindow getMain ()
  {
    return mMain;
  }
  
  @Override
  public boolean isEnabled ()
  {
    return super.isEnabled ();
  }
  
  @Override
  public boolean isVisible ()
  {
    return true;
  }
  
  @Override
  public void setEnabled (boolean newValue)
  {
    super.setEnabled (newValue);
  }
  
  @Override
  public void setVisible (boolean visible)
  {
  }
}
