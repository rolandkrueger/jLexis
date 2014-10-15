/*
 * Created on 26.05.2009.
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
package info.rolandkrueger.jlexis.managers.learningunitmanager;

import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.datamodels.JLexisListModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Roland Krueger
 * @version $Id: LearningUnitTableModel.java 204 2009-12-17 15:20:16Z roland $
 */
public class LearningUnitTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = -3653844481124729959L;
 
  private JLexisListModel<LearningUnit> mData;
  
  public LearningUnitTableModel ()
  {
    mData = new JLexisListModel<LearningUnit> ();
  }
  
  public List<LearningUnit> getData ()
  {
    return mData.getData ();
  }
  
  public void addLearningUnits (List<LearningUnit> units)
  {
    mData.add (units);
    fireTableDataChanged ();
  }
  
  public void removeLearningUnits (List<LearningUnit> units)
  {
    mData.remove (units);
    fireTableDataChanged ();
  }
  
  @Override
  public int getColumnCount ()
  {
    return 5;
  }

  @Override
  public int getRowCount ()
  {
    return mData.getSize ();
  }

  @Override
  public Object getValueAt (int rowIndex, int columnIndex)
  {
    LearningUnit unit = mData.get (rowIndex);
    
    switch (columnIndex)
    {
      case 0:
        return unit.getName ();
      case 1:
        return unit.getNativeLanguage ().getLanguageName ();
      case 2:
        return unit.getLanguagesAsString ();
      case 3:
        return unit.getDescription ();
      case 4:
        return unit.getSize ();
      default:
        return "?";
    }
  }

  @Override
  public String getColumnName (int column)
  {
    switch (column)
    {
    // TODO: I18N
      case 0:
        return "Name";
      case 1:
//        return "Muttersprache";
        return "Mother tongue";
      case 2:
//        return "Fremdsprachen";
        return "Foreign language";
      case 3:
//        return "Beschreibung";
        return "Description";
      case 4:
//        return "Anzahl Vokabeln";
        return "Vocabulary count";
      default:
        return "?";
    }  
  }
}
