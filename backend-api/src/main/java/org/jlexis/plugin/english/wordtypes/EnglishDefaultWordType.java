/*
 * Created on 20.11.2009.
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
package org.jlexis.plugin.english.wordtypes;

import org.jlexis.data.DefaultWordType;
import org.jlexis.plugin.english.inputpanels.EnglishIPATextfield;

public class EnglishDefaultWordType extends DefaultWordType {
    public EnglishDefaultWordType() {
        // TODO I18N
//    super ("Sonstiges");
        super("Noun");
    }

    @Override
    public AbstractVocableInputPanel getInputPanel() {
        return new DefaultVocableInputPanel(new EnglishIPATextfield(), this);
    }
}
