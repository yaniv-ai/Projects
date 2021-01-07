package dbManagers;

import helpers.EqualMapSet;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import enums.KodShem;

import liga.Liga;
import liga.LigaDB;

import vo.DBStatsVO;
import vo.GameListVO;
import vo.GameVO;
import vo.GroupVO;
import vo.SiduriShemVO;

public class MischakimDBManager extends LigaDB {

	public MischakimDBManager() {
	}

	public Vector<GameListVO> getReshima() throws Exception {
		Vector<GameListVO> gamesList = new Vector<GameListVO>();

		Integer mischakID;
		Integer mekomitID;
		String mekomitShem;
		Integer mekomitNekudot;
		Integer yerivaID;
		String yerivaShem;
		Integer yerivaNekudot;
		Integer ezorID;
		String ezorShem;
		Integer baitID;
		String baitShem;
		Integer machzor;
		Integer sevev;
		Calendar taarich;

		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();

			mischakID = g.getGameID();

			mekomitID = g.getMekomitID();
			if (mekomitID != null) {
				mekomitShem = getName(mekomitID, KodShem.kvutza);
			} else {
				mekomitShem = null;
			}
			mekomitNekudot = g.getMekomitNekudot();

			yerivaID = g.getYerivaID();
			if (yerivaID != null) {
				yerivaShem = getName(yerivaID, KodShem.kvutza);
			} else {
				yerivaShem = null;
			}
			yerivaNekudot = g.getYerivaNekudot();

			ezorID = g.getEzorID();
			if (ezorID != null) {
				ezorShem = getName(ezorID, KodShem.ezor);
			} else {
				ezorShem = null;
			}

			baitID = g.getBaitID();
			;
			if (baitID != null) {
				baitShem = getName(baitID, KodShem.bait);
			} else {
				baitShem = null;
			}
			machzor = g.getMachzor();
			sevev = g.getSevev();
			if (g.getGameDate() == null) {
				taarich = null;
			} else {
				taarich = Calendar.getInstance();
				taarich.setTimeInMillis(g.getGameDate().getTimeInMillis());
			}

			gamesList
					.add(new GameListVO(mischakID, mekomitID, mekomitNekudot,
							yerivaID, yerivaNekudot, ezorID, ezorShem, baitID,
							baitShem, machzor, sevev, taarich, mekomitShem,
							yerivaShem));
		}

