// 
// Decompiled by Procyon v0.5.36
// 

package main;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.awt.Font;

public class MainWindow {
	public static final Font FONT;

	static {
		FONT = new Font("Dialog", 0, 18);
	}

	public static void main(final String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					final JFrame frame = new JFrame();
					final MasachRashi masachRashi = new MasachRashi();
					frame.getContentPane().add(masachRashi);
					frame.setDefaultCloseOperation(3);
					frame.setResizable(false);
					frame.pack();
					frame.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
