/*
 * $Id: LearningUnit.java 203 2009-12-16 16:24:28Z roland $
 * Created on 17.11.2008
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
import org.jlexis.data.vocable.Vocable;

import java.util.*;

public class LearningUnit extends Observable implements Iterable<Vocable> {
    private long mID;
    private String mName;
    private String mDescription;
    private Language mNativeLanguage;
    private List<Language> mLanguages;
    private Date mCreationDate;
    private List<Vocable> mVocables;
    private boolean mOpenForEditing;
    //    private EditLearningUnitInternalFrame mEditFrame;
    private String mLanguagesString;

    public LearningUnit() {
        mVocables = new ArrayList<Vocable>();
        mOpenForEditing = false;
    }

    public long getId() {
        return mID;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        mID = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Language getNativeLanguage() {
        return mNativeLanguage;
    }

    public void setNativeLanguage(Language nativeLanguage) {
        mNativeLanguage = nativeLanguage;
    }

    public void setNativeLanguageName(String nativeLanguageName) {
        //TODO: fix me
//        LanguagePlugin nativeLanguagePlugin = JLexisMain.getInstance().getPluginManager().getNativeLanguagePlugin();
//        Session session = JLexisPersistenceManager.getInstance().getSession();
//        Query q = session.createQuery("from Language l where l.languageName=:name and " +
//                "l.pluginIdentifier=:identifier and l.pluginVersion=:version")
//                .setParameter("name", nativeLanguageName)
//                .setParameter("identifier", nativeLanguagePlugin.getIdentifier())
//                .setParameter("version", nativeLanguagePlugin.getVersionNumber());
//        Object result = q.uniqueResult();
//
//        if (result == null) {
//            mNativeLanguage = LanguageFactory.getInstance().getLanguageFor(nativeLanguagePlugin);
//            mNativeLanguage.setLanguageName(nativeLanguageName);
//        } else {
//            mNativeLanguage = (Language) result;
//        }
    }

    public List<Language> getLanguages() {
        if (mLanguages == null)
            mLanguages = new ArrayList<Language>();

        return mLanguages;
    }

    /**
     * Sets the languages that are defined for this {@link LearningUnit}. For every language which is defined for a
     * {@link LearningUnit} the user can add vocabulary data.
     *
     * @param languages a list of languages that can be learning with this {@link LearningUnit}
     */
    public void setLanguages(List<Language> languages) {
        mLanguages = new ArrayList<Language>(languages);
    }

    /**
     * Provides the creation date for this {@link LearningUnit}.
     *
     * @return the {@link LearningUnit}'s creation date
     */
    public Date getCreationDate() {
        return mCreationDate;
    }

    /**
     * Sets the creation date for this {@link LearningUnit}.
     *
     * @param creationDate the {@link LearningUnit}'s creation date.
     */
    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }

    /**
     * Gets the complete vocabulary for this {@link LearningUnit}.
     *
     * @return all {@link Vocable}s
     */
    @SuppressWarnings("unused")
    private List<Vocable> getVocables() {
        return mVocables;
    }

    /**
     * Setter method for the vocabulary data for Hibernate support.
     *
     * @param vocables the vocabulary data
     */
    @SuppressWarnings("unused")
    private void setVocables(List<Vocable> vocables) {
        mVocables = vocables;
    }

    public void addVocable(Vocable vocable) {
//        TODO: fix me
//        mVocables.add(vocable);
//        JLexisPersistenceManager.getInstance().flushSession();
//        setChanged();
//        notifyObservers();
    }

    public void insertVocable(Vocable vocable, int index) {
        //        TODO: fix me
//        if (index < 0 || index > mVocables.size() - 1) {
//            addVocable(vocable);
//        } else {
//            mVocables.add(index, vocable);
//        }
//
//        JLexisPersistenceManager.getInstance().getSession().flush();
//        setChanged();
//        notifyObservers();
    }

    /**
     * Provides the number of vocabulary entries contained in this {@link LearningUnit}.
     *
     * @return the number of vocabulary entries contained in this {@link LearningUnit}.
     */
    public int getSize() {
        return mVocables.size();
    }

    @SuppressWarnings("unused")
    private void setSize(int size) {
    }

    public Vocable getVocableAt(int index) {
        return mVocables.get(index);
    }

    public int indexOf(Vocable vocable) {
        return mVocables.indexOf(vocable);
    }

    public void removeVocable(Vocable vocable) {
        mVocables.remove(vocable);
        setChanged();
        notifyObservers();
    }

    public void replaceVocable(Vocable voc, int index) {
        //        TODO: fix me
//        getVocableAt(index).replace(voc);
//        JLexisPersistenceManager.getInstance().flushSession();
//        setChanged();
//        notifyObservers();
    }

    //        TODO: fix me
//    public void openForEditing(EditLearningUnitInternalFrame frame) {
//        assert !mOpenForEditing;
//        if (frame == null)
//            throw new NullPointerException("Edit frame is null.");
//        mOpenForEditing = true;
//        mEditFrame = frame;
//        // get the language plugins for the languages used by this unit
//        for (Language language : getLanguages()) {
//            LanguagePlugin plugin = JLexisMain.getInstance().getPluginManager().getPluginFor(language);
//            if (plugin == null) {
//                //TODO: handle this case in a user-friendly way
//                throw new NullPointerException("No plugin found for language " + language);
//            }
//            language.setLanguagePlugin(plugin);
//        }
//    }

    //        TODO: fix me
//    public EditLearningUnitInternalFrame getEditFrame() {
//        return mEditFrame;
//    }

    public void closeForEditing() {
        //        TODO: fix me
//        assert mOpenForEditing;
//        mEditFrame.saveChanges();
//        mOpenForEditing = false;
//        mEditFrame = null;
    }

    public boolean isOpenForEditing() {
        return mOpenForEditing;
    }

    /**
     * Deletes this {@link LearningUnit}.
     */
    public void delete() {
        //        TODO: fix me
//        JLexisPersistenceManager.getInstance().deleteObject(this);
//        LearningUnitManager.getInstance().removeLearningUnit(this);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        return (int) getId();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO
        return super.equals(obj);
    }

    @Override
    public Iterator<Vocable> iterator() {
        return mVocables.iterator();
    }

    public String getLanguagesAsString() {
        if (mLanguagesString == null) {
            StringBuilder buf = new StringBuilder();
            for (Language lang : getLanguages()) {
                buf.append(lang.getName());
                buf.append(", ");
            }
            buf.setLength(buf.length() - 2);

            mLanguagesString = buf.toString();
        }
        return mLanguagesString;
    }
}
