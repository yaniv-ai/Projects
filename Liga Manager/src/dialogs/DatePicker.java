package dialogs;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

import javax.swing.*;

import main.MasachRashi;

public class DatePicker {
	private int month = java.util.Calendar.getInstance().get(
			java.util.Calendar.MONTH);
	private int year = java.util.Calendar.getInstance().get(
			java.util.Calendar.YEAR);;
	private JLabel l = new JLabel("", JLabel.CENTER);
	private String day = "";
	private JDialog d;
	private JButton[] button = new JButton[49];
	private boolean dateChosen = false;

	public DatePicker(JComponent parent) {
		d = new JDialog();
		d.setModal(true);
		d.setResizable(false);
		String[] header = { "א", "ב", "ג", "ד", "ה", "ו", "ש" };
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		p1.setPreferredSize(new Dimension(350, 300));

		for (int x = 0; x < button.length; x++) {
			final int selection = x;
			button[x] = new JButton();
			button[x].setFont(MasachRashi.FONT);
			button[x].setFocusPainted(false);
			button[x].setBackground(Color.white);
			if (x > 6) {
				if (((x + 1) % 7) == 0) {
					button[x].setBackground(Color.CYAN);
				}
				button[x].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						day = button[selection].getActionCommand();
						dateChosen = true;
						d.dispose();
					}
				});
			}
			if (x < 7) {
				button[x].setText(header[x]);
				button[x].setForeground(Color.blue);
				button[x].setBackground(Color.pink);
			}

			p1.add(button[x]);
		}
		JPanel p2 = new JPanel(new GridLayout(1, 3));
		JButton previous = new JButton("הקודם >>");
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				month--;
				displayDate();
			}
		});
		p2.add(previous);
		p2.add(l);
		JButton next = new JButton("<< הבא");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				month++;
				displayDate();
			}
		});
		p2.add(next);
		d.add(p1, BorderLayout.CENTER);
		d.add(p2, BorderLayout.SOUTH);
		d.pack();
		d.setLocationRelativeTo(parent);
		displayDate();
		d.setVisible(true);
	}

	private void displayDate() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"MMMM yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, 1);
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		int firstPlaceOfDayOfTheMonth = 6 + dayOfWeek;
		int lastPlaceOfDayOfTheMonth = 6 + dayOfWeek + daysInMonth;
		int dayInMonth = 1;
		for (int x = 7; x < button.length; x++) {
			if (x >= firstPlaceOfDayOfTheMonth && x < lastPlaceOfDayOfTheMonth) {
				button[x].setText("" + dayInMonth);
				button[x].setEnabled(true);
				button[x].setBackground(Color.white);
				dayInMonth++;
			} else {
				button[x].setText(null);
				button[x].setEnabled(false);
				button[x].setBackground(Color.lightGray);
			}
		}
		l.setText(sdf.format(cal.getTime()));
		d.setTitle("Date Picker");
	}

	public Calendar getPickedDate() {

		Calendar cal = null;
		if (dateChosen) {
			try {
				cal = java.util.Calendar.getInstance();
				cal.set(year, month, Integer.parseInt(day));
			} catch (Exception e) {
				cal = null;
			}
		}
		return cal;
	}
}