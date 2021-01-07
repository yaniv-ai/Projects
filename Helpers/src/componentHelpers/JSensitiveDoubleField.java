package componentHelpers;

import javax.swing.text.Document;

public class JSensitiveDoubleField extends JSensitiveNumericField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7834224852031815496L;

	/**
	 * Constructs a new <code>JSensitiveDoubleField</code>. A default model is created, the
	 * initial string is <code>null</code>, and the number of columns is set to
	 * 0.
	 */
	public JSensitiveDoubleField() {
		super();
	}

	/**
	 * Constructs a new <code>JTextField</code> that uses the given text storage
	 * model and the given number of columns. This is the constructor through
	 * which the other constructors feed. If the document is <code>null</code>,
	 * a default model is created.
	 * 
	 * @param doc
	 *            the text storage to use; if this is <code>null</code>, a
	 *            default will be provided by calling the
	 *            <code>createDefaultModel</code> method
	 * @param text
	 *            the initial string to display, or <code>null</code>
	 * @param columns
	 *            the number of columns to use to calculate the preferred width
	 *            >= 0; if <code>columns</code> is set to zero, the preferred
	 *            width will be whatever naturally results from the component
	 *            implementation
	 * @exception IllegalArgumentException
	 *                if <code>columns</code> < 0
	 */
	public JSensitiveDoubleField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * Constructs a new empty <code>JSensitiveDoubleField</code> with the specified number
	 * of columns. A default model is created and the initial string is set to
	 * <code>null</code>.
	 * 
	 * @param columns
	 *            the number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation
	 */
	public JSensitiveDoubleField(int arg0) {
		super(arg0);
	}

	/**
	 * Constructs a new <code>JSensitiveDoubleField</code> initialized with the specified
	 * text and columns. A default model is created.
	 * 
	 * @param text
	 *            the text to be displayed, or <code>null</code>
	 * @param columns
	 *            the number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation
	 */
	public JSensitiveDoubleField(String arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructs a new <code>JSensitiveDoubleField</code> initialized with the
	 * specified text. A default model is created and the number of columns is
	 * 0.
	 * 
	 * @param text
	 *            the text to be displayed, or <code>null</code>
	 */
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
