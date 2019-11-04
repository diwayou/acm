package com.diwayou.game.go.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class GoPortTextField extends GoPopupTextField {
    public GoPortTextField(String text, int columns) {
        super(text, columns);
    }

    protected Document createDefaultModel() {
        return new PortNODocument(5);
    }

    class PortNODocument extends PlainDocument {
        int maxCharacters;

        public PortNODocument(int maxChars) {
            maxCharacters = maxChars;
        }

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if ((getLength() + str.length()) <= maxCharacters) {
                char[] source = str.toCharArray();
                char[] result = new char[source.length];
                int j = 0;

                for (int i = 0; i < result.length; i++) {
                    if (Character.isDigit(source[i])) result[j++] = source[i];
                    else Toolkit.getDefaultToolkit().beep();
                }

                super.insertString(offs, new String(result, 0, j), a);
            } else Toolkit.getDefaultToolkit().beep();
        }
    }
} 