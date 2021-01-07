package helpers;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import javax.swing.JTextField;

public class JSpecialField extends JTextField implements FocusListener, TextListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final static String numbers = "0123456789";
	protected final static String goodInteger = "0123456789";
	protected final static String goodDouble = "0123456789.";
	protected final static String badDirectory = "\\:*?/\".|<>";
	protected final static String onlyLatinLettersy = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private final int fieldLengthLimit;
	private final int FIELD_TYPE;

	private int lastLength = 0;

	public final static int INT_FIELD = 1; // only numbers
	public final static int DOUBLE_FIELD = 2; // only numbers and ONE point allowed
	public final static int FULL_DIRECTORY_FIELD = 3; // only valid directory path
	public final static int PARTIAL_DIRECTORY_FIELD = 4; // only valid directory path without Drive name
	public final static int LATIN_LETTERS_ONLY_FIELD = 5; // only letters big or small

	/**
	 * Enter initial field restrictions:
	 * 
	 * @param numbersOnly true if you want the field to include only numbers
	 * @param lenghtLimit length limitation of the string
	 */

	public JSpecialField(int FIELD_TYPE, int fieldLenghtLimit) {
		super();
		lastLength = 0;
		this.FIELD_TYPE = FIELD_TYPE;
		this.fieldLengthLimit = fieldLenghtLimit;
		this.addFocusListener(this);
	}

	/**
	 * Enter initial field restrictions:
	 * 
	 * @param numbersOnly true if you want the field to include only numbers
	 * @param lenghtLimit length limitation of the string
	 */
	public JSpecialField(int FIELD_TYPE, int fieldLenghtLimit, String text, int columns) {
		super(text, columns);
		lastLength = 0;
		this.FIELD_TYPE = FIELD_TYPE;
		this.fieldLengthLimit = fieldLenghtLimit;
		this.addFocusListener(this);
	}

	/**
	 * Enter initial field restrictions:
	 * 
	 * @param numbersOnly true if you want the field to include only numbers
	 * @param lenghtLimit length limitation of the string
	 */
	public JSpecialField(int FIELD_TYPE, int fieldLenghtLimit, String text) {
		super(text);
		lastLength = 0;
		this.FIELD_TYPE = FIELD_TYPE;
		this.fieldLengthLimit = fieldLenghtLimit;
		this.addFocusListener(this);
	}

