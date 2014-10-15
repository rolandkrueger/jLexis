/*
 * Created on 27.05.2009.
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info). All rights reserved.
 * 
 * This file is part of Anteeo.
 */
package info.rolandkrueger.jlexis.managers.learningunitmanager;

import info.rolandkrueger.jlexis.data.units.LearningUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: LearningUnitManagerCallbackHandler.java 125 2009-05-30 08:58:51Z roland $
 */
public class LearningUnitManagerCallbackHandler
{
  private List<LearningUnitManagerCallbackInterface> mListeners;
  private Object mOwner;
  
  public LearningUnitManagerCallbackHandler (Object owner)
  {
    if (owner == null)
      throw new NullPointerException ("Owner is null.");
    mListeners = new ArrayList<LearningUnitManagerCallbackInterface> ();
    mOwner = owner;
  }
  
  public void addLearningUnitManagerCallback (LearningUnitManagerCallbackInterface listener)
  {
    if (listener == null) throw new NullPointerException ("Listener is null.");
    mListeners.add (listener);
  }
  
  public void removeLearningUnitManagerCallback (LearningUnitManagerCallbackInterface listener)
  {
    mListeners.remove (listener);
  }
  
  public void removeAllListeners ()
  {
    mListeners.clear ();
  }
  
  public void fireUnitsAdded (List<LearningUnit> units)
  {
    if (units == null) throw new NullPointerException ("Learning unit list is null.");
    List<LearningUnitManagerCallbackInterface> copyList = new ArrayList<LearningUnitManagerCallbackInterface> (mListeners);
    for (LearningUnitManagerCallbackInterface listener : copyList)
    {
      listener.unitsAdded (mOwner, units);
    }    
  }
  
  public void fireUnitsRemoved (List<LearningUnit> units)
  {
    if (units == null) throw new NullPointerException ("Learning unit list is null.");
    List<LearningUnitManagerCallbackInterface> copyList = new ArrayList<LearningUnitManagerCallbackInterface> (mListeners);
    for (LearningUnitManagerCallbackInterface listener : copyList)
    {
      listener.unitsRemoved (mOwner, units);
    } 
  }
  
  public void fireLearningUnitAdded (LearningUnit unit)
  {
    if (unit == null) throw new NullPointerException ("Learning unit is null.");
    List<LearningUnitManagerCallbackInterface> copyList = new ArrayList<LearningUnitManagerCallbackInterface> (mListeners);
    for (LearningUnitManagerCallbackInterface listener : copyList)
    {
      listener.unitAdded (mOwner, unit);
    }
  }
  
  public void fireLearningUnitRemoved (LearningUnit unit)
  {
    if (unit == null) throw new NullPointerException ("Learning unit is null.");
    List<LearningUnitManagerCallbackInterface> copyList = new ArrayList<LearningUnitManagerCallbackInterface> (mListeners);
    for (LearningUnitManagerCallbackInterface listener : copyList)
    {
      listener.unitRemoved (mOwner, unit);
    }
  }
}
