/*
 * Created on 29.03.2009
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
package org.jlexis.quiz.data;

import com.google.common.base.Preconditions;
import org.jlexis.data.languages.Language;
import org.jlexis.data.units.UnmodifiableLearningUnit;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractQuizTypeConfiguration {
    private Optional<Language> language;
    private List<AbstractQuizQuestion> quizQuestions;
    private AbstractQuizType quizType;

    protected AbstractQuizTypeConfiguration(AbstractQuizType type) {
        language = Optional.empty();
        quizType = Preconditions.checkNotNull(type);
    }

    protected abstract List<AbstractQuizQuestion> createQuizQuestionsForImpl(List<UnmodifiableLearningUnit> units, Language forLanguage);

    public abstract String getDescription();

    public abstract AbstractQuizTypeConfiguration getCopy();

    public final AbstractQuizType getQuizType() {
        return quizType;
    }

    public final void createQuizQuestionsFor(List<UnmodifiableLearningUnit> units, Language forLanguage) {
        quizQuestions = null;
        quizQuestions = createQuizQuestionsForImpl(units, forLanguage);
    }

    public final List<AbstractQuizQuestion> getQuizQuestions() {
        if (quizQuestions == null) return Collections.emptyList();
        return quizQuestions;
    }

    public final Optional<Language> getCorrespondingLanguage() {
        return language;
    }

    public final void setCorrespondingLanguage(Language lang) {
        if (lang == null) return;
        language = Optional.of(lang);
    }
}
