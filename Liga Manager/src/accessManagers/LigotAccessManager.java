package accessManagers;

import java.util.Vector;

import vo.SiduriShemVO;
import dbManagers.LigotDBManager;

public class LigotAccessManager {
	private LigotDBManager ligotDBManager;

	public LigotAccessManager() {
		ligotDBManager = new LigotDBManager();
	}

	public Integer mezaheAcharonBeShimush() {
		return ligotDBManager.mezaheAcharonBeShimush();
	}

	public Vector<SiduriShemVO> getList() {
		return ligotDBManager.getList();
	}

	public void update(SiduriShemVO liga) throws Exception {
		ligotDBManager.update(liga);
	}

	public Integer hosef(SiduriShemVO liga) throws Exception {
		return ligotDBManager.hosef(liga);
	}

	public void mechak(Integer ligaID) throws Exception {
		ligotDBManager.mechak(ligaID);
	}

	public void hosefEzor(String newEzor) throws Exception {
		ligotDBManager.hosefEzor(newEzor);
	}

	public void hosefBait(String newBait) throws Exception {
		ligotDBManager.hosefBait(newBait);
	}

	public void resetTable() throws Exception {
		ligotDBManager.resetTable();
	}
}
