package main;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainWindow {
	// הגדרת פונט אחיד לכל המסכים
	// עבור כל שדה או כפתור, אני אשתמש בפונט הבא וככך נשמור על אחידות
	// public static final Font FONT = new Font("Dialog", Font.PLAIN, 18);

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// הגדרת מסגרת כללית. היא תכיל את המסכים
					final JFrame frame = new JFrame();

					// חישוב שטח המסך המוצג
					Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());

					// הפקודה הבאה מורה למסגרת לסגור את החלון ברגע שלוחצים על
					// האיקס בפינה העליונה
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					// שתי הפקודות הבאות מורות למסגרת לסדר את הרכיבים, ולהציג את
					// המסך
					frame.pack();
					final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

					frame.setMaximizedBounds(env.getMaximumWindowBounds());
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.addComponentListener(new ComponentAdapter() {
						public void componentResized(ComponentEvent e) {
							frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
						}
					});

					// השורה הבאה מחוללת מופע של המסך הראשי
					MasachRashi masachRashi = new MasachRashi(
							new Dimension(frame.getWidth(), frame.getHeight() - scnMax.bottom));

					// את המסך הראשי נשים בתוך המסגרת
					frame.getContentPane().add(masachRashi);
					frame.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
