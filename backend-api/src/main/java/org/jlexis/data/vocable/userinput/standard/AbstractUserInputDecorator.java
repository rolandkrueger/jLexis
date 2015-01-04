/*
 * AbstractStandardUserInputDataHandler.java
 * Created on 06.12.2009
 * 
 * Copyright Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.userinput.standard;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.userinput.UserInput;
import org.jlexis.data.vocable.verification.VocableVerificationData;

/**
 * @author Roland Krueger
 */
public abstract class AbstractUserInputDecorator implements UserInput {
    private UserInput delegate;
    private String userInputIdentifierExtension;

    protected AbstractUserInputDecorator(UserInput delegate, String userInputIdentifierExtension) {
        this.delegate = Preconditions.checkNotNull(delegate);
        this.userInputIdentifierExtension = Strings.nullToEmpty(userInputIdentifierExtension);
    }

    protected RegisteredVocableDataKey getUniqueIdentifier(String discriminator) {
        return new RegisteredVocableDataKey(String.format("%s_%s%s", delegate.getUserInputIdentifier(),
                userInputIdentifierExtension, discriminator));
    }

    protected final String getUserInputIdentifierExtension() {
        return userInputIdentifierExtension;
    }

    protected final UserInput getDelegate() {
        return delegate;
    }

    public abstract boolean isAnyTextInputDefined();
    
    @Override
    public void addUserInput(RegisteredVocableDataKey key, String input) {
        getDelegate().addUserInput(key, input);
    }

    @Override
    public UserInput createUserInputObject() {
        return new StandardAdjectiveUserInputDecorator(getDelegate(), getUserInputIdentifierExtension());
    }

    @Override
    public boolean correspondsTo(UserInput other) {
        return getDelegate().correspondsTo(other);
    }

    @Override
    public AbstractTerm getUserInput(RegisteredVocableDataKey key) {
        return getDelegate().getUserInput(key);
    }

    @Override
    public boolean isInputDefinedFor(RegisteredVocableDataKey key) {
        return getDelegate().isInputDefinedFor(key);
    }

    @Override
    public String getUserInputIdentifier() {
        return getDelegate().getUserInputIdentifier();
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        return getDelegate().getQuizVerificationData();
    }

    @Override
    public void registerKeyForWordStem(RegisteredVocableDataKey key) {
        getDelegate().registerKeyForWordStem(key);
    }

    @Override
    public void registerKeyForInflectedTerm(RegisteredVocableDataKey governingWordStemKey, RegisteredVocableDataKey inflectedTermKey) {
        getDelegate().registerKeyForInflectedTerm(governingWordStemKey, inflectedTermKey);
    }
}
