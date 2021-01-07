package excelWriters;

import java.awt.Component;
import java.io.*;

import javax.swing.JOptionPane;

import liga.Liga;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import actions.ActionManager;

public class ExportToExcel {
	protected HSSFWorkbook workbook = null;

	public ExportToExcel(Component parent, String filename, final Integer actionManagerId) {
		File f = new File(filename);
		boolean makeFile = false;
		if (f.exists() && f.isFile()) {
			int confirm = JOptionPane.showConfirmDialog(parent, "���� ��� �� ��� ����, ��� ������ �� ������");
			if (confirm == 0) {
				makeFile = true;
			}
		} else {
			JOptionPane.showMessageDialog(parent, "���� �� ����� ����� ������");
		}
		if (makeFile) {
			try {
				ActionManager actionManager = Liga.getActionManager(actionManagerId);
				workbook = new HSSFWorkbook();
				new GameExcelWriter(workbook.createSheet("Games"), actionManager).fillShit();
				new GroupExcelWriter(workbook.createSheet("Groups"), actionManager).fillShit();
				new StatsExcelWriter(workbook.createSheet("Stats"), actionManager).fillShit();

				FileOutputStream fileOut = new FileOutputStream(filename);
				workbook.write(fileOut);
				fileOut.close();

				JOptionPane.showMessageDialog(parent, "����� ����� ������");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(parent, "���� ��� ����� ����� �����");
			}
		}
	}
}
