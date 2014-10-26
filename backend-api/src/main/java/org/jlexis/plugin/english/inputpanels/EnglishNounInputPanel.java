/*
 * Created on 14.11.2009
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

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class EnglishNounInputPanel extends AbstractVocableInputPanel {
    private static final long serialVersionUID = 6437408959742820207L;
    private static final EnglishNounUserInput sExpectedInput = new EnglishNounUserInput();  //  @jve:decl-index=0:

    private JTabbedPane beaeTabbedPane = null;
    private NounInputPanel beNounInputPanel = null;
    private NounInputPanel aeNounInputPanel = null;
    private EnglishLanguagePlugin mPlugin;

    /**
     * This is the default constructor
     */
    public EnglishNounInputPanel(EnglishNounWordType wordType) {
        super(wordType);
        initialize();
    }

    public void setPlugin(EnglishLanguagePlugin plugin) {
        CheckForNull.check(plugin);
        mPlugin = plugin;
        getBeaeTabbedPane().setIconAt(0, mPlugin.getIcon());
        getBeaeTabbedPane().setIconAt(1, mPlugin.getUSAFlagIcon());
    }

    @Override
    public List<Component> getComponentsInFocusTraversalOrder() {
        List<Component> result = new LinkedList<Component>();
        result.addAll(beNounInputPanel.getComponentsInFocusTraversalOrder());
        result.addAll(aeNounInputPanel.getComponentsInFocusTraversalOrder());
        return result;
    }

    @Override
    public void clearInput() {
        getBeNounInputPanel().clearInput();
        getAeNounInputPanel().clearInput();
    }

    @Override
    public AbstractUserInput getCurrentUserInput() {
        EnglishNounUserInput result = new EnglishNounUserInput();
        DefaultUserInput defaultUserInput = new DefaultUserInput();
        getBeNounInputPanel().getStandardInputFieldsPanel().getCurrentUserInput(defaultUserInput);
        result.setBritishStandardValues(defaultUserInput);
        getAeNounInputPanel().getStandardInputFieldsPanel().getCurrentUserInput(defaultUserInput);
        result.setAmericanStandardValues(defaultUserInput);
        result.setCountabilityBE(getBeNounInputPanel().getCountability());
        result.setCountabilityAE(getAeNounInputPanel().getCountability());
        result.setSingularPluralTypeBE(getBeNounInputPanel().getSingularPluralType());
        result.setSingularPluralTypeAE(getAeNounInputPanel().getSingularPluralType());
        result.setPluralIrregularBE(getBeNounInputPanel().hasIrregularPlural());
        result.setPluralIrregularAE(getAeNounInputPanel().hasIrregularPlural());
        result.setSingularBE(getBeNounInputPanel().getNounSingular());
        result.setSingularAE(getAeNounInputPanel().getNounSingular());
        result.setPluralBE(getBeNounInputPanel().getNounPlural());
        result.setPluralAE(getAeNounInputPanel().getNounPlural());
        result.setPluralPhoneticsBE(getBeNounInputPanel().getPluralPhonetics());
        result.setPluralPhoneticsAE(getAeNounInputPanel().getPluralPhonetics());
        return result;
    }

    @Override
    public boolean isUserInputEmpty() {
        return getBeNounInputPanel().isUserInputEmpty() &&
                getAeNounInputPanel().isUserInputEmpty();
    }

    @Override
    public void setUserInput(UserInputInterface input) {
        CheckForNull.check(input);
        if (!sExpectedInput.isTypeCorrect(input))
            throw new IllegalStateException("Passed wrong user input object. Expected " + EnglishNounUserInput.class.getName()
                    + " but was " + input.getClass().getName());
        EnglishNounUserInput englishInput = (EnglishNounUserInput) input;

        getBeNounInputPanel().setCountability(englishInput.getCountabilityBE());
        getAeNounInputPanel().setCountability(englishInput.getCountabilityAE());
        getBeNounInputPanel().setSingularPluralType(englishInput.getSingularPluralTypeBE());
        getAeNounInputPanel().setSingularPluralType(englishInput.getSingularPluralTypeAE());
        getBeNounInputPanel().setIrregularPlural(englishInput.isPluralIrregularBE());
        getAeNounInputPanel().setIrregularPlural(englishInput.isPluralIrregularAE());
        getBeNounInputPanel().setNounSingular(englishInput.getSingularBE());
        getAeNounInputPanel().setNounSingular(englishInput.getSingularAE());
        getBeNounInputPanel().setNounPlural(englishInput.getPluralBE());
        getAeNounInputPanel().setNounPlural(englishInput.getPluralAE());
        getBeNounInputPanel().setPluralPhonetics(englishInput.getPluralPhoneticsBE());
        getAeNounInputPanel().setPluralPhonetics(englishInput.getPluralPhoneticsAE());
        getBeNounInputPanel().getStandardInputFieldsPanel().setUserInput(englishInput.getBritishStandardValues());
        getAeNounInputPanel().getStandardInputFieldsPanel().setUserInput(englishInput.getAmericanStandardValues());
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(494, 355);
        this.setLayout(new BorderLayout());
        this.add(getBeaeTabbedPane(), BorderLayout.CENTER);
    }

    /**
     * This method initializes beaetabbedPane
     *
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getBeaeTabbedPane() {
        if (beaeTabbedPane == null) {
            beaeTabbedPane = new JTabbedPane();
            // TODO: i18n
            beaeTabbedPane.addTab("British English", null, getBeNounInputPanel(), null);
            beaeTabbedPane.addTab("American English", null, getAeNounInputPanel(), null);
        }
        return beaeTabbedPane;
    }

    /**
     * This method initializes beNounInputPanel
     *
     * @return info.rolandkrueger.lexis.plugin.english.inputpanels.NounInputPanel
     */
    private NounInputPanel getBeNounInputPanel() {
        if (beNounInputPanel == null) {
            beNounInputPanel = new NounInputPanel();
        }
        return beNounInputPanel;
    }

    /**
     * This method initializes aeNounInputPanel
     *
     * @return info.rolandkrueger.lexis.plugin.english.inputpanels.NounInputPanel
     */
    private NounInputPanel getAeNounInputPanel() {
        if (aeNounInputPanel == null) {
            aeNounInputPanel = new NounInputPanel();
        }
        return aeNounInputPanel;
    }
}  //  @jve:decl-index=0:visual-constraint="12,18"
