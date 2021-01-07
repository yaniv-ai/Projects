package main;

import helpers.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import liga.Liga;
import liga.LigaDB;

import enums.LigaActions;
import templates.MyPanel;
import ui.AdminPanel;
import ui.BchiratLigaPanel;
import ui.GamesPanel;
import ui.GroupsPanel;
import ui.StatsPanel;

/*
 * האובייקט הבא הינו המסך הראשי.
 * דרכו ננהל את כל המעברים בין המסכים. 
 * הוא יורש מאובייקט קיים,
 * וכמו כן אנחנו ממשים תכונות של מאזין.
 * כאשר נרצה שפעולה כלשהי תשפיע על המסך,
 * בעצם תופעל המתודה של המאזין
 */
public class MasachRashi extends MyPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Font FONT = new Font("Dialog", Font.PLAIN, 14);
	public static final Font BIGFONT = new Font("Dialog", Font.BOLD, 18);
	public static final Dimension TEXT_FIELD_DIMENSION = new Dimension(200, 50);
	public static final Dimension COMBO_BOX_DIMENSION = new Dimension(200, 70);
	public static final Dimension BUTTON_DIMENSION = new Dimension(200, 30);
	public static final int HEIGHT_INSET = 30;
	public static final int WIDTH_INSET = 50;

	public final Dimension SCREEN_DIMENSION;
	private final int buttonPanelWidth;
	private final int buttonPanelHight = 80;
	private final int miniPanelWidth;
	private final int miniPanelHight;
	private final int messagePanelWidth;
	private final int messagePanelHight = 100;
	private double messageHourRatio = 0.9;

	// הגבלות המסכים
	private GridBagConstraints buttonPanelConstraints;
	private GridBagConstraints miniPanelConstraints;
	private GridBagConstraints messagePanelConstraints;
	private GridBagConstraints messageTableConstraints;

	private static DefaultTableModel messagesTableModel;

	// פאנל לחצני המסכים העליון
	private JPanel buttonsPanel = null;

	// הפאנל הראשי עליו יוצגו שאר המסכים
	private JPanel miniScreen = null;

	// הפאנל התחתון בו יוצגו ההודעות
	private JPanel messagesPanel = null;

	// מסכים
	private GroupsPanel groupsPanel = null;
	private StatsPanel statsPanel = null;
	private GamesPanel gamesPanel = null;
	private AdminPanel adminPanel = null;
	private BchiratLigaPanel bchiratLigaPanel = null;

	private JLabel ligaLabel = null;
	private final Integer actionManagerId;

	private static Integer ligaId = null;
	private static String ligaName = null;

	public MasachRashi(Dimension screenDimension) {
		actionManagerId = Liga.addActionManager();
		SCREEN_DIMENSION = screenDimension;

		buttonPanelWidth = SCREEN_DIMENSION.width;
		miniPanelWidth = SCREEN_DIMENSION.width;
		messagePanelWidth = SCREEN_DIMENSION.width;

		miniPanelHight = SCREEN_DIMENSION.height - buttonPanelHight
				- messagePanelHight;

		calculatePanelsConstraints();

		// יצירת פאנל הלחצנים
		buttonsPanel = createButtonsPanel();

		// קיבוע גודל הפאנל הראשי
		miniScreen = createMiniScreen();

		// יצירת פאנל ההודעות
		messagesPanel = createMessagesPanel();

		add(buttonsPanel, buttonPanelConstraints);
		add(miniScreen, miniPanelConstraints);
		add(messagesPanel, messagePanelConstraints);

		// masachKnisa();
		masachBchiratLiga();

	}

	private void calculatePanelsConstraints() {
		buttonPanelConstraints = new GridBagConstraints();
		buttonPanelConstraints.gridx = 0;
		buttonPanelConstraints.gridy = 0;
		buttonPanelConstraints.ipadx = buttonPanelWidth;
		buttonPanelConstraints.ipady = buttonPanelHight;
		buttonPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonPanelConstraints.anchor = GridBagConstraints.PAGE_START;

		miniPanelConstraints = new GridBagConstraints();
		miniPanelConstraints.gridx = 0;
		miniPanelConstraints.gridy = 1;
		miniPanelConstraints.ipadx = miniPanelWidth;
		miniPanelConstraints.ipady = miniPanelHight;
		miniPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		miniPanelConstraints.anchor = GridBagConstraints.CENTER;

		messagePanelConstraints = new GridBagConstraints();
		messagePanelConstraints.gridx = 0;
		messagePanelConstraints.gridy = 2;
		messagePanelConstraints.ipadx = 0;
		messagePanelConstraints.ipady = 0;
		messagePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		messagePanelConstraints.anchor = GridBagConstraints.PAGE_END;
		messagePanelConstraints.weightx = 0.5;
		messagePanelConstraints.weighty = 0.5;

		messageTableConstraints = new GridBagConstraints();
		messageTableConstraints.gridx = 0;
		messageTableConstraints.gridy = 0;
		messageTableConstraints.ipadx = messagePanelWidth;
		messageTableConstraints.ipady = messagePanelHight;
		messageTableConstraints.fill = GridBagConstraints.BOTH;
		messageTableConstraints.anchor = GridBagConstraints.CENTER;
		messageTableConstraints.weightx = 0.5;
		messageTableConstraints.weighty = 0.5;

	}

	private JPanel createMiniScreen() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(miniPanelWidth, miniPanelHight));
		panel.setBorder(BorderFactory.createEtchedBorder());
		return panel;
	}

	private JPanel createMessagesPanel() {
		// קביעת כותרת הגרף
		Object[] columnNames = { "תוכן", "שעה" };
		messagesTableModel = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(messagesTableModel);

		// גבולות הגרף
		table.setPreferredScrollableViewportSize(new Dimension(
				messagePanelWidth, messagePanelHight));
		table.getTableHeader().setFont(MasachRashi.FONT);

		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.setFont(MasachRashi.FONT);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(20);
		table.setCellSelectionEnabled(false);

		// קביעת היחס בין עמודת ההודעה לעמודת השעה
		int messagesWidth = (int) (messagePanelWidth * messageHourRatio);
		int hourWidth = messagePanelWidth - messagesWidth;

		// קביעת גודת העמודות לפי היחס והצמדה לימין
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(0).setMinWidth(messagesWidth);
		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
		midRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(1).setMinWidth(hourWidth);
		table.getColumnModel().getColumn(1).setCellRenderer(midRenderer);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.add(new JScrollPane(table), messageTableConstraints);

		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.MAGENTA, 2, true),
				" סיכום פעולות אחרונות ", TitledBorder.CENTER,
				TitledBorder.CENTER, MasachRashi.FONT));

		panel.setBackground(Color.PINK);
		panel.setPreferredSize(new Dimension(messagePanelWidth,
				messagePanelHight));

		return panel;
	}

	private JPanel createButtonsPanel() {

		JPanel buttonGridPanel = new JPanel(new GridLayout(1, 4, 4, 4));
		JPanel ligaPanel = new JPanel(new BorderLayout(10, 4));
		JPanel buttonsGridPanel = new JPanel(new GridLayout(2, 1, 4, 4));

		JButton b;

		// הגדרת לחצן קבוצות
		b = new JButton(LigaActions.kvutzot.getDescription());
		b.setActionCommand(LigaActions.kvutzot.name());
		b.setFont(MasachRashi.FONT);
		b.addActionListener(this);
		buttonGridPanel.add(b);

		// הגדרת לחצן סטטיסטיקות
		b = new JButton(LigaActions.stats.getDescription());
		b.setActionCommand(LigaActions.stats.name());
		b.setFont(MasachRashi.FONT);
		b.addActionListener(this);
		buttonGridPanel.add(b);

		// הגדרת לחצן משחקים
		b = new JButton(LigaActions.mischakim.getDescription());
		b.setActionCommand(LigaActions.mischakim.name());
		b.setFont(MasachRashi.FONT);
		b.addActionListener(this);
		buttonGridPanel.add(b);

		// הגדרת לחצן ניהול
		b = new JButton(LigaActions.admin.getDescription());
		b.setActionCommand(LigaActions.admin.name());
		b.setFont(MasachRashi.FONT);
		b.addActionListener(this);
		b.setBackground(Color.PINK);
		buttonGridPanel.add(b);

		// הגדרת כותרת ליגה
		ligaLabel = new JLabel(ligaName, SwingConstants.CENTER);
		ligaLabel.setFont(MasachRashi.BIGFONT);
		ligaLabel.setBackground(Color.LIGHT_GRAY);
		ligaLabel.setOpaque(true);
		ligaPanel.add(ligaLabel, BorderLayout.CENTER);

		// הגדרת לחצן בחירת ליגה
		b = new JButton(LigaActions.liga.getDescription());
		b.setActionCommand(LigaActions.liga.name());
		b.setFont(MasachRashi.FONT);
		b.addActionListener(this);
		ligaPanel.add(b, BorderLayout.EAST);

		buttonGridPanel.setBackground(Color.GREEN);
		ligaPanel.setBackground(Color.GREEN);

		buttonsGridPanel.add(buttonGridPanel);
		buttonsGridPanel.add(ligaPanel);

		buttonsGridPanel.setBackground(Color.GREEN);

		buttonsGridPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK, 4, true),
				" מקשי מסכים ", TitledBorder.CENTER, TitledBorder.CENTER,
				MasachRashi.FONT));

		buttonsGridPanel.setPreferredSize(new Dimension(buttonPanelWidth,
				buttonPanelHight));

		return buttonsGridPanel;
	}

	public static void publishMessage(String message) {
		if (message != null) {
			messagesTableModel.insertRow(0,
					new Object[] { message, Utils.calanderToTime() });
		}
	}

	// /*
	// * כשעוברים ממסך למסך, סדר הפעולות זהה קודם כל מנקים את המסך הראשי
	// מרכיבים,
	// * אחר כך מגדירים מופע חדש של המסך המבוקש לאחר מכן שולחים מאזין כדי שהמסך
	// * הראשי יוכל לדעת מתי לעבור למסך אחר ממקמים את המסך החדש במרכז המסך הראשי
	// * ובסופו של דבר מפעילים מתודה שתורה למחשב להראות את השינויים בתצוגה
	// */
	//

	private void masachKvutzot() {
		miniScreen.removeAll();
		groupsPanel = new GroupsPanel(new Dimension(miniPanelWidth,
				miniPanelHight), actionManagerId);
		groupsPanel.addActionListener(this);
		miniScreen.add(groupsPanel);
		groupsPanel.showPanel();
		updateUI();
	}

	private void masachStatisticot() {
		miniScreen.removeAll();
		statsPanel = new StatsPanel(new Dimension(miniPanelWidth,
				miniPanelHight), actionManagerId);
		statsPanel.addActionListener(this);
		miniScreen.add(statsPanel);
		statsPanel.showPanel();
		updateUI();
	}

	private void masachMischakim() {
		miniScreen.removeAll();
		gamesPanel = new GamesPanel(new Dimension(miniPanelWidth,
				miniPanelHight), actionManagerId);
		gamesPanel.addActionListener(this);
		miniScreen.add(gamesPanel);
		gamesPanel.showPanel();
		updateUI();
	}

	private void masachNihul() {
		miniScreen.removeAll();
		adminPanel = new AdminPanel(new Dimension(miniPanelWidth,
				miniPanelHight), actionManagerId, this);
		adminPanel.addActionListener(this);
		miniScreen.add(adminPanel);
		updateUI();
	}

	private void masachBchiratLiga() {
		miniScreen.removeAll();
		bchiratLigaPanel = new BchiratLigaPanel(new Dimension(miniPanelWidth,
				miniPanelHight), actionManagerId, this);
		bchiratLigaPanel.addActionListener(this);
		miniScreen.add(bchiratLigaPanel);
		bchiratLigaPanel.showPanel();
		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LigaActions action;
		try {
			action = LigaActions.valueOf(e.getActionCommand());
		} catch (Exception e1) {
			action = LigaActions.noAction;
		}
		publishMessage(action.getDescription());
		updateUI();

		if (ligaId != null) {
			switch (action) {
			case kvutzot:
				masachKvutzot();
				break;
			case mischakim:
				masachMischakim();
				break;
			case stats:
				masachStatisticot();
				break;
			case admin:
				masachNihul();
				break;
			case liga:
				masachBchiratLiga();
				break;
			default:
				break;
			}
		} else {
			switch (action) {
			case admin:
				masachNihul();
				break;
			case liga:
				masachBchiratLiga();
				break;
			default:
				break;
			}
		}
	}

	public void setNewLiga(Integer ligaId, String ligaName) {
		try {
			if (ligaId == null) {
				ligaName = null;
			}
			MasachRashi.ligaId = ligaId;
			MasachRashi.ligaName = ligaName;

			LigaDB.setLigaId(ligaId);
			LigaDB.kraTavlaotLiga();

			ligaLabel.setText(ligaName);
			if (ligaId == null) {
				masachBchiratLiga();
			}
		} catch (Exception e) {
			ligaId = null;
			ligaName = null;
			String message = e.getMessage();
			if (message == null) {
				message = "תקלה בעת נסיון להחליף ליגה";
			}
			JOptionPane.showMessageDialog(MasachRashi.this, message,
					"Player addition failed", JOptionPane.ERROR_MESSAGE);
		}
		updateUI();
	}

	public static Integer getLigaId() {
		return ligaId;
	}

	public static String getLigaName() {
		return ligaName;
	}
}
