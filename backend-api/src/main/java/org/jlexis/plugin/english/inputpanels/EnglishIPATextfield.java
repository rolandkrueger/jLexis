/*
 * Created on 26.10.2009
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
package org.jlexis.plugin.english.inputpanels;


/**
 * @author Roland Krueger
 * @version $Id: EnglishIPATextfield.java 160 2009-10-26 18:09:58Z roland $
 */
public class EnglishIPATextfield extends JLexisIPATextField {
    private static final long serialVersionUID = -5792671852378010987L;

    public EnglishIPATextfield() {
        setCharacterMapping(new EnglishIPAKeyMapping());
    }

    private class EnglishIPAKeyMapping extends AugmentedTypingKeyMapping {
        // a
        Character[] aChars = {'\u0251', '\u00E6', '\u0251', '\u0252', '\u028C'};
        // c
        Character[] cChars = {'\u0254'};
        // e
        Character[] eChars = {'\u0259', '\u025C'};
        // i
        Character[] iChars = {'\u026a'};
        // n
        Character[] nChars = {'\u014B'};
        // o
        Character[] oChars = {'\u0254'};
        // s
        Character[] sChars = {'\u0283', '\u0292'};
        // t
        Character[] tChars = {'\u03B8', '\u00F0'};
        // u
        Character[] uChars = {'\u028a'};
        // z
        Character[] zChars = {'\u0292'};

        // :
        Character[] colonChars = {'\u02D0'};

        public EnglishIPAKeyMapping() {
            addMapping('a', aChars);
            addMapping('c', cChars);
            addMapping('e', eChars);
            addMapping('i', iChars);
            addMapping('n', nChars);
            addMapping('o', oChars);
            addMapping('s', sChars);
            addMapping('t', tChars);
            addMapping('u', uChars);
            addMapping('z', zChars);
            addMapping(':', colonChars);
        }
    }
}
