/*
 * $Id: LexisInternalFrame.java 203 2009-12-16 16:24:28Z roland $
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
package info.rolandkrueger.jlexis.gui.internalframes;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class JLexisInternalFrame extends JInternalFrame implements InternalFrameListener
{
  private static final long serialVersionUID = -7381443622271590453L;
 
  protected JCheckBoxMenuItem mMenuItem;
  
  public JLexisInternalFrame ()
  {
    addInternalFrameListener (this);
    setDefaultCloseOperation (JInternalFrame.DISPOSE_ON_CLOSE);
  }
  
  public void setCorrespondingMenuItem (JCheckBoxMenuItem menuItem)
  {
    mMenuItem = menuItem; 
  }

  @Override
  public void internalFrameActivated (InternalFrameEvent e)
  {
  }

  @Override
  public void internalFrameClosed (InternalFrameEvent e)
  {
    if (mMenuItem != null)
      mMenuItem.setState (false);
  }

  @Override
  public void internalFrameClosing (InternalFrameEvent e) {}

  @Override
  public void internalFrameDeactivated (InternalFrameEvent e)
  {
    if (mMenuItem != null)
      mMenuItem.setState (false);
  }

  @Override
  public void internalFrameDeiconified (InternalFrameEvent e)
  {}

  @Override
  public void internalFrameIconified (InternalFrameEvent e)
  {
  }

  @Override
  public void internalFrameOpened (InternalFrameEvent e)
  {
  }
}
