/*
 * Created on 04.04.2009
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
package info.rolandkrueger.jlexis.data.vocable.verification;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.vocable.terms.TermDataInterface;
import info.rolandkrueger.jlexis.managers.ConfigurationManager;
import info.rolandkrueger.jlexis.util.StringUtils;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.IntRange;

/**
 * @author Roland Krueger
 * @version $Id: VocableVerificationData.java 197 2009-12-14 07:27:08Z roland $
 */
public class VocableVerificationData
{
  private static Pattern sPattern1 = Pattern.compile ("\\w+(\\w*\\W*)*\\w*\\W");
  private static Pattern sPattern2 = Pattern.compile ("\\W\\w*(\\w*\\W*)*\\w+");
  private static Pattern sPattern3 = Pattern.compile ("\\W(\\w*\\W*)*\\w*\\W");
  
  protected Set<Set<String>>              mData;
  protected boolean                       mAllIsOptional = false;
  protected List<VocableVerificationData> mAlternatives;
  protected VocableVerificationData       mOptionalValues;
  protected String                        mAdditonalQuestionText;
  
  public VocableVerificationData ()
  {
    mData = new HashSet<Set<String>> ();
    mAlternatives = new LinkedList<VocableVerificationData> ();
  }
  
  /**
   * Copy constructor.
   * 
   * @param other
   *          {@link VocableVerificationData} object to be copied
   */
  private VocableVerificationData (VocableVerificationData other)
  {
    this ();
    mAllIsOptional = other.mAllIsOptional;
    
    mData.addAll (other.mData);
    addOptionalValues (other.getOptionalValues ());
    
    // copy the alternatives
    for (VocableVerificationData data : other.mAlternatives)
    {
      mAlternatives.add (new VocableVerificationData (data));
    }    
  }
  
  public String getAdditonalQuestionText ()
  {
    return mAdditonalQuestionText;
  }
  
  public void setAdditonalQuestionText (String mAdditonalQuestionText)
  {
    this.mAdditonalQuestionText = mAdditonalQuestionText;
  }

  private void resolveAllParentheses ()
  {
    for (Set<String> set : mData)
    {
      for (String string : set)
      {
        set.addAll (resolveParentheses (string));
      }
    }
  }
  
  public VocableVerificationResult verify (VocableVerificationData comparisonValue)
  {
    return verify (comparisonValue, null, false);
  }
  
  public VocableVerificationResult verify (VocableVerificationData comparisonValue, Language forLanguage)
  {
    return verify (comparisonValue, forLanguage, true);  
  }
  
  public VocableVerificationResult verify (VocableVerificationData comparisonValue, Language forLanguage, 
      boolean normalizeAbbreviations)
  {
    if (normalizeAbbreviations)
    {
      if (forLanguage == null) throw new NullPointerException ("Cannot normalize abbreviations: Language object is null.");
      normalizeAbbreviations                 (forLanguage);
      comparisonValue.normalizeAbbreviations (forLanguage);
    }
    
    VocableVerificationResult result = new VocableVerificationResult ();
    Set<String> inputSet = new VerificationHashSet (comparisonValue.getAllTokens ());

    resolveAllParentheses ();
    if (mOptionalValues != null) mOptionalValues.resolveAllParentheses ();
    
    for (VocableVerificationData alternative : mAlternatives)
    {
      VocableVerificationResult alternativeResult = alternative.verify (comparisonValue, forLanguage, normalizeAbbreviations);
      if (alternativeResult.getResult () == VocableVerificationResultEnum.CORRECT)
        return alternativeResult;
    }
    
    for (VocableVerificationData data : comparisonValue.getAlternatives ())
    {
      inputSet.addAll (data.getAllTokens ());
    }
    
    // Create a copy of this object. The data of this copy will be removed as needed.
    VocableVerificationData compareData = new VocableVerificationData (this);
  
    for (String value : new HashSet<String> (inputSet))
    {
      Set<String> thisMandatory = getAlternativesForMandatoryValue (value);
      if (thisMandatory != null)
      {
        // if one of the mandatory answers is contained in the comparison verification data object
        // remove the respective set of alternative values for this mandatory answer from the
        // result object
        compareData.mData.remove (thisMandatory);
        inputSet.removeAll (thisMandatory);
      } else if (compareData.getOptionalValues ().contains (value))
      {
        compareData.removeOptionalValue (value);
        inputSet.remove (value);
      }
    }
    
    if (compareData.isEmpty ())
    {
      if (inputSet.isEmpty ())
        result.setResult (VocableVerificationResultEnum.CORRECT);
      else
      {
        result.addRedundantValues (inputSet);        
        result.setResult (VocableVerificationResultEnum.TOO_MANY_VALUES);
      }
    } else
    {
      result.setResult (VocableVerificationResultEnum.NOT_ENOUGH_VALUES);
      result.addMissingValues (compareData.getAllTokens ());
    }
    
    return result;
  }
  
