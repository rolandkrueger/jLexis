/*
 * Created on 17.03.2009
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
package info.rolandkrueger.jlexis.plugin.swedish.quiztypes;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.UnmodifiableLearningUnit;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels.DisplayTypeEnum;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels.NounPluralQuizConfigPanel;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizTypeConfiguration;

import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author Roland Krueger
 * @version $Id: NounPluralQuiz.java 128 2009-06-02 18:52:42Z roland $
 */
public class NounPluralQuiz extends AbstractQuizType
{
  private NounPluralQuizConfigPanel mConfigPanel;
  
  public NounPluralQuiz ()
  {
    mConfigPanel = new NounPluralQuizConfigPanel ();
  }
  
  @Override
  public JPanel getConfigurationPanel ()
  {
    return mConfigPanel;
  }

  @Override
  public String getName ()
  {
    //TODO: I18N
//    return "Substantive: Plural";
    return "Nouns: Plural";
  }

  @Override
  public AbstractQuizTypeConfiguration getQuizTypeConfiguration ()
  {
    return new NounPluralQuizConfiguration (this);
  }
  
  public class NounPluralQuizConfiguration extends AbstractQuizTypeConfiguration
  {
    private DisplayTypeEnum mDisplayType;
    private boolean         mIsIndefinite;
    private boolean         mIsDefinite;
    private boolean         mIsGroups;
    private boolean         mIsIrregular;
    private String          mDescription;
    
    protected NounPluralQuizConfiguration (AbstractQuizType type)
    {
      super (type);
      mDisplayType  = mConfigPanel.getDisplayType ();
      mIsIndefinite = mConfigPanel.isIndefiniteFormSelected ();
      mIsDefinite   = mConfigPanel.isDefiniteFormSelected ();
      mIsGroups     = mConfigPanel.isGroupsSelected ();
      mIsIrregular  = mConfigPanel.isIrregularSelected ();
    }
    
    @Override
    public AbstractQuizTypeConfiguration getCopy ()
    {
      return new NounPluralQuizConfiguration (getQuizType ());
    }

    @Override
    protected List<AbstractQuizQuestion> createQuizQuestionsForImpl (List<UnmodifiableLearningUnit> units, Language forLanguage)
    {
      return Collections.emptyList ();
    }

    @Override
    public String getDescription ()
    {
      // TODO: I18N
      if (mDescription == null)
      {
        StringBuilder buf = new StringBuilder ();
//        buf.append ("Abzufragen: ");
        buf.append ("To be tested: ");
        if (mIsIndefinite)
        {
//          buf.append ("unbestimmte Form");
          buf.append ("indefinite form");
          buf.append (", ");
        }
        if (mIsDefinite)
        {
//          buf.append ("bestimmte Form");
          buf.append ("definite form");
          buf.append (", ");
        }
        if (mIsGroups)
        {
//          buf.append ("Gruppen");
          buf.append ("groups");
          buf.append (", ");
        }
        if (mIsIrregular)
        {
//          buf.append ("nur unregelm\u00E4\u00dfige");
          buf.append ("only irregular");
          buf.append (", ");
        }
//        buf.append ("Anzeige: ");
        buf.append ("Display: ");
        buf.append (mDisplayType.toString ());
        mDescription = buf.toString ();
      }
      return mDescription;
    }    
  }
}
