/*
 * Created on 26.09.2009.
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info). All rights reserved.
 * 
 * This file is part of Anteeo.
 */
package org.jlexis.util;


/**
 * @author Roland Krueger
 * @version $Id: StringUtils.java 204 2009-12-17 15:20:16Z roland $
 */
public class StringUtils {
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
}
