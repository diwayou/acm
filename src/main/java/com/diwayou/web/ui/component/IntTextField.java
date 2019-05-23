package com.diwayou.web.ui.component;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class IntTextField extends JTextField {
    public IntTextField(int defval, int size) {
        super(new IntTextDocument(), "" + defval, size);
    }

    @Override
    protected Document createDefaultModel() {
        return new IntTextDocument();
    }

    public int getValue() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static class IntTextDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null)
                return;

            String oldString = getText(0, getLength());
            String newString = oldString.substring(0, offs) + str
                    + oldString.substring(offs);
            try {
                Integer.parseInt(newString + "0");
                super.insertString(offs, str, a);
            } catch (NumberFormatException ignore) {
            }
        }
    }
}
