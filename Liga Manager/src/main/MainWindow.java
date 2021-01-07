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
	// ����� ���� ���� ��� ������
	// ���� �� ��� �� �����, ��� ����� ����� ��� ���� ����� �� ������
	// public static final Font FONT = new Font("Dialog", Font.PLAIN, 18);

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// ����� ����� �����. ��� ���� �� ������
					final JFrame frame = new JFrame();

					// ����� ��� ���� �����
					Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());

					// ������ ���� ���� ������ ����� �� ����� ���� ������� ��
					// ����� ����� �������
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					// ��� ������� ����� ����� ������ ���� �� �������, ������ ��
					// ����
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

					// ����� ���� ������ ���� �� ���� �����
					MasachRashi masachRashi = new MasachRashi(
							new Dimension(frame.getWidth(), frame.getHeight() - scnMax.bottom));

					// �� ���� ����� ���� ���� ������
					frame.getContentPane().add(masachRashi);
					frame.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
