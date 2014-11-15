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
import com.google.common.collect.Ordering;
import org.jlexis.util.StringUtils;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class AbbreviationVariants {

    private String fullForm;
    private Set<String> variants;
    private String regex;

    public AbbreviationVariants(String fullForm) {
        this.fullForm = " " + collapseWhitespace(checkNotNull(fullForm)) + " ";
    }

    public String getFullForm() {
        return fullForm.trim();
    }

    public Set<String> getVariants() {
        return variants;
    }

    public void setVariants(String variant, String... variantsArray) {
        List<String> sortedVariants = createSortedListOfVariantsArray(variant, variantsArray);
        buildRegexForFindingAbbreviationVariants(sortedVariants);
        assembleVariantsInSet(variant, variantsArray);
    }

    private void assembleVariantsInSet(String variant, String[] variantsArray) {
        variants = new HashSet<>();
        variants.add(variant);

        if (variantsArray != null) {
            variants.addAll(Arrays.asList(variantsArray));
        }
    }

    private void buildRegexForFindingAbbreviationVariants(List<String> sortedVariants) {
        List<String> transformedVariants = new ArrayList<>(sortedVariants.size());
        for (String variants : sortedVariants) {
            transformAndAddVariant(transformedVariants, variants);
        }

        regex = "(" + Joiner.on("|").skipNulls().join(transformedVariants) + ")";
    }

    private List<String> createSortedListOfVariantsArray(String variant, String[] variantsArray) {
        List<String> sortedVariants = new ArrayList<>(variantsArray == null ? 1 : variantsArray.length + 1);
        sortedVariants.add(variant);
        if (variantsArray != null) {
            for (String arrayVariant : variantsArray) {
                sortedVariants.add(arrayVariant);
            }
        }
        return  Ordering.natural().reverse().sortedCopy(sortedVariants);
    }

    private void transformAndAddVariant(List<String> list, String variants) {
        list.add(
                transformToRegex(
                        collapseWhitespace(StringUtils.escapeRegexSpecialChars(
                                checkNotNull(variants, "null variant is not allowed")))));
    }

    public String harmonize(String input) {
        String result = collapseWhitespace(input);
        result = result.replaceAll(regex, fullForm);
        return collapseWhitespace(result);
    }

    private String transformToRegex(String variants) {
        if (variants.isEmpty()) {
            return "";
        }

        String result = variants;
        // use positive look behind and look ahead groups to assure that an abbreviation is enclosed by
        // either a punctuation character, a whitespace, a word boundary or the beginning or end of the input
        result = "(?<=(\\p{Punct})|(\\s)|(\\b)|^)" + result + "(?=(\\p{Punct})|(\\s)|(\\b)|$)";
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
        input = input.replaceAll(" ?(\\p{Punct}) ?", "$1");
        return input;
    }

    private String collapseSequenceOfWhitespaceIntoOneSpaceCharacter(String input) {
        return input.replaceAll("\\s+", " ");
    }
}
