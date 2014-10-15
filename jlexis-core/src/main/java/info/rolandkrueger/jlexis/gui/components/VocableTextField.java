/*
 * VocableTextField.java
 * Created on 05.04.2007
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

import javax.swing.JTextField;

/**
 * 
 * @author Roland Krueger
 * @version $Id: VocableTextField.java 188 2009-11-24 17:08:39Z roland $ 
 */
public class VocableTextField extends JTextField implements Observer
{
  private static final long serialVersionUID = 8250976173800956044L;
  
  public VocableTextField ()
  {
  }
  
  public void update (Observable observable, Object data)
  {
    setFont (ConfigurationManager.getInstance ().getApplicationFont ());
  }
  
  public boolean isUserInputEmpty ()
  {
    return getText ().trim ().length () == 0;
  }
  
  public void clearInput ()
  {
    setText ("");
  }
}
