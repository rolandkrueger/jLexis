/*
 * Created on 01.12.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
 * 
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
package org.jlexis.plugin.english.inputpanels;

import org.jlexis.data.vocable.AbstractUserInput;
import org.jlexis.data.vocable.AbstractWordType;
import org.jlexis.data.vocable.DefaultUserInput;
import org.jlexis.data.vocable.UserInput;
import org.jlexis.plugin.english.EnglishLanguagePlugin;
import org.jlexis.plugin.english.userinput.EnglishVerbUserInput;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class EnglishVerbInputPanel extends AbstractVocableInputPanel {
    private static final long serialVersionUID = -5818146994948840231L;
    private final static EnglishVerbUserInput sExpectedInput = new EnglishVerbUserInput();  //  @jve:decl-index=0:

    private JTabbedPane beaeTabbedPane = null;
    private VerbInputPanel beVerbInputPanel = null;
    private VerbInputPanel aeVerbInputPanel = null;
    private EnglishLanguagePlugin mPlugin;

    public EnglishVerbInputPanel(AbstractWordType correspondingWordType) {
        super(correspondingWordType);
        initialize();
    }

    public void setPlugin(EnglishLanguagePlugin plugin) {
        CheckForNull.check(plugin);
        mPlugin = plugin;
        getBeaeTabbedPane().setIconAt(0, mPlugin.getIcon());
        getBeaeTabbedPane().setIconAt(1, mPlugin.getUSAFlagIcon());
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(339, 254));
        this.add(getBeaeTabbedPane(), BorderLayout.CENTER);
    }

    @Override
    public List<Component> getComponentsInFocusTraversalOrder() {
        List<Component> result = new LinkedList<Component>();
        result.addAll(beVerbInputPanel.getComponentsInFocusTraversalOrder());
        result.addAll(aeVerbInputPanel.getComponentsInFocusTraversalOrder());
        return result;
    }

    @Override
    public void clearInput() {
        getBEVerbInputPanel().clearInput();
        getAEVerbInputPanel().clearInput();
    }

    @Override
    public AbstractUserInput getCurrentUserInput() {
        EnglishVerbUserInput result = new EnglishVerbUserInput();
        DefaultUserInput defaultUserInput = new DefaultUserInput();
        getBEVerbInputPanel().getStandardInputFieldsPanel().getCurrentUserInput(defaultUserInput);
        result.setBritishStandardValues(defaultUserInput);
        getAEVerbInputPanel().getStandardInputFieldsPanel().getCurrentUserInput(defaultUserInput);
        result.setAmericanStandardValues(defaultUserInput);
        result.setInfinitiveBE(getBEVerbInputPanel().getInfinitive());
        result.setInfinitiveAE(getAEVerbInputPanel().getInfinitive());
        result.setPastTenseBE(getBEVerbInputPanel().getPastTense());
        result.setPastTenseAE(getAEVerbInputPanel().getPastTense());
        result.setPastParticipleBE(getBEVerbInputPanel().getPastParticiple());
        result.setPastParticipleAE(getAEVerbInputPanel().getPastParticiple());
        result.setIrregularBE(getBEVerbInputPanel().isIrregular());
        result.setIrregularAE(getAEVerbInputPanel().isIrregular());

        return result;
    }

    @Override
    public boolean isUserInputEmpty() {
        return getBEVerbInputPanel().isUserInputEmpty() &&
                getAEVerbInputPanel().isUserInputEmpty();
    }

    @Override
    public void setUserInput(UserInput input) {
        CheckForNull.check(input);
        if (!sExpectedInput.isTypeCorrect(input))
            throw new IllegalStateException("Passed wrong user input object. Expected " + EnglishVerbUserInput.class.getName()
                    + " but was " + input.getClass().getName());
        EnglishVerbUserInput englishInput = (EnglishVerbUserInput) input;

        getBEVerbInputPanel().setInfinitive(englishInput.getInfinitiveBE());
        getAEVerbInputPanel().setInfinitive(englishInput.getInfinitiveAE());
        getBEVerbInputPanel().setPastTense(englishInput.getPastTenseBE());
        getAEVerbInputPanel().setPastTense(englishInput.getPastTenseAE());
        getBEVerbInputPanel().setPastParticiple(englishInput.getPastParticipleBE());
        getAEVerbInputPanel().setPastParticiple(englishInput.getPastParticipleAE());
        getBEVerbInputPanel().setIrregular(englishInput.isIrregularBE());
        getAEVerbInputPanel().setIrregular(englishInput.isIrregularAE());
        getBEVerbInputPanel().getStandardInputFieldsPanel().setUserInput(englishInput.getBritishStandardValues());
        getAEVerbInputPanel().getStandardInputFieldsPanel().setUserInput(englishInput.getAmericanStandardValues());
    }

    /**
     * This method initializes beaeTabbedPane
     *
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getBeaeTabbedPane() {
        if (beaeTabbedPane == null) {
            beaeTabbedPane = new JTabbedPane();
            beaeTabbedPane.addTab("British English", null, getBEVerbInputPanel(), null);
            beaeTabbedPane.addTab("American English", null, getAEVerbInputPanel(), null);
        }
        return beaeTabbedPane;
    }

    /**
     * This method initializes BritishVerbInputPanel
     *
     * @return info.rolandkrueger.lexis.plugin.english.inputpanels.VerbInputPanel
     */
    private VerbInputPanel getBEVerbInputPanel() {
        if (beVerbInputPanel == null) {
            beVerbInputPanel = new VerbInputPanel();
        }
        return beVerbInputPanel;
    }

    /**
     * This method initializes AmericanVerbInputPanel
     *
     * @return info.rolandkrueger.lexis.plugin.english.inputpanels.VerbInputPanel
     */
    private VerbInputPanel getAEVerbInputPanel() {
        if (aeVerbInputPanel == null) {
            aeVerbInputPanel = new VerbInputPanel();
        }
        return aeVerbInputPanel;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
