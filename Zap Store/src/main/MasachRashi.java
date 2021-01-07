package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import masachim.MasachHashvaatMechirim;
import masachim.MasachHosafatMenahel;
import masachim.MasachHosafatMutzar;
import masachim.MasachKnisa;
import masachim.MasachTafrit;

import databaseManager.DBQueries;

import enums.Options;
import enums.Ranks;

import templates.MyPanel;

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

	// מסכים
	private MasachKnisa masachKnisa;
	private MasachTafrit masachTafrit;
	private MasachHosafatMenahel masachHosafatMenahel;
	private MasachHosafatMutzar masachHosafatMutzar;
	private MasachHashvaatMechirim hashvaatMechirim;

	// יישות ששולפת נתונים מהטבלאות
	private DBQueries dbq;

	// דרגת המשתמש
	private Ranks rank = null;

	// בעת הקמת מופע של האובייקט, נגדיר רכיב שעובד מול הטבלאות
	// ובגלל שזה השימוש הראשון, נשלח אותו להראות את מסך הכניסה
	public MasachRashi() {
		dbq = new DBQueries();
		setLayout(new BorderLayout());
		masachKnisa();
	}

	/*
	 * כשעוברים ממסך למסך, סדר הפעולות זהה קודם כל מנקים את המסך הראשי מרכיבים,
	 * אחר כך מגדירים מופע חדש של המסך המבוקש לאחר מכן שולחים מאזין כדי שהמסך
	 * הראשי יוכל לדעת מתי לעבור למסך אחר ממקמים את המסך החדש במרכז המסך הראשי
	 * ובסופו של דבר מפעילים מתודה שתורה למחשב להראות את השינויים בתצוגה
	 */

	private void masachKnisa() {
		this.removeAll();
		masachKnisa = new MasachKnisa();
		masachKnisa.addActionListener(this);
		add(masachKnisa, BorderLayout.CENTER);
		updateUI();
	}

	private void masachTafrit() {
		removeAll();
		masachTafrit = new MasachTafrit(rank); // בשביל לחולל מסף תפריט צריך
												// לדעת מה דרגת המשתמש
		masachTafrit.addActionListener(this);
		add(masachTafrit, BorderLayout.CENTER);
		updateUI();
	}

	private void masachHosafatManager() {
		try {
			removeAll();
			masachHosafatMenahel = new MasachHosafatMenahel();
			masachHosafatMenahel.addActionListener(this);
			add(masachHosafatMenahel, BorderLayout.CENTER);
			updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"עקב תקלה טכנית לא ניתן להמשיך בתכנית", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void masachHosafatMutzar() {
		try {
			removeAll();
			masachHosafatMutzar = new MasachHosafatMutzar();
			masachHosafatMutzar.addActionListener(this);
			add(masachHosafatMutzar, BorderLayout.CENTER);
			updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"עקב תקלה טכנית לא ניתן להמשיך בתכנית", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void masachHashvaatMechirim() {
		try {
			removeAll();
			hashvaatMechirim = new MasachHashvaatMechirim();
			hashvaatMechirim.addActionListener(this);
			add(hashvaatMechirim, BorderLayout.CENTER);
			updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"עקב תקלה טכנית לא ניתן להמשיך בתכנית", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void login() {
		String message = null;
		Ranks rank = null;
		this.rank = null;
		try {
			// אחזור שם משתמש ממסך הכניסה
			String username = masachKnisa.getUserName();

			// אחזור הסיסמא ממסך הכניסה
			String password = new String(masachKnisa.getPassword());

			// בדיקת שם משתמש וסיסמא ושליפת דרגת המשתמש
			rank = dbq.login(username, password);

			// אם לא חזרה הדרגה זה אומר שאין דרגה או ששם המשתמש והסיסמה לא
			// תקינים
			if (rank != null) {
				// אם חזרה דרגה, נשמוא אותה ונעבור למסך התפריט
				this.rank = rank;
			} else {
				message = "מזהה לא קיים או סיסמא שגוייה";
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}

		// במידה ויש הודעת שגיאה, נציג למשתמש
		if (message != null) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Options option;
		try {
			option = Options.valueOf(e.getActionCommand());
		} catch (Exception e1) {
			option = Options.noOption;
		}
		switch (option) {
		case knisatMishtamesh:
			rank = Ranks.user;
			masachTafrit();
			break;
		case knisatMenahel:
			login();
			if (rank != null) {
				masachTafrit();
			}
			break;
		case hashvaatMechirim:
			masachHashvaatMechirim();
			break;
		case hosafatMutzar:
			masachHosafatMutzar();
			break;
		case hosafatManager:
			masachHosafatManager();
			break;
		case tafrit:
			masachTafrit();
			break;
		case knisa:
			masachKnisa();
			break;
		default:
			break;
		}
	}
}
