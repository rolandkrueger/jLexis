/*
 * Copyright 2007-2014 Roland Krueger (www.rolandkrueger.info)
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages a set of {@link AbbreviationVariants}
 *
 * @author Roland Krüger
 * @see AbbreviationVariants
 */
public class SetOfAbbreviationVariants {

    private Map<String, AbbreviationVariants> variants;

    public SetOfAbbreviationVariants() {
        variants = new HashMap<>();
    }

    /**
     * Add an abbreviation and its variants to this set.
     *
     * @param fullForm     the full form of the abbreviation (e.g. 'something'). Must not be <code>null</code> or
     *                     empty.
     * @param variant      one of the variants of the abbreviation (e.g. 'sth.') (mandatory value). Must not be
     *                     <code>null</code> or empty.
     * @param moreVariants more optional variants (e.g. 'someth.', 'sth', 'smthg.', ...)
     */
    public void addAbbreviation(String fullForm, String variant, String... moreVariants) {
        checkNotNull(fullForm);
        checkNotNull(variant);
        if ("".equals(fullForm.trim()) || "".equals(variant.trim())) {
            throw new IllegalStateException("full form and first variant must not be empty");
        }
        AbbreviationVariants abbreviationVariants = new AbbreviationVariants(fullForm);
        abbreviationVariants.setVariants(variant, moreVariants);
        variants.put(fullForm, abbreviationVariants);
    }

    /**
     * Harmonizes all abbreviations contained in the given input String using all abbreviation variants managed by this
     * set.
     *
     * @param input String which may contain abbreviations to be harmonized
     * @return the input String with all contained abbreviations harmonized with all abbreviation variants of this set
     */
    public String harmonizeAll(String input) {
        checkNotNull(input);

        for (AbbreviationVariants abbreviationVariants : variants.values()) {
            input = abbreviationVariants.harmonize(input);
        }

        return input;
    }

    /**
     * Returns the set of variants for the given abbreviation full form if available. Returns an empty set if no such
     * abbreviation has been added to this set.
     *
     * @param fullForm abbreviation whose variants are queried
     * @return set of variants for the specified abbreviation full form or an empty set if abbreviation is unknown
     */
    public Set<String> getVariantsFor(String fullForm) {
        if (variants.get(fullForm) == null) {
            return Collections.emptySet();
        }

        return variants.get(fullForm).getVariants();
    }

    public int getSize() {
        return variants.size();
    }
}
