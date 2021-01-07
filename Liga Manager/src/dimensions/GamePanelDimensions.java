package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class GamePanelDimensions {

	private final Dimension koteretDimension;
	private final Dimension machzorDimension;
	private final Dimension ipusDateDimension;
	private final Dimension chooseDateDimension;
	private final Dimension gameDateLabelDimension;
	private final Dimension homeGroupNameDimension;
	private final Dimension visitorsGroupNameDimension;
	private final Dimension homeScoreDimension;
	private final Dimension visitorsScoreDimension;
	private final Dimension homeGroupTableDimension;
	private final Dimension visitorsGroupTableDimension;
	private final Dimension hosefButtonDimension;
	private final Dimension ipusButtonDimension;
	private final Dimension screenDimension;

	private final Dimension notPlayedButtonDimension;
	private final Dimension playedButtonDimension;
	private final Dimension simunHakolButtonDimension;
	private final Dimension mechakButtonDimension;
	private final Dimension gameTableDimension;

	public GamePanelDimensions(Dimension screenDimension) {
		this.screenDimension = screenDimension;
		int adderPanelWidth = MasachRashi.TEXT_FIELD_DIMENSION.width * 2;
		int gameTableWidth = screenDimension.width - adderPanelWidth;

		adderPanelWidth -= MasachRashi.WIDTH_INSET;
		gameTableWidth -= 2 * MasachRashi.WIDTH_INSET;

		int groupTableHeight = screenDimension.height - 5
				* MasachRashi.TEXT_FIELD_DIMENSION.height - 3
				* MasachRashi.BUTTON_DIMENSION.height;
		int gameTableHeight = screenDimension.height - 2
				* MasachRashi.TEXT_FIELD_DIMENSION.height;

		int gameAdderFieldWidth = adderPanelWidth / 2;
		int tablePanelButtonsWidth = gameTableWidth / 4;

		koteretDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);

		//
		machzorDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		ipusDateDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.BUTTON_DIMENSION.height);
		chooseDateDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.BUTTON_DIMENSION.height);
		gameDateLabelDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		homeGroupNameDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		visitorsGroupNameDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		homeScoreDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		visitorsScoreDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		homeGroupTableDimension = new Dimension(gameAdderFieldWidth,
				groupTableHeight - MasachRashi.HEIGHT_INSET);
		visitorsGroupTableDimension = new Dimension(gameAdderFieldWidth,
				groupTableHeight - MasachRashi.HEIGHT_INSET);
		hosefButtonDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.BUTTON_DIMENSION.height);
		ipusButtonDimension = new Dimension(gameAdderFieldWidth,
				MasachRashi.BUTTON_DIMENSION.height);

		// מילוי הצד של פאנל הטבלה
		notPlayedButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		playedButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		simunHakolButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);
		mechakButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);

		gameTableDimension = new Dimension(gameTableWidth
				- MasachRashi.WIDTH_INSET, gameTableHeight
				- MasachRashi.HEIGHT_INSET);
	}

	public Dimension getKoteretDimension() {
		return koteretDimension;
	}

	public Dimension getMachzorDimension() {
		return machzorDimension;
	}

	public Dimension getIpusDateDimension() {
		return ipusDateDimension;
	}

	public Dimension getChooseDateDimension() {
		return chooseDateDimension;
	}

	public Dimension getGameDateLabelDimension() {
		return gameDateLabelDimension;
	}

	public Dimension getHomeGroupNameDimension() {
		return homeGroupNameDimension;
	}

	public Dimension getVisitorsGroupNameDimension() {
		return visitorsGroupNameDimension;
	}

	public Dimension getHomeScoreDimension() {
		return homeScoreDimension;
	}

	public Dimension getVisitorsScoreDimension() {
		return visitorsScoreDimension;
	}

	public Dimension getHomeGroupTableDimension() {
		return homeGroupTableDimension;
	}

	public Dimension getVisitorsGroupTableDimension() {
		return visitorsGroupTableDimension;
	}

	public Dimension getHosefButtonDimension() {
		return hosefButtonDimension;
	}

	public Dimension getIpusButtonDimension() {
		return ipusButtonDimension;
	}

	public Dimension getScreenDimension() {
		return screenDimension;
	}

	public Dimension getNotPlayedButtonDimension() {
		return notPlayedButtonDimension;
	}

	public Dimension getPlayedButtonDimension() {
		return playedButtonDimension;
	}

	public Dimension getSimunHakolButtonDimension() {
		return simunHakolButtonDimension;
	}

	public Dimension getMechakButtonDimension() {
		return mechakButtonDimension;
	}

	public Dimension getGameTableDimension() {
		return gameTableDimension;
	}

}
