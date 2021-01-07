package componentHelpers;

import javax.swing.text.Document;

abstract class JSensitiveNumericField extends JSensitiveTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8863124158870974579L;
	protected final static String numchars = "0123456789";

	/**
     * Constructs a new <code>JSensitiveNumericField</code>.  A default model is created,
     * the initial string is <code>null</code>,
     * and the number of columns is set to 0.
     */
	public JSensitiveNumericField() {
		super();
		addFocusListener(this);
	}

    /**
     * Constructs a new <code>JTextField</code> that uses the given text
     * storage model and the given number of columns.
     * This is the constructor through which the other constructors feed.
     * If the document is <code>null</code>, a default model is created.
     *
     * @param doc  the text storage to use; if this is <code>null</code>,
     *          a default will be provided by calling the
     *          <code>createDefaultModel</code> method
     * @param text  the initial string to display, or <code>null</code>
     * @param columns  the number of columns to use to calculate
     *   the preferred width >= 0; if <code>columns</code>
     *   is set to zero, the preferred width will be whatever
     *   naturally results from the component implementation
     * @exception IllegalArgumentException if <code>columns</code> < 0
     */
	public JSensitiveNumericField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		validateText();
		addFocusListener(this);
	}

	/**
     * Constructs a new empty <code>JSensitiveNumericField</code> with the specified
     * number of columns.
     * A default model is created and the initial string is set to
     * <code>null</code>.
     *
     * @param columns  the number of columns to use to calculate
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
	public JSensitiveNumericField(int arg0) {
		super(arg0);
		addFocusListener(this);
	}

    /**
     * Constructs a new <code>JSensitiveNumericField</code> initialized with the
     * specified text and columns.  A default model is created.
     *
     * @param text the text to be displayed, or <code>null</code>
     * @param columns  the number of columns to use to calculate
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
	public JSensitiveNumericField(String arg0, int arg1) {
		super(arg0, arg1);
		addFocusListener(this);
		validateText();
	}
	
	 /**
     * Constructs a new <code>JSensitiveNumericField</code> initialized with the
     * specified text. A default model is created and the number of
     * columns is 0.
     *
     * @param text the text to be displayed, or <code>null</code>
     */
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
