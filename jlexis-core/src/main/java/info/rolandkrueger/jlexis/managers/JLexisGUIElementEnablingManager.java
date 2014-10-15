/*
 * $Id: LexisGUIElementEnablingManager.java 208 2010-11-23 17:39:08Z roland $
 * Created on 22.10.2009
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
package info.rolandkrueger.jlexis.managers;

import info.rolandkrueger.jlexis.gui.actions.CreateNewQuizAction;
import info.rolandkrueger.jlexis.gui.actions.DeleteLearningUnitAction;
import info.rolandkrueger.jlexis.gui.actions.EditFileAction;
import info.rolandkrueger.jlexis.gui.actions.OpenLearningUnitAction;
import info.rolandkrueger.jlexis.gui.actions.SelectUserProfileAction;
import info.rolandkrueger.roklib.util.conditionalengine.Condition;
import info.rolandkrueger.roklib.util.groupvisibility.VisibilityGroupManager;

/**
 * @author Roland Krueger
 * @version $Id: LexisGUIElementEnablingManager.java 208 2010-11-23 17:39:08Z roland $
 */
public class JLexisGUIElementEnablingManager extends VisibilityGroupManager
{
  private static final long serialVersionUID = 3653121482001276710L;

  public final static String LEARNING_UNIT_SELECTED_GROUP = "LEARNING_UNIT_SELECTED_GROUP";
  public final static String NO_USER_PROFILE_EXISTS       = "NO_USER_PROFILE_EXISTS";
  public final static String NO_USER_PROFILE_SELECTED     = "NO_USER_PROFILE_SELECTED";
  public final static String CREATE_QUIZ_GROUP            = "CREATE_QUIZ_GROUP";
  
  private Condition mUserProfileExistsCondition;
  private Condition mUserProfileSelectedCondition;
  
  public JLexisGUIElementEnablingManager ()
  {
    mUserProfileExistsCondition   = new Condition ("UserProfileExists", false);
    mUserProfileSelectedCondition = new Condition ("UserProfileSelectedCondition", false);
  }
  
  public Condition getUserProfileExistsCondition ()
  {
    return mUserProfileExistsCondition;
  }
  
  public Condition getUserProfileSelectedCondition ()
  {
    return mUserProfileSelectedCondition;
  }
  
  public void setDeleteLearningUnitAction (DeleteLearningUnitAction deleteLearningUnitAction)
  {
    addGroupMember (JLexisGUIElementEnablingManager.LEARNING_UNIT_SELECTED_GROUP, deleteLearningUnitAction);
  }
  
  public void setEditFileAction (EditFileAction editFileAction)
  {
    addGroupMember (JLexisGUIElementEnablingManager.LEARNING_UNIT_SELECTED_GROUP, editFileAction);
  }
  
  public void setOpenLearningUnitAction (OpenLearningUnitAction openLearningUnitAction)
  {
    addGroupMember (JLexisGUIElementEnablingManager.LEARNING_UNIT_SELECTED_GROUP, openLearningUnitAction);
  }
  
  public void setSelectUserProfileAction (SelectUserProfileAction selectUserProfileAction)
  {
    addGroupMember (JLexisGUIElementEnablingManager.NO_USER_PROFILE_EXISTS, selectUserProfileAction);
  }
  
  public void setCreateNewQuizAction (CreateNewQuizAction createNewQuizAction)
  {
//    addGroupMember (LexisGUIElementEnablingManager.CREATE_QUIZ_GROUP, createNewQuizAction);
//    setExpressionForEnabling (CREATE_QUIZ_GROUP, 
//        BoolExpressionBuilder.createANDExpression (
//            mUserProfileExistsCondition, 
//            mUserProfileSelectedCondition));    
  }
}
