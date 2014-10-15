/*
 * $Id: LearningUnitManagerFrame.java 204 2009-12-17 15:20:16Z roland $ 
 * 
 * Created on 24.11.2008 
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info) 
 * 
 * This file is part of jLexis. jLexis is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version. jLexis is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with jLexis;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package info.rolandkrueger.jlexis.gui.internalframes.learningunitmanager;

import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.containers.LearningUnitManagerPanel;
import info.rolandkrueger.jlexis.gui.internalframes.JLexisInternalFrame;

import java.util.List;

import javax.swing.event.ChangeListener;

public class LearningUnitManagerFrame extends JLexisInternalFrame 
{
  private static final long serialVersionUID = 3424500097210748426L;
  private LearningUnitManagerPanel learningUnitManagerPanel = null;

  /**
   * This is the default constructor.
   */
  public LearningUnitManagerFrame ()
  {
    super ();
    initialize ();
  }

  /**
   * This method initializes this
   */
  private void initialize ()
  {
    this.setSize (657, 519);
    this.setContentPane(getLearningUnitManagerPanel());
    this.setMaximizable (true);
    this.setResizable (true);
    this.setClosable (true);
    this.setIconifiable (true);
    // TODO: I18N
//    this.setTitle ("Lerneinheiten");
    this.setTitle ("Learning units");
    this.setDefaultCloseOperation (HIDE_ON_CLOSE);
  }

  public List<LearningUnit> getSelectedLearningUnits ()
  {
    return getLearningUnitManagerPanel ().getSelectedLearningUnits ();
  }

  public void addChangeListener (ChangeListener listener)
  {
    getLearningUnitManagerPanel ().addChangeListener (listener);
  }
  
  public boolean isSelectionMade ()
  {
    return getLearningUnitManagerPanel ().isSelectionMade ();
  }

  /**
   * This method initializes learningUnitManagerPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.LearningUnitManagerPanel	
   */
  private LearningUnitManagerPanel getLearningUnitManagerPanel ()
  {
    if (learningUnitManagerPanel == null)
    {
      learningUnitManagerPanel = new LearningUnitManagerPanel ();
    }
    return learningUnitManagerPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
