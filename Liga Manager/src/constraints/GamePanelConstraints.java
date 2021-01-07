package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GamePanelConstraints {

	public final GridBagConstraints koteretConstraints;

	private final GridBagConstraints machzorConstraints;
	private final GridBagConstraints ipusDateConstraints;
	private final GridBagConstraints chooseDateConstraints;
	private final GridBagConstraints gameDateLabelConstraints;
	private final GridBagConstraints homeGroupNameConstraints;
	private final GridBagConstraints visitorsGroupNameConstraints;
	private final GridBagConstraints homeScoreConstraints;
	private final GridBagConstraints visitorsScoreConstraints;
	private final GridBagConstraints homeGroupTableConstraints;
	private final GridBagConstraints visitorsGroupTableConstraints;
	private final GridBagConstraints hosefButtonConstraints;
	private final GridBagConstraints ipusButtonConstraints;

	public final GridBagConstraints notPlayedButtonConstraints;
	public final GridBagConstraints playedButtonConstraints;
	public final GridBagConstraints simunHakolButtonConstraints;
	public final GridBagConstraints mechakButtonConstraints;
	public final GridBagConstraints gameTableConstraints;

	public GamePanelConstraints() {
		koteretConstraints = new GridBagConstraints();
		koteretConstraints.gridx = 0;
		koteretConstraints.gridy = 0;
		koteretConstraints.gridwidth = 5;
		koteretConstraints.fill = GridBagConstraints.NONE;
		koteretConstraints.anchor = GridBagConstraints.CENTER;
		koteretConstraints.insets = new Insets(0, 10, 0, 10);

		// מילוי הגבלות הפאנל
		machzorConstraints = new GridBagConstraints();
		machzorConstraints.gridx = 0;
		machzorConstraints.gridy = 2;
		machzorConstraints.gridwidth = 2;
		machzorConstraints.fill = GridBagConstraints.HORIZONTAL;
		machzorConstraints.anchor = GridBagConstraints.CENTER;
		machzorConstraints.insets = new Insets(0, 10, 0, 10);

		chooseDateConstraints = new GridBagConstraints();
		chooseDateConstraints.gridx = 0;
		chooseDateConstraints.gridy = 3;
		chooseDateConstraints.fill = GridBagConstraints.HORIZONTAL;
		chooseDateConstraints.anchor = GridBagConstraints.CENTER;
		chooseDateConstraints.insets = new Insets(0, 10, 0, 10);

		ipusDateConstraints = new GridBagConstraints();
		ipusDateConstraints.gridx = 1;
		ipusDateConstraints.gridy = 3;
		ipusDateConstraints.fill = GridBagConstraints.HORIZONTAL;
		ipusDateConstraints.anchor = GridBagConstraints.CENTER;
		ipusDateConstraints.insets = new Insets(0, 10, 0, 10);

		gameDateLabelConstraints = new GridBagConstraints();
		gameDateLabelConstraints.gridx = 0;
		gameDateLabelConstraints.gridy = 4;
		gameDateLabelConstraints.gridwidth = 2;
		gameDateLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
		gameDateLabelConstraints.anchor = GridBagConstraints.CENTER;
		gameDateLabelConstraints.insets = new Insets(0, 10, 0, 10);

		homeGroupNameConstraints = new GridBagConstraints();
		homeGroupNameConstraints.gridx = 0;
		homeGroupNameConstraints.gridy = 5;
		homeGroupNameConstraints.fill = GridBagConstraints.HORIZONTAL;
		homeGroupNameConstraints.anchor = GridBagConstraints.CENTER;
		homeGroupNameConstraints.insets = new Insets(0, 10, 0, 10);

		visitorsGroupNameConstraints = new GridBagConstraints();
		visitorsGroupNameConstraints.gridx = 1;
		visitorsGroupNameConstraints.gridy = 5;
		visitorsGroupNameConstraints.fill = GridBagConstraints.HORIZONTAL;
		visitorsGroupNameConstraints.anchor = GridBagConstraints.CENTER;
		visitorsGroupNameConstraints.insets = new Insets(0, 10, 0, 10);

		homeScoreConstraints = new GridBagConstraints();
		homeScoreConstraints.gridx = 0;
		homeScoreConstraints.gridy = 6;
		homeScoreConstraints.fill = GridBagConstraints.HORIZONTAL;
		homeScoreConstraints.anchor = GridBagConstraints.CENTER;
		homeScoreConstraints.insets = new Insets(0, 10, 0, 10);

		visitorsScoreConstraints = new GridBagConstraints();
		visitorsScoreConstraints.gridx = 1;
		visitorsScoreConstraints.gridy = 6;
		visitorsScoreConstraints.fill = GridBagConstraints.HORIZONTAL;
		visitorsScoreConstraints.anchor = GridBagConstraints.CENTER;
		visitorsScoreConstraints.insets = new Insets(0, 10, 0, 10);

		homeGroupTableConstraints = new GridBagConstraints();
		homeGroupTableConstraints.gridx = 0;
		homeGroupTableConstraints.gridy = 7;
		homeGroupTableConstraints.fill = GridBagConstraints.BOTH;
		homeGroupTableConstraints.anchor = GridBagConstraints.CENTER;
		homeGroupTableConstraints.insets = new Insets(0, 10, 0, 10);

		visitorsGroupTableConstraints = new GridBagConstraints();
		visitorsGroupTableConstraints.gridx = 1;
		visitorsGroupTableConstraints.gridy = 7;
		visitorsGroupTableConstraints.fill = GridBagConstraints.BOTH;
		visitorsGroupTableConstraints.anchor = GridBagConstraints.CENTER;
		visitorsGroupTableConstraints.insets = new Insets(0, 10, 0, 10);

		ipusButtonConstraints = new GridBagConstraints();
		ipusButtonConstraints.gridx = 0;
		ipusButtonConstraints.gridy = 8;
		ipusButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		ipusButtonConstraints.anchor = GridBagConstraints.CENTER;
		ipusButtonConstraints.insets = new Insets(0, 10, 0, 10);

		hosefButtonConstraints = new GridBagConstraints();
		hosefButtonConstraints.gridx = 1;
		hosefButtonConstraints.gridy = 8;
		hosefButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		hosefButtonConstraints.anchor = GridBagConstraints.CENTER;
		hosefButtonConstraints.insets = new Insets(0, 10, 0, 10);

		// מילוי הגבלות מסך הטבלה
		playedButtonConstraints = new GridBagConstraints();
		playedButtonConstraints.gridx = 2;
		playedButtonConstraints.gridy = 1;
		playedButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		playedButtonConstraints.anchor = GridBagConstraints.CENTER;
		playedButtonConstraints.insets = new Insets(0, 10, 0, 10);

		notPlayedButtonConstraints = new GridBagConstraints();
		notPlayedButtonConstraints.gridx = 3;
		notPlayedButtonConstraints.gridy = 1;
		notPlayedButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		notPlayedButtonConstraints.anchor = GridBagConstraints.CENTER;
		notPlayedButtonConstraints.insets = new Insets(0, 10, 0, 10);

		simunHakolButtonConstraints = new GridBagConstraints();
		simunHakolButtonConstraints.gridx = 5;
		simunHakolButtonConstraints.gridy = 0;
		simunHakolButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		simunHakolButtonConstraints.anchor = GridBagConstraints.CENTER;
		simunHakolButtonConstraints.insets = new Insets(0, 10, 0, 10);

		mechakButtonConstraints = new GridBagConstraints();
		mechakButtonConstraints.gridx = 5;
		mechakButtonConstraints.gridy = 1;
		mechakButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		mechakButtonConstraints.anchor = GridBagConstraints.LAST_LINE_END;
		mechakButtonConstraints.insets = new Insets(0, 10, 0, 10);

		gameTableConstraints = new GridBagConstraints();
		gameTableConstraints.gridx = 2;
		gameTableConstraints.gridy = 2;
		gameTableConstraints.gridwidth = 4;
		gameTableConstraints.gridheight = 7;
		gameTableConstraints.fill = GridBagConstraints.HORIZONTAL;
		gameTableConstraints.anchor = GridBagConstraints.CENTER;

	}

	public GridBagConstraints getKoteretConstraints() {
		return koteretConstraints;
	}

	public GridBagConstraints getMachzorConstraints() {
		return machzorConstraints;
	}

	public GridBagConstraints getIpusDateConstraints() {
		return ipusDateConstraints;
	}

	public GridBagConstraints getChooseDateConstraints() {
		return chooseDateConstraints;
	}

	public GridBagConstraints getGameDateLabelConstraints() {
		return gameDateLabelConstraints;
	}

	public GridBagConstraints getHomeGroupNameConstraints() {
		return homeGroupNameConstraints;
	}

	public GridBagConstraints getVisitorsGroupNameConstraints() {
		return visitorsGroupNameConstraints;
	}

	public GridBagConstraints getHomeScoreConstraints() {
		return homeScoreConstraints;
	}

	public GridBagConstraints getVisitorsScoreConstraints() {
		return visitorsScoreConstraints;
	}

	public GridBagConstraints getHomeGroupTableConstraints() {
		return homeGroupTableConstraints;
	}

	public GridBagConstraints getVisitorsGroupTableConstraints() {
		return visitorsGroupTableConstraints;
	}

	public GridBagConstraints getHosefButtonConstraints() {
		return hosefButtonConstraints;
	}

	public GridBagConstraints getIpusButtonConstraints() {
		return ipusButtonConstraints;
	}

	public GridBagConstraints getNotPlayedButtonConstraints() {
		return notPlayedButtonConstraints;
	}

	public GridBagConstraints getPlayedButtonConstraints() {
		return playedButtonConstraints;
	}

	public GridBagConstraints getSimunHakolButtonConstraints() {
		return simunHakolButtonConstraints;
	}

	public GridBagConstraints getMechakButtonConstraints() {
		return mechakButtonConstraints;
	}

	public GridBagConstraints getGameTableConstraints() {
		return gameTableConstraints;
	}

}
