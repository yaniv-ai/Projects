// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.text.Document;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class JSensitiveTextField extends JTextField implements FocusListener {
	private static final long serialVersionUID = 450105722217399099L;
	private String wouldBeText;
	protected int lengthLimit;
	private static final String KEY_TYPED;
	protected int deletedKeys;

	static {
		KEY_TYPED = new String("KEY_TYPED");
	}

	public JSensitiveTextField() {
		this.wouldBeText = "";
		this.lengthLimit = 0;
		this.deletedKeys = 0;
		this.addFocusListener(this);
	}

	public JSensitiveTextField(final Document arg0, final String arg1, final int arg2) {
		super(arg0, arg1, arg2);
		this.wouldBeText = "";
		this.lengthLimit = 0;
		this.deletedKeys = 0;
		this.validateText();
		this.addFocusListener(this);
	}

	public JSensitiveTextField(final int arg0) {
		super(arg0);
		this.wouldBeText = "";
		this.lengthLimit = 0;
		this.deletedKeys = 0;
		this.addFocusListener(this);
	}

	public JSensitiveTextField(final String arg0, final int arg1) {
		super(arg0, arg1);
		this.wouldBeText = "";
		this.lengthLimit = 0;
		this.deletedKeys = 0;
		this.addFocusListener(this);
		this.validateText();
	}

	public JSensitiveTextField(final String arg0) {
		super(arg0);
		this.wouldBeText = "";
		this.lengthLimit = 0;
		this.deletedKeys = 0;
		this.addFocusListener(this);
		this.validateText();
	}

	@Override
	public void postActionEvent() {
		super.postActionEvent();
	}

	public void processKeyEvent(final KeyEvent ev) {
		super.processKeyEvent(ev);
		if (ev.paramString().startsWith(JSensitiveTextField.KEY_TYPED)) {
			int caretPosition = this.getCaretPosition();
			this.deletedKeys = 0;
			this.validateText();
			if (!this.wouldBeText.equals(this.getText())) {
				this.wouldBeText = this.getText();
				final ActionListener[] al = this.getActionListeners();
				ActionListener[] array;
				for (int length = (array = al).length, i = 0; i < length; ++i) {
					final ActionListener a = array[i];
					a.actionPerformed(new ActionEvent(ev.getSource(), ev.getID(), "textChanged"));
				}
			}
			caretPosition -= this.deletedKeys;
			if (this.getText() == null) {
				caretPosition = 0;
			} else if (this.getText().length() < caretPosition) {
				caretPosition = this.getText().length();
			} else if (caretPosition < 0) {
				caretPosition = 0;
			}
			this.setCaretPosition(caretPosition);
		}
	}

	protected void validateText() {
		if (this.lengthLimit > 0) {
			this.setText(this.limitize(this.getText()));
		}
	}

	protected String limitize(final String text) {
		if (text == null || text.length() <= this.lengthLimit) {
			return text;
		}
		this.deletedKeys += text.length() - this.lengthLimit;
		return text.substring(0, this.lengthLimit);
	}

	public int getLengthLimit() {
		return this.lengthLimit;
	}

	public void setLengthLimit(final int lengthLimit) {
		this.lengthLimit = lengthLimit;
	}

	@Override
	public void focusGained(final FocusEvent e) {
		if (this.getText() != null && this.getText().length() > 0) {
			this.select(0, this.getText().length());
		}
	}

	@Override
	public void focusLost(final FocusEvent e) {
		this.lastTextCheck();
	}

	protected void lastTextCheck() {
	}
}
