/*
 * TitledBorderPanel.java
 * Created on 04.02.2007
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
package info.rolandkrueger.jlexis.gui.containers;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TitledBorderPanel extends JPanel
{
  private static final long serialVersionUID = -504122352854982396L;

  public TitledBorderPanel ()
  {
    setLayout (new BorderLayout ());
  }
  
  public TitledBorderPanel (String title, Component component)
  {
    this ();
    setTitle (title);
    setComponent (component);
  }
  
  public void setTitle (String title)
  {
    setBorder (new TitledBorder (title));
  }
  
  public void setComponent (Component component)
  {
    add (component, BorderLayout.CENTER);    
  }
}
