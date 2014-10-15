/*
 * $Id: PopupListener.java 72 2009-03-06 18:13:41Z roland $
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
package info.rolandkrueger.jlexis.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter
{
  private JPopupMenu mPopup;
  
  public PopupListener (JPopupMenu popup)
  {
    if (popup == null)
      throw new NullPointerException ("Popup menu is null");
    mPopup = popup;
  }

  public void mousePressed (MouseEvent e)
  {
    maybeShowPopup (e);
  }

  public void mouseReleased (MouseEvent e)
  {
    maybeShowPopup (e);
  }

  private void maybeShowPopup (MouseEvent e)
  {
    if (e.isPopupTrigger ())
    {
      mPopup.show (e.getComponent (), e.getX (), e.getY ());
    }
  }
}
