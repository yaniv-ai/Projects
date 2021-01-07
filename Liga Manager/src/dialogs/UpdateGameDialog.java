package dialogs;

import helpers.JSensitiveIntField;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

import javax.swing.*;

import liga.Liga;
import main.MasachRashi;

import vo.GameListVO;

public class UpdateGameDialog {
	private JDialog d;
	private GameListVO game = null;
	private GameListVO tmpGame = null;
	private JSensitiveIntField homeGoals;
	private JSensitiveIntField visitorsGoal;
	private JLabel dateLabel;

	public UpdateGameDialog(Integer gameID, final Integer actionManagerId)
			throws Exception {
		tmpGame = Liga.getActionManager(actionManagerId).getFullGame(gameID);
		if (tmpGame.getGameDate() == null) {
			tmpGame.setGameDate(Calendar.getInstance());
		}
		final JPanel center = new JPanel(new GridLayout(4, 2, 10, 10));
		homeGoals = new JSensitiveIntField(3);
		visitorsGoal = new JSensitiveIntField(3);
		homeGoals.setFont(MasachRashi.FONT);
		visitorsGoal.setFont(MasachRashi.FONT);
		Integer hg = tmpGame.getMekomitNekudot();
		Integer vg = tmpGame.getYerivaNekudot();
		if (hg != null && hg > 0) {
			homeGoals.setText(hg.toString());
		}
		if (vg != null && vg > 0) {
			visitorsGoal.setText(vg.toString());
		}
		JButton chooseDate = new JButton("בחר תאריך");
		chooseDate.setFont(MasachRashi.FONT);
		chooseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				tmpGame.setGameDate(new DatePicker(center).getPickedDate());
				String calString;
				try {
					calString = Liga.calendarToString(tmpGame.getGameDate());
				} catch (Exception e) {
					calString = null;
				}
				dateLabel.setText(calString);
			}
		});
		JLabel homeLabel = new JLabel(tmpGame.getMekomitShem());
		homeLabel.setFont(MasachRashi.FONT);
		JLabel visitorsLabel = new JLabel(tmpGame.getYerivaShem());
		String calString;
		try {
			calString = Liga.calendarToString(tmpGame.getGameDate());
		} catch (Exception e) {
			calString = null;
		}
		visitorsLabel.setFont(MasachRashi.FONT);
		dateLabel = new JLabel(calString);
		dateLabel.setFont(MasachRashi.FONT);

		d = new JDialog();
		d.setModal(true);
		d.setResizable(false);
		JButton idkun = new JButton("עדכון");
		idkun.setFont(MasachRashi.FONT);
		JButton bitul = new JButton("ביטול");
		bitul.setFont(MasachRashi.FONT);
		idkun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tmpGame != null) {
					int hg = 0, vg = 0;
					try {
						hg = Integer.parseInt(homeGoals.getText());
					} catch (Exception e1) {

					}
					try {
						vg = Integer.parseInt(visitorsGoal.getText());
					} catch (Exception e1) {

					}
					tmpGame.setMekomitNekudot(hg);
					tmpGame.setYerivaNekudot(vg);
					game = tmpGame;
					d.dispose();
				}
			}
		});
		bitul.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.dispose();
			}
		});
		center.add(homeGoals);
		center.add(homeLabel);
		center.add(visitorsGoal);
		center.add(visitorsLabel);
		center.add(dateLabel);
		center.add(chooseDate);
		center.add(bitul);
		center.add(idkun);
		d.add(center, BorderLayout.CENTER);
		d.pack();
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		int x = rect.x;
		int y = rect.y;
		d.setLocation(x + 430, y + 200);
		d.setVisible(true);
	}

	public GameListVO getGame() {
		return game;
	}

}