/*
 * Created on 20.11.2009.
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
package info.rolandkrueger.jlexis.plugin.english.wordtypes;

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.WordTypeEnum;
import info.rolandkrueger.jlexis.gui.containers.AbstractVocableInputPanel;
import info.rolandkrueger.jlexis.plugin.english.EnglishLanguagePlugin;
import info.rolandkrueger.jlexis.plugin.english.inputpanels.EnglishAdjectiveInputPanel;
import info.rolandkrueger.jlexis.plugin.english.userinput.EnglishAdjectiveUserInput;

public class EnglishAdjectiveWordType extends AbstractWordType
{
  private EnglishLanguagePlugin mPlugin;
  
  public EnglishAdjectiveWordType (EnglishLanguagePlugin plugin)
  {
    //TODO I18N
//    super ("Adjektiv", "EnglishAdjective", new EnglishAdjectiveUserInput ());
    super ("Adjective", "EnglishAdjective", new EnglishAdjectiveUserInput ());
    mPlugin = plugin;
  }
  
  @Override
  public AbstractVocableInputPanel getInputPanel ()
  {
    EnglishAdjectiveInputPanel panel = new EnglishAdjectiveInputPanel (this);
    panel.setPlugin (mPlugin);
    return panel;
  }

  @Override
  public WordTypeEnum getWordTypeEnum ()
  {
    return WordTypeEnum.ADJECTIVE;
  }
}
