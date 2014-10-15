/*
 * Created on 27.03.2009
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
package info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels;

/**
 * @author Roland Krueger
 * @version $Id: AnswerInputTypeEnum.java 120 2009-05-26 19:23:58Z roland $
 */
public enum AnswerInputTypeEnum
{
  DEFINITE_TEXTUAL ("Definite textual"), 
  MULTIPLE_CHOICE ("Multiple Choice"), 
  UNKNOWN ("Unknown");
  
  private final String mLabel;

  AnswerInputTypeEnum (String label) {
    this.mLabel = label;
  }
  
  public String getLabel () {
    return this.mLabel;
  }
  
  @Override
  public String toString ()
  {
    return getLabel ();
  }
}
