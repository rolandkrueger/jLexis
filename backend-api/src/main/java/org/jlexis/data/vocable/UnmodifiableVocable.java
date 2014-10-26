/*
 * Created on 23.03.2009
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
package org.jlexis.data.vocable;

import org.jlexis.data.languages.Language;
import org.jlexis.data.vocable.terms.TermDataInterface;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Roland Krueger
 * @version $Id: UnmodifiableVocable.java 197 2009-12-14 07:27:08Z roland $
 */
public final class UnmodifiableVocable extends Vocable {
    private static final long serialVersionUID = -4235951974726789314L;

    public UnmodifiableVocable(Vocable data) {
        super();
        mData = data.mData;
        setId(data.getId());
    }

    @Override
    public final void addVariant(Language forLanguage, AbstractWordType wordType, AbstractUserInput data) {
        throw new UnsupportedOperationException("This object cannot be modified.");
    }

    @Override
    public final UserInput getVariantInput(Language forLanguage) {
        return new UnmodifiableUserInput(super.getVariantInput(forLanguage));
    }

    @Override
    public final void replace(Vocable voc) {
        throw new UnsupportedOperationException("This object cannot be modified.");
    }

    private final class UnmodifiableUserInput implements UserInput {
        private UserInput mData;

        public UnmodifiableUserInput(UserInput data) {
            if (data == null) throw new NullPointerException("Argument is null.");
            mData = data;
        }

        public final void addUserData(String identifier, String data) {
            mData.addUserData(identifier, data);
        }

        @Deprecated
        public final void appendXMLData(Document document, Element root) {
            throw new UnsupportedOperationException("Deprecated method.");
        }

        public final DBO_AbstractUserInput getDatabaseObject() {
            throw new UnsupportedOperationException("Not supported.");
        }

        public final String getHTMLVersion() {
            return mData.getHTMLVersion();
        }

        public final String getShortVersion() {
            return mData.getShortVersion();
        }

        public final TermDataInterface getUserData(String identifier) {
            return new UnmodifiableTermData(mData.getUserData(identifier));
        }

        public final String getUserInputIdentifier() {
            return mData.getUserInputIdentifier();
        }

        public final boolean isDataDefinedFor(String identifier) {
            return mData.isDataDefinedFor(identifier);
        }

        public final boolean isEmpty() {
            return mData.isEmpty();
        }

        public final boolean isTypeCorrect(UserInput other) {
            return mData.isTypeCorrect(other);
        }

        public final void replace(AbstractUserInput userInput) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public VocableVerificationData getQuizVerificationData() {
            return new VocableVerificationData.UnmodifiableVocableVerificationData(mData.getQuizVerificationData());
        }

        @Override
        public String getComment() {
            return mData.getComment();
        }

        @Override
        public String getExample() {
            return mData.getExample();
        }

        @Override
        public void init() {
            mData.init();
        }
    }

    private final class UnmodifiableTermData implements TermDataInterface {
        private TermDataInterface mData;

        public UnmodifiableTermData(TermDataInterface data) {
            if (data == null) throw new NullPointerException("Argument is null.");
            mData = data;
        }

        public final String getNormalizedTerm() {
            return mData.getNormalizedTerm();
        }

        public final void setNormalizedTerm(String normalizedTerm) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        public final String getPurgedTerm() {
            return mData.getPurgedTerm();
        }

        public final String getResolvedTerm() {
            return mData.getResolvedTerm();
        }

        public final String getUserEnteredTerm() {
            return mData.getUserEnteredTerm();
        }

        public final void setUserEnteredTerm(String term) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        public final String getWordStem() {
            return mData.getWordStem();
        }

        public final boolean isEmpty() {
            return mData.isEmpty();
        }

        public final boolean isWordStem() {
            return mData.isWordStem();
        }

        @Override
        public final String toString() {
            return mData.toString();
        }

        @Override
        public VocableVerificationData getVerificationData() {
            return mData.getVerificationData();
        }

        @Override
        public String getResolvedAndPurgedTerm() {
            return mData.getResolvedAndPurgedTerm();
        }
    }
}
