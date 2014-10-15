/*
 * LexisTextArea.java
 * Created on 15.04.2007
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
package info.rolandkrueger.jlexis.gui.components;

import info.rolandkrueger.jlexis.managers.ConfigurationManager;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class JLexisTextArea extends JTextArea implements Observer
{
  /**
   * 
   */
  private static final long serialVersionUID = 1044216570844861061L;

  public JLexisTextArea (Document doc, String text, int rows, int columns)
  {
    super (doc, text, rows, columns);
    initialize ();
  }

  public JLexisTextArea (Document doc)
  {
    super (doc);
    initialize ();
  }

  public JLexisTextArea (int rows, int columns)
  {
    super (rows, columns);
    initialize ();
  }

  public JLexisTextArea (String text, int rows, int columns)
  {
    super (text, rows, columns);
    initialize ();
  }

  public JLexisTextArea (String text)
  {
    super (text);
    initialize ();
  }

  public JLexisTextArea ()
  {
    super ();
    initialize ();
  }
  
  private void initialize ()
  {
    ConfigurationManager.getInstance ().addObserver (this);
    setFont (ConfigurationManager.getInstance ().getApplicationFont ());
  }

  public void update (Observable arg0, Object arg1)
  {
    setFont (ConfigurationManager.getInstance ().getApplicationFont ());
  }

  
}
