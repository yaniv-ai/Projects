// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import javax.swing.text.Document;

abstract class JSensitiveNumericField extends JSensitiveTextField
{
    private static final long serialVersionUID = 8863124158870974579L;
    protected static final String numchars = "0123456789";
    
    public JSensitiveNumericField() {
        this.addFocusListener(this);
    }
    
    public JSensitiveNumericField(final Document arg0, final String arg1, final int arg2) {
        super(arg0, arg1, arg2);
        this.validateText();
        this.addFocusListener(this);
    }
    
    public JSensitiveNumericField(final int arg0) {
        super(arg0);
        this.addFocusListener(this);
    }
    
    public JSensitiveNumericField(final String arg0, final int arg1) {
        super(arg0, arg1);
        this.addFocusListener(this);
        this.validateText();
    }
    
    public JSensitiveNumericField(final String arg0) {
        super(arg0);
        this.addFocusListener(this);
        this.validateText();
    }
    
    @Override
    protected void validateText() {
        this.setText(this.numerize(this.getText()));
        if (this.lengthLimit > 0) {
            this.setText(this.limitize(this.getText()));
        }
    }
    
    protected abstract String numerize(final String p0);
    
    @Override
    protected void lastTextCheck() {
        this.addLastZero();
        this.numericToText();
    }
    
    protected abstract void numericToText();
    
    private void addLastZero() {
        final String text = this.getText();
        if ((text != null & text.length() > 0) && "0123456789".indexOf(text.charAt(text.length() - 1)) == -1) {
            this.setText(String.valueOf(this.getText()) + "0");
        }
    }
}
