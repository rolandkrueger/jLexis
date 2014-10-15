/*
 * AbstractVocableInputPanel.java
 * Created on 29.01.2007
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

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.UserInputInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.List;
import java.util.Vector;

public abstract class AbstractVocableInputPanel extends AbstractPanelWithCustomFocusTraversalPolicy
{
  private static final long serialVersionUID = 1579723729678562548L;
  private AbstractWordType mCorrespondingWordType;
    
  public AbstractVocableInputPanel (AbstractWordType correspondingWordType)
  {
    if (correspondingWordType == null) 
      throw new NullPointerException ("the corresponding word type must not be null");
    mCorrespondingWordType = correspondingWordType;
  }
  
  public abstract AbstractUserInput getCurrentUserInput ();
  public abstract void              setUserInput        (UserInputInterface input);
  public abstract void              clearInput          ();
  public abstract boolean           isUserInputEmpty    ();
  
  public AbstractWordType getCorrespondingWordType ()
  {
    return mCorrespondingWordType;
  }
  
  public final void init ()
  {
    List<Component> componentsInFocusTraversalOrder = getComponentsInFocusTraversalOrder ();
    if (componentsInFocusTraversalOrder == null) return;
    VocableInputFocusTraversalPolicy traversalPolicy = new VocableInputFocusTraversalPolicy (componentsInFocusTraversalOrder);
    setFocusTraversalPolicy (traversalPolicy);
  }
  
  private static class VocableInputFocusTraversalPolicy extends FocusTraversalPolicy
  {
    private Vector<Component> mOrder;

    public VocableInputFocusTraversalPolicy (List<Component> order) 
    {
      this.mOrder = new Vector <Component> (order.size());
      this.mOrder.addAll (order);
    }
    
    public Component getComponentAfter(Container focusCycleRoot, Component aComponent)
    {
      if (mOrder.isEmpty ()) return null;
      int index = (mOrder.indexOf (aComponent) + 1) % mOrder.size ();
      return mOrder.get (index);
    }

    public Component getComponentBefore(Container focusCycleRoot, Component aComponent)
    {
      if (mOrder.isEmpty ()) return null;
      int index = mOrder.indexOf(aComponent) - 1;
      if (index < 0) 
      {
        index = mOrder.size() - 1;
      }
      return mOrder.get(index);
    }

    public Component getDefaultComponent(Container focusCycleRoot)
    {
      if (mOrder.isEmpty ()) return null;
      return mOrder.get(0);
    }

    public Component getLastComponent(Container focusCycleRoot)
    {
      if (mOrder.isEmpty ()) return null;
      return mOrder.lastElement ();
    }

    public Component getFirstComponent(Container focusCycleRoot)
    {
      if (mOrder.isEmpty ()) return null;
      return mOrder.get(0);
    }
  }
}
