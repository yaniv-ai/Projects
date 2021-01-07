package dbManagers;

import helpers.EqualMapSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import enums.KodShem;

import liga.Liga;
import liga.LigaDB;

import vo.GroupVO;
import vo.KvutzaLigaVO;
import vo.SiduriShemVO;

public class KvutzotDBManager extends LigaDB {

	public KvutzotDBManager() {
	}

	public GroupVO getKvutza(Integer groupID) throws Exception {
		if (groupID == null) {
			throw new NullPointerException("Kvutza id cannot be null");
		}

		SiduriShemVO vo = kvutzot.get(groupID);
		if (vo == null) {
			throw new Exception("Could not locate the kvutza in database");
		}

		KvutzaLigaVO kl = kvutzotBeLiga.get(groupID);
		Integer ezorID = kl.getEzorID();
		Integer baitID = kl.getBaitID();
		String ezorShem = null;
		String baitShem = null;
		if (ezorID != null) {
			ezorShem = getName(ezorID, KodShem.ezor);
		}
		if (baitID != null) {
			baitShem = getName(baitID, KodShem.bait);
		}
		GroupVO group = new GroupVO(groupID, vo.getShem(), ezorID, ezorShem,
				baitID, baitShem);

		return group;

	}

	public Vector<GroupVO> getReshima(boolean inLiga) throws Exception {
		Vector<GroupVO> groupList = new Vector<GroupVO>();
		if (inLiga) {
			Collection<KvutzaLigaVO> col = kvutzotBeLiga.values();
			Iterator<KvutzaLigaVO> i = col.iterator();
			while (i.hasNext()) {
				KvutzaLigaVO kl = i.next();
				groupList.add(getKvutza(kl.getGroupID()));
			}
		} else {
			Collection<SiduriShemVO> col = kvutzot.values();
			Iterator<SiduriShemVO> i = col.iterator();
			while (i.hasNext()) {
				SiduriShemVO sd = i.next();
				if (!kvutzotBeLiga.containsKey(sd.getSiduri())) {
					groupList.add(new GroupVO(sd.getSiduri(), sd.getShem(),
							null, null, null, null));
				}
			}
		}
		return groupList;
	}

	public Vector<GroupVO> getReshima(Integer ezorID, Integer baitID)
			throws Exception {
		if (ezorID == null) {
			throw new NullPointerException("Ezor id cannot be null");
		}
		if (baitID == null) {
			throw new NullPointerException("Bait id cannot be null");
		}

		Vector<GroupVO> groupList = new Vector<GroupVO>();
		Collection<KvutzaLigaVO> kls = kvutzotBeLiga.values();
		Iterator<KvutzaLigaVO> ikls = kls.iterator();
		while (ikls.hasNext()) {
			KvutzaLigaVO kl = ikls.next();
			if (Liga.integerFieldAreEqual(kl.getEzorID(), ezorID)
					&& Liga.integerFieldAreEqual(kl.getBaitID(), baitID)) {
				groupList.add(getKvutza(kl.getGroupID()));
			}
		}
		return groupList;

	}

	public synchronized Integer hosef(GroupVO group) throws Exception {
		if (group == null) {
			throw new NullPointerException("Kvutza cannot be null");
		}

		if (group.getGroupID() == null) {
			group.setGroupID(mezaheAcharonBeShimush() + 1);
		}
		Integer ezorID = null;
		Integer baitID = null;

		if (group.getEzor() != null) {
			ezorID = group.getEzor().intValue();
		}
		if (group.getBait() != null) {
			baitID = group.getBait().intValue();
		}
		SiduriShemVO k = new SiduriShemVO(group.getGroupID().intValue(),
				group.getGroupName());
		KvutzaLigaVO kl = new KvutzaLigaVO(group.getGroupID().intValue(),
				ezorID, baitID);

		kvutzot.put(group.getGroupID().intValue(), k);
		shmorKvutzot();
		if (getLigaId() != null) {
			kvutzotBeLiga.put(group.getGroupID().intValue(), kl);
			shmorKvutzotLiga();
		}

		return group.getGroupID().intValue();
	}

	public synchronized void adken(GroupVO group) throws Exception {
		hosef(group);
	}

	public void mechak(Integer groupID) throws Exception {
		if (groupID == null) {
			throw new NullPointerException("Kvutza id cannot be null");
		}

		if (getLigaId() != null) {
			kvutzotBeLiga.remove(groupID);
			shmorKvutzotLiga();
		}
	}

	public void resetTable() throws Exception {
		if (getLigaId() != null) {
			kvutzotBeLiga = new EqualMapSet<Integer, KvutzaLigaVO>();
			shmorKvutzotLiga();
		}
	}

	public Integer mezaheAcharonBeShimush() {
		int mezaheAcharon = 0;
		for (int i = 0; i < kvutzot.size(); i++) {
			if (kvutzot.elementAt(i) > mezaheAcharon) {
				mezaheAcharon = kvutzot.elementAt(i);
			}
		}
		return mezaheAcharon;
	}

	public EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>> getEzorimVeBatim()
			throws Exception {
		EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>> regionsMapSet = new EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>>();

		Integer ezorID;
		String ezorShem;
		Integer baitID;
		String baitShem;
		Collection<KvutzaLigaVO> kls = kvutzotBeLiga.values();
		Iterator<KvutzaLigaVO> ikls = kls.iterator();
		while (ikls.hasNext()) {
			KvutzaLigaVO kl = ikls.next();

			ezorID = kl.getEzorID();
			baitID = kl.getBaitID();

			if (ezorID != null && baitID != null) {
				ezorShem = getName(ezorID, KodShem.ezor);
				baitShem = getName(baitID, KodShem.bait);

				SiduriShemVO newEzor = new SiduriShemVO(ezorID, ezorShem);
				SiduriShemVO newBait = new SiduriShemVO(baitID, baitShem);
				if (regionsMapSet.containsKey(newEzor)) {
					Vector<SiduriShemVO> vector = regionsMapSet.get(newEzor);
					vector.add(newBait);
					regionsMapSet.put(newEzor, vector);
				} else {
					Vector<SiduriShemVO> vector = new Vector<SiduriShemVO>();
					vector.add(newBait);
					regionsMapSet.put(newEzor, vector);
				}
			}
		}
		return regionsMapSet;
	}

	public String getEzor(Integer ezorID) throws Exception {
		if (ezorID == null) {
			throw new NullPointerException("Ezor id cannot be null");
		}

		return Liga.getLigaDB().getName(ezorID, KodShem.ezor);
	}

	public String getBait(Integer baitID) throws Exception {
		if (baitID == null) {
			throw new NullPointerException("Ezor id cannot be null");
		}

		return Liga.getLigaDB().getName(baitID, KodShem.bait);
	}

	public Vector<SiduriShemVO> getBatim() {
		Vector<SiduriShemVO> list = new Vector<SiduriShemVO>();
		Collection<SiduriShemVO> col = batim.values();
		Iterator<SiduriShemVO> i = col.iterator();
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			list.add(new SiduriShemVO(sd));
		}
		return list;
	}

	public Vector<SiduriShemVO> getEzorim() {
		Vector<SiduriShemVO> list = new Vector<SiduriShemVO>();
		Collection<SiduriShemVO> col = ezorim.values();
		Iterator<SiduriShemVO> i = col.iterator();
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			list.add(new SiduriShemVO(sd));
		}
		return list;
	}

}
