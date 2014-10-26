/*
 * Created on 24.03.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jlexis.data.vocable.terms;


import org.jlexis.data.vocable.verification.VocableVerificationData;

/*
 * @author Roland Krueger
 * @version $Id: TermDataInterface.java 113 2009-05-20 18:19:32Z roland $
 */
public interface TermDataInterface {
    public abstract String getNormalizedTerm();

    public abstract void setNormalizedTerm(String normalizedTerm);

    public abstract String getUserEnteredTerm();

    public abstract void setUserEnteredTerm(String term);

    public abstract String getPurgedTerm();

    public abstract boolean isEmpty();

    public abstract String getResolvedTerm();

    public abstract String getResolvedAndPurgedTerm();

    public abstract String getWordStem();

    public abstract boolean isWordStem();

    public VocableVerificationData getVerificationData();
}