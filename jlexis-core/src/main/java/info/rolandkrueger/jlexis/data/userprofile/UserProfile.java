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
package info.rolandkrueger.jlexis.data.userprofile;

import java.util.Date;

/**
 * @author Roland Krueger
 * @version $Id: UserProfile.java 156 2009-10-23 19:40:41Z roland $
 */
public class UserProfile
{
  private long   mId;
  private String mProfileName;
  private Date   mCreationDate;
  private String mComment;
  
  public long getId ()
  {
    return mId;
  }
  
  @SuppressWarnings("unused")
  private void setId (long id)
  {
    mId = id;
  }
  
  public String getProfileName ()
  {
    return mProfileName;
  }
  
  public void setProfileName (String profileName)
  {
    mProfileName = profileName;
  }
  
  public Date getCreationDate ()
  {
    return mCreationDate;
  }
  
  public void setCreationDate (Date creationDate)
  {
    mCreationDate = creationDate;
  }

  public String getComment ()
  {
    return mComment;
  }

  public void setComment (String comment)
  {
    mComment = comment;
  }
  
  @Override
  public String toString ()
  {
    return mProfileName;
  }
  
  @Override
  public boolean equals (Object obj)
  {
    if (obj == null) return false;
    if (obj == this) return true;
    
    if (obj instanceof UserProfile)
    {
      UserProfile other = (UserProfile) obj;
      return other.mId == mId;
    }
    return false;
  }
  
  @Override
  public int hashCode ()
  {
    return Long.valueOf (mId).hashCode ();
  }
}