  public List<VocableVerificationData> getAlternatives ()
  {
    return Collections.unmodifiableList (mAlternatives);
  }
  
  public void addOptionalValues (VocableVerificationData optionalValues)
  {
    if (mOptionalValues == null)
    {
      mOptionalValues = new VocableVerificationData ();
      mOptionalValues.makeAllOptional ();      
    }
    
    mOptionalValues.addOptions (optionalValues.getAllTokens ());
  }
  
  public void addOptionalTerm (TermDataInterface term)
  {
    addOptionalValues (new VocableVerificationData ().tokenizeAndAddString (term.getResolvedAndPurgedTerm ()));
  }
  
  private void removeOptionalValue (String value)
  {
    assert mOptionalValues.mAllIsOptional;
    
    if (mOptionalValues.mData.size () > 0)
      mOptionalValues.mData.iterator ().next ().remove (value);
  }
  
  public VocableVerificationData getOptionalValues ()
  {
    if (mOptionalValues == null) return new VocableVerificationData ();
    return mOptionalValues;
  }
  
  public void addAlternative (VocableVerificationData alternative)
  {
    if (alternative == null)
      throw new NullPointerException ("Alternative is null.");
    
    mAlternatives.add (alternative);
  }
  
  public void addAlternativeTerm (TermDataInterface alternativeTermData)
  {
    VocableVerificationData alternative = new VocableVerificationData ();
    alternative.tokenizeAndAddString (alternativeTermData.getResolvedAndPurgedTerm ());
    addAlternative (alternative);
  }
  
  public void addMandatoryTerm (TermDataInterface mandatoryTermData)
  {
    tokenizeAndAddString (mandatoryTermData.getResolvedAndPurgedTerm ());    
  }
  
  public void makeAllOptional ()
  {
    Set<String> allTokens = getAllTokensInternal ();
    mData.clear ();
    if (allTokens.size () > 0)
      mData.add (allTokens);
    mAllIsOptional = true;
  }
  
  public boolean isEmpty ()
  {
    if (mData.size () == 0) return true;
    
    for (Set<String> set : mData)
    {
      if (! set.isEmpty ()) return false;
    }
    return true;
  }
  
  public void addOption (String option)
  {
    if ( ! mAllIsOptional)
      throw new IllegalStateException ("This verification data is not all optional. Set this state with " +
      "makeAllOptional() first.");
    if (option == null)
      throw new NullPointerException ("Argument is null.");
    
    Set<String> set;
    if (mData.size () > 0)
    {
      set = mData.iterator ().next ();
    } else
    {
      set = new VerificationHashSet ();
      mData.add (set);
    }
        
//    set.addAll (resolveParentheses (option));
    set.add (option);
  }
  
  public void addOptions (Collection<String> options)
  {
    if (options == null)
      throw new NullPointerException ("Argument is null.");
    
    for (String option : options)
    {
      addOption (option);
    }
  }
  
  public void addMandatoryValue (String mandatoryValue)
  {
    if (mAllIsOptional)
      throw new IllegalStateException ("Cannot add mandatory values. This verification data is all optional. " +
      		" Use addOption() instead.");
    if (mandatoryValue == null)
      throw new NullPointerException ("Argument is null.");
    
    if (contains (mandatoryValue)) 
      return;
    
    Set<String> set = new VerificationHashSet ();
//    set.addAll (resolveParentheses (mandatoryValue));
    set.add (mandatoryValue);
    
    if (set.size () > 0)
      mData.add (set);
  }
  
  public void addMandatoryValueWithOptions (String mandatoryValue, String[] options)
  {
    if (mAllIsOptional)
      throw new IllegalStateException ("Cannot add mandatory values. This verification data is all optional. " +
      " Use addOption() instead.");

    addMandatoryValueWithOptions (mandatoryValue, Arrays.asList (options));
  }
  
  public void addMandatoryValueWithOptions (Collection<String> options)
  {
    if (mAllIsOptional)
      throw new IllegalStateException ("Cannot add mandatory values. This verification data is all optional. " +
      " Use addOption() instead.");
    if (options == null)
      throw new NullPointerException ("Argument is null.");

    Set<String> newSet = new VerificationHashSet ();
    for (String option : options)
    {
//      newSet.addAll (resolveParentheses (option));
      newSet.add (option);
    }
    if (newSet.size () > 0)
      mData.add (newSet);
  }
  
