/*
 * Copyright 2007-2015 Roland Krueger (www.rolandkrueger.info)
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

package org.jlexis.ui.spring.service;

import org.jlexis.plugin.LanguagePlugin;
import org.jlexis.plugin.english.EnglishLanguagePlugin;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Provides all available language plugins.
 *
 * @author Roland Krüger
 */
@Service
public class LanguageService {

    private final List<LanguagePlugin> plugins;

    public LanguageService() {
        plugins = Arrays.asList(new EnglishLanguagePlugin());
    }

    public List<LanguagePlugin> getAvailableLanguages() {
        return plugins;
    }
}
