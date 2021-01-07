package accessManagers;

import java.io.IOException;
import java.util.Vector;

import dbManagers.ExcelDBManager;

import vo.GroupVO;

public class ExcelAccessManager {

	private ExcelDBManager excelDBManager;

	public ExcelAccessManager() {
		excelDBManager = new ExcelDBManager();
	}

	public Vector<GroupVO> getExcelGroups() throws IOException {
		return excelDBManager.getExcelGroups();
	}

}
