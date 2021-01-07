package excelWriter;

import org.apache.poi.hssf.usermodel.HSSFSheet;

abstract class ExcelWriter {
	private final HSSFSheet sheet;

	public ExcelWriter(HSSFSheet sheet) throws Exception {
		this.sheet = sheet;
		if (sheet == null) {
			throw new Exception("Must initialize with a sheet");
		}
	}

	public void fillShit(HSSFSheet sheet) throws Exception {
	}

	public HSSFSheet getSheet() {
		return sheet;
	}
}
