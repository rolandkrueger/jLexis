/*
 * Created on 23.03.2009
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
package org.jlexis.data.units;


import org.jlexis.data.languages.Language;
import org.jlexis.data.vocable.UnmodifiableVocable;
import org.jlexis.data.vocable.Vocable;
import org.jlexis.util.JLexisUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: UnmodifiableLearningUnit.java 128 2009-06-02 18:52:42Z roland $
 */
public final class UnmodifiableLearningUnit implements Iterable<Vocable> {
    private LearningUnit mManagedUnit;

    public UnmodifiableLearningUnit(LearningUnit managedUnit) {
        if (managedUnit == null)
            throw new NullPointerException("Argument is null.");
        mManagedUnit = managedUnit;
    }

    public final Vocable getVocableAt(int index) {
        return new UnmodifiableVocable(mManagedUnit.getVocableAt(index));
    }

    public final boolean equals(Object obj) {
        return mManagedUnit.equals(obj);
    }

    public final Date getCreationDate() {
        return mManagedUnit.getCreationDate();
    }

    public final String getDescription() {
        return mManagedUnit.getDescription();
    }

    public final long getId() {
        return mManagedUnit.getId();
    }

    public final List<Language> getLanguages() {
        return mManagedUnit.getLanguages();
    }

    public final String getName() {
        return mManagedUnit.getName();
    }

    public final Language getNativeLanguage() {
        return mManagedUnit.getNativeLanguage();
    }

    public final int getSize() {
        return mManagedUnit.getSize();
    }

    public final int hashCode() {
        return mManagedUnit.hashCode();
    }

    public final int indexOf(Vocable vocable) {
        return mManagedUnit.indexOf(vocable);
    }

    public final boolean isOpenForEditing() {
        return mManagedUnit.isOpenForEditing();
    }

    public final String toString() {
        return mManagedUnit.toString();
    }

    public Language getRandomLanguage() {
        List<Language> languages = new ArrayList<Language>(getLanguages());
        languages.add(getNativeLanguage());
        return randomlySelectLanguage(languages);
    }

    public Language getRandomLanguageExcept(Language exception) {
        List<Language> languages = new ArrayList<Language>(getLanguages());
        languages.add(getNativeLanguage());
        languages.remove(exception);
        return randomlySelectLanguage(languages);
    }

    private Language randomlySelectLanguage(List<Language> languages) {
        if (languages.size() == 1) return languages.get(0);
        return languages.get(JLexisUtils.getRandomNumber(languages.size()));
    }

    @Override
    public final Iterator<Vocable> iterator() {
        return new UnmodifiableIterator(mManagedUnit.iterator());
    }

    private final class UnmodifiableIterator implements Iterator<Vocable> {
        private Iterator<Vocable> mOther;

        public UnmodifiableIterator(Iterator<Vocable> other) {
            if (other == null) throw new NullPointerException("Argument is null.");
            mOther = other;
        }

        @Override
        public boolean hasNext() {
            return mOther.hasNext();
        }

        @Override
        public Vocable next() {
            return new UnmodifiableVocable(mOther.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Iterator is not allowed to modify data structure.");
        }
    }
}
