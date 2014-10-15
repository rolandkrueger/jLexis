/*
 * Created on 22.03.2009
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
package info.rolandkrueger.jlexis.gui.datamodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public class JLexisListModel<T> implements ListModel
{
  private List<T>                mData;
  private List<ListDataListener> mListeners;
  
  public JLexisListModel ()
  {
    mData      = new ArrayList<T> ();
    mListeners = new ArrayList<ListDataListener> ();
  }
  
  public List<T> getData ()
  {
    return new ArrayList<T> (mData);
  }
  
  public void add (List<T> data)
  {
    int index0 = mData.size ();
    mData.addAll (data);
    fireIntervalAdded (index0, mData.size () - 1);
  }
  
  public void add (T value)
  {
    mData.add (value);
    fireIntervalAdded (mData.size () - 1, mData.size () - 1);
  }
  
  public List<T> getDataForIndices (int[] indices)
  {
    List<T> result = new ArrayList<T> (indices.length);
    for (int i : indices)
    {
      result.add (get (i));
    }
    return result;
  }
  
  public boolean isEmpty ()
  {
    return mData.isEmpty ();
  }
  
  public boolean remove (T value)
  {
    if ( ! mData.contains (value)) return false;
    int index = mData.indexOf (value);
    boolean result = mData.remove (value);
    if (result)
    {
      fireIntervalRemoved (index, index);
    }
    return result;
  }
  
  public List<T> remove (int[] indices)
  {
    List<T> removedData = new ArrayList<T> (indices.length);
    for (int i : indices)
    {
      T removedElement = mData.remove (i);
      if (removedElement != null)
        removedData.add (removedElement);        
    }
    fireContentsChanged (0, mData.size ());
    return removedData;
  }
  
  public List<T> remove (List<T> elements)
  {
    List<T> removedData = new ArrayList<T> (elements.size ());
    for (T element : elements)
    {
      if (mData.contains (element))
        removedData.add (element);   
      mData.remove (element);
    }
    fireContentsChanged (0, mData.size ());
    return removedData;
  }
  
  public void clear ()
  {
    mData.clear ();
    fireContentsChanged (0, 0);
  }
  
  @Override
  public void addListDataListener (ListDataListener l)
  {
    mListeners.add (l);
    fireContentsChanged (0, mData.size ());
  }

  @Override
  public Object getElementAt (int index)
  {
    return get (index);
  }

  public T get (int index)
  {
    return mData.get (index);
  }
  
  @Override
  public int getSize ()
  {
    return mData.size ();
  }
  
  @Override
  public void removeListDataListener (ListDataListener l)
  {
    mListeners.remove (l);
  }
  
  private void fireIntervalAdded (int index0, int index1)
  {
    List<ListDataListener> copyList = new ArrayList<ListDataListener> (mListeners);
    for (ListDataListener listener : copyList)
    {
      listener.intervalAdded (new ListDataEvent (this, ListDataEvent.INTERVAL_ADDED, index0, index1));
    }
  } 
  
  private void fireIntervalRemoved (int index0, int index1)
  {
    List<ListDataListener> copyList = new ArrayList<ListDataListener> (mListeners);
    for (ListDataListener listener : copyList)
    {
      listener.intervalRemoved (new ListDataEvent (this, ListDataEvent.INTERVAL_REMOVED, index0, index1));
    }
  } 
  
  private void fireContentsChanged (int index0, int index1)
  {
    List<ListDataListener> copyList = new ArrayList<ListDataListener> (mListeners);
    for (ListDataListener listener : copyList)
    {
      listener.contentsChanged (new ListDataEvent (this, ListDataEvent.CONTENTS_CHANGED, index0, index1));
    }
  } 
}
