package componentHelpers;

import javax.swing.text.Document;

public class JSensitiveIntField extends JSensitiveNumericField {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8374390835689471765L;
	protected final static String numchars = "0123456789";

	public JSensitiveIntField() {
		super();
	}

	public JSensitiveIntField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	public JSensitiveIntField(int arg0) {
		super(arg0);
	}

	public JSensitiveIntField(String arg0, int arg1) {
		super(arg0, arg1);
	}

	public JSensitiveIntField(String arg0) {
		super(arg0);
	}

	@Override
	protected void numericToText() {
		try {
			Integer i = Integer.parseInt(getText());
			setText(i.toString());
		} catch (Exception e1) {
			setText(null);
		}
	}

	@Override
	protected String numerize(String text) {
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

			if (i == 0 && (text.charAt(i) == '-' || text.charAt(i) == '+')) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
				deletedKeys--;
			}
		}
		return newText;
	}

	public Integer toInt() {
		try {
			return Integer.parseInt(getText());
		} catch (Exception e) {
			return null;
		}
	}
}
