/*
 * $Id: RemoveVocableCommand.java 78 2009-03-07 22:54:49Z roland $
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
package info.rolandkrueger.jlexis.commands;

import info.rolandkrueger.jlexis.controllers.EditVocabularyControllerInterface;
import info.rolandkrueger.jlexis.data.vocable.Vocable;

public class RemoveVocableCommand implements UndoableCommand
{
  private EditVocabularyControllerInterface mEditVocabularyController; 
  private Vocable                           mVocable;
  private int                               mVocableIndex;
  
  public RemoveVocableCommand (EditVocabularyControllerInterface editVocabularyController, 
                               Vocable vocable, int vocableIndex)
  {
    if (editVocabularyController == null || vocable == null)
      throw new NullPointerException ("One of the arguments is null.");
    mEditVocabularyController = editVocabularyController;
    mVocable                  = vocable;
    mVocableIndex             = vocableIndex;
  }
  
  @Override
  public void execute ()
  {
    mEditVocabularyController.removeVocable (mVocable);
  }

  @Override
  public String getDescription ()
  {
    // TODO: I18N
    return String.format ("Vokabel %d entfernt aus %s.", mVocableIndex, mEditVocabularyController.getLearningUnitName ());
  }

  @Override
  public void undo ()
  {
    mEditVocabularyController.insertVocable (mVocable, mVocableIndex);
  }
}
