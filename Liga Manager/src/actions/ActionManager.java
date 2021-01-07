package actions;

import helpers.EqualMapSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import liga.Liga;

import vo.DBStatsVO;
import vo.GameListVO;
import vo.GameRandomizerVO;
import vo.GameVO;
import vo.GroupVO;
import vo.SiduriShemVO;
import vo.StatsVO;

public class ActionManager {

	private static final Integer NEKUDOT_LE_ZCHIYA = 3;
	private static final Integer NEKUDOT_LE_HEFSED = 0;
	private static final Integer NEKUDOT_LE_TEKO = 1;

	public ActionManager() {
	}

	// Groups
	public Vector<GroupVO> getReshimatKvutzot(boolean inLiga) throws Exception {
		return Liga.getKvutzotAccessManager().getReshima(inLiga);
	}

	public Vector<GroupVO> getReshimatKvutzot(Integer ezorID, Integer baitID)
			throws Exception {
		return Liga.getKvutzotAccessManager().getReshima(ezorID, baitID);
	}

	public Vector<GroupVO> getReshimatKvutzot(int machzor) throws Exception {
		return Liga.getMischakimAccessManager().getReshimatKvutzot(machzor);
	}

	public Integer hosefKvutza(GroupVO group) throws Exception {
		return Liga.getKvutzotAccessManager().hosef(group);
	}

	public void adkenKvutza(GroupVO group) throws Exception {
		Liga.getKvutzotAccessManager().adken(group);
	}

	public EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>> getEzorimVeBatim()
			throws Exception {
		return Liga.getKvutzotAccessManager().getEzorimVeBatim();
	}

	public Vector<SiduriShemVO> getBatim() {
		return Liga.getKvutzotAccessManager().getBatim();
	}

	public Vector<SiduriShemVO> getEzorim() {
		return Liga.getKvutzotAccessManager().getEzorim();
	}

	public int getEzor(Integer mekomit, Integer yeriva) throws Exception {
		GroupVO groupMekomit = null;
		GroupVO groupYeriva = null;
		int ezorID = Liga.EZOR_MEORAV;
		Integer ezorMekomit = null, ezorYeriva = null;

		if (mekomit != null) {
			groupMekomit = Liga.getKvutzotAccessManager().getKvutza(mekomit);
			ezorMekomit = groupMekomit.getEzor();
		}
		if (yeriva != null) {
			groupYeriva = Liga.getKvutzotAccessManager().getKvutza(yeriva);
			ezorYeriva = groupYeriva.getEzor();
		}

		if (ezorMekomit != null && ezorYeriva != null) {
			if (ezorMekomit.intValue() == ezorYeriva.intValue()) {
				ezorID = ezorMekomit.intValue();
			}
		} else if (ezorMekomit != null) {
			ezorID = ezorMekomit.intValue();
		} else if (ezorYeriva != null) {
			ezorID = ezorYeriva.intValue();
		}

		return ezorID;
	}

	public int getBait(Integer mekomit, Integer yeriva) throws Exception {
		GroupVO groupMekomit = null;
		GroupVO groupYeriva = null;
		int baitID = Liga.BAIT_MEORAV;
		Integer baitMekomit = null, baitYeriva = null;

		if (mekomit != null) {
			groupMekomit = Liga.getKvutzotAccessManager().getKvutza(mekomit);
			baitMekomit = groupMekomit.getBait();
		}
		if (yeriva != null) {
			groupYeriva = Liga.getKvutzotAccessManager().getKvutza(yeriva);
			baitYeriva = groupYeriva.getBait();
		}

		if (baitMekomit != null && baitYeriva != null) {
			if (baitMekomit.intValue() == baitYeriva.intValue()) {
				baitID = baitMekomit.intValue();
			}
		} else if (baitMekomit != null) {
			baitID = baitMekomit.intValue();
		} else if (baitYeriva != null) {
			baitID = baitYeriva.intValue();
		}

		return baitID;
	}

	/**
	 * כאשר יש ערך בשדה של השנה, מסירים את הקבוצה מהליגה כאשר הערך הוא ריק,
	 * מסירים את הקבוצה
	 * 
	 * @param groupList
	 * @param ligaID
	 * @throws Exception
	 * @throws DBConnectionException
	 * @throws GeneralException
	 * @throws DBDuplicateException
	 */
	public void mechakKvutzot(Vector<Integer> groupList) throws Exception {
		if (groupList != null && groupList.size() > 0) {
			Iterator<Integer> i = groupList.iterator();
			while (i.hasNext()) {
				Integer groupID = i.next();
				Liga.getMischakimAccessManager().mechakKvutza(groupID);
				Liga.getKvutzotAccessManager().mechak(groupID);
			}
		}
	}

