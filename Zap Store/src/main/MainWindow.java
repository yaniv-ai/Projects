package main;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainWindow {
	// ����� ���� ���� ��� ������
	// ���� �� ��� �� �����, ��� ����� ����� ��� ���� ����� �� ������
	public static final Font FONT = new Font("Dialog", Font.PLAIN, 18);

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// ����� ����� �����. ��� ���� �� ������
					final JFrame frame = new JFrame();

					// ����� ���� ������ ���� �� ���� �����
					MasachRashi masachRashi = new MasachRashi();

					// �� ���� ����� ���� ���� ������
					frame.getContentPane().add(masachRashi);

					// ������ ���� ���� ������ ����� �� ����� ���� ������� ��
					// ����� ����� �������
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					// ����� ���� ���� ����� ������� �� ������
					frame.setPreferredSize(new Dimension(500, 500));
					frame.setMinimumSize(new Dimension(800, 500));

					// ��� ������� ����� ����� ������ ���� �� �������, ������ ��
					// ����
					frame.pack();
					frame.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
