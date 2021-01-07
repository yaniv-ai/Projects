package main;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainWindow {
	// הגדרת פונט אחיד לכל המסכים
	// עבור כל שדה או כפתור, אני אשתמש בפונט הבא וככך נשמור על אחידות
	public static final Font FONT = new Font("Dialog", Font.PLAIN, 18);

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// הגדרת מסגרת כללית. היא תכיל את המסכים
					final JFrame frame = new JFrame();

					// השורה הבאה מחוללת מופע של המסך הראשי
					MasachRashi masachRashi = new MasachRashi();

					// את המסך הראשי נשים בתוך המסגרת
					frame.getContentPane().add(masachRashi);

					// הפקודה הבאה מורה למסגרת לסגור את החלון ברגע שלוחצים על
					// האיקס בפינה העליונה
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					// קביעת גודל עדיף וגודל מינימלי של המסגרת
					frame.setPreferredSize(new Dimension(500, 500));
					frame.setMinimumSize(new Dimension(800, 500));

					// שתי הפקודות הבאות מורות למסגרת לסדר את הרכיבים, ולהציג את
					// המסך
					frame.pack();
					frame.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
