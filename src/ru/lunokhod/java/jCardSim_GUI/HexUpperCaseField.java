package ru.lunokhod.java.jCardSim_GUI;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class HexUpperCaseField extends JTextField {
	 
	private int maxLenght = 256;
	
    public HexUpperCaseField() {
         super();
    }
 
    public void setMaxLenght(int lenght) {
    	maxLenght = lenght;
    }
    
    public int getMaxLenght() {
    	return maxLenght;
    }
    
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }
 
    private class UpperCaseDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        	String chars = "0123456789ABCDEFabcdef";
        	
            if (str == null) {
                return;
            }
            
            if (((getLength() + str.length()) <= maxLenght) || maxLenght == 0 ) {
            	char[] upper = str.toCharArray();
            	String out = "";
            	
            	for (int i = 0; i < upper.length; i++) {
            		if (chars.indexOf(upper[i]) != -1)
            			out = out + Character.toUpperCase(upper[i]);
            	}
            	
            	super.insertString(offs, out, a);
            }
        }
    }
}
