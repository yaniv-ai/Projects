package helpers;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class IDButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8606282470569107819L;
	private Integer id;

	public IDButton() {
		super();
	}

	public IDButton(Action a) {
		super(a);
	}

	public IDButton(Icon icon) {
		super(icon);
	}

	public IDButton(String text, Icon icon) {
		super(text, icon);
	}

	public IDButton(String text) {
		super(text);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