  public void addMandatoryValueWithOptions (String mandatoryValue, Collection<String> options)
  {
    if (mAllIsOptional)
      throw new IllegalStateException ("Cannot add mandatory values. This verification data is all optional. " +
      " Use addOption() instead.");
    if (mandatoryValue == null || options == null)
      throw new NullPointerException ("One of the arguments is null.");
    
    Set<String> set = getAlternativesForMandatoryValue (mandatoryValue);
    if (set == null)
    {
      Set<String> newSet = new VerificationHashSet ();
      for (String option : options)
      {
//        newSet.addAll (resolveParentheses (option));
        newSet.add (option);
      }
//      newSet.addAll (resolveParentheses (mandatoryValue));
      newSet.add (mandatoryValue);
      
      if (newSet.size () > 0)
        mData.add (newSet);      
    } else
    {
      for (String option : options)
//        set.addAll (resolveParentheses (option));
        set.add (option);
    }
  }
  
  public VocableVerificationData tokenizeAndAddString (String value)
  {
    if (value == null || value.trim ().equals (""))
      return this;
    
    value = value.trim ();

    String[] mandatoryTokens = value.split (ConfigurationManager.getInstance ().getMandatoryTokenSplitChar ());

    for (String mandatoryToken : mandatoryTokens)
    {
      if (mandatoryToken.trim ().equals ("")) continue;
      mandatoryToken = normalizeWhitespaces (mandatoryToken);
      String[] optionalTokens = mandatoryToken.split (ConfigurationManager.getInstance ().getOptionalTokenSplitChar ());
      Set<String> set = new VerificationHashSet ();
      for (String token : optionalTokens)
      {
        if (token.trim ().equals ("")) continue;
        token = normalizeWhitespaces (token);
//        set.addAll (resolveParentheses (token));
        set.add (token);
      }
      addMandatoryValueWithOptions (set);
    }
    return this;
  }
  
  private String normalizeWhitespaces (String value)
  {
    return value.replaceAll ("\\s+", " ");
  }
  
  private Set<String> resolveParentheses (String value)
  {
    Set<String> result = new VerificationHashSet ();
    result.add (value);

    int start = -1;
    int end   = -1;
    
    int i = 0;
    boolean moreToDo = true;
    boolean done;
    Set<IntRange> alreadyDoneParenthesesPairs = new HashSet<IntRange> ();
    moreToDo: while (moreToDo)
    {
      start = -1;
      end   = -1;
      i     = 0;
      
      for (char c : value.toCharArray ())
      {
        // TODO: make parentheses characters configurable
        if (c == '(')
        {
          done = false;
          for (IntRange range : alreadyDoneParenthesesPairs)
          {
            if (range.getMinimumInteger () == i)
            {
              done = true;
              break;              
            }
          }
          if ( ! done) start = i;
        }
        if (c == ')') 
        {
          done = false;
          for (IntRange range : alreadyDoneParenthesesPairs)
          {
            if (range.getMaximumInteger () == i + 1)
            {
              done = true;
              break;              
            }
          }
          if ( ! done) end = i + 1;
          
          if (end > start) 
          {
            IntRange pair = new IntRange (start, end);
            if ( ! alreadyDoneParenthesesPairs.contains (pair))
            {
              alreadyDoneParenthesesPairs.add (pair);
              if (start > -1 && end > -1 && end > start)
              {
                StringBuilder buf = new StringBuilder ();
                buf.append (value.substring (0, start).trim ()).append (value.substring (end));
                result.add (buf.toString ());
                result.addAll (resolveParentheses (buf.toString ()));
                
                buf.setLength (0);
                buf.append (value.substring (0, start));
                buf.append (value.substring (start + 1, end - 1));
                buf.append (value.substring (end));
                result.add (buf.toString ());
                result.addAll (resolveParentheses (buf.toString ()));
                continue moreToDo;
              }
            } else
            {
              start = -1;
              end   = -1;
            }
          }
        }
        ++i;
      }
      
      if (start < 0 || end < 0) moreToDo = false;
    }

    return result;
  }
  
  public boolean contains (String token)
  {
    for (Set<String> set : mData)
    {
      if (set.contains (token))
        return true;
    }
    return false;
  }

  public Set<String> getAllTokens ()
  {
    return Collections.unmodifiableSet (getAllTokensInternal ());
  }
  
  private Set<String> getAllTokensInternal ()
  {
    Set<String> result = new VerificationHashSet ();
  
    for (Set<String> set : mData)
    {
      result.addAll (set);
    }
    
    if (mOptionalValues != null)
      result.addAll (mOptionalValues.getAllTokens ());
    for (VocableVerificationData data : mAlternatives)
      result.addAll (data.getAllTokens ());
    
    return result;
  }
  
  public Set<String> getAlternativesForMandatoryValue (String mandatoryValue)
  {
    for (Set<String> set : mData)
    {
      if (set.contains (mandatoryValue)) return set;
    }
    return null;
  }
  
