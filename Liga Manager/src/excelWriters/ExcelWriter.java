package excelWriters;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import actions.ActionManager;

abstract class ExcelWriter {
	private final HSSFSheet sheet;
	private final ActionManager actionManager;

	public ExcelWriter(HSSFSheet sheet, ActionManager actionManager)
			throws Exception {
		this.sheet = sheet;
		this.actionManager = actionManager;
		if (sheet == null) {
			throw new Exception("Must initialize with a sheet");
		}
		if (actionManager == null) {
			throw new Exception("Must initialize with an action manager");
		}
	}

	public void fillShit() throws Exception {

	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}
}
