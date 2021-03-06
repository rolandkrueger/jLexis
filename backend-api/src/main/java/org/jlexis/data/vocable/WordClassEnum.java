/*
 * $Id: WordTypeEnum.java 190 2009-12-06 16:01:09Z roland $
 * Created on 07.03.2009
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
package org.jlexis.data.vocable;

/**
 * List of supported word classes.
 */
public enum WordClassEnum {
    ADJECTIVE,
    ADPOSITION,
    ADVERB,
    CARDINAL_NUMBER,
    CLITIC,
    COLLOCATION,
    CONJUNCTION,
    CONTRACTION,
    COVERB,
    DETERMINER,
    IDIOM,
    INTERJECTION,
    MEASURE_WORD,
    NOUN,
    PARTICLE,
    PHRASAL_VERB,
    PREVERB,
    PRONOUN,
    VERB,
    /**
     * Default value; to be used of no other word class is applicable.
     */
    DEFAULT,
    EXTRA
}
