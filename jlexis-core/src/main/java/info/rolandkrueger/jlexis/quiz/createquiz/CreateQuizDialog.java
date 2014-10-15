/*
 * Created on 17.03.2009
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
package info.rolandkrueger.jlexis.quiz.createquiz;

import info.rolandkrueger.jlexis.quiz.manager.CreateQuizCallbackInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * @author Roland Krueger
 * @version $Id: CreateQuizDialog.java 126 2009-06-01 16:59:04Z roland $
 */
public class CreateQuizDialog extends JDialog
{
  private static final long serialVersionUID = -5162431452235582389L;
  private CreateQuizPanel createQuizPanel = null;
  private CreateQuizCallbackInterface callback;
  
  /**
   * This method initializes
   */
  public CreateQuizDialog ()
  {
    super ();
    initialize ();
  }

  public void setCreateQuizCallback (CreateQuizCallbackInterface callback)
  {
    getCreateQuizPanel ().setCreateQuizCallback (callback);
    this.callback = callback;
  }
  
  /**
   * This method initializes this
   */
  private void initialize ()
  {
    this.setContentPane(getCreateQuizPanel());
    setResizable(true);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setModal(true);
    // TODO: I18N
//    this.setTitle("Neue Abfragerunde beginnen");
    this.setTitle("Start new quiz round");
    this.setSize(new Dimension(740, 614));
    getCreateQuizPanel ().addCancelButtonActionListener (new ActionListener () {
      @Override
      public void actionPerformed (ActionEvent e)
      {
        setVisible (false);        
      }});
    getCreateQuizPanel ().addStartButtonActionListener (new ActionListener () {

      @Override
      public void actionPerformed (ActionEvent e)
      {
        callback.startQuiz (createQuizPanel.getQuizConfiguration ());    
        setVisible (false);
      }});
  }

  /**
   * This method initializes createQuizPanel	
   * 	
   * @return info.rolandkrueger.lexis.gui.containers.CreateQuizPanel	
   */
  private CreateQuizPanel getCreateQuizPanel ()
  {
    if (createQuizPanel == null)
    {
      createQuizPanel = new CreateQuizPanel ();
    }
    return createQuizPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
