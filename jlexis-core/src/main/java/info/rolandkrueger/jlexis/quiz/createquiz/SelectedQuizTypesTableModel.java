/*
 * Created on 31.03.2009
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
package info.rolandkrueger.jlexis.quiz.createquiz;

import info.rolandkrueger.jlexis.quiz.data.AbstractQuizTypeConfiguration;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Roland Krueger
 * @version $Id: SelectedQuizTypesTableModel.java 197 2009-12-14 07:27:08Z roland $
 */
public class SelectedQuizTypesTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = -4266955637799390713L;
  private List<AbstractQuizTypeConfiguration> mSelectedQuizTypes;
  
  public SelectedQuizTypesTableModel ()
  {
    mSelectedQuizTypes = Collections.emptyList ();
  }
  
  public void setQuizTypeConfigurations (List<AbstractQuizTypeConfiguration> selectedQuizTypes)
  {
    if (selectedQuizTypes == null)
      throw new NullPointerException ("Selected quiz types list is null.");
    mSelectedQuizTypes = selectedQuizTypes;
    fireTableDataChanged ();
  }
  
  @Override
  public int getColumnCount ()
  {
    return 4;
  }
  
  @Override
  public String getColumnName (int column)
  {
    switch (column)
    {
    // TODO: I18N
      case 0:
//        return "Sprache";
        return "Language";
      case 1:
//        return "Abfragetyp";
        return "Quiz type";
      case 2:
//        return "Beschreibung";
        return "Description";
      case 3:
//        return "Anzahl Fragen";
        return "Question count";
      default:
        return "?";
    }     
  }

  @Override
  public int getRowCount ()
  {
    return mSelectedQuizTypes.size ();
  }

  @Override
  public Object getValueAt (int rowIndex, int columnIndex)
  {
    AbstractQuizTypeConfiguration row = mSelectedQuizTypes.get (rowIndex);
    switch (columnIndex)
    {
      //TODO: i18n
      case 0:
        return row.getCorrespondingLanguage ().canRead () ? row.getCorrespondingLanguage ().getValue () : "?";
      case 1:
        return row.getQuizType ().getName ();
      case 2:
        return row.getDescription ();
      case 3:
        return row.getQuizQuestions ().size ();
      default:
        return "?";
    }     
  }
}
