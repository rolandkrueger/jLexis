/*
 * Created on 12.11.2014
 *
 * Copyright 2014 Roland Krueger (www.rolandkrueger.info)
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jLexis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jlexis.data.vocable.verification;

import com.google.common.base.Joiner;
import org.jlexis.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class AbbreviationAlternatives {

    private String fullForm;
    private Set<String> alternatives;
    private String regex;

    public AbbreviationAlternatives(String fullForm) {
        this.fullForm = " " + collapseWhitespace(checkNotNull(fullForm)) + " ";
    }

    public String getFullForm() {
        return fullForm.trim();
    }

    public Set<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(String... alternativesArray) {
        alternatives = new HashSet<>();
        if (alternativesArray == null || alternativesArray.length == 0) {
            return;
        }

        for (String alternative : alternativesArray) {
            alternatives.add(
                    transformToRegex(
                            collapseWhitespace(StringUtils.escapeRegexSpecialChars(
                                    checkNotNull(alternative, "null alternative is not allowed")))));
        }

        regex = "(" + Joiner.on("|").skipNulls().join(alternatives) + ")";
        alternatives.clear();
        alternatives.addAll(Arrays.asList(alternativesArray));
    }

    public String harmonize(String input) {
        String result = collapseWhitespace(input);
        result = result.replaceAll(regex, "$2" + fullForm + "$5");
        return collapseWhitespace(result);
    }

    private String transformToRegex(String alternative) {
        if (alternative.isEmpty()) {
            return "";
        }

        String result = alternative;
        result = "((\\s)|\\b|^|(\\W))" + result + "((\\s)|\\b|$|(\\W))";
        return result;
    }

    private String collapseWhitespace(String input) {
        if (input == null) {
            return "";
        }
        String result = collapseSequenceOfWhitespaceIntoOneSpaceCharacter(input);
        result = removeAllSpacesAroundNonWordCharacters(result);

        return result.trim();
    }

    private String removeAllSpacesAroundNonWordCharacters(String input) {
        input = input.replaceAll(" ?(\\W) ?", "$1");
        return input;
    }

    private String collapseSequenceOfWhitespaceIntoOneSpaceCharacter(String input) {
        return input.replaceAll("\\s+", " ");
    }
}
