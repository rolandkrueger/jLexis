/*
 * Created on 23.10.2009
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
import info.rolandkrueger.roklib.ui.swing.augmentedtyping.AugmentedTypingTextField;
import info.rolandkrueger.roklib.ui.swing.augmentedtyping.DefaultIPAKeyMapping;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Roland Krueger
 * @version $Id: LexisIPATextField.java 156 2009-10-23 19:40:41Z roland $
 */
public class JLexisIPATextField extends AugmentedTypingTextField implements Observer
{
  private static final long serialVersionUID = -3072226669186305241L;

  public JLexisIPATextField ()
  {
    super (new DefaultIPAKeyMapping ());
    setFont (ConfigurationManager.getInstance ().getApplicationFont ());
    ConfigurationManager.getInstance ().addObserver (this);    
  }
  
  public void update (Observable observable, Object data)
  {
    setFont (ConfigurationManager.getInstance ().getApplicationFont ());
  }
}
