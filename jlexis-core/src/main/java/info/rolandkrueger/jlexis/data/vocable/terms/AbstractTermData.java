/*
 * $Id: AbstractTermData.java 204 2009-12-17 15:20:16Z roland $
 * Created on 07.03.2009
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
package info.rolandkrueger.jlexis.data.vocable.terms;

import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationData;
import info.rolandkrueger.jlexis.managers.ConfigurationManager;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTermData implements TermDataInterface
{
  protected final static String WORD_STEM_MARKER         = "${|}";
  protected final static String WORD_STEM_BEGIN_MARKER   = "${<}";
  protected final static String WORD_STEM_END_MARKER     = "${>}";
  protected final static String WORD_STEM_PLACEHOLDER    = "${-}";
  protected final static String SPECIAL_CHAR_PLACEHOLDER = "${$}";  

  protected String mNormalizedTerm = "";
  
  public void setNormalizedTerm (String normalizedTerm)
  {
    if (normalizedTerm == null)
      throw new NullPointerException ("Normalized term is null.");

    mNormalizedTerm = normalizedTerm.trim ();
  }
  
  public String getNormalizedTerm ()
  {
    return mNormalizedTerm;
  }
  
  public void setUserEnteredTerm (String term)
  {
    if (term == null)
      throw new NullPointerException ("Term is null.");
    
    ConfigurationManager config = ConfigurationManager.getInstance ();
    String result = term.replace  ("$", SPECIAL_CHAR_PLACEHOLDER);
           result = result.replace (config.getWordStemMarker      (), WORD_STEM_MARKER);
           result = result.replace (config.getWordStemBeginMarker (), WORD_STEM_BEGIN_MARKER);
           result = result.replace (config.getWordStemEndMarker   (), WORD_STEM_END_MARKER);
           result = result.replace (config.getWordStemPlaceholder (), WORD_STEM_PLACEHOLDER);
    
    setNormalizedTerm (result);
  }
  
  public String getUserEnteredTerm ()
  {
    ConfigurationManager config = ConfigurationManager.getInstance ();
    String result = mNormalizedTerm.replace  (WORD_STEM_MARKER, config.getWordStemMarker ());
           result = result.replace (WORD_STEM_BEGIN_MARKER, config.getWordStemBeginMarker ());    
           result = result.replace (WORD_STEM_END_MARKER,   config.getWordStemEndMarker ());    
           result = result.replace (WORD_STEM_PLACEHOLDER,  config.getWordStemPlaceholder ());    
           result = result.replace (SPECIAL_CHAR_PLACEHOLDER, "$");
    return result;
  }
  
  protected String removeMarkerStrings (String string)
  {
    String result = string.replace (WORD_STEM_MARKER, "");
           result = result.replace (WORD_STEM_BEGIN_MARKER, "");
           result = result.replace (WORD_STEM_END_MARKER, "");
           result = result.replace (WORD_STEM_PLACEHOLDER, "");
           result = result.replace (SPECIAL_CHAR_PLACEHOLDER, "$");
    
    return result;
  }
  
  public String getPurgedTerm ()
  {
    return purge (mNormalizedTerm);
  }
  
  protected String purge (String string)
  {
    String result = string.replace (WORD_STEM_MARKER, "");
    result = result.replace (WORD_STEM_BEGIN_MARKER, "");
    result = result.replace (WORD_STEM_END_MARKER, "");
    result = result.replace (WORD_STEM_PLACEHOLDER, "-");    
    result = result.replace (SPECIAL_CHAR_PLACEHOLDER, "$");
    
    return result;
  }
  
  public boolean isEmpty ()
  {
    return getNormalizedTerm ().equals ("");
  }
  
  @Override
  public String toString ()
  {
    return getUserEnteredTerm ();
  }
  
  public VocableVerificationData getVerificationData ()
  {
    VocableVerificationData result = new VocableVerificationData ();
    VocableVerificationData tmp    = new VocableVerificationData ();
    
    tmp.tokenizeAndAddString (getNormalizedTerm ());
    for (Set<String> mandatorySetWithNormalizedTerms : tmp.getMandatoryValuesWithOptions ())
    {
      Set<String> mandatorySet = new HashSet<String> ();
      for (String value : mandatorySetWithNormalizedTerms)
      {
        // For every token which the user has entered, add its resolved form to the
        // verification data, so that the resolved term is also a valid answer
        AbstractTermData term;
        if (isInflected ())
        {
          term = new InflectedTerm (getWordStemObject ());
        } else
        {
          term = new RegularTerm ();
        }
        term.setNormalizedTerm (value);
        mandatorySet.add (term.getPurgedTerm ());
        
        RegularTerm regularTerm = new RegularTerm ();
        regularTerm.setUserEnteredTerm (term.getResolvedTerm ());
        mandatorySet.add (regularTerm.getPurgedTerm ());
      }
      result.addMandatoryValueWithOptions (mandatorySet);
    }
    
    return result;
  }
  
  /**
   * @see info.rolandkrueger.jlexis.data.vocable.terms.TermDataInterface#getResolvedTerm()
   */
  public abstract String getResolvedTerm ();
  
  /**
   * @see info.rolandkrueger.jlexis.data.vocable.terms.TermDataInterface#getWordStem()
   */
  public abstract String getWordStem ();
  
  /**
   * @see info.rolandkrueger.jlexis.data.vocable.terms.TermDataInterface#isWordStem()
   */
  public abstract boolean isWordStem ();
  
  protected abstract boolean isInflected ();
  
  protected abstract AbstractTermData getWordStemObject ();
}