		return gamesList;
	}

	public Vector<Integer> getReshima(Integer machzor) {
		if (machzor == null) {
			throw new NullPointerException("machzor cannot be null");
		}

		Vector<Integer> gamesIDs = new Vector<Integer>();

		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();
			if (Liga.integerFieldAreEqual(machzor, g.getMachzor())) {
				gamesIDs.add(g.getGameID().intValue());
			}
		}

		return gamesIDs;
	}

	public Vector<DBStatsVO> getStatisticotMischakim() throws Exception {

		Vector<DBStatsVO> gameStats = new Vector<DBStatsVO>();

		Integer mekomitID;
		String mekomitShem;
		Integer mekomitNekudot;
		Integer yerivaID;
		String yerivaShem;
		Integer yerivaNekudot;
		Integer ezorID;
		String ezorShem;
		Integer baitID;
		String baitShem;
		Integer machzor;
		Integer sevev;
		Calendar taarich;

		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();

			mekomitID = g.getMekomitID();
			if (mekomitID != null) {
				mekomitShem = getName(mekomitID, KodShem.kvutza);
			} else {
				mekomitShem = null;
			}
			mekomitNekudot = g.getMekomitNekudot();

			yerivaID = g.getYerivaID();
			if (yerivaID != null) {
				yerivaShem = getName(yerivaID, KodShem.kvutza);
			} else {
				yerivaShem = null;
			}
			yerivaNekudot = g.getYerivaNekudot();

			ezorID = g.getEzorID();
			if (ezorID != null) {
				ezorShem = getName(ezorID, KodShem.ezor);
			} else {
				ezorShem = null;
			}

			baitID = g.getBaitID();
			;
			if (baitID != null) {
				baitShem = getName(baitID, KodShem.bait);
			} else {
				baitShem = null;
			}
			machzor = g.getMachzor();
			sevev = g.getSevev();
			if (g.getGameDate() == null) {
				taarich = null;
			} else {
				taarich = Calendar.getInstance();
				taarich.setTimeInMillis(g.getGameDate().getTimeInMillis());
			}

			if (mekomitID != null) {
				gameStats.add(new DBStatsVO(mekomitID, mekomitShem, ezorID,
						ezorShem, baitID, baitShem, machzor, sevev,
						mekomitNekudot, yerivaNekudot, taarich));
			}
			if (yerivaID != null) {
				gameStats.add(new DBStatsVO(yerivaID, yerivaShem, ezorID,
						ezorShem, baitID, baitShem, machzor, sevev,
						yerivaNekudot, mekomitNekudot, taarich));
			}

		}

		return gameStats;
	}

	public GameListVO hosef(GameVO game) throws Exception {
		if (game == null) {
			throw new NullPointerException("Mischak cannot be null");
		}
		if (game.getEzorID() == null) {
			throw new NullPointerException("Ezor id cannot be null");
		}
		if (game.getBaitID() == null) {
			throw new NullPointerException("Bait id cannot be null");
		}
		if (game.getMachzor() == null) {
			throw new NullPointerException("Machzor cannot be null");
		}
		if (game.getSevev() == null) {
			throw new NullPointerException("sevev cannot be null");
		}
		if (getLigaId() == null) {
			throw new NullPointerException("liga cannot be null");
		}

		if (game.getGameID() == null) {
			game.setGameID(mezaheAcharonBeShimush() + 1);
		}

		mischakim.put(game.getGameID().intValue(), game);

		shmorMischakim();

		return getNetuneyMischakMeleim(game.getGameID());
	}

	public void adken(GameVO game) throws Exception {
		hosef(game);
	}

	public void mechak(Integer gameID) throws Exception {
		if (gameID == null) {
			throw new NullPointerException("Mischak id cannot be null");
		}
		if (getLigaId() == null) {
			throw new NullPointerException("liga cannot be null");
		}

		mischakim.remove(gameID);
		shmorMischakim();
	}

	public void resetTable() throws Exception {
		if (getLigaId() != null) {
			mischakim = new EqualMapSet<Integer, GameVO>();
			shmorMischakim();
		}
	}

	public void mechakKvutza(Integer groupID) throws Exception {
		if (groupID == null) {
			throw new NullPointerException("Kvutza id cannot be null");
		}
		Vector<Integer> gamesIDs = new Vector<Integer>();
		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();
			if (Liga.integerFieldAreEqual(groupID, g.getMekomitID())
					|| Liga.integerFieldAreEqual(groupID, g.getYerivaID())) {
				gamesIDs.add(g.getGameID().intValue());
			}
		}

		for (int j = 0; j < gamesIDs.size(); j++) {
			mechak(gamesIDs.elementAt(j));
		}
	}

	public Vector<GameListVO> getMischakimLeIrbul(Integer ezorID,
			Integer baitID, Integer machzor) throws Exception {
		if (ezorID == null) {
			throw new NullPointerException("Ezor id cannot be null");
		}
		if (baitID == null) {
			throw new NullPointerException("Bait id cannot be null");
		}
		if (machzor == null) {
			throw new NullPointerException("machzor cannot be null");
		}

		Vector<GameListVO> gamesList = new Vector<GameListVO>();

		Integer mischakID;
		Integer mekomitID;
		String mekomitShem;
		Integer mekomitNekudot;
		Integer yerivaID;
		String yerivaShem;
		Integer yerivaNekudot;
		String ezorShem;
		String baitShem;
		Integer sevev;
		Calendar taarich;

		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();

			mischakID = g.getGameID();

			mekomitID = g.getMekomitID();
			if (mekomitID != null) {
				mekomitShem = getName(mekomitID, KodShem.kvutza);
			} else {
				mekomitShem = null;
			}
			mekomitNekudot = g.getMekomitNekudot();

			yerivaID = g.getYerivaID();
			if (yerivaID != null) {
				yerivaShem = getName(yerivaID, KodShem.kvutza);
			} else {
				yerivaShem = null;
			}
			yerivaNekudot = g.getYerivaNekudot();

			ezorID = g.getEzorID();
			if (ezorID != null) {
				ezorShem = getName(ezorID, KodShem.ezor);
			} else {
				ezorShem = null;
			}

			baitID = g.getBaitID();
			;
			if (baitID != null) {
				baitShem = getName(baitID, KodShem.bait);
			} else {
				baitShem = null;
			}
			machzor = g.getMachzor();
			sevev = g.getSevev();
			if (g.getGameDate() == null) {
				taarich = null;
			} else {
				taarich = Calendar.getInstance();
				taarich.setTimeInMillis(g.getGameDate().getTimeInMillis());
			}
			if (Liga.integerFieldAreEqual(ezorID, g.getEzorID())
					&& Liga.integerFieldAreEqual(baitID, g.getBaitID())
					&& Liga.integerFieldAreEqual(machzor, g.getMachzor())) {
				gamesList.add(new GameListVO(mischakID, mekomitID,
						mekomitNekudot, yerivaID, yerivaNekudot, ezorID
								.intValue(), ezorShem, baitID.intValue(),
						baitShem, machzor.intValue(), sevev, taarich,
						mekomitShem, yerivaShem));
			}

		}
		return gamesList;
	}

	public Integer mezaheAcharonBeShimush() {
		int mezaheAcharon = 0;
		for (int i = 0; i < mischakim.size(); i++) {
			if (mischakim.elementAt(i) > mezaheAcharon) {
				mezaheAcharon = mischakim.elementAt(i);
			}
		}
		return mezaheAcharon;
	}

	public GameListVO getNetuneyMischakMeleim(Integer gameID) throws Exception {
		if (gameID == null) {
			throw new NullPointerException("Mischak id cannot be null");
		}
		GameListVO game = null;

		Integer mekomitID;
		String mekomitShem;
		Integer mekomitNekudot;
		Integer yerivaID;
		String yerivaShem;
		Integer yerivaNekudot;
		Integer ezorID;
		String ezorShem;
		Integer baitID;
		String baitShem;
		Integer machzor;
		Integer sevev;
		Calendar taarich;

		GameVO g = mischakim.get(gameID);

		if (g != null) {
			mekomitID = g.getMekomitID();
			if (mekomitID != null) {
				mekomitShem = getName(mekomitID, KodShem.kvutza);
			} else {
				mekomitShem = null;
			}
			mekomitNekudot = g.getMekomitNekudot();

			yerivaID = g.getYerivaID();
			if (yerivaID != null) {
				yerivaShem = getName(yerivaID, KodShem.kvutza);
			} else {
				yerivaShem = null;
			}
			yerivaNekudot = g.getYerivaNekudot();

			ezorID = g.getEzorID();
			if (ezorID != null) {
				ezorShem = getName(ezorID, KodShem.ezor);
			} else {
				ezorShem = null;
			}

			baitID = g.getBaitID();
			;
			if (baitID != null) {
				baitShem = getName(baitID, KodShem.bait);
			} else {
				baitShem = null;
			}
			machzor = g.getMachzor();
			sevev = g.getSevev();
			if (g.getGameDate() == null) {
				taarich = null;
			} else {
				taarich = Calendar.getInstance();
				taarich.setTimeInMillis(g.getGameDate().getTimeInMillis());
			}

			game = new GameListVO(gameID, mekomitID, mekomitNekudot, yerivaID,
					yerivaNekudot, ezorID, ezorShem, baitID, baitShem, machzor,
					sevev, taarich, mekomitShem, yerivaShem);
		}

		return game;

	}

	public EqualMapSet<SiduriShemVO, Vector<GroupVO>> getBatimVeKvutzotBeMachzor(
			int machzor) throws Exception {
		EqualMapSet<SiduriShemVO, Vector<GroupVO>> batimVeKvutzot = new EqualMapSet<SiduriShemVO, Vector<GroupVO>>();
		Vector<Integer> groupIDs = new Vector<Integer>();
		Vector<GroupVO> groups = new Vector<GroupVO>();
		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();
			if (Liga.integerFieldAreEqual(machzor, g.getMachzor())) {
				Integer groupID = null;
				String groupName = null;
				Integer ezorID = g.getEzorID();
				String ezorShem = null;
				if (ezorID != null) {
					ezorShem = getName(ezorID, KodShem.ezor);
				}
				Integer baitID = g.getBaitID();
				String baitShem = null;
				if (baitID != null) {
					baitShem = getName(baitID, KodShem.bait);
				}
				if (g.getMekomitID() != null
						&& !groupIDs.contains(g.getMekomitID())) {
					groupID = g.getMekomitID().intValue();
				}
				if (g.getYerivaID() != null
						&& !groupIDs.contains(g.getYerivaID())) {
					groupID = g.getYerivaID().intValue();
				}

				if (groupID != null) {
					groupName = getName(groupID, KodShem.kvutza);
					GroupVO group = new GroupVO(groupID, groupName, ezorID,
							ezorShem, baitID, baitShem);
					groupIDs.add(groupID);
					groups.add(group);
				}
			}
		}

		for (int j = 0; j < groups.size(); j++) {
			GroupVO group = groups.elementAt(j);
			SiduriShemVO bait = new SiduriShemVO(group.getBait(),
					group.getBaitName());
			if (batimVeKvutzot.containsKey(bait)) {
				Vector<GroupVO> gps = batimVeKvutzot.get(bait);
				gps.add(group);
				batimVeKvutzot.put(bait, gps);
			} else {
				Vector<GroupVO> gps = new Vector<GroupVO>();
				gps.add(group);
				batimVeKvutzot.put(bait, gps);
			}
		}

		return batimVeKvutzot;
	}

	public Vector<GroupVO> getReshimatKvutzot(int machzor) throws Exception {
		Vector<Integer> groupIDs = new Vector<Integer>();
		Vector<GroupVO> groups = new Vector<GroupVO>();
		Collection<GameVO> col = mischakim.values();
		Iterator<GameVO> i = col.iterator();
		while (i.hasNext()) {
			GameVO g = i.next();
			if (Liga.integerFieldAreEqual(machzor, g.getMachzor())) {
				Integer groupID = null;
				String groupName = null;
				Integer ezorID = g.getEzorID();
				String ezorShem = null;
				if (ezorID != null) {
					ezorShem = getName(ezorID, KodShem.ezor);
				}
				Integer baitID = g.getBaitID();
				String baitShem = null;
				if (baitID != null) {
					baitShem = getName(baitID, KodShem.bait);
				}
				if (g.getMekomitID() != null
						&& !groupIDs.contains(g.getMekomitID())) {
					groupID = g.getMekomitID().intValue();
				}
				if (g.getYerivaID() != null
						&& !groupIDs.contains(g.getYerivaID())) {
					groupID = g.getYerivaID().intValue();
				}

				if (groupID != null) {
					groupName = getName(groupID, KodShem.kvutza);
					GroupVO group = new GroupVO(groupID, groupName, ezorID,
							ezorShem, baitID, baitShem);
					groupIDs.add(groupID);
					groups.add(group);
				}
			}
		}

		return groups;
	}

}
