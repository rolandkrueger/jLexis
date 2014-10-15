/*
 * $Id: EditLearningUnitInternalFrame.java 204 2009-12-17 15:20:16Z roland $
 * Created on 15.12.2008
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package info.rolandkrueger.jlexis.gui.internalframes;

import info.rolandkrueger.jlexis.controllers.EditVocabularyController;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.gui.containers.EditVocabularyPanel;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameEvent;

public class EditLearningUnitInternalFrame extends JLexisInternalFrame
{
  private static final long serialVersionUID = 3251518633864529710L;

  private LearningUnit             mLearningUnit;
  private EditVocabularyController mEditController;
  private EditVocabularyPanel      mEditVocabularyPanel;
  
  public EditLearningUnitInternalFrame (LearningUnit unitToEdit)
  {
    if (unitToEdit == null)
      throw new NullPointerException ("Learning unit is null.");
    
    setMaximizable(true);
    setResizable(true);
    setClosable(true);
    setIconifiable(true);
    setTitle (unitToEdit.getName ());
    setSize (1000, 800);
    

    mLearningUnit = unitToEdit;
    mEditVocabularyPanel = new EditVocabularyPanel ();
    mEditController = new EditVocabularyController (mEditVocabularyPanel);
    mEditController.setEditedLearningUnit (unitToEdit);
    setContentPane (mEditVocabularyPanel);
    getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK), 
    "switchInputPanel");
    getActionMap ().put ("switchInputPanel", mEditVocabularyPanel.getSwitchInputPanelTabAction ());
  }
  
  /**
   * Returns the {@link LearningUnit} that is displayed by this frame.
   * 
   * @return the {@link LearningUnit} maintained by this frame
   */
  public LearningUnit getEditedLearningUnit ()
  {
    return mLearningUnit;
  }
  
  @Override
  public void internalFrameClosing (InternalFrameEvent e)
  {
    super.internalFrameClosed (e);
    mLearningUnit.closeForEditing ();
  }
  
  @Override
  public void setVisible (boolean visible)
  {
    super.setVisible (visible);
    if (mEditVocabularyPanel != null)
      mEditVocabularyPanel.getVocableInputTabbedPane ().requestFocusForFirstComponent ();
  }
  
  public void saveChanges ()
  {
    mEditController.saveInput (false);
  }
}
