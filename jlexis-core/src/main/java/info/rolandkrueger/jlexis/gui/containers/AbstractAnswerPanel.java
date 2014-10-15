/*
 * Created on 06.04.2009
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
package info.rolandkrueger.jlexis.gui.containers;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * @author Roland Krueger
 * @version $Id: AbstractAnswerPanel.java 131 2009-07-03 15:49:12Z roland $
 */
public abstract class AbstractAnswerPanel extends JPanel
{
  private static final long serialVersionUID = -4675856598216114336L;
  private ActionListener actionListener;
  
  protected abstract void addCheckAnswerActionListenerImpl (ActionListener listener);
  protected abstract void removeCheckAnswerActionListener  (ActionListener listener);
  protected abstract void requestFocusForInputElementImpl ();
  
  public abstract void reset ();
  
  public void requestFocusForInputElement ()
  {
    requestFocusForInputElementImpl (); 
  }
  
  public void setCheckAnswerActionListener (ActionListener listener)
  {
    if (listener == null)
      throw new NullPointerException ("Action listener is null.");
    
    if (actionListener == listener) return; 

    if (actionListener != null)
      removeCheckAnswerActionListener  (actionListener);
    
    actionListener = listener;
    addCheckAnswerActionListenerImpl (listener);
  }
}
