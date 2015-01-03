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


import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.roklib.TextFormatter;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public abstract class AbstractStandardUserInputDataHandler {
    private AbstractUserInput mParent;
    private String mUserInputIdentifierExtension;

    protected AbstractStandardUserInputDataHandler(AbstractUserInput parent, String userInputIdentifierExtension) {
//    CheckForNull.check (parent);
        mParent = parent;
        if (userInputIdentifierExtension == null) userInputIdentifierExtension = "";
        mUserInputIdentifierExtension = userInputIdentifierExtension;
    }

    protected RegisteredVocableDataKey getUniqueIdentifier(String discriminator) {
        return new RegisteredVocableDataKey(String.format("%s_%s%s", mParent.getUserInputIdentifier(),
                mUserInputIdentifierExtension, discriminator));
    }

    protected String getUserInputIdentifierExtension() {
        return mUserInputIdentifierExtension;
    }

    protected AbstractUserInput getParent() {
        return mParent;
    }

    public abstract boolean isEmpty();

    public abstract boolean isAnyTextInputDefined();

    public abstract RegisteredVocableDataKey[] getUserInputIdentifiers();

    public abstract void getHTMLVersion(TextFormatter formatter, String addOn);
}
