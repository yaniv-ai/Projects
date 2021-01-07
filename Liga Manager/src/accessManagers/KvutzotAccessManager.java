package accessManagers;

import helpers.EqualMapSet;

import java.util.Vector;

import vo.GroupVO;
import vo.SiduriShemVO;

import dbManagers.KvutzotDBManager;

public class KvutzotAccessManager {
	private KvutzotDBManager kvutzotDBManager;

	public KvutzotAccessManager() {
		kvutzotDBManager = new KvutzotDBManager();
	}

	public Vector<GroupVO> getReshima(boolean inLiga) throws Exception {
		return kvutzotDBManager.getReshima(inLiga);
	}

	public Vector<GroupVO> getReshima(Integer ezorID, Integer baitID)
			throws Exception {
		return kvutzotDBManager.getReshima(ezorID, baitID);
	}

	public Integer hosef(GroupVO group) throws Exception {
		return kvutzotDBManager.hosef(group);
	}

	public void adken(GroupVO group) throws Exception {
		kvutzotDBManager.adken(group);
	}

	public GroupVO getKvutza(Integer groupID) throws Exception {
		return kvutzotDBManager.getKvutza(groupID);
	}

	public void mechak(Integer groupID) throws Exception {
		kvutzotDBManager.mechak(groupID);
	}

	public void resetTable() throws Exception {
		kvutzotDBManager.resetTable();

	}

	public Integer mezaheAcharonBeShimush() {
		return kvutzotDBManager.mezaheAcharonBeShimush();
	}

	public EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>> getEzorimVeBatim()
			throws Exception {
		return kvutzotDBManager.getEzorimVeBatim();
	}

	public String getEzor(int ezorID) throws Exception {
		return kvutzotDBManager.getEzor(ezorID);
	}

	public String getBait(int baitID) throws Exception {
		return kvutzotDBManager.getBait(baitID);
	}

	public Vector<SiduriShemVO> getBatim() {
		return kvutzotDBManager.getBatim();
	}

	public Vector<SiduriShemVO> getEzorim() {
		return kvutzotDBManager.getEzorim();
	}
}
