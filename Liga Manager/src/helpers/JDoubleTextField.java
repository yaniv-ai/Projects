package helpers;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class JDoubleTextField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// protected final static String badchars =
	// "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";
	protected final static String numchars = "0123456789";
	private final boolean numbersOnly;
	private final int lengthLimit;

	/**
	 * Enter initial field restrictions:
	 * 
	 * @param numbersOnly
	 *            true if you want the field to include only numbers
	 * @param lenghtLimit
	 *            length limitation of the string
	 */
	public JDoubleTextField(boolean numbersOnly, int lenghtLimit) {
		super();
		this.numbersOnly = numbersOnly;
		this.lengthLimit = lenghtLimit;
	}

	/**
	 * Enter initial field restrictions:
	 * 
	 * @param numbersOnly
	 *            true if you want the field to include only numbers
	 * @param lenghtLimit
	 *            length limitation of the string
	 */
	public JDoubleTextField(boolean numbersOnly, int lenghtLimit, String text,
			int columns) {
		super(text, columns);
		this.numbersOnly = numbersOnly;
		this.lengthLimit = lenghtLimit;

	}

	/**
	 * Enter initial field restrictions:
	 * 
	 * @param numbersOnly
	 *            true if you want the field to include only numbers
	 * @param lenghtLimit
	 *            length limitation of the string
	 */
	public JDoubleTextField(boolean numbersOnly, int lenghtLimit, String text) {
		super(text);
		this.numbersOnly = numbersOnly;
		this.lengthLimit = lenghtLimit;
	}

	public void processKeyEvent(KeyEvent ev) {
		super.processKeyEvent(ev);
		if (numbersOnly) {
			setText(numerize(getText()));
		}
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
	private String numerize(String text) {

		boolean pointFound = false;
		if (text == null || text.length() == 0) {
			return text;
		}

		String newText = "";
		for (int i = 0; i < text.length(); i++) {
			if (numchars.indexOf(text.charAt(i)) > -1) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
			if (text.charAt(i) == '.' && !pointFound) {
				pointFound = true;
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
			if (i == 0 && (text.charAt(i) == '-' || text.charAt(i) == '+')) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
		}

		return newText;
	}

	/**
	 * The limitize method substrings the text if it exceeds its limit
	 * 
	 * @param text
	 * @return - the text with maximum limitLength characters
	 */
	private String limitize(String text) {
		if (text == null || text.length() <= lengthLimit) {
			return text;
		}
		return text.substring(0, lengthLimit);
	}

	/**
	 * The toDouble method returns the converted string to double, or returns
	 * null if the string is null or invalid
	 * 
	 * @return - the text in Double format or null
	 */

	public Double toDouble() {
		try {
			return Double.parseDouble(this.getText());
		} catch (Exception e) {
			return null;
		}
	}

}
