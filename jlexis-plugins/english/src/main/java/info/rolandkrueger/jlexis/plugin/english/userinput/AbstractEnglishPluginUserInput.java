/*
 * Created on 06.12.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
 * 
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
package info.rolandkrueger.jlexis.plugin.english.userinput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.DefaultUserInput;
import info.rolandkrueger.jlexis.data.vocable.standarduserinput.StandardUserInputDataHandler;

public abstract class AbstractEnglishPluginUserInput extends AbstractUserInput
{
  private StandardUserInputDataHandler mStandardInputBE;
  private StandardUserInputDataHandler mStandardInputAE;
  
  protected AbstractEnglishPluginUserInput (String inputType)
  {
    super (inputType);
    mStandardInputBE = new StandardUserInputDataHandler (this, "BE");
    mStandardInputAE = new StandardUserInputDataHandler (this, "AE");
  }
  
  protected abstract String[] getUserInputIdentifiersImpl ();
  protected abstract boolean isEmptyImpl ();
  
  @Override
  public boolean isEmpty ()
  {
    return mStandardInputBE.isEmpty () &&
           mStandardInputAE.isEmpty () &&
           isEmptyImpl ();
  }
  
  @Override
  public String[] getUserInputIdentifiers ()
  {
    List<String> userInputIDs = new ArrayList<String> (10);
    userInputIDs.addAll (Arrays.asList (mStandardInputBE.getUserInputIdentifiers ()));
    userInputIDs.addAll (Arrays.asList (mStandardInputAE.getUserInputIdentifiers ()));
    userInputIDs.addAll (Arrays.asList (getUserInputIdentifiersImpl ()));
    return userInputIDs.toArray (new String[] {});
  }

  protected StandardUserInputDataHandler getStandardInputBE ()
  {
    return mStandardInputBE;
  }
  
  protected StandardUserInputDataHandler getStandardInputAE ()
  {
    return mStandardInputAE;
  }
  
  public DefaultUserInput getBritishStandardValues ()
  {
    DefaultUserInput result = new DefaultUserInput ();
    result.setComment (mStandardInputBE.getComment ());
    result.setExample (mStandardInputBE.getExample ());
    result.setPhonetics (mStandardInputBE.getPhonetics ());
    result.setPronunciation (mStandardInputBE.getPronunciation ());
    return result;
  }
  
  public void setBritishStandardValues (DefaultUserInput defaultBritishEnglish)
  {
    mStandardInputBE.setComment (defaultBritishEnglish.getComment ());
    mStandardInputBE.setExample (defaultBritishEnglish.getExample ());
    mStandardInputBE.setPhonetics (defaultBritishEnglish.getPhonetics ());
    mStandardInputBE.setPronunciation (defaultBritishEnglish.getPronunciation ());
  }
  
  public DefaultUserInput getAmericanStandardValues ()
  {
    DefaultUserInput result = new DefaultUserInput ();
    result.setComment (mStandardInputAE.getComment ());
    result.setExample (mStandardInputAE.getExample ());
    result.setPhonetics (mStandardInputAE.getPhonetics ());
    result.setPronunciation (mStandardInputAE.getPronunciation ());
    return result;
  }
  
  public void setAmericanStandardValues (DefaultUserInput defaultAmericanEnglish)
  {
    mStandardInputAE.setComment (defaultAmericanEnglish.getComment ());
    mStandardInputAE.setExample (defaultAmericanEnglish.getExample ());
    mStandardInputAE.setPhonetics (defaultAmericanEnglish.getPhonetics ());
    mStandardInputAE.setPronunciation (defaultAmericanEnglish.getPronunciation ());
  }
  
  @Override
  protected String getCommentImpl ()
  {
    return mStandardInputBE.getComment ();
  }

  @Override
  protected String getExampleImpl ()
  {
    return mStandardInputBE.getExample ();
  }
}