//	public static void main(String[] args) {
//		JSpecialField intField = new JSpecialField(JSpecialField.INT_FIELD, 8);
//		JSpecialField doubleField = new JSpecialField(JSpecialField.DOUBLE_FIELD, 8);
//		final JSpecialField fullDirectoryField = new JSpecialField(JSpecialField.FULL_DIRECTORY_FIELD, 20);
//		final JSpecialField partialDirectoryField = new JSpecialField(JSpecialField.PARTIAL_DIRECTORY_FIELD, 20);
//		final JSpecialField lettersOnly = new JSpecialField(JSpecialField.LATIN_LETTERS_ONLY_FIELD, 20);
//		JFrame f = new JFrame();
//		JPanel p = new JPanel(new GridLayout(6, 2));
//
//		final JLabel l = new JLabel();
//
//		p.add(new JLabel("Int Field"));
//		p.add(intField);
//		p.add(new JLabel("Double Field"));
//		p.add(doubleField);
//		p.add(new JLabel("Full Directory Field"));
//		p.add(fullDirectoryField);
//		p.add(new JLabel("Partial Directory Field"));
//		p.add(partialDirectoryField);
//		p.add(new JLabel("Letters Only"));
//		p.add(lettersOnly);
//		JButton b = new JButton("Result");
//		b.setActionCommand("result");
//		b.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("Action");
//				l.setText(fullDirectoryField.getText());
//
//			}
//
//		});
//		p.add(b);
//		p.add(l);
//		f.add(p);
//		f.pack();
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setVisible(true);
//	}

	public void processKeyEvent(KeyEvent ev) {
		super.processKeyEvent(ev);
		this.setText(this.getText());
	}

	private String toLetters(String text) {
		String newText = "";
		if (text == null || text.length() == 0) {
			return text;
		}
		for (int i = 0; i < text.length(); i++) {
			if (onlyLatinLettersy.indexOf(text.charAt(i)) >= 0) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
		}
		return newText;
	}

	private String toDirectory(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		boolean charFound = false;
		String newText = "";
		for (int i = 0; i < text.length(); i++) {
			if (badDirectory.indexOf(text.charAt(i)) > -1) {
				if (i > 0 && text.charAt(i) == '\\' && charFound && text.charAt(i - 1) != '\\') {
					newText = newText.concat(String.valueOf(text.charAt(i)));

				}
				if (i == 1 && text.charAt(1) == ':') {
					if (this.FIELD_TYPE == JSpecialField.FULL_DIRECTORY_FIELD) {
						newText = newText.concat(String.valueOf(text.charAt(i)));
					}
					if (this.FIELD_TYPE == JSpecialField.PARTIAL_DIRECTORY_FIELD) {
						newText = "";
					}
				}

			} else {
				charFound = true;
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
		}
		if (newText.length() == 2 && newText.charAt(1) == ':') {
			if (lastLength > 2) {
				newText = String.valueOf(text.charAt(0));
			} else {
				newText = newText.concat("\\");
			}
		}
		lastLength = newText.length();
		return newText;
	}

	private String toDouble(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		boolean pointFound = false;
		String newText = "";
		for (int i = 0; i < text.length(); i++) {
			char charAt = text.charAt(i);
			if (numeric(charAt)) {
				newText = newText.concat(String.valueOf(charAt));
			}
			if (charAt == '.' && !pointFound) {
				pointFound = true;
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
		}

		return newText;
	}

	private String toInt(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}

		String newText = "";
		for (int i = 0; i < text.length(); i++) {
			char charAt = text.charAt(i);
			if (numeric(charAt)) {
				newText = newText.concat(String.valueOf(charAt));
			}
		}

		return newText;
	}

	/**
	 * Checks whether the {@link Character} is numeric
	 * 
	 * @param n - The char to check
	 * @return - true if the character is numeric
	 */
	private boolean numeric(char n) {
		if (numbers.indexOf(n) > -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The limitize method substrings the text if it exceeds its limit
	 * 
	 * @param text
	 * @return - the text with maximum limitLength characters
	 */
	private String limitize(String text) {
		if (text == null || text.length() <= fieldLengthLimit || fieldLengthLimit <= 0) {
			return text;
		}
		return text.substring(0, fieldLengthLimit);
	}

	@Override
	public void focusGained(FocusEvent arg0) {

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		setText(getText());
		setText(validateText(getText()));
	}

	private String validateText(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}

		switch (FIELD_TYPE) {
		case DOUBLE_FIELD:
			if (text.indexOf(".") > -1) {

			} else {
				text = text.concat(".");
			}
			break;
		case FULL_DIRECTORY_FIELD:
		case PARTIAL_DIRECTORY_FIELD:
			if (text.length() > 3 && text.charAt(1) == ':' && text.charAt(text.length() - 1) == '\\') {
				text = text.substring(0, text.length() - 1);
			}
			break;
		case LATIN_LETTERS_ONLY_FIELD:
			text = toLetters(text);
		default:
			break;
		}
		return text;
	}

	@Override
	public void textValueChanged(TextEvent arg0) {
		setText(getText());
		// setText(validateText());
	}

	@Override
	public void setText(String text) {
		String newText = "";
		switch (FIELD_TYPE) {
		case INT_FIELD:
			newText = toInt(text);
			break;
		case DOUBLE_FIELD:
			newText = toDouble(text);
			break;
		case FULL_DIRECTORY_FIELD:
		case PARTIAL_DIRECTORY_FIELD:
			newText = toDirectory(text);
			break;
		case LATIN_LETTERS_ONLY_FIELD:
			newText = toLetters(text);
			break;
		default:
			break;
		}
		newText = limitize(newText);
		super.setText(newText);
	}

}