// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import javax.swing.text.Document;

public class JSensitiveIntField extends JSensitiveNumericField
{
    private static final long serialVersionUID = -8374390835689471765L;
    protected static final String numchars = "0123456789";
    private Integer rangeFrom;
    private Integer rangeTo;
    
    public JSensitiveIntField() {
        this.rangeFrom = null;
        this.rangeTo = null;
    }
    
    public JSensitiveIntField(final Document arg0, final String arg1, final int arg2) {
        super(arg0, arg1, arg2);
        this.rangeFrom = null;
        this.rangeTo = null;
    }
    
    public JSensitiveIntField(final int arg0) {
        super(arg0);
        this.rangeFrom = null;
        this.rangeTo = null;
    }
    
    public JSensitiveIntField(final String arg0, final int arg1) {
        super(arg0, arg1);
        this.rangeFrom = null;
        this.rangeTo = null;
    }
    
    public JSensitiveIntField(final String arg0) {
        super(arg0);
        this.rangeFrom = null;
        this.rangeTo = null;
    }
    
    @Override
    protected void numericToText() {
        try {
            final Integer i = Integer.parseInt(this.getText());
            this.setText(i.toString());
        }
        catch (Exception e1) {
            this.setText(null);
        }
    }
    
    @Override
    protected String numerize(final String text) {
        if (text == null || text.length() == 0) {
            return text;
        }
        String newText = "";
        for (int i = 0; i < text.length(); ++i) {
            ++this.deletedKeys;
            if ("0123456789".indexOf(text.charAt(i)) > -1) {
                newText = newText.concat(String.valueOf(text.charAt(i)));
                --this.deletedKeys;
            }
            if (i == 0 && (text.charAt(i) == '-' || text.charAt(i) == '+')) {
                newText = newText.concat(String.valueOf(text.charAt(i)));
                --this.deletedKeys;
            }
        }
        return newText;
    }
    
    @Override
    protected void validateText() {
        super.validateText();
        final Integer n = this.toInt();
        if (this.rangeFrom != null && n != null && n < this.rangeFrom) {
            this.setText(this.rangeFrom.toString());
        }
        if (this.rangeTo != null && n != null && n > this.rangeTo) {
            this.setText(this.rangeTo.toString());
        }
    }
    
    public Integer toInt() {
        try {
            return Integer.parseInt(this.getText());
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public void setRangeFrom(final Integer rangeFrom) {
        this.rangeFrom = rangeFrom;
    }
    
    public void setRangeTo(final Integer rangeTo) {
        this.rangeTo = rangeTo;
    }
}
