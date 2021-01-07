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
			throw new Exception("לא נשלח שם איזר");
		}

		Collection<SiduriShemVO> col = ezorim.values();
		Iterator<SiduriShemVO> i = col.iterator();
		int n = 0;
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			if (Liga.stringFieldAreEqual(sd.getShem(), newEzor)) {
				throw new Exception("איזור כבר קיים בשם זה");
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
			throw new Exception("לא נשלח שם בית");
		}

		Collection<SiduriShemVO> col = batim.values();
		Iterator<SiduriShemVO> i = col.iterator();
		int n = 0;
		while (i.hasNext()) {
			SiduriShemVO sd = i.next();
			if (Liga.stringFieldAreEqual(sd.getShem(), newBait)) {
				throw new Exception("בית כבר קיים בשם זה");
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
				new SiduriShemVO(Liga.EZOR_LIGA, "אזור ליגה"));
		ezorim.put(Liga.EZOR_MEORAV, new SiduriShemVO(Liga.EZOR_MEORAV,
				"אזור מעורב"));
		hosefEzor("צפון");
		hosefEzor("מרכז/דרום");

		batim.put(Liga.BAIT_MEORAV, new SiduriShemVO(Liga.BAIT_MEORAV,
				"בית מעורב"));
		batim.put(1, new SiduriShemVO(1, "א"));
		batim.put(2, new SiduriShemVO(2, "ב"));
		batim.put(3, new SiduriShemVO(3, "ג"));
		batim.put(4, new SiduriShemVO(4, "ד"));
		batim.put(5, new SiduriShemVO(5, "ה"));
		batim.put(6, new SiduriShemVO(6, "ו"));
		batim.put(7, new SiduriShemVO(7, "ז"));
		batim.put(8, new SiduriShemVO(8, "ח"));
		batim.put(9, new SiduriShemVO(9, "ט"));
		batim.put(10, new SiduriShemVO(10, " י"));
		batim.put(11, new SiduriShemVO(11, "אי"));
		batim.put(12, new SiduriShemVO(12, "בי"));
		batim.put(13, new SiduriShemVO(13, "גי"));
		batim.put(14, new SiduriShemVO(14, "די"));
		batim.put(15, new SiduriShemVO(15, "הי"));
		batim.put(16, new SiduriShemVO(16, "וי"));
		batim.put(17, new SiduriShemVO(17, "זי"));
		batim.put(18, new SiduriShemVO(18, "חי"));
		batim.put(19, new SiduriShemVO(19, "טי"));
		batim.put(20, new SiduriShemVO(20, " כ"));
		batim.put(21, new SiduriShemVO(21, "אכ"));
		batim.put(22, new SiduriShemVO(22, "בכ"));
		batim.put(23, new SiduriShemVO(23, "גכ"));
		batim.put(24, new SiduriShemVO(24, "דכ"));
		batim.put(25, new SiduriShemVO(25, "הכ"));
		batim.put(26, new SiduriShemVO(26, "וכ"));
		batim.put(27, new SiduriShemVO(27, "זכ"));
		batim.put(28, new SiduriShemVO(28, "חכ"));
		batim.put(29, new SiduriShemVO(29, "טכ"));
		batim.put(30, new SiduriShemVO(30, " ל"));
		batim.put(31, new SiduriShemVO(31, "אל"));
		batim.put(32, new SiduriShemVO(32, "בל"));
		batim.put(33, new SiduriShemVO(33, "גל"));
		batim.put(34, new SiduriShemVO(34, "דל"));
		batim.put(35, new SiduriShemVO(35, "הל"));
		batim.put(36, new SiduriShemVO(36, "ול"));
		batim.put(37, new SiduriShemVO(37, "זל"));
		batim.put(38, new SiduriShemVO(38, "חל"));
		batim.put(39, new SiduriShemVO(39, "טל"));
		batim.put(40, new SiduriShemVO(40, " מ"));
		batim.put(41, new SiduriShemVO(41, "אמ"));
		batim.put(42, new SiduriShemVO(42, "במ"));
		batim.put(43, new SiduriShemVO(43, "גמ"));
		batim.put(44, new SiduriShemVO(44, "דמ"));
		batim.put(45, new SiduriShemVO(45, "המ"));
		batim.put(46, new SiduriShemVO(46, "ומ"));
		batim.put(47, new SiduriShemVO(47, "זמ"));
		batim.put(48, new SiduriShemVO(48, "חמ"));
		batim.put(49, new SiduriShemVO(49, "טמ"));
		batim.put(50, new SiduriShemVO(50, " נ"));
		batim.put(51, new SiduriShemVO(51, "אנ"));
		batim.put(52, new SiduriShemVO(52, "בנ"));
		batim.put(53, new SiduriShemVO(53, "גנ"));
		batim.put(54, new SiduriShemVO(54, "דנ"));
		batim.put(55, new SiduriShemVO(55, "הנ"));
		batim.put(56, new SiduriShemVO(56, "ונ"));
		batim.put(57, new SiduriShemVO(57, "זנ"));
		batim.put(58, new SiduriShemVO(58, "חנ"));
		batim.put(59, new SiduriShemVO(59, "טנ"));
		batim.put(60, new SiduriShemVO(60, " ס"));
		batim.put(61, new SiduriShemVO(61, "אס"));
		batim.put(62, new SiduriShemVO(62, "בס"));
		batim.put(63, new SiduriShemVO(63, "גס"));
		batim.put(64, new SiduriShemVO(64, "דס"));
		batim.put(65, new SiduriShemVO(65, "הס"));
		batim.put(66, new SiduriShemVO(66, "וס"));
		batim.put(67, new SiduriShemVO(67, "זס"));
		batim.put(68, new SiduriShemVO(68, "חס"));
		batim.put(69, new SiduriShemVO(69, "טס"));
		batim.put(70, new SiduriShemVO(70, " ע"));
		batim.put(71, new SiduriShemVO(71, "אע"));
		batim.put(72, new SiduriShemVO(72, "בע"));
		batim.put(73, new SiduriShemVO(73, "גע"));
		batim.put(74, new SiduriShemVO(74, "דע"));
		batim.put(75, new SiduriShemVO(75, "הע"));
		batim.put(76, new SiduriShemVO(76, "וע"));
		batim.put(77, new SiduriShemVO(77, "זע"));
		batim.put(78, new SiduriShemVO(78, "חע"));
		batim.put(79, new SiduriShemVO(79, "טע"));
		batim.put(80, new SiduriShemVO(80, " פ"));
		batim.put(81, new SiduriShemVO(81, "אפ"));
		batim.put(82, new SiduriShemVO(82, "בפ"));
		batim.put(83, new SiduriShemVO(83, "גפ"));
		batim.put(84, new SiduriShemVO(84, "דפ"));
		batim.put(85, new SiduriShemVO(85, "הפ"));
		batim.put(86, new SiduriShemVO(86, "ופ"));
		batim.put(87, new SiduriShemVO(87, "זפ"));
		batim.put(88, new SiduriShemVO(88, "חפ"));
		batim.put(89, new SiduriShemVO(89, "טפ"));
		batim.put(90, new SiduriShemVO(90, " צ"));
		batim.put(91, new SiduriShemVO(91, "אצ"));
		batim.put(92, new SiduriShemVO(92, "בצ"));
		batim.put(93, new SiduriShemVO(93, "גצ"));
		batim.put(94, new SiduriShemVO(94, "דצ"));
		batim.put(95, new SiduriShemVO(95, "הצ"));
		batim.put(96, new SiduriShemVO(96, "וצ"));
		batim.put(97, new SiduriShemVO(97, "זצ"));
		batim.put(98, new SiduriShemVO(98, "חצ"));
		batim.put(99, new SiduriShemVO(99, "טצ"));

		shmorBatim();
		shmorEzorim();
		shmorLigot();
	}
}
