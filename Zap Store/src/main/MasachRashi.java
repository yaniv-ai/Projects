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
 * �������� ��� ���� ���� �����.
 * ���� ���� �� �� ������� ��� ������. 
 * ��� ���� �������� ����,
 * ���� �� ����� ����� ������ �� �����.
 * ���� ���� ������ ����� ����� �� ����,
 * ���� ����� ������ �� ������
 */
public class MasachRashi extends MyPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// �����
	private MasachKnisa masachKnisa;
	private MasachTafrit masachTafrit;
	private MasachHosafatMenahel masachHosafatMenahel;
	private MasachHosafatMutzar masachHosafatMutzar;
	private MasachHashvaatMechirim hashvaatMechirim;

	// ����� ������ ������ ��������
	private DBQueries dbq;

	// ���� ������
	private Ranks rank = null;

	// ��� ���� ���� �� ��������, ����� ���� ����� ��� �������
	// ����� ��� ������ ������, ���� ���� ������ �� ��� ������
	public MasachRashi() {
		dbq = new DBQueries();
		setLayout(new BorderLayout());
		masachKnisa();
	}

	/*
	 * �������� ���� ����, ��� ������� ��� ���� �� ����� �� ���� ����� �������,
	 * ��� �� ������� ���� ��� �� ���� ������ ���� ��� ������ ����� ��� �����
	 * ����� ���� ���� ��� ����� ���� ��� ������ �� ���� ���� ����� ���� �����
	 * ������ �� ��� ������� ����� ����� ����� ������ �� �������� ������
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
		masachTafrit = new MasachTafrit(rank); // ����� ����� ��� ����� ����
												// ���� �� ���� ������
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
					"��� ���� ����� �� ���� ������ ������", "Error",
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
					"��� ���� ����� �� ���� ������ ������", "Error",
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
					"��� ���� ����� �� ���� ������ ������", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void login() {
		String message = null;
		Ranks rank = null;
		this.rank = null;
		try {
			// ����� �� ����� ���� ������
			String username = masachKnisa.getUserName();

			// ����� ������ ���� ������
			String password = new String(masachKnisa.getPassword());

			// ����� �� ����� ������ ������ ���� ������
			rank = dbq.login(username, password);

			// �� �� ���� ����� �� ���� ���� ���� �� ��� ������ ������� ��
			// ������
			if (rank != null) {
				// �� ���� ����, ����� ���� ������ ���� ������
				this.rank = rank;
			} else {
				message = "���� �� ���� �� ����� ������";
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}

		// ����� ��� ����� �����, ���� ������
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
