/*
 * SwedishIPATextField.java
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
package info.rolandkrueger.jlexis.plugin.swedish.inputpanels;

import info.rolandkrueger.jlexis.gui.components.JLexisIPATextField;
import info.rolandkrueger.roklib.ui.swing.augmentedtyping.AugmentedTypingKeyMapping;

public class SwedishIPATextField extends JLexisIPATextField
{
  private static final long serialVersionUID = -5573188015573933612L;

  public SwedishIPATextField ()
  {
    setCharacterMapping (new SwedishIPAKeyMapping ());
  }
  
  private class SwedishIPAKeyMapping extends AugmentedTypingKeyMapping
  {
    // a , '\ u', 
    Character[] aChars = {'\u0251', '\u00E6'};
    // ae
    Character[] aeChars = {'\u00E6', '\u025B'};
    // c
    Character[] cChars = {'\u00E7'};
    // d
    Character[] dChars = {'\u0256'};
    // e
    Character[] eChars = {'\u025B', '\u0259', '\u00E6'};
    // h
    Character[] hChars = {'\u0267'};
    // l
    Character[] lChars = {'\u026D'};
    // n
    Character[] nChars = {'\u014B', '\u0273'};
    // o
    Character[] oChars = {'\u0254', '\u00F8', '\u0153', '\u0275'};
    // oe
    Character[] oeChars = {'\u00F8', '\u0153', '\u0275'};
    // s
    Character[] sChars = {'\u0282', '\u0283'};
    // t
    Character[] tChars = {'\u0288'};
    // u
    Character[] uChars = {'\u0289', '\u0275'};
    // v
    Character[] vChars = {'\u02C7'};
    // :
    Character[] colonChars = {'\u02D0'};
    
    public SwedishIPAKeyMapping ()
    {
      addMapping ('a', aChars);
      addMapping ('\u00E4', aeChars);
      addMapping ('c', cChars);
      addMapping ('d', dChars);
      addMapping ('e', eChars);
      addMapping ('h', hChars);
      addMapping ('l', lChars);
      addMapping ('n', nChars);
      addMapping ('o', oChars);
      addMapping ('\u00F6', oeChars);
      addMapping ('s', sChars);
      addMapping ('t', tChars);
      addMapping ('u', uChars);
      addMapping ('v', vChars);
      addMapping (':', colonChars);
    }
  }
}
