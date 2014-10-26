package org.jlexis.roklib;

import javax.swing.table.TableStringConverter;

/**
 * Created by rkrueger on 17.10.2014.
 */
public class TextFormatter {
    private int length;

    public TextFormatter(HTMLTextFormatter htmlTextFormatter) {

    }

    public TextFormatter append(String s) {
        return null;
    }

    public TextFormatter appendBold(String comment) {
        return this;
    }

    public TableStringConverter getFormattedText() {
        return null;
    }

    public TextFormatter appendItalic(String s) {
        return null;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
