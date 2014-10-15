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

import info.rolandkrueger.jlexis.gui.containers.AbstractAnswerPanel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roland Krueger
 * @version $Id: AnswerPanelManager.java 131 2009-07-03 15:49:12Z roland $
 */
public class AnswerPanelManager
{
  private static AnswerPanelManager sInstance;
  
  private Map<AnswerPanelHandle, AbstractAnswerPanel> mRegisteredAnswerPanels;
  
  private AnswerPanelManager () 
  {
    mRegisteredAnswerPanels = new HashMap<AnswerPanelHandle, AbstractAnswerPanel> ();
  }
  
  public static AnswerPanelManager getInstance ()
  {
    if (sInstance == null)
      sInstance = new AnswerPanelManager ();
    
    return sInstance;
  }
  
  public AnswerPanelHandle registerAnswerPanel (AbstractAnswerPanel answerPanel)
  {
    AnswerPanelHandle result = new AnswerPanelHandle ();
    mRegisteredAnswerPanels.put (result, answerPanel);
    return result;
  }
  
  public Map<AnswerPanelHandle, AbstractAnswerPanel> getRegisteredAnswerPanels ()
  {
    return Collections.unmodifiableMap (mRegisteredAnswerPanels);
  }
  
  public AbstractAnswerPanel getAnswerPanelFor (AnswerPanelHandle handle)
  {
    return mRegisteredAnswerPanels.get (handle);
  }
}
