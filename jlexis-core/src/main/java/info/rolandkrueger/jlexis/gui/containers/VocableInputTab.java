/*
 * VocableInputTab.java
 * Created on 19.04.2007
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
package info.rolandkrueger.jlexis.gui.containers;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;

import java.awt.CardLayout;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JPanel;

public class VocableInputTab extends JPanel
{
  private static final long serialVersionUID = -8397546699884518729L;

  private HashMap<AbstractWordType, AbstractVocableInputPanel> mPanelMapping;
  private CardLayout                                           mLayout;
  private AbstractVocableInputPanel                            mTopmost;
  private Language                                             mLanguage;

  public VocableInputTab (Language forLanguage)
  {
    mLanguage = forLanguage;
    mLayout = new CardLayout ();
    setLayout (mLayout);
    mPanelMapping = new HashMap<AbstractWordType, AbstractVocableInputPanel> ();

    for (AbstractWordType wordType : forLanguage.getLanguagePlugin ().getValue ().getWordTypes ())
    {
      AbstractVocableInputPanel panel = wordType.getInputPanel (); 
      panel.init ();
      mPanelMapping.put (wordType, panel);
      add (panel, wordType.getName ());
    }
    show (forLanguage.getLanguagePlugin ().getValue ().getDefaultWordType ());
  }

  public void show (AbstractWordType wordType)
  {
    mLayout.show (this, wordType.getName ());
    if (mPanelMapping.get (wordType) == null) return;
    mTopmost = mPanelMapping.get (wordType);
    requestFocusForFirstComponent ();
  }
  
  public AbstractVocableInputPanel getTopmost ()
  {
    return mTopmost;
  }

  public Collection<AbstractVocableInputPanel> getInputPanels ()
  {
    return mPanelMapping.values ();
  }
  
  public Icon getIcon ()
  {
    return mLanguage.getLanguagePlugin ().getValue ().getIcon ();
  }
  
  public void requestFocusForFirstComponent ()
  {
    if (mTopmost.getComponentsInFocusTraversalOrder ().isEmpty ()) return;
    mTopmost.getComponentsInFocusTraversalOrder ().get (0).requestFocusInWindow ();
  }
}
