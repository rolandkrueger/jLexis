/*
 * Created on 27.03.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/*
 * @author Roland Krueger
 * @version $Id: NounPluralQuizConfigPanel.java 106 2009-05-15 15:21:29Z roland $
 */
public class NounPluralQuizConfigPanel extends JPanel  
{
  private static final long serialVersionUID = -6565434873358866514L;
  private JPanel lowerPanel = null;
  private JPanel checkboxPanel = null;
  private JCheckBox indefiniteFormCheckBox = null;
  private JCheckBox definiteFormCheckBox = null;
  private JCheckBox groupsCheckBox = null;
  private JCheckBox onlyIrregularCheckBox = null;
  private DisplayConfigPanel displayConfigPanel = null;
    
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public DisplayTypeEnum getDisplayType ()
  {
    return displayConfigPanel.getDisplayType ();
  }
  
  public boolean isIndefiniteFormSelected ()
  {
    return indefiniteFormCheckBox.isSelected ();
  }
  
  public boolean isDefiniteFormSelected ()
  {
    return definiteFormCheckBox.isSelected ();
  }
  
  public boolean isGroupsSelected ()
  {
    return groupsCheckBox.isSelected ();
  }
  
  public boolean isIrregularSelected ()
  {
    return onlyIrregularCheckBox.isSelected ();
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  /**
   * This is the default constructor
   */
  public NounPluralQuizConfigPanel ()
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
    this.setSize (302, 290);
    this.setLayout (new BorderLayout());
    this.add(getDisplayConfigPanel(), BorderLayout.NORTH);
    this.add(getLowerPanel(), BorderLayout.CENTER);
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
      lowerPanel.setBorder(BorderFactory.createTitledBorder(null, "Abzufragende Bestandteile", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      lowerPanel.add(getCheckboxPanel(), BorderLayout.NORTH);
    }
    return lowerPanel;
  }

  /**
   * This method initializes checkboxPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getCheckboxPanel ()
  {
    if (checkboxPanel == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      checkboxPanel = new JPanel ();
      checkboxPanel.setLayout(gridLayout);
      checkboxPanel.add(getIndefiniteFormCheckBox(), null);
      checkboxPanel.add(getDefiniteFormCheckBox(), null);
      checkboxPanel.add(getGroupsCheckBox(), null);
      checkboxPanel.add(getOnlyIrregularCheckBox1(), null);
    }
    return checkboxPanel;
  }

  /**
   * This method initializes indefiniteFormCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getIndefiniteFormCheckBox ()
  {
    if (indefiniteFormCheckBox == null)
    {
      indefiniteFormCheckBox = new JCheckBox ();
      // TODO: I18N
//      indefiniteFormCheckBox.setText("unbestimmte Form");
      indefiniteFormCheckBox.setText("indefinite form");
    }
    return indefiniteFormCheckBox;
  }

  /**
   * This method initializes definiteFormCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getDefiniteFormCheckBox ()
  {
    if (definiteFormCheckBox == null)
    {
      definiteFormCheckBox = new JCheckBox ();
      // TODO: I18N
//      definiteFormCheckBox.setText("bestimmte Form");
      definiteFormCheckBox.setText("definite form");
    }
    return definiteFormCheckBox;
  }

  /**
   * This method initializes groupsCheckBox	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getGroupsCheckBox ()
  {
    if (groupsCheckBox == null)
    {
      groupsCheckBox = new JCheckBox ();
      // TODO: I18N
//      groupsCheckBox.setText("Gruppe");
      groupsCheckBox.setText("group");
    }
    return groupsCheckBox;
  }

  /**
   * This method initializes onlyIrregularCheckBox1	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getOnlyIrregularCheckBox1 ()
  {
    if (onlyIrregularCheckBox == null)
    {
      onlyIrregularCheckBox = new JCheckBox ();
      // TODO: I18N
//      onlyIrregularCheckBox.setText("nur unregelm\u00E4\u00dfige");
      onlyIrregularCheckBox.setText("only irregular");
    }
    return onlyIrregularCheckBox;
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

}  //  @jve:decl-index=0:visual-constraint="10,10"
