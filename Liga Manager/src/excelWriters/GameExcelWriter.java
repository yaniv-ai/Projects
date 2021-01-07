package excelWriters;

import java.util.Iterator;
import java.util.Vector;

import liga.Liga;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import actions.ActionManager;

import vo.GameListVO;

public class GameExcelWriter extends ExcelWriter {

	public GameExcelWriter(HSSFSheet sheet, ActionManager actionManager)
			throws Exception {
		super(sheet, actionManager);
	}

	public void fillShit() throws Exception {
		HSSFRow rowhead = getSheet().createRow(0);
		rowhead.createCell(0).setCellValue("שם קבוצה מקומית");
		rowhead.createCell(1).setCellValue("ניקוד קבוצה מקומית");
		rowhead.createCell(2).setCellValue("שם קבוצה יריבה");
		rowhead.createCell(3).setCellValue("ניקוד קבוצה יריבה");
		rowhead.createCell(4).setCellValue("אזור");
		rowhead.createCell(5).setCellValue("בית");
		rowhead.createCell(6).setCellValue("מחזור");
		rowhead.createCell(7).setCellValue("סבב");
		rowhead.createCell(8).setCellValue("תאריך משחק");
		rowhead.createCell(9).setCellValue("מספר קבוצה מקומית");
		rowhead.createCell(10).setCellValue("מספר קבוצה יריבה");
		rowhead.createCell(11).setCellValue("מספר אזור");
		rowhead.createCell(12).setCellValue("מספר בית");
		rowhead.createCell(13).setCellValue("מספר משחק");

		int n = 0;
		Vector<GameListVO> gamesVector = getActionManager().getGames();
		if (gamesVector != null) {
			Iterator<GameListVO> i = gamesVector.iterator();

			String shemMekomit;
			int nekudotMekomit;
			String shemYeriva;
			int nekudotYeriva;
			String ezor;
			String bait;
			int machzor;
			int sevev;
			String taarich;
			int mekomitID;
			int yerivaID;
			int ezorID;
			int baitID;
			int mischakID;

			while (i.hasNext()) {
				GameListVO vo = i.next();

				shemMekomit = vo.getMekomitShem();
				if (shemMekomit == null) {
					shemMekomit = "";
				}
				try {
					nekudotMekomit = vo.getMekomitNekudot();
				} catch (Exception e) {
					nekudotMekomit = 0;
				}
				shemYeriva = vo.getYerivaShem();
				if (shemYeriva == null) {
					shemYeriva = "";
				}
				try {
					nekudotYeriva = vo.getYerivaNekudot();
				} catch (Exception e) {
					nekudotYeriva = 0;
				}
				ezor = vo.getEzorShem();
				if (ezor == null) {
					ezor = "";
				}

				bait = vo.getBaitShem();
				if (bait == null) {
					bait = "";
				}

				try {
					machzor = vo.getMachzor();
				} catch (Exception e) {
					machzor = 0;
				}
				try {
					sevev = vo.getSevev();
				} catch (Exception e) {
					sevev = 0;
				}

				try {
					taarich = Liga.calendarToString(vo.getGameDate());
				} catch (Exception e) {
					taarich = "";
				}

				try {
					mekomitID = vo.getMekomitID();
				} catch (Exception e) {
					mekomitID = 0;
				}
				try {
					yerivaID = vo.getYerivaID();
				} catch (Exception e) {
					yerivaID = 0;
				}
				try {
					ezorID = vo.getEzorID();
				} catch (Exception e) {
					ezorID = 0;
				}
				try {
					baitID = vo.getBaitID();
				} catch (Exception e) {
					baitID = 0;
				}
				try {
					mischakID = vo.getGameID();
				} catch (Exception e) {
					mischakID = 0;
				}

				HSSFRow row = getSheet().createRow(++n);

				row.createCell(0).setCellValue(shemMekomit);
				row.createCell(1).setCellValue(nekudotMekomit);
				row.createCell(2).setCellValue(shemYeriva);
				row.createCell(3).setCellValue(nekudotYeriva);
				row.createCell(4).setCellValue(ezor);
				row.createCell(5).setCellValue(bait);
				row.createCell(6).setCellValue(machzor);
				row.createCell(7).setCellValue(sevev);
				row.createCell(8).setCellValue(taarich);
				row.createCell(9).setCellValue(mekomitID);
				row.createCell(10).setCellValue(yerivaID);
				row.createCell(11).setCellValue(ezorID);
				row.createCell(12).setCellValue(baitID);
				row.createCell(13).setCellValue(mischakID);

			}
		}

	}
}
