package helpers;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import vo.GameVO;

public class GameButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8606282470569107819L;
	private GameVO game;

	public GameButton() {
		super();
	}

	public GameButton(Action a) {
		super(a);
	}

	public GameButton(Icon icon) {
		super(icon);
	}

	public GameButton(String text, Icon icon) {
		super(text, icon);
	}

	public GameButton(String text) {
		super(text);
	}

	public GameVO getGame() {
		return game;
	}

	public void setGame(GameVO game) {
		this.game = game;
	}

}
