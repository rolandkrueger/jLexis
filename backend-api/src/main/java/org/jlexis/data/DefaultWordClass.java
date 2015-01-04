/*
 * DefaultWordType.java
 * Created on 29.01.2007
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
package org.jlexis.data;

import org.jlexis.data.vocable.AbstractWordClass;
import org.jlexis.data.vocable.userinput.DefaultUserInput;
import org.jlexis.data.vocable.WordClassEnum;

/**
 * This is the most basic word class that can be possibly used. It does not belong to a particular word class ({@link
 * org.jlexis.data.vocable.WordClassEnum#DEFAULT}) and uses a {@link org.jlexis.data.vocable.userinput
 * .DefaultUserInput} object for storing user input. This word class is typically used by default or when no other,
 * better fitting word class could be found in certain situations.
 */
public class DefaultWordClass extends AbstractWordClass {
    private static final long serialVersionUID = - 3261404278184669765L;

    public DefaultWordClass(String name) {
        super(name, "default", new DefaultUserInput());
    }

    @Override
    public WordClassEnum getWordTypeEnum() {
        return WordClassEnum.DEFAULT;
    }
}
