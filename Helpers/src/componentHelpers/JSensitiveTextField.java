package componentHelpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class JSensitiveTextField extends JTextField implements FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 450105722217399099L;
	private String wouldBeText = "";
	private static final String TEXT_CHANGED_COMMAND = "textChanged";
	protected int lengthLimit = 0;
	private static final String KEY_TYPED = new String("KEY_TYPED");
	protected int deletedKeys = 0;

	/**
	 * Constructs a new <code>JSensitiveTextField</code>. A default model is created, the
	 * initial string is <code>null</code>, and the number of columns is set to
	 * 0.
	 */
	public JSensitiveTextField() {
		super();
		addFocusListener(this);
	}

	/**
	 * Constructs a new <code>JJSensitiveTextField</code> that uses the given text storage
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
	public JSensitiveTextField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		validateText();
		addFocusListener(this);
	}

	/**
	 * Constructs a new empty <code>JSensitiveTextField</code> with the specified number
	 * of columns. A default model is created and the initial string is set to
	 * <code>null</code>.
	 * 
	 * @param columns
	 *            the number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation
	 */
	public JSensitiveTextField(int arg0) {
		super(arg0);
		addFocusListener(this);
	}

	/**
	 * Constructs a new <code>JSensitiveTextField</code> initialized with the specified
	 * text and columns. A default model is created.
	 * 
	 * @param text
	 *            the text to be displayed, or <code>null</code>
	 * @param columns
	 *            the number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation
	 */
	public JSensitiveTextField(String arg0, int arg1) {
		super(arg0, arg1);
		addFocusListener(this);
		validateText();
	}

	/**
	 * Constructs a new <code>JSensitiveTextField</code> initialized with the specified
	 * text. A default model is created and the number of columns is 0.
	 * 
	 * @param text
	 *            the text to be displayed, or <code>null</code>
	 */
	public JSensitiveTextField(String arg0) {
		super(arg0);
		addFocusListener(this);
		validateText();
	}

	@Override
	public void postActionEvent() {
		super.postActionEvent();
	}

	public void processKeyEvent(KeyEvent ev) {
		super.processKeyEvent(ev);

		if (ev.paramString().startsWith(KEY_TYPED)) {
			int caretPosition = getCaretPosition();

			deletedKeys = 0;
			validateText();

			if (!wouldBeText.equals(getText())) {
				wouldBeText = getText();
				ActionListener[] al = this.getActionListeners();
				for (ActionListener a : al) {
					a.actionPerformed(new ActionEvent(ev.getSource(), ev
							.getID(), JSensitiveTextField.TEXT_CHANGED_COMMAND));
				}
			}
			caretPosition -= deletedKeys;
			if (getText() == null) {
				caretPosition = 0;
			} else if (getText().length() < caretPosition) {
				caretPosition = getText().length();
			} else if (caretPosition < 0) {
				caretPosition = 0;
			}
			setCaretPosition(caretPosition);
		}
	}

	protected void validateText() {
		if (lengthLimit > 0) {
			setText(limitize(getText()));
		}
	}

	/**
	 * The limitize method substrings the text if it exceeds its limit
	 * 
	 * @param text
	 * @return - the text with maximum limitLength characters
	 */
	protected String limitize(String text) {
		if (text == null || text.length() <= lengthLimit) {
			return text;
		}
		deletedKeys += text.length() - lengthLimit;
		return text.substring(0, lengthLimit);
	}

	public int getLengthLimit() {
		return lengthLimit;
	}

	public void setLengthLimit(int lengthLimit) {
		this.lengthLimit = lengthLimit;
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (getText() != null && getText().length() > 0) {
			select(0, getText().length());
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		lastTextCheck();
	}

	protected void lastTextCheck() {
	}
}
