/*
 * $Id: EditVocabularyControllerInterface.java 78 2009-03-07 22:54:49Z roland $
 * Created on 06.03.2009
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

import java.util.List;

public interface EditVocabularyControllerInterface
{
  /**
   * If the user has selected a vocable for editing, this method must be called to indicate the
   * index of the selected vocable in the currently edited {@link LearningUnit}.
   * 
   * @param index
   *          The index of the selected vocable to be edited. If <code>index</code> is -1 a new
   *          vocable has to be created.
   */
  public void setSelectedVocable (int index);
  
  /**
   * This method is called if a new vocable shall be created. The controller will forward this
   * request do the view so that the user will get an empty edit area with an interface
   * corresponding to the word type.
   * 
   * @param nextWordType
   *          the word type of the new vocable.
   */
  public void editNewVocable (AbstractWordType nextWordType);
  
  /**
   * Provides a list of the {@link Language} objects for the currently edited {@link LearningUnit}.
   * 
   * @return a list of all {@link Language}s which are configured for the currently edited
   *         {@link LearningUnit}.
   */
  public List<Language> getLanguagesOfEditedLearningUnit ();
  
  public void removeVocable (Vocable vocable);
  public String getLearningUnitName ();
  public void insertVocable (Vocable vocable, int index);
  public Vocable getVocableAtIndex (int index);
  public void removeSelectedVocable ();
}
