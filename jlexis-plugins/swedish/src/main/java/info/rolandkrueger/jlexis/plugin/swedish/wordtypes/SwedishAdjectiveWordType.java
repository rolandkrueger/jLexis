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
package info.rolandkrueger.jlexis.plugin.swedish.wordtypes;

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.WordTypeEnum;
import info.rolandkrueger.jlexis.gui.containers.AbstractVocableInputPanel;
import info.rolandkrueger.jlexis.plugin.swedish.inputpanels.SwedishAdjectiveInputPanel;
import info.rolandkrueger.jlexis.plugin.swedish.userinput.SwedishAdjectiveUserInput;

public class SwedishAdjectiveWordType extends AbstractWordType
{
  public SwedishAdjectiveWordType ()
  {
    // TODO i18n
//    super ("Adjektiv", "SwedishAdjective", new SwedishAdjectiveUserInput ());
    super ("Adjective", "SwedishAdjective", new SwedishAdjectiveUserInput ());
  }

  @Override
  public AbstractVocableInputPanel getInputPanel ()
  {
    return new SwedishAdjectiveInputPanel ();
  }

  @Override
  public WordTypeEnum getWordTypeEnum ()
  {
    return WordTypeEnum.ADJECTIVE;
  }
}
