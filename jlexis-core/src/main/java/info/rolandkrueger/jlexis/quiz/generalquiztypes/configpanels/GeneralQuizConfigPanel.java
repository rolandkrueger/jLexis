/*
 * Created on 15.05.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package info.rolandkrueger.jlexis.quiz.generalquiztypes.configpanels;

import info.rolandkrueger.jlexis.quiz.generalquiztypes.GeneralQuizMode;

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
 * @version $Id: GeneralQuizConfigPanel.java 204 2009-12-17 15:20:16Z roland $
 */
public class GeneralQuizConfigPanel extends JPanel
{
  private static final long serialVersionUID = -5760676565331063196L;
  private JPanel directionPanel = null;
  private JRadioButton nativeForeignRB = null;
  private JRadioButton foreignNativeRB = null;
  private JRadioButton mixedRB = null;
  private JPanel modePanel = null;
  private JRadioButton enterTextRB = null;
  private JRadioButton withoutTextEntryRB = null;
  private JPanel helpPanel = null;

  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- START --
  ////////////////////////////////////////////////////////////
  
  public GeneralQuizMode getSelectedQuizMode ()
  {
    if (getNativeForeignRB ().isSelected ())
      return GeneralQuizMode.NATIVE_TO_FOREIGN_LANGUAGE;
    if (getForeignNativeRB ().isSelected ())
      return GeneralQuizMode.FOREIGN_LANGUAGE_TO_NATIVE;
    if (getMixedRB ().isSelected ())
      return GeneralQuizMode.MIXED;
    
    return GeneralQuizMode.UNKNOWN;
  }
  
  ////////////////////////////////////////////////////////////
  // USER ADDED METHODS -- END --
  ////////////////////////////////////////////////////////////
  
  public GeneralQuizConfigPanel ()
  {
    super ();
    initialize ();
  }

  private void initialize ()
  {
    this.setSize (333, 216);
    this.setLayout (new BorderLayout());
    this.add(getHelpPanel(), BorderLayout.NORTH);
  }

  private JPanel getDirectionPanel ()
  {
    if (directionPanel == null)
    {
      GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(0);
      directionPanel = new JPanel ();
      directionPanel.setLayout(gridLayout);
      // TODO: I18N
//      directionPanel.setBorder(BorderFactory.createTitledBorder(null, "Abfragerichtung", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      directionPanel.setBorder(BorderFactory.createTitledBorder(null, "Language order", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      ButtonGroup group = new ButtonGroup ();
      group.add (getNativeForeignRB ());
      group.add (getForeignNativeRB ());
      group.add (getMixedRB ());
      directionPanel.add(getNativeForeignRB(), null);
      directionPanel.add(getForeignNativeRB(), null);
      directionPanel.add(getMixedRB(), null);
    }
    return directionPanel;
  }

  private JRadioButton getNativeForeignRB ()
  {
    if (nativeForeignRB == null)
    {
      nativeForeignRB = new JRadioButton ();
      // TODO: I18N
//      nativeForeignRB.setText("Muttersprache -> Fremdsprache");
      nativeForeignRB.setText("mother tongue -> foreign language");
      nativeForeignRB.setSelected (true);
    }
    return nativeForeignRB;
  }

  private JRadioButton getForeignNativeRB ()
  {
    if (foreignNativeRB == null)
    {
      foreignNativeRB = new JRadioButton ();
      // TODO: I18N
//      foreignNativeRB.setText("Fremdsprache -> Muttersprache");
      foreignNativeRB.setText("foreign language -> mother tongue");
    }
    return foreignNativeRB;
  }

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

  private JPanel getModePanel ()
  {
    if (modePanel == null)
    {
      GridLayout gridLayout1 = new GridLayout();
      gridLayout1.setColumns(1);
      gridLayout1.setRows(0);
      modePanel = new JPanel ();
      modePanel.setLayout(gridLayout1);
      // TODO: I18N      
//      modePanel.setBorder(BorderFactory.createTitledBorder(null, "Abfragemodus", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      modePanel.setBorder(BorderFactory.createTitledBorder(null, "Quiz mode", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
      ButtonGroup group = new ButtonGroup ();
      group.add (getEnterTextRB ());
      group.add (getWithoutTextEntryRB ());
      modePanel.add(getEnterTextRB(), null);
      modePanel.add(getWithoutTextEntryRB(), null);
    }
    return modePanel;
  }

  private JRadioButton getEnterTextRB ()
  {
    if (enterTextRB == null)
    {
      enterTextRB = new JRadioButton ();
      // TODO: I18N
//      enterTextRB.setText("mit Texteingabe");
      enterTextRB.setText("with text input");
      enterTextRB.setSelected (true);
    }
    return enterTextRB;
  }

  private JRadioButton getWithoutTextEntryRB ()
  {
    if (withoutTextEntryRB == null)
    {
      withoutTextEntryRB = new JRadioButton ();
      // TODO: I18N
//      withoutTextEntryRB.setText("ohne Texteingabe");
      withoutTextEntryRB.setText("without text input");
    }
    return withoutTextEntryRB;
  }

  private JPanel getHelpPanel ()
  {
    if (helpPanel == null)
    {
      GridLayout gridLayout2 = new GridLayout();
      gridLayout2.setColumns(1);
      gridLayout2.setRows(0);
      helpPanel = new JPanel ();
      helpPanel.setLayout(gridLayout2);
      helpPanel.add(getDirectionPanel(), null);
      helpPanel.add(getModePanel(), null);
    }
    return helpPanel;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
