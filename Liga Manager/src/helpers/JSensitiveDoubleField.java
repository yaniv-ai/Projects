package helpers;

import javax.swing.text.Document;

public class JSensitiveDoubleField extends JSensitiveNumericField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7834224852031815496L;

	public JSensitiveDoubleField() {
		super();
	}

	public JSensitiveDoubleField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	public JSensitiveDoubleField(int arg0) {
		super(arg0);
	}

	public JSensitiveDoubleField(String arg0, int arg1) {
		super(arg0, arg1);
	}

	public JSensitiveDoubleField(String arg0) {
		super(arg0);
	}

	@Override
	protected String numerize(String text) {

		boolean pointFound = false;
		if (text == null || text.length() == 0) {
			return text;
		}

		String newText = "";
		for (int i = 0; i < text.length(); i++) {
			deletedKeys++;
			if (numchars.indexOf(text.charAt(i)) > -1) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
				deletedKeys--;
			}
			if (text.charAt(i) == '.' && !pointFound) {
				pointFound = true;
				newText = newText.concat(String.valueOf(text.charAt(i)));
				deletedKeys--;
			}
			if (i == 0 && (text.charAt(i) == '-' || text.charAt(i) == '+')) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
				deletedKeys--;
			}
		}

		return newText;
	}

	@Override
	protected void numericToText() {
		try {
			Double d = Double.parseDouble(getText());
			setText(d.toString());
		} catch (Exception e1) {
			setText(null);
		}
	}

	public Double toDouble() {
		try {
			return Double.parseDouble(getText());
		} catch (Exception e) {
			return null;
		}
	}
}
