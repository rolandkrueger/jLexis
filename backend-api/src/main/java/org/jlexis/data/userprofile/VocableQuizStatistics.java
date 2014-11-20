/*
 * Created on 03.10.2009.
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
package org.jlexis.data.userprofile;


import org.jlexis.data.vocable.Vocable;

public class VocableQuizStatistics {
    private long mId;
    private Vocable mVocable;
//    private UserProfile mForUser;
    private int mCorrectlyAnswered;
    private int mIncorrectlyAnswered;

    public long getId() {
        return mId;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        mId = id;
    }

    public Vocable getVocable() {
        return mVocable;
    }

    public void setVocable(Vocable vocable) {
        mVocable = vocable;
    }

    public int getCorrectlyAnswered() {
        return mCorrectlyAnswered;
    }

    public void setCorrectlyAnswered(int correctlyAnswered) {
        mCorrectlyAnswered = correctlyAnswered;
    }

    public int getIncorrectlyAnswered() {
        return mIncorrectlyAnswered;
    }

    public void setIncorrectlyAnswered(int incorrectlyAnswered) {
        mIncorrectlyAnswered = incorrectlyAnswered;
    }

    public int getTotal() {
        return mIncorrectlyAnswered + mCorrectlyAnswered;
    }

//    public UserProfile getForUser() {
//        return null;
//    }
//
//    public void setForUser(UserProfile forUser) {
//
//    }
}