	public void adkenKvutzot(Vector<Integer> groupList) throws Exception {
		if (groupList != null && groupList.size() > 0) {
			Iterator<Integer> i = groupList.iterator();
			while (i.hasNext()) {
				Integer groupID = i.next();
				Liga.getMischakimAccessManager().mechakKvutza(groupID);
				Liga.getKvutzotAccessManager().mechak(groupID);
			}
		}
	}

	// Reset tables
	public void resetAllTables() throws Exception {
		Liga.getLigotAccessManager().resetTable();
	}

	public void resetGamesTable() throws Exception {
		Liga.getMischakimAccessManager().resetTable();
	}

	public void resetGroupsTable() throws Exception {
		Liga.getKvutzotAccessManager().resetTable();
	}

	// Excel
	public void loadGroupsFromExcel() throws Exception {

		Vector<GroupVO> groups = Liga.getExcelAccessManager().getExcelGroups();
		Iterator<GroupVO> i = groups.iterator();
		while (i.hasNext()) {
			GroupVO group = i.next();
			hosefKvutza(group);
		}
	}

	// Games
	public EqualMapSet<SiduriShemVO, Vector<GroupVO>> getBatimVeKvutzotBeMachzor(
			int machzor) throws Exception {
		return Liga.getMischakimAccessManager().getBatimVeKvutzotBeMachzor(
				machzor);
	}

	public void randomizeGames(Vector<GroupVO> kvutzotLeIrbul, int ezorID,
			int baitID, int machzor, int kamutMifgashim) throws Exception {

		if (kvutzotLeIrbul == null || kvutzotLeIrbul.size() < 2) {
			return;
		}

		GameRandomizerVO[][] svavim = kvutzotLeSvavim(kvutzotLeIrbul);

		// א. אם כמות הקבוצות איננה מתחלקת ב 2 אז נוסיף עוד סבב
		// ב. בסבב הנוסף נבדוק כמה כל קבוצה שיחקה ונוסיף רק משחקים של הקבוצות
		// שחסר להם משחק
		EqualMapSet<Integer, Integer> sfiratMischakim = new EqualMapSet<Integer, Integer>();
		int kamutKvutzot = kvutzotLeIrbul.size();
		for (int i = 0; i < kamutKvutzot; i++) {
			sfiratMischakim.put(kvutzotLeIrbul.elementAt(i).getGroupID()
					.intValue(), 0);
		}
		int n = 0;
		boolean allSet = false;
		// נרוץ כל עד שכל הקבוצות ישחקו לפחות כמספר הסבבים המינימלי
		for (int i = 0; n < svavim.length && !allSet; i++) {
			for (int j = 0; j < svavim[n].length; j++) {
				Integer groupA = svavim[n][j].getGroupA();
				Integer groupB = svavim[n][j].getGroupB();
				Integer countA = sfiratMischakim.get(groupA);
				Integer countB = sfiratMischakim.get(groupB);

				if (groupA == 0) {
					groupA = null;
				}
				if (groupB == 0) {
					groupB = null;
				}
				// אם שתי הקבוצות מאופסות אין מה להתייחס
				if (groupA != null || groupB != null) {
					// אם שתי הקבוצות קיימות, נבדוק אם צריך להוסיף אותם
					if (groupA != null && groupB != null) {
						if (countA < kamutMifgashim || countB < kamutMifgashim) {
							countA++;
							sfiratMischakim.put(groupA, countA);
							countB++;
							sfiratMischakim.put(groupB, countB);

							GameVO newGame = new GameVO(null, groupA, null,
									groupB, null, ezorID, baitID, machzor,
									i + 1, null);
							addGame(newGame);
						}
					} else {

					}
				}
			}
			n++;
			if (n == svavim.length) {
				n = 0;
			}

			allSet = true;
			Collection<Integer> sfirot = sfiratMischakim.values();
			Iterator<Integer> iSfirot = sfirot.iterator();
			while (iSfirot.hasNext() && allSet) {
				Integer sfira = iSfirot.next();
				if (sfira.intValue() < kamutMifgashim) {
					allSet = false;
				}
			}
		}
	}

	private GameRandomizerVO[][] kvutzotLeSvavim(Vector<GroupVO> kvutzotLeIrbul) {
		if (kvutzotLeIrbul == null || kvutzotLeIrbul.size() == 0) {
			return new GameRandomizerVO[0][2];
		}
		int groupCount = kvutzotLeIrbul.size();
		if (kvutzotLeIrbul.size() % 2 != 0) {
			groupCount++;
		}
		int[] groupsID = new int[groupCount];

		for (int i = 0; i < groupCount; i++) {
			if (i < kvutzotLeIrbul.size()) {
				groupsID[i] = kvutzotLeIrbul.elementAt(i).getGroupID();
			} else {
				groupsID[i] = 0;
			}
		}

		groupsID = irbul(groupsID);

		return kvutzotLeSvavimMi6(groupsID);
	}

