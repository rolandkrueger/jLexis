/*
 * $Id: EditVocabularyViewInterface.java 200 2009-12-14 17:31:53Z roland $
 * Created on 05.03.2009
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
package info.rolandkrueger.jlexis.controllers;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.gui.keyactions.NextVocableAction;

public interface EditVocabularyViewInterface
{
  public void    setController               (EditVocabularyControllerInterface controller);
  public void    removeAllLanguageInputAreas ();
  public void    clearAllUserInput           ();
  public void    addLanguage                 (Language language);
  public void    setEditedLearningUnit       (LearningUnit unit);
  public Vocable getCurrentInput             ();
  public void    editNewVocable              (AbstractWordType nextWordType);
  public void    setCurrentlyEditedVocable   (Vocable vocable);
  public void    updateView                  ();
  public void    setEditedIndex              (int index);
  public int     getSelectedVocableIndex     ();
  public void    setNextVocableAction (NextVocableAction action);
}
