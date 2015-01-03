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
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.terms.UnmodifiableTerm;
import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.userinput.DBO_AbstractUserInput;
import org.jlexis.data.vocable.userinput.UserInput;
import org.jlexis.data.vocable.verification.VocableVerificationData;

/**
 * @author Roland Krueger
 */
public final class UnmodifiableVocable extends Vocable {
    private static final long serialVersionUID = -4235951974726789314L;

    public UnmodifiableVocable(Vocable data) {
        super();
        this.mData = data.mData;
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
        private UserInput data;

        public UnmodifiableUserInput(UserInput data) {
            if (data == null) throw new NullPointerException("Argument is null.");
            this.data = data;
        }

        public final void addUserInput(RegisteredVocableDataKey identifier, String data) {
            this.data.addUserInput(identifier, data);
        }

        public final DBO_AbstractUserInput getDatabaseObject() {
            throw new UnsupportedOperationException("Not supported.");
        }

        public final String getHTMLVersion() {
            return data.getHTMLVersion();
        }

        public final String getShortVersion() {
            return data.getShortVersion();
        }

        public final AbstractTerm getUserInput(RegisteredVocableDataKey identifier) {
            return new UnmodifiableTerm(data.getUserInput(identifier));
        }

        public final String getUserInputIdentifier() {
            return data.getUserInputIdentifier();
        }

        public final boolean isInputDefinedFor(RegisteredVocableDataKey identifier) {
            return data.isInputDefinedFor(identifier);
        }

        public final boolean isEmpty() {
            return data.isEmpty();
        }

        public final boolean correspondsTo(UserInput other) {
            return data.correspondsTo(other);
        }

        public final void replace(AbstractUserInput userInput) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public VocableVerificationData getQuizVerificationData() {
            return data.getQuizVerificationData();
        }

        @Override
        public void init() {
            data.init();
        }
    }
}