	private GameRandomizerVO[][] kvutzotLeSvavimMi6(int[] groupsID) {

		int groupCount = groupsID.length;
		if (groupCount % 2 != 0) {
			groupCount++;
		}

		int chezti = groupCount / 2;
		int[] left = new int[chezti];
		int[] right = new int[chezti];

		GameRandomizerVO[][] svavimMi6a = new GameRandomizerVO[chezti][chezti];

		for (int i = 0; i < chezti; i++) {
			if (i < groupsID.length) {
				left[i] = groupsID[i];
			} else {
				left[i] = 0;
			}
			if (i + chezti < groupsID.length) {
				right[i] = groupsID[i + chezti];
			} else {
				right[i] = 0;
			}
		}

		for (int i = 0; i < chezti; i++) {
			for (int j = 0; j < chezti; j++) {
				int rightInd = (j + i) % chezti;
				svavimMi6a[i][j] = new GameRandomizerVO(left[j],
						right[rightInd]);
			}
		}

		if (chezti >= 2) {
			GameRandomizerVO[][] svavimMi6left = kvutzotLeSvavimMi6(left);
			GameRandomizerVO[][] svavimMi6right = kvutzotLeSvavimMi6(right);
			int kamutSvavim = svavimMi6left.length;
			int kamutMischakimBeSevev = svavimMi6left[0].length;
			GameRandomizerVO[][] svavimMi6b = new GameRandomizerVO[kamutSvavim][kamutMischakimBeSevev * 2];

			for (int i = 0; i < kamutSvavim; i++) {
				for (int j = 0; j < kamutMischakimBeSevev; j++) {
					svavimMi6b[i][j] = svavimMi6left[i][j];
					svavimMi6b[i][j + kamutMischakimBeSevev] = svavimMi6right[i][j];
				}
			}

			GameRandomizerVO[][] svavimMi6Kolel = new GameRandomizerVO[chezti
					+ kamutSvavim][];

			int n = 0;
			while (n < chezti) {
				svavimMi6Kolel[n] = svavimMi6a[n];
				n++;
			}
			int m = 0;
			while (m < kamutSvavim) {
				svavimMi6Kolel[n + m] = svavimMi6b[m];
				m++;
			}
			svavimMi6a = svavimMi6Kolel;
		}
		return svavimMi6a;
	}

	private int[] irbul(int[] groupsID) {
		if (groupsID == null || groupsID.length <= 0) {
			return new int[0];
		}
		Integer[] indexArray = new Integer[groupsID.length];
		for (int i = 0; i < indexArray.length; i++) {
			indexArray[i] = i;
		}
		ArrayList<Integer> arrayToShuffle = new ArrayList<Integer>(
				Arrays.asList(indexArray));
		Collections.shuffle(arrayToShuffle);

		int[] newGroupsID = new int[groupsID.length];
		for (int i = 0; i < arrayToShuffle.size(); i++) {
			newGroupsID[i] = groupsID[arrayToShuffle.get(i)];
		}

		return newGroupsID;
	}

