/*
 * Created on 03.07.2009.
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
package info.rolandkrueger.jlexis.managers.answerpanelmanager;

/**
 * @author Roland Krueger
 * @version $Id: AnswerPanelHandle.java 131 2009-07-03 15:49:12Z roland $
 */
public class AnswerPanelHandle
{
  private static int sInstanceCount;
  private int mID;
  
  protected AnswerPanelHandle ()
  {
    mID = sInstanceCount;
    sInstanceCount++;    
  }
  
  @Override
  public String toString ()
  {
    return String.valueOf (mID);
  }
  
  @Override
  public boolean equals (Object obj)
  {
    if (obj == null) return false;
    if (obj == this) return true;
    
    if ( ! (obj instanceof AnswerPanelHandle)) return false;
    
    AnswerPanelHandle other = (AnswerPanelHandle) obj;
    return (other.mID == mID);
  }
  
  @Override
  public int hashCode ()
  {
    return mID;
  }
}
