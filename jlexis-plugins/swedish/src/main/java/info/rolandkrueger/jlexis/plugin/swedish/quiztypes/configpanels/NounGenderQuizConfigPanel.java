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
package info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

/*
 * @author Roland Krueger
 * @version $Id: NounGenderQuizConfigPanel.java 106 2009-05-15 15:21:29Z roland $
 */
public class NounGenderQuizConfigPanel extends JPanel
{
  private static final long serialVersionUID = 7062835929798010436L;
  private DisplayConfigPanel displayConfigPanel = null;
  private JPanel inputPanel = null;
  private JRadioButton definiteFormTextualRB = null;
  private JRadioButton multipleChoiceRB = null;
  private JPanel lowerPanel = null;
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public DisplayTypeEnum getDisplayType ()
  {
    return displayConfigPanel.getDisplayType ();
  }
  
  public AnswerInputTypeEnum getAnswerInputType ()
  {
    if (getDefiniteFormTextualRB ().isSelected ()) return AnswerInputTypeEnum.DEFINITE_TEXTUAL;
    if (getMultipleChoiceRB ().isSelected ()) return AnswerInputTypeEnum.MULTIPLE_CHOICE;
    return AnswerInputTypeEnum.UNKNOWN;
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////

  /**
   * This is the default constructor
   */
  public NounGenderQuizConfigPanel ()
  {
    initialize ();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (338, 342);
    this.setLayout (new BorderLayout());
    this.add(getDisplayConfigPanel(), BorderLayout.NORTH);
    this.add(getLowerPanel(), BorderLayout.CENTER);
    getDefiniteFormTextualRB ().setSelected (true);
  }

  /**
   * This method initializes displayConfigPanel	
   * 	
   * @return info.rolandkrueger.lexis.plugin.swedish.quiztypes.configpanels.DisplayConfigPanel	
   */
  private DisplayConfigPanel getDisplayConfigPanel ()
  {
    if (displayConfigPanel == null)
    {
      displayConfigPanel = new DisplayConfigPanel ();
    }
    return displayConfigPanel;
  }

  /**
   * This method initializes inputPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getInputPanel ()
  {
    if (inputPanel == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      inputPanel = new JPanel ();
      inputPanel.setLayout(gridLayout);
      ButtonGroup group = new ButtonGroup ();
      group.add (getMultipleChoiceRB());
      group.add (getDefiniteFormTextualRB ());
      inputPanel.add(getDefiniteFormTextualRB(), null);
      inputPanel.add(getMultipleChoiceRB(), null);
    }
    return inputPanel;
  }

  /**
   * This method initializes definiteFormTextualRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getDefiniteFormTextualRB ()
  {
    if (definiteFormTextualRB == null)
    {
      definiteFormTextualRB = new JRadioButton ();
      // TODO: I18N
//      definiteFormTextualRB.setText("bestimmte Form, textuell");
      definiteFormTextualRB.setText("definite form, textual");
    }
    return definiteFormTextualRB;
  }

  /**
   * This method initializes multipleChoiceRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getMultipleChoiceRB ()
  {
    if (multipleChoiceRB == null)
    {
      multipleChoiceRB = new JRadioButton ();
      // TODO: I18N
      multipleChoiceRB.setText("multiple choice");
    }
    return multipleChoiceRB;
  }

  /**
   * This method initializes lowerPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getLowerPanel ()
  {
    if (lowerPanel == null)
    {
      lowerPanel = new JPanel ();
      lowerPanel.setLayout(new BorderLayout());
      // TODO: I18N
//      lowerPanel.setBorder(BorderFactory.createTitledBorder(null, "Eingabe", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      lowerPanel.setBorder(BorderFactory.createTitledBorder(null, "Input", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      lowerPanel.add(getInputPanel(), BorderLayout.NORTH);
    }
    return lowerPanel;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
