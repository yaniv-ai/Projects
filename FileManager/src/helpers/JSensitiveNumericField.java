package helpers;

import javax.swing.text.Document;

abstract class JSensitiveNumericField extends JSensitiveTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8863124158870974579L;
	protected final static String numchars = "0123456789";

	public JSensitiveNumericField() {
		super();
		addFocusListener(this);
	}

	public JSensitiveNumericField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		validateText();
		addFocusListener(this);
	}

	public JSensitiveNumericField(int arg0) {
		super(arg0);
		addFocusListener(this);
	}

	public JSensitiveNumericField(String arg0, int arg1) {
		super(arg0, arg1);
		addFocusListener(this);
		validateText();
	}

	public JSensitiveNumericField(String arg0) {
		super(arg0);
		addFocusListener(this);
		validateText();
	}

	@Override
	protected void validateText() {
		setText(numerize(getText()));
		if (lengthLimit > 0) {
			setText(limitize(getText()));
		}
	}

	/**
	 * The numerize method removes all none numeric characters
	 * 
	 * @param text
	 * @return
	 */
	protected abstract String numerize(String text);

	@Override
	protected void lastTextCheck() {
		addLastZero();
		numericToText();
	}

	protected abstract void numericToText();

	private void addLastZero() {
		String text = getText();
		if (text != null & text.length() > 0
				&& numchars.indexOf(text.charAt(text.length() - 1)) == -1) {
			setText(getText() + "0");
		}
	}
}
