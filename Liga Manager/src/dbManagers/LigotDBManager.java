package dbManagers;

import helpers.EqualMapSet;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import liga.Liga;
import liga.LigaDB;
import vo.GameVO;
import vo.KvutzaLigaVO;
import vo.SiduriShemVO;

public class LigotDBManager extends LigaDB {

	public LigotDBManager() {
	}

	public Vector<SiduriShemVO> getList() {
		Vector<SiduriShemVO> list = new Vector<SiduriShemVO>();
		Collection<SiduriShemVO> col = ligot.values();
		Iterator<SiduriShemVO> i = col.iterator();
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			list.add(new SiduriShemVO(sd));
		}
		return list;
	}

	public Integer mezaheAcharonBeShimush() {
		int mezaheAcharon = 0;
		for (int i = 0; i < ligot.size(); i++) {
			if (ligot.elementAt(i) > mezaheAcharon) {
				mezaheAcharon = ligot.elementAt(i);
			}
		}
		return mezaheAcharon;
	}

	public Integer hosef(SiduriShemVO liga) throws Exception {
		if (liga == null) {
			return null;
		}

		if (liga.getSiduri() == null) {
			liga.setSiduri(mezaheAcharonBeShimush() + 1);
		}

		ligot.put(liga.getSiduri().intValue(), liga);
		shmorLigot();

		return liga.getSiduri().intValue();

	}

	public void update(SiduriShemVO liga) throws Exception {
		hosef(liga);
	}

	public void mechak(Integer ligaID) throws Exception {
		if (ligaID == null) {
			throw new NullPointerException("liga id cannot be null");
		}
		if (Liga.integerFieldAreEqual(ligaID, getLigaId())) {
			kvutzotBeLiga = new EqualMapSet<Integer, KvutzaLigaVO>();
			mischakim = new EqualMapSet<Integer, GameVO>();

		}

		ligot.remove(ligaID);
		shmorLigot();

		File fKvutza = new File(DATA_PATH + KVUTZOT_BE_LIGA_FILENAME
				+ ligaID.toString() + EXTENTION);
		File fmischak = new File(DATA_PATH + MISCHAKIM_FILENAME
				+ ligaID.toString() + EXTENTION);
		fKvutza.delete();
		fmischak.delete();
	}

	public boolean hosefEzor(String newEzor) throws Exception {
		if (newEzor == null || newEzor.length() == 0) {
			throw new Exception("�� ���� �� ����");
		}

		Collection<SiduriShemVO> col = ezorim.values();
		Iterator<SiduriShemVO> i = col.iterator();
		int n = 0;
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			if (Liga.stringFieldAreEqual(sd.getShem(), newEzor)) {
				throw new Exception("����� ��� ���� ��� ��");
			}
			if (sd.getSiduri() > n) {
				n = sd.getSiduri();
			}
		}
		n++;
		ezorim.put(n, new SiduriShemVO(n, newEzor));
		shmorEzorim();
		return true;
	}

	public boolean hosefBait(String newBait) throws Exception {
		if (newBait == null || newBait.length() == 0) {
			throw new Exception("�� ���� �� ���");
		}

		Collection<SiduriShemVO> col = batim.values();
		Iterator<SiduriShemVO> i = col.iterator();
		int n = 0;
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			if (Liga.stringFieldAreEqual(sd.getShem(), newBait)) {
				throw new Exception("��� ��� ���� ��� ��");
			}
			if (sd.getSiduri() > n) {
				n = sd.getSiduri();
			}
		}
		n++;
		batim.put(n, new SiduriShemVO(n, newBait));
		shmorBatim();
		return true;
	}

	public void resetTable() throws Exception {
		resetAllFiles();
		batim = new EqualMapSet<Integer, SiduriShemVO>();
		ezorim = new EqualMapSet<Integer, SiduriShemVO>();
		ligot = new EqualMapSet<Integer, SiduriShemVO>();
		kvutzot = new EqualMapSet<Integer, SiduriShemVO>();
		kvutzotBeLiga = new EqualMapSet<Integer, KvutzaLigaVO>();
		mischakim = new EqualMapSet<Integer, GameVO>();

		ezorim.put(Liga.EZOR_LIGA,
				new SiduriShemVO(Liga.EZOR_LIGA, "���� ����"));
		ezorim.put(Liga.EZOR_MEORAV, new SiduriShemVO(Liga.EZOR_MEORAV,
				"���� �����"));
		hosefEzor("����");
		hosefEzor("����/����");

		batim.put(Liga.BAIT_MEORAV, new SiduriShemVO(Liga.BAIT_MEORAV,
				"��� �����"));
		batim.put(1, new SiduriShemVO(1, "�"));
		batim.put(2, new SiduriShemVO(2, "�"));
		batim.put(3, new SiduriShemVO(3, "�"));
		batim.put(4, new SiduriShemVO(4, "�"));
		batim.put(5, new SiduriShemVO(5, "�"));
		batim.put(6, new SiduriShemVO(6, "�"));
		batim.put(7, new SiduriShemVO(7, "�"));
		batim.put(8, new SiduriShemVO(8, "�"));
		batim.put(9, new SiduriShemVO(9, "�"));
		batim.put(10, new SiduriShemVO(10, " �"));
		batim.put(11, new SiduriShemVO(11, "��"));
		batim.put(12, new SiduriShemVO(12, "��"));
		batim.put(13, new SiduriShemVO(13, "��"));
		batim.put(14, new SiduriShemVO(14, "��"));
		batim.put(15, new SiduriShemVO(15, "��"));
		batim.put(16, new SiduriShemVO(16, "��"));
		batim.put(17, new SiduriShemVO(17, "��"));
		batim.put(18, new SiduriShemVO(18, "��"));
		batim.put(19, new SiduriShemVO(19, "��"));
		batim.put(20, new SiduriShemVO(20, " �"));
		batim.put(21, new SiduriShemVO(21, "��"));
		batim.put(22, new SiduriShemVO(22, "��"));
		batim.put(23, new SiduriShemVO(23, "��"));
		batim.put(24, new SiduriShemVO(24, "��"));
		batim.put(25, new SiduriShemVO(25, "��"));
		batim.put(26, new SiduriShemVO(26, "��"));
		batim.put(27, new SiduriShemVO(27, "��"));
		batim.put(28, new SiduriShemVO(28, "��"));
		batim.put(29, new SiduriShemVO(29, "��"));
		batim.put(30, new SiduriShemVO(30, " �"));
		batim.put(31, new SiduriShemVO(31, "��"));
		batim.put(32, new SiduriShemVO(32, "��"));
		batim.put(33, new SiduriShemVO(33, "��"));
		batim.put(34, new SiduriShemVO(34, "��"));
		batim.put(35, new SiduriShemVO(35, "��"));
		batim.put(36, new SiduriShemVO(36, "��"));
		batim.put(37, new SiduriShemVO(37, "��"));
		batim.put(38, new SiduriShemVO(38, "��"));
		batim.put(39, new SiduriShemVO(39, "��"));
		batim.put(40, new SiduriShemVO(40, " �"));
		batim.put(41, new SiduriShemVO(41, "��"));
		batim.put(42, new SiduriShemVO(42, "��"));
		batim.put(43, new SiduriShemVO(43, "��"));
		batim.put(44, new SiduriShemVO(44, "��"));
		batim.put(45, new SiduriShemVO(45, "��"));
		batim.put(46, new SiduriShemVO(46, "��"));
		batim.put(47, new SiduriShemVO(47, "��"));
		batim.put(48, new SiduriShemVO(48, "��"));
		batim.put(49, new SiduriShemVO(49, "��"));
		batim.put(50, new SiduriShemVO(50, " �"));
		batim.put(51, new SiduriShemVO(51, "��"));
		batim.put(52, new SiduriShemVO(52, "��"));
		batim.put(53, new SiduriShemVO(53, "��"));
		batim.put(54, new SiduriShemVO(54, "��"));
		batim.put(55, new SiduriShemVO(55, "��"));
		batim.put(56, new SiduriShemVO(56, "��"));
		batim.put(57, new SiduriShemVO(57, "��"));
		batim.put(58, new SiduriShemVO(58, "��"));
		batim.put(59, new SiduriShemVO(59, "��"));
		batim.put(60, new SiduriShemVO(60, " �"));
		batim.put(61, new SiduriShemVO(61, "��"));
		batim.put(62, new SiduriShemVO(62, "��"));
		batim.put(63, new SiduriShemVO(63, "��"));
		batim.put(64, new SiduriShemVO(64, "��"));
		batim.put(65, new SiduriShemVO(65, "��"));
		batim.put(66, new SiduriShemVO(66, "��"));
		batim.put(67, new SiduriShemVO(67, "��"));
		batim.put(68, new SiduriShemVO(68, "��"));
		batim.put(69, new SiduriShemVO(69, "��"));
		batim.put(70, new SiduriShemVO(70, " �"));
		batim.put(71, new SiduriShemVO(71, "��"));
		batim.put(72, new SiduriShemVO(72, "��"));
		batim.put(73, new SiduriShemVO(73, "��"));
		batim.put(74, new SiduriShemVO(74, "��"));
		batim.put(75, new SiduriShemVO(75, "��"));
		batim.put(76, new SiduriShemVO(76, "��"));
		batim.put(77, new SiduriShemVO(77, "��"));
		batim.put(78, new SiduriShemVO(78, "��"));
		batim.put(79, new SiduriShemVO(79, "��"));
		batim.put(80, new SiduriShemVO(80, " �"));
		batim.put(81, new SiduriShemVO(81, "��"));
		batim.put(82, new SiduriShemVO(82, "��"));
		batim.put(83, new SiduriShemVO(83, "��"));
		batim.put(84, new SiduriShemVO(84, "��"));
		batim.put(85, new SiduriShemVO(85, "��"));
		batim.put(86, new SiduriShemVO(86, "��"));
		batim.put(87, new SiduriShemVO(87, "��"));
		batim.put(88, new SiduriShemVO(88, "��"));
		batim.put(89, new SiduriShemVO(89, "��"));
		batim.put(90, new SiduriShemVO(90, " �"));
		batim.put(91, new SiduriShemVO(91, "��"));
		batim.put(92, new SiduriShemVO(92, "��"));
		batim.put(93, new SiduriShemVO(93, "��"));
		batim.put(94, new SiduriShemVO(94, "��"));
		batim.put(95, new SiduriShemVO(95, "��"));
		batim.put(96, new SiduriShemVO(96, "��"));
		batim.put(97, new SiduriShemVO(97, "��"));
		batim.put(98, new SiduriShemVO(98, "��"));
		batim.put(99, new SiduriShemVO(99, "��"));

		shmorBatim();
		shmorEzorim();
		shmorLigot();
	}
}
