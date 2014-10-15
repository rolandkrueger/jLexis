/*
 * VocableInputTabbedPane.java
 * Created on 18.04.2007
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

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VocableInputTabbedPane extends JTabbedPane
{
  private static final long serialVersionUID = -433581095983366547L;

  private List<VocableInputTab> mTabs;
  
  public VocableInputTabbedPane ()
  {
    mTabs = new ArrayList<VocableInputTab> ();
    addChangeListener (new ChangeListener()
    {
      @Override
      public void stateChanged (ChangeEvent e)
      {
        requestFocusForFirstComponent ();
      }
    });
  }
  
  public void requestFocusForFirstComponent ()
  {
    mTabs.get (getSelectedIndex ()).requestFocusForFirstComponent ();    
  }
    
  @Override
  public void removeAll ()
  {
    mTabs.clear     ();
    super.removeAll ();
  }
  
  public void addPanel (VocableInputTab panel, String name)
  {
    mTabs.add (panel);
    super.addTab (name, panel.getIcon (), panel);
  }
  
  public void addPanel (VocableInputTab panel, String name, Icon icon)
  {
    mTabs.add (panel);
    super.addTab (name, icon, panel);
  }
  
  public List<AbstractVocableInputPanel> getAllTopmostPanels ()
  {
    ArrayList<AbstractVocableInputPanel> panels = new ArrayList<AbstractVocableInputPanel> ();
    for (VocableInputTab tab : mTabs)
    {
      panels.add (tab.getTopmost ());
    }
    return panels;
  }
  
  public AbstractVocableInputPanel getTopmostPanel ()
  {
    return mTabs.get (getSelectedIndex ()).getTopmost ();
  }
  
  public boolean isInputEmpty ()
  {
    for (VocableInputTab tab : mTabs)
    {
      if ( ! tab.getTopmost ().isUserInputEmpty ()) 
        return false;
    }
    return true;
  }
  
  public void clearAllInput ()
  { 
    for (VocableInputTab tab : mTabs)
    {
      tab.getTopmost ().clearInput ();
    }     
  }

  @Override
  public Component add (Component component, int index)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public void add (Component component, Object constraints, int index)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public void add (Component component, Object constraints)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public Component add (Component component)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public Component add (String title, Component component)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public void addTab (String title, Component component)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public void addTab (String title, Icon icon, Component component, String tip)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  @Override
  public void addTab (String title, Icon icon, Component component)
  {
    throw new UnsupportedOperationException ("Add panels with addPanel().");
  }

  public void showNextTab ()
  {
    int index = getSelectedIndex ();
    index++;
    if (index >= getTabCount ()) index = 0;
    setSelectedIndex (index);
    mTabs.get (index).requestFocusForFirstComponent ();
  }

}
