/*
 * Created on 26.09.2009.
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info). All rights reserved.
 * 
 * This file is part of Anteeo.
 */
package org.jlexis.util;

import com.google.common.base.Strings;

/**
 * @author Roland Krueger
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static String escapeRegexSpecialChars(String value) {
        value = value.replace(".", "\\.");
        value = value.replace("?", "\\?");
        value = value.replace("*", "\\*");
        value = value.replace("+", "\\+");
        value = value.replace("(", "\\(");
        value = value.replace(")", "\\)");
        value = value.replace("[", "\\[");
        value = value.replace("]", "\\]");
        value = value.replace("{", "\\{");
        value = value.replace("}", "\\}");
        value = value.replace("^", "\\^");
        value = value.replace("$", "\\$");
        value = value.replace("|", "\\|");
        value = value.replace("!", "\\!");
        return value;
    }

    public static boolean isStringNullOrEmpty(String value) {
        return Strings.nullToEmpty(value).trim().isEmpty();
    }
}
