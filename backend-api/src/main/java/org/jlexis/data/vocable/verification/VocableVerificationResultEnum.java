/*
 * Created on 19.05.2009
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
package org.jlexis.data.vocable.verification;

/**
 * Defines the outcome of the evaluation of a quiz question. If a quiz question was answered correctly, the result of
 * the vocable verification is {@link VocableVerificationResultEnum#CORRECT}.
 *
 * @author Roland Krueger
 */
public enum VocableVerificationResultEnum {
    /**
     * Given answer was correct
     */
    CORRECT,
    /**
     * Value for an incorrect answer: all required variants were given, but in addition one or more incorrect surplus
     * variants were given as well.
     */
    TOO_MANY_VALUES,
    /**
     * Value for an incorrect answer: not all required variants were given.
     */
    NOT_ENOUGH_VALUES
}
