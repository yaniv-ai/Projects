package excelWriters;

import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import actions.ActionManager;

import vo.GroupVO;

public class GroupExcelWriter extends ExcelWriter {

	public GroupExcelWriter(HSSFSheet sheet, ActionManager actionManager)
			throws Exception {
		super(sheet, actionManager);
	}

	public void fillShit() throws Exception {
		Vector<GroupVO> groupVector = getActionManager().getReshimatKvutzot(
				true);
		if (groupVector != null) {
			Iterator<GroupVO> i = groupVector.iterator();
			HSSFRow rowhead = getSheet().createRow(0);

			rowhead.createCell(0).setCellValue("מספר קבוצה");
			rowhead.createCell(1).setCellValue("שם קבוצה");
			rowhead.createCell(2).setCellValue("אזור");
			rowhead.createCell(3).setCellValue("בית");
			rowhead.createCell(4).setCellValue("מספר אזור");
			rowhead.createCell(5).setCellValue("מספר בית");

			int n = 0;
			int groupID;
			String groupName;
			int ezorID;
			String ezor;
			int baitID;
			String bait;
			while (i.hasNext()) {
				GroupVO group = i.next();
				try {
					groupID = group.getGroupID();
				} catch (Exception e) {
					groupID = 0;
				}
				groupName = group.getGroupName();
				if (groupName == null) {
					groupName = "";
				}
				try {
					ezorID = group.getEzor();
				} catch (Exception e) {
					ezorID = 0;
				}
				ezor = group.getEzorName();
				if (ezor == null) {
					ezor = "";
				}
				try {
					baitID = group.getBait();
				} catch (Exception e) {
					baitID = 0;
				}
				bait = group.getBaitName();
				if (bait == null) {
					bait = "";
				}

				HSSFRow row = getSheet().createRow(++n);
				row.createCell(0).setCellValue(groupID);
				row.createCell(1).setCellValue(groupName);
				row.createCell(2).setCellValue(ezor);
				row.createCell(3).setCellValue(bait);
				row.createCell(4).setCellValue(ezorID);
				row.createCell(5).setCellValue(baitID);
			}
		}
	}
}
