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
		// ����� ����� ����
		JLabel nameLabel = new JLabel("        ��: ", SwingConstants.RIGHT);
		JLabel passwordLabel = new JLabel("        �����: ",
				SwingConstants.RIGHT);
		userName = new JTextField(10);
		password = new JPasswordField(10);

		// ����� ������ ������
		nameLabel.setFont(MainWindow.FONT);
		passwordLabel.setFont(MainWindow.FONT);
		userName.setFont(MainWindow.FONT);
		password.setFont(MainWindow.FONT);

		// ����� ���� ������ �� ��������
		knisatMishtamesh = new JButton(Options.knisatMishtamesh.getDescription());
		knisatMishtamesh.setActionCommand(Options.knisatMishtamesh.name());
		knisatMishtamesh.setFont(MainWindow.FONT);

		// ����� ���� ������ �� �������
		knisatMenahel = new JButton(Options.knisatMenahel.getDescription());
		knisatMenahel.setActionCommand(Options.knisatMenahel.name());
		knisatMenahel.setFont(MainWindow.FONT);

		// ����� ����� ����� ������ ������
		// �� ���� �� ��� ����� ��� ����� �� ������
		KeyListener enterKnisa = new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
					knisatMenahel.doClick();
				}
			}
		};

		// ����� ����� ������ ����� �����
		userName.addKeyListener(enterKnisa);
		password.addKeyListener(enterKnisa);

		// ����� ���� ������
		JPanel mishtameshPanel = new JPanel(new BorderLayout());
		// ����� �����, �� ����� ����� ��� ����� �� ���� �����
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(knisatMishtamesh);
		mishtameshPanel.add(gridBagPanel, BorderLayout.CENTER);
		// ����� ���� ����� ������ ����� ������ �� ������ ������
		mishtameshPanel.add(new JLabel("     "), BorderLayout.WEST);
		mishtameshPanel.add(new JLabel("     "), BorderLayout.SOUTH);
		mishtameshPanel.add(new JLabel("     "), BorderLayout.EAST);
		// ���� ������
		mishtameshPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true),
				" ����� ������� ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));

		// ����� ���� �� 2 �� 2 ����� �� ����� ������
		JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
		grid.add(userName);
		grid.add(nameLabel);
		grid.add(password);
		grid.add(passwordLabel);

		// ����� ����� �� �������
		// ������� �� ������ ���� ����
		// ���� �� �� ����� �� �����
		// ��� �� ����� ����� ���� ����� ������
		// ���� ����� �� ���� ������ �� �������
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
		// ���� �����
		adminPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.lightGray, 2, true),
				" ����� ������ ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));

		// ����� ���� �� ����� �� �������� �� ����� �� ������� ���� ���
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(adminPanel);
		add(gridBagPanel);

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(mishtameshPanel);
		add(gridBagPanel);
	}

	public String getUserName() {
		// �� ���� ���� ����� �����, ����� �� ���� ���� ����� ����
		return (userName == null) ? null : userName.getText();
	}

	public char[] getPassword() {
		// �� ���� ���� ����� ������, ����� �� ���� ���� ����� ����
		return (password == null) ? null : password.getPassword();
	}

	@Override
	public synchronized void addActionListener(ActionListener l) {
		super.addActionListener(l);

		// ����� "�����" ����� ��� ����� ����� ���� ����� �����
		if (knisatMenahel != null) {
			knisatMenahel.addActionListener(l);
		}
		if (knisatMishtamesh != null) {
			knisatMishtamesh.addActionListener(l);
		}
	}

}
