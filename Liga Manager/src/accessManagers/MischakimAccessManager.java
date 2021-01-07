package accessManagers;

import helpers.EqualMapSet;

import java.util.Vector;

import dbManagers.MischakimDBManager;

import vo.DBStatsVO;
import vo.GameListVO;
import vo.GameVO;
import vo.GroupVO;
import vo.SiduriShemVO;

public class MischakimAccessManager {
	private MischakimDBManager mischakimDBManager;

	public MischakimAccessManager() {
		mischakimDBManager = new MischakimDBManager();
	}

	public Vector<GameListVO> getReshima() throws Exception {
		return mischakimDBManager.getReshima();
	}

	public Vector<Integer> getReshima(Integer machzor) {
		return mischakimDBManager.getReshima(machzor);
	}

	public Vector<DBStatsVO> getStatisticotMischakim() throws Exception {
		return mischakimDBManager.getStatisticotMischakim();
	}

	public GameListVO hosef(GameVO game) throws Exception {
		return mischakimDBManager.hosef(game);
	}

	public void adken(GameVO game) throws Exception {
		mischakimDBManager.adken(game);
	}

	public void mechak(Integer playerID) throws Exception {
		mischakimDBManager.mechak(playerID);
	}

	public void resetTable() throws Exception {
		mischakimDBManager.resetTable();
	}
	
	public void mechakKvutza(Integer groupID) throws Exception {
		mischakimDBManager.mechakKvutza(groupID);
	}

	public Vector<GameListVO> getMischakimLeIrbul(Integer ezorID,
			Integer baitID, Integer machzor) throws Exception {
		return mischakimDBManager.getMischakimLeIrbul(ezorID, baitID, machzor);
	}

	public Integer mezaheAcharonBeShimush() {
		return mischakimDBManager.mezaheAcharonBeShimush();
	}

	public GameListVO getNetuneyMischakMeleim(Integer gameID) throws Exception {
		return mischakimDBManager.getNetuneyMischakMeleim(gameID);
	}

	public EqualMapSet<SiduriShemVO, Vector<GroupVO>> getBatimVeKvutzotBeMachzor(
			int machzor) throws Exception {
		return mischakimDBManager.getBatimVeKvutzotBeMachzor(machzor);
	}

	public Vector<GroupVO> getReshimatKvutzot(int machzor) throws Exception {
		return mischakimDBManager.getReshimatKvutzot(machzor);
	}

	

}