  public Set<Set<String>> getMandatoryValuesWithOptions ()
  {
    return Collections.unmodifiableSet (mData);
  }
  
  public void normalizeAbbreviations (Language forLanguage)
  {
    if ( ! forLanguage.getLanguagePlugin ().canRead ()) return;
    List<Set<String>> abbreviationAlternatives = 
      forLanguage.getLanguagePlugin ().getValue ().getAbbreviationAlternatives ();
    
    String abbreviation;
    for (Set<String> set : mData)
    {
      Set<String> copySet = new HashSet<String> (set); 
      for (String userAnswer : copySet)
      {
        Set<IntRange> excludedRanges = new HashSet<IntRange> ();
        set.remove (userAnswer);
        for (Set<String> abbreviationSet : abbreviationAlternatives)
        {
          Set<String> copyAbbreviationSet = new HashSet<String> (abbreviationSet);
          if (abbreviationSet.isEmpty ()) continue;
          String abbreviationMaster = abbreviationSet.iterator ().next ();
         
          
          while ( ! copyAbbreviationSet.isEmpty ())
          {
            abbreviation = "";
            for (String s : copyAbbreviationSet)
            {
              if (s.length () > abbreviation.length ()) abbreviation = s;
            }
            copyAbbreviationSet.remove (abbreviation);
            userAnswer = replaceAbbreviation (excludedRanges, userAnswer, abbreviation, abbreviationMaster);
          }
        }
        set.add (userAnswer);
      }
    }
  }
  
  private String replaceAbbreviation (Set<IntRange> excludedRanges, String input, 
      String replacedAbbreviation, String masterAbbreviation)
  {
    assert excludedRanges != null;
    String regex = "\\b%s\\b";
    Matcher m = sPattern1.matcher (replacedAbbreviation);
    if (m.matches ()) regex = "\\b%s";
    m = sPattern2.matcher (replacedAbbreviation);
    if (m.matches ()) regex = "%s\\b";
    m = sPattern3.matcher (replacedAbbreviation);
    if (m.matches ()) regex = "%s";
    
    Pattern pattern = Pattern.compile (String.format (regex, 
        StringUtils.escapeRegexSpecialChars (replacedAbbreviation).replaceAll ("\\s+",  "\\\\s+")));
    m = pattern.matcher (input);
    
    StringBuilder buf = new StringBuilder (input);
    whileLoop: while (m.find ())
    {
      for (IntRange range : excludedRanges)
      {
        if (range.containsInteger (m.start ())) continue whileLoop;
      }
      IntRange range = new IntRange (m.start (), m.start () + masterAbbreviation.length () - 1);
      excludedRanges.add (range);
      
      buf.replace (m.start (), m.end (), masterAbbreviation);
      m = pattern.matcher (buf);
    }
    
    return buf.toString ();
  }
  
  @Override
  public int hashCode ()
  {
    return mData.hashCode ();
  }
  
  @Override
  public boolean equals (Object obj)
  {
    if (obj == null) return false;
    if (obj == this) return true;
    if ( ! (obj instanceof VocableVerificationData))
      return false;
    
    VocableVerificationData other = (VocableVerificationData) obj;
    return mData.equals (other.mData);
  }
  
  @Override
  public String toString ()
  {
    return mData.toString ();
  }
  
  public final static class UnmodifiableVocableVerificationData extends VocableVerificationData
  {
    
    public UnmodifiableVocableVerificationData (VocableVerificationData data)
    {
      CheckForNull.check (data);
      mData                  = data.mData;
      mAdditonalQuestionText = data.mAdditonalQuestionText;
      mAllIsOptional         = data.mAllIsOptional;
      mAlternatives          = data.mAlternatives;
      mOptionalValues        = data.mOptionalValues;
    }

    @Override
    public void setAdditonalQuestionText (String mAdditonalQuestionText)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addMandatoryTerm (TermDataInterface mandatoryTermData)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addAlternative (VocableVerificationData alternative)
    {
      // TODO Auto-generated method stub
      super.addAlternative (alternative);
    }

    @Override
    public void addAlternativeTerm (TermDataInterface alternativeTermData)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addMandatoryValue (String mandatoryValue)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addMandatoryValueWithOptions (Collection<String> options)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addMandatoryValueWithOptions (String mandatoryValue, Collection<String> options)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addMandatoryValueWithOptions (String mandatoryValue, String[] options)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addOption (String option)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addOptionalValues (VocableVerificationData optionalValues)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addOptions (Collection<String> options)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void makeAllOptional ()
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public void addOptionalTerm (TermDataInterface term)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }

    @Override
    public VocableVerificationData tokenizeAndAddString (String value)
    {
      throw new UnsupportedOperationException ("This object cannot be modified.");
    }   
  }
}