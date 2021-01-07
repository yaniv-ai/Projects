package excelWriters;

import java.util.Iterator;
import java.util.Vector;

import liga.Liga;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import vo.StatsVO;

import actions.ActionManager;

public class StatsExcelWriter extends ExcelWriter {

	public StatsExcelWriter(HSSFSheet sheet, ActionManager actionManager)
			throws Exception {
		super(sheet, actionManager);
	}

	public void fillShit() throws Exception {
		Vector<Vector<StatsVO>> totalStatsVector = getActionManager()
				.getGameStats();

		int n = 0;
		int groupID;
		String groupName;
		int ezorID;
		String ezor;
		int baitID;
		String bait;
		int machzor;
		int golimZchut;
		int golimChova;
		int hefreshNikud;
		int wins;
		int losses;
		int draws;
		int nikudKolel;
		String taarich;

		for (int indTavla = 0; indTavla < totalStatsVector.size(); indTavla++) {
			if (totalStatsVector.elementAt(indTavla) != null) {
				Iterator<StatsVO> i = totalStatsVector.elementAt(indTavla)
						.iterator();
				boolean firstLine = true;
				while (i.hasNext()) {
					StatsVO stat = i.next();
					if (firstLine) {
						firstLine = false;
						n = addKoteret(stat, n);
					}

					groupName = stat.getGroupName();
					if (groupName == null) {
						groupName = "";
					}

					ezor = stat.getEzorShem();
					if (ezor == null) {
						ezor = "";
					}

					bait = stat.getBaitShem();
					if (bait == null) {
						bait = "";
					}

					try {
						machzor = stat.getMachzor();
					} catch (Exception e) {
						machzor = 0;
					}

					try {
						golimZchut = stat.getGolimZchut();
					} catch (Exception e) {
						golimZchut = 0;
					}

					try {
						golimChova = stat.getGolimChova();
					} catch (Exception e) {
						golimChova = 0;
					}

					try {
						hefreshNikud = stat.getHefreshNikud();
					} catch (Exception e) {
						hefreshNikud = 0;
					}

					try {
						wins = stat.getWins();
					} catch (Exception e) {
						wins = 0;
					}

					try {
						draws = stat.getDraws();
					} catch (Exception e) {
						draws = 0;
					}

					try {
						losses = stat.getLosses();
					} catch (Exception e) {
						losses = 0;
					}

					try {
						nikudKolel = stat.getNikudKolel();
					} catch (Exception e) {
						nikudKolel = 0;
					}

					try {
						taarich = Liga.calendarToString(stat.getGameDate());
					} catch (Exception e) {
						taarich = "";
					}

					try {
						groupID = stat.getGroupID();
					} catch (Exception e) {
						groupID = 0;
					}
					try {
						ezorID = stat.getEzorID();
					} catch (Exception e) {
						ezorID = 0;
					}
					try {
						baitID = stat.getBaitID();
					} catch (Exception e) {
						baitID = 0;
					}
					HSSFRow row = getSheet().createRow(n);
					row.createCell(0).setCellValue(groupName);
					row.createCell(1).setCellValue(ezor);
					row.createCell(2).setCellValue(bait);
					row.createCell(3).setCellValue(machzor);
					row.createCell(4).setCellValue(golimZchut);
					row.createCell(5).setCellValue(golimChova);
					row.createCell(6).setCellValue(hefreshNikud);
					row.createCell(7).setCellValue(wins);
					row.createCell(8).setCellValue(draws);
					row.createCell(9).setCellValue(losses);
					row.createCell(10).setCellValue(nikudKolel);
					row.createCell(11).setCellValue(taarich);
					row.createCell(12).setCellValue(groupID);
					row.createCell(13).setCellValue(ezorID);
					row.createCell(14).setCellValue(baitID);
					n++;
				}
			}
		}
	}

	private int addKoteret(StatsVO stat, int n) {

		HSSFRow blank = getSheet().createRow(n);
		blank.createCell(0).setCellValue(" ");
		n++;

		HSSFRow rowKoteret = getSheet().createRow(n);
		rowKoteret.createCell(0).setCellValue(
				" איזור: " + stat.getEzorShem() + ", בית: "
						+ stat.getBaitShem() + ", מחזור: " + stat.getMachzor());
		n++;

		HSSFRow rowhead = getSheet().createRow(n);

		rowhead.createCell(0).setCellValue("שם קבוצה");
		rowhead.createCell(1).setCellValue("איזור");
		rowhead.createCell(2).setCellValue("בית");
		rowhead.createCell(3).setCellValue("מחזור");
		rowhead.createCell(4).setCellValue("נקודות זכות");
		rowhead.createCell(5).setCellValue("נקודות חובה");
		rowhead.createCell(6).setCellValue("הפרש נקודות");
		rowhead.createCell(7).setCellValue("נצחונות");
		rowhead.createCell(8).setCellValue("תיקו");
		rowhead.createCell(9).setCellValue("הפסדים");
		rowhead.createCell(10).setCellValue("ניקוד כולל");
		rowhead.createCell(11).setCellValue("תאריך משחק");
		rowhead.createCell(12).setCellValue("מספר קבוצה");
		rowhead.createCell(13).setCellValue("מספר אזור");
		rowhead.createCell(14).setCellValue("מספר בית");
		n++;

		HSSFRow rowTochem = getSheet().createRow(n);
		rowTochem.createCell(0).setCellValue("----------------");
		rowTochem.createCell(1).setCellValue("----------------");
		rowTochem.createCell(2).setCellValue("----------------");
		rowTochem.createCell(3).setCellValue("----------------");
		rowTochem.createCell(4).setCellValue("----------------");
		rowTochem.createCell(5).setCellValue("----------------");
		rowTochem.createCell(6).setCellValue("----------------");
		rowTochem.createCell(7).setCellValue("----------------");
		rowTochem.createCell(8).setCellValue("----------------");
		rowTochem.createCell(9).setCellValue("----------------");
		rowTochem.createCell(10).setCellValue("----------------");
		rowTochem.createCell(11).setCellValue("----------------");
		rowTochem.createCell(12).setCellValue("----------------");
		rowTochem.createCell(13).setCellValue("----------------");
		rowTochem.createCell(14).setCellValue("----------------");
		n++;

		return n;
	}
}
