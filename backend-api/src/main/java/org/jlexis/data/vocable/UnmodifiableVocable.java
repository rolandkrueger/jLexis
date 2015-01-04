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
import org.jlexis.data.vocable.userinput.UserInput;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.roklib.TextFormatter;

import static com.google.common.base.Preconditions.*;

/**
 * @author Roland Krueger
 */
public final class UnmodifiableVocable extends Vocable {
    private static final long serialVersionUID = -4235951974726789314L;

    public UnmodifiableVocable(Vocable delegate) {
        super(delegate);
        setId(delegate.getId());
    }

    @Override
    public final void addVariant(Language forLanguage, AbstractWordClass wordClass, UserInput userInput) {
        throw new UnsupportedOperationException("This object cannot be modified.");
    }

    @Override
    public final UserInput getVariantInput(Language forLanguage) {
        return new UnmodifiableUserInput(super.getVariantInput(forLanguage));
    }

    private final class UnmodifiableUserInput implements UserInput {
        private static final long serialVersionUID = 4671844719293807954L;
        private UserInput delegate;

        public UnmodifiableUserInput(UserInput delegate) {
            this.delegate = checkNotNull(delegate, "Argument is null.");
        }

        public final void addUserInput(RegisteredVocableDataKey key, String data) {
            this.delegate.addUserInput(key, data);
        }

        @Override
        public UserInput createUserInputObject() {
            return new UnmodifiableUserInput(delegate);
        }

        public final void provideFullDisplayText(TextFormatter formatter) {
            delegate.provideFullDisplayText(formatter);
        }

        public final void provideShortDisplayText(TextFormatter formatter) {
            delegate.provideShortDisplayText(formatter);
        }

        public final AbstractTerm getUserInput(RegisteredVocableDataKey key) {
            return new UnmodifiableTerm(delegate.getUserInput(key));
        }

        public final String getUserInputIdentifier() {
            return delegate.getUserInputIdentifier();
        }

        public final boolean isInputDefinedFor(RegisteredVocableDataKey key) {
            return delegate.isInputDefinedFor(key);
        }

        public final boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean isAnyTextInputDefined() {
            return delegate.isAnyTextInputDefined();
        }

        public final boolean correspondsTo(UserInput other) {
            return delegate.correspondsTo(other);
        }

        @Override
        public VocableVerificationData getQuizVerificationData() {
            return delegate.getQuizVerificationData();
        }

        @Override
        public void registerKeyForWordStem(RegisteredVocableDataKey key) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void registerKeyForInflectedTerm(RegisteredVocableDataKey governingWordStemKey, RegisteredVocableDataKey inflectedTermKey) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void init() {
            delegate.init();
        }
    }
}
