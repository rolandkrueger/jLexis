/*
 * Created on 18.03.2009
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

import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: LearningUnitManagerCallbackInterface.java 204 2009-12-17 15:20:16Z roland $
 */
public interface LearningUnitManagerCallbackInterface
{
  public void unitAdded   (Object source, LearningUnit unit);
  public void unitRemoved (Object source, LearningUnit unit);
  public void unitsAdded   (Object source, List<LearningUnit> units);
  public void unitsRemoved (Object source, List<LearningUnit> units);
}