	public Vector<Vector<StatsVO>> getGameStats() throws Exception {

		Vector<Vector<StatsVO>> vectotLefitavlaot = new Vector<Vector<StatsVO>>();

		// מערך המכיל את מזהה הקבוצה והסטטיסטיקות שלה
		EqualMapSet<DBStatsVO, StatsVO> tavlatStatisticot = new EqualMapSet<DBStatsVO, StatsVO>();

		Vector<DBStatsVO> dbStats = Liga.getMischakimAccessManager()
				.getStatisticotMischakim();
		Iterator<DBStatsVO> i = dbStats.iterator();
		// הסיכום הוא ברמת שנה, איזור, בית, מחזור וקבוצה
		// מאוחר יותר נחלק את כל הרשומות לוקטורים של מיני טבלאות
		// כך ניצור וקטור של וקטורים של טבלאות
		while (i.hasNext()) {
			DBStatsVO stats = i.next();
			StatsVO gameStats;
			if (tavlatStatisticot.containsKey(stats)) {
				gameStats = tavlatStatisticot.get(stats);
			} else {
				gameStats = new StatsVO(stats);
			}

			gameStats.setGolimZchut(gameStats.getGolimZchut()
					+ stats.getGolimZchut());
			gameStats.setGolimChova(gameStats.getGolimChova()
					+ stats.getGolimChova());
			gameStats.setHefreshNikud(gameStats.getGolimZchut()
					- +gameStats.getGolimChova());
			if (stats.getGolimZchut() > stats.getGolimChova()) {
				gameStats.setWins(gameStats.getWins() + 1);
				gameStats.setNikudKolel(gameStats.getNikudKolel()
						+ NEKUDOT_LE_ZCHIYA);
			} else if (stats.getGolimZchut() < stats.getGolimChova()) {
				gameStats.setLosses(gameStats.getLosses() + 1);
				gameStats.setNikudKolel(gameStats.getNikudKolel()
						+ NEKUDOT_LE_HEFSED);
			} else {
				gameStats.setDraws(gameStats.getDraws() + 1);
				gameStats.setNikudKolel(gameStats.getNikudKolel()
						+ NEKUDOT_LE_TEKO);
			}
			tavlatStatisticot.put(stats, gameStats);
		}

		if (tavlatStatisticot != null && tavlatStatisticot.size() > 0) {
			Collection<StatsVO> gs = tavlatStatisticot.values();
			Vector<StatsVO> gsList = new Vector<StatsVO>();
			Iterator<StatsVO> j = gs.iterator();
			while (j.hasNext()) {
				gsList.add(j.next());
			}

			// מיון הוקטורים לצורך שבירה
			boolean flag = true;
			StatsVO temp;
			while (flag) {
				flag = false;
				for (int k = 0; k < gsList.size() - 1; k++) {
					if (gsList.elementAt(k).compareTo(gsList.elementAt(k + 1)) < 0) {
						temp = gsList.elementAt(k);
						gsList.set(k, gsList.elementAt(k + 1));
						gsList.set(k + 1, temp);
						flag = true;
					}
				}
			}

			// חלוקת הוקטורים לטבלאות ע"י שבירה
			Vector<StatsVO> tavlaVector = new Vector<StatsVO>();
			int it = 0;
			while (it < gsList.size()) {
				tavlaVector.add(gsList.elementAt(it));

				it++;
				if (it == gsList.size()) {
					vectotLefitavlaot.add(tavlaVector);
				} else if (!gsList.elementAt(it).equalsLigaRegionBaitMachzor(
						gsList.elementAt(it - 1))) {
					vectotLefitavlaot.add(tavlaVector);
					tavlaVector = new Vector<StatsVO>();
				}
			}

			// מיון הטבלאות בסדר יורד לפי תאריכי המשחק
			flag = true;
			Vector<StatsVO> tempVec = null;
			while (flag) {
				flag = false;
				for (int k = 0; k < vectotLefitavlaot.size() - 1; k++) {
					if (vectotLefitavlaot
							.elementAt(k)
							.elementAt(0)
							.compareDate(
									vectotLefitavlaot.elementAt(k + 1)
											.elementAt(0)) < 0) {
						tempVec = vectotLefitavlaot.elementAt(k);
						vectotLefitavlaot.set(k,
								vectotLefitavlaot.elementAt(k + 1));
						vectotLefitavlaot.set(k + 1, tempVec);
						flag = true;
					}
				}
			}
		}
		return vectotLefitavlaot;
	}

	public Vector<GameListVO> getGames() throws Exception {
		return Liga.getMischakimAccessManager().getReshima();
	}

	public Vector<Integer> getGamesIDs(Integer machzor) {
		return Liga.getMischakimAccessManager().getReshima(machzor);
	}

	public GameListVO addGame(GameVO game) throws Exception {
		return Liga.getMischakimAccessManager().hosef(game);
	}

	public void deleteGames(Vector<Integer> gameList) throws Exception {
		if (gameList != null && gameList.size() > 0) {
			Iterator<Integer> i = gameList.iterator();
			while (i.hasNext()) {
				Integer gameID = i.next();
				Liga.getMischakimAccessManager().mechak(gameID);
			}
		}
	}

	public void updateGame(GameVO game) throws Exception {
		Liga.getMischakimAccessManager().adken(game);
	}

	public GameListVO getFullGame(Integer gameID) throws Exception {
		return Liga.getMischakimAccessManager().getNetuneyMischakMeleim(gameID);
	}

	public void changeUserPassword(String newUserPassword) {
	}

	public void changeUsername(String newUsername) {
	}

	public Vector<SiduriShemVO> getLigot() {
		return Liga.getLigotAccessManager().getList();
	}

	public void updateLiga(SiduriShemVO liga) throws Exception {
		Liga.getLigotAccessManager().update(liga);
	}

	public Integer hosefLiga(SiduriShemVO liga) throws Exception {
		return Liga.getLigotAccessManager().hosef(liga);
	}

	public void mechakLigot(Vector<Integer> ligotList) throws Exception {
		if (ligotList != null && ligotList.size() > 0) {
			Iterator<Integer> i = ligotList.iterator();
			while (i.hasNext()) {
				Integer ligaID = i.next();
				Liga.getLigotAccessManager().mechak(ligaID);
			}
		}
	}

	public void hosefEzor(String newEzor) throws Exception {
		Liga.getLigotAccessManager().hosefEzor(newEzor);
	}

	public void hosefBait(String newBait) throws Exception {
		Liga.getLigotAccessManager().hosefBait(newBait);
	}

}
