/*
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
package org.jlexis.data.vocable;

import com.google.common.base.Strings;
import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.userinput.UserInput;

import java.util.Objects;

import static com.google.common.base.Preconditions.*;

public abstract class AbstractWordClass {
    private String name;
    private String identifier;
    private AbstractUserInput userInputPrototype;

    protected AbstractWordClass(String name, String identifier, AbstractUserInput userInputPrototype) {
        this.name = checkNotNull(name);
        this.userInputPrototype = checkNotNull(userInputPrototype);

        checkArgument(! Strings.isNullOrEmpty(identifier), "Identifier must not be null or the empty string.");
        this.identifier = identifier;

    }

    /**
     * Provides the concrete word class enum value that this object represents.
     */
    public abstract WordClassEnum getWordTypeEnum();

    /**
     * Factory method for creating the user input object that corresponds to this word type.
     * @return a new user input object that corresponds to this word type.
     */
    public final UserInput createNewUserInputObject() {
        UserInput result = userInputPrototype.createUserInputObject();
        result.init();
        return result;
    }

    public final String getUserInputIdentifier() {
        return userInputPrototype.getUserInputIdentifier();
    }

    public final String getName() {
        return name;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public void registerUserInputIdentifiers() {
//        userInputPrototype.registerUserInputIdentifiers();
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        return obj instanceof AbstractWordClass && Objects.equals(identifier, ((AbstractWordClass) obj).identifier);
    }
}
