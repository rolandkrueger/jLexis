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
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

/*
 * @author Roland Krueger
 * @version $Id: DisplayConfigPanel.java 98 2009-03-27 19:08:32Z roland $
 */
public class DisplayConfigPanel extends JPanel
{
  private static final long serialVersionUID = 1213191892889239103L;
  private JPanel radioButtonPanel = null;
  private JRadioButton swedishRB = null;
  private JRadioButton nativeLangRB = null;
  private JRadioButton allRB = null;
  private JRadioButton mixedRB = null;

  public DisplayTypeEnum getDisplayType ()
  {
    if (getSwedishRB ().isSelected ()) return DisplayTypeEnum.SWEDISH;
    if (getNativeLangRB ().isSelected ()) return DisplayTypeEnum.NATIVE;
    if (getAllRB ().isSelected ()) return DisplayTypeEnum.ALL;
    if (getMixedRB ().isSelected ()) return DisplayTypeEnum.MIXED;
    return DisplayTypeEnum.UNKNOWN;
  }
  
  /**
   * This is the default constructor
   */
  public DisplayConfigPanel ()
  {
    super ();
    initialize ();
    getSwedishRB ().setSelected (true);
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize ()
  {
    this.setSize (300, 200);
    setLayout(new BorderLayout());
    // TODO: I18N
//    setBorder(BorderFactory.createTitledBorder(null, "Anzeige", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    setBorder(BorderFactory.createTitledBorder(null, "Display", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    add(getRadioButtonPanel(), java.awt.BorderLayout.NORTH);
  }

  /**
   * This method initializes radioButtonPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getRadioButtonPanel ()
  {
    if (radioButtonPanel == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      radioButtonPanel = new JPanel ();
      radioButtonPanel.setLayout(gridLayout);
      ButtonGroup group = new ButtonGroup ();
      group.add (getSwedishRB ());
      group.add (getNativeLangRB ());
      group.add (getAllRB ());
      group.add (getMixedRB ());      
      radioButtonPanel.add(getSwedishRB(), getSwedishRB().getName());
      radioButtonPanel.add(getNativeLangRB(), getNativeLangRB().getName());
      radioButtonPanel.add(getAllRB(), getAllRB().getName());
      radioButtonPanel.add(getMixedRB(), getMixedRB().getName());
    }
    return radioButtonPanel;
  }

  /**
   * This method initializes swedishRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getSwedishRB ()
  {
    if (swedishRB == null)
    {
      swedishRB = new JRadioButton ();
      // TODO: I18N
//      swedishRB.setText("Schwedisch");
      swedishRB.setText("Swedish");
    }
    return swedishRB;
  }

  /**
   * This method initializes nativeLangRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getNativeLangRB ()
  {
    if (nativeLangRB == null)
    {
      nativeLangRB = new JRadioButton ();
      // TODO: I18N
//      nativeLangRB.setText("Muttersprache");
      nativeLangRB.setText("mother tongue");
    }
    return nativeLangRB;
  }

  /**
   * This method initializes allRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getAllRB ()
  {
    if (allRB == null)
    {
      allRB = new JRadioButton ();
      // TODO: I18N
//      allRB.setText("alles ausser Schwedisch");
      allRB.setText("everything except Swedish");
    }
    return allRB;
  }

  /**
   * This method initializes mixedRB	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getMixedRB ()
  {
    if (mixedRB == null)
    {
      mixedRB = new JRadioButton ();
      // TODO: I18N
//      mixedRB.setText("gemischt");
      mixedRB.setText("shuffled");
    }
    return mixedRB;
  }

}
