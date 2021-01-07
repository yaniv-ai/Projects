package masachim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import enums.Options;

import templates.MyPanel;

import main.MainWindow;

public class MasachKnisa extends MyPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField userName;
	private final JPasswordField password;
	final JButton knisatMishtamesh;
	final JButton knisatMenahel;

	public MasachKnisa() {
		// הגדרת השדות למסך
		JLabel nameLabel = new JLabel("        שם: ", SwingConstants.RIGHT);
		JLabel passwordLabel = new JLabel("        סיסמא: ",
				SwingConstants.RIGHT);
		userName = new JTextField(10);
		password = new JPasswordField(10);

		// הגדרת פונטים קבועים
		nameLabel.setFont(MainWindow.FONT);
		passwordLabel.setFont(MainWindow.FONT);
		userName.setFont(MainWindow.FONT);
		password.setFont(MainWindow.FONT);

		// הגדרת לחצן הכניסה של המשתמשים
		knisatMishtamesh = new JButton(Options.knisatMishtamesh.getDescription());
		knisatMishtamesh.setActionCommand(Options.knisatMishtamesh.name());
		knisatMishtamesh.setFont(MainWindow.FONT);

		// הגדרת לחצן הכניסה של המנהלים
		knisatMenahel = new JButton(Options.knisatMenahel.getDescription());
		knisatMenahel.setActionCommand(Options.knisatMenahel.name());
		knisatMenahel.setFont(MainWindow.FONT);

		// הגדרת מאזין שיודע להקשיב למקלדת
		// אם נלחץ על מקש האנטר הוא יפעיל את הכפתור
		KeyListener enterKnisa = new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
					knisatMenahel.doClick();
				}
			}
		};

		// הוספת מאזין המקלדת לשדות הטקסט
		userName.addKeyListener(enterKnisa);
		password.addKeyListener(enterKnisa);

		// בניית פאנל המשתמש
		JPanel mishtameshPanel = new JPanel(new BorderLayout());
		// הוספת הטבלה, כם שתהיה באמצע ולא תתרחב עם המסך יוגדל
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(knisatMishtamesh);
		mishtameshPanel.add(gridBagPanel, BorderLayout.CENTER);
		// הוספת שדות ריקים בצדדים בשביל להרחיב את גבולות המסגרת
		mishtameshPanel.add(new JLabel("     "), BorderLayout.WEST);
		mishtameshPanel.add(new JLabel("     "), BorderLayout.SOUTH);
		mishtameshPanel.add(new JLabel("     "), BorderLayout.EAST);
		// הכנת המסגרת
		mishtameshPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true),
				" כניסת משתמשים ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));

		// יצירת טבלה של 2 על 2 בשביל שם משתמש וסיסמא
		JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
		grid.add(userName);
		grid.add(nameLabel);
		grid.add(password);
		grid.add(passwordLabel);

		// הגדרת הפאנל של המנהלים
		// מוסיפים את החלקים בטור אנכי
		// קודם כל את הטבלה עם השדות
		// אחר כך נוסיף תווית ריקה בשביל הריווח
		// בסוף נוסיף את לחצן הכניסה של המנהלים
		JPanel adminGrid = new JPanel();
		adminGrid.setLayout(new BoxLayout(adminGrid, BoxLayout.Y_AXIS));
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(grid);
		adminGrid.add(gridBagPanel);
		adminGrid.add(new JLabel("  "));
		adminGrid.add(new JLabel("  "));
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(knisatMenahel);
		adminGrid.add(gridBagPanel);
		JPanel adminPanel = new JPanel(new BorderLayout());
		adminPanel.add(adminGrid, BorderLayout.CENTER);
		adminPanel.add(new JLabel("  "), BorderLayout.NORTH);
		adminPanel.add(new JLabel("     "), BorderLayout.WEST);
		adminPanel.add(new JLabel("     "), BorderLayout.SOUTH);
		adminPanel.add(new JLabel("  "), BorderLayout.EAST);
		// הכנת מסגרת
		adminPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.lightGray, 2, true),
				" כניסת מנהלים ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));

		// לבסוף נאחד את הפאנל של המשתמשים עם הפאנל של המנהלים למסך אחד
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(adminPanel);
		add(gridBagPanel);

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(mishtameshPanel);
		add(gridBagPanel);
	}

	public String getUserName() {
		// אם קיים משהו בתיבת הטקטס, נחזיר את המלל אחרת נחזיר כלום
		return (userName == null) ? null : userName.getText();
	}

	public char[] getPassword() {
		// אם קיים משהו בתיבת הסיסמה, נחזיר את המלל אחרת נחזיר כלום
		return (password == null) ? null : password.getPassword();
	}

	@Override
	public synchronized void addActionListener(ActionListener l) {
		super.addActionListener(l);

		// הוספת "מאזין" ללחצן כדי שנוכל לעשות משהו כשהוא יילחץ
		if (knisatMenahel != null) {
			knisatMenahel.addActionListener(l);
		}
		if (knisatMishtamesh != null) {
			knisatMishtamesh.addActionListener(l);
		}
	}

}
