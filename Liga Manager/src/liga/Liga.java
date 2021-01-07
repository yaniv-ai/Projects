package liga;

import helpers.EqualMapSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import accessManagers.ExcelAccessManager;
import accessManagers.KvutzotAccessManager;
import accessManagers.LigotAccessManager;
import accessManagers.MischakimAccessManager;
import actions.ActionManager;

public class Liga {

	public static final String excelFileName = "properties\\TeinatKvutzot.txt";
	public static final String groupsFileName = "files\\TeinatKvutzot.txt";
	private static Integer lastActionManagerIDInUse = 0;

	public static final Integer EZOR_MEORAV = 1;
	public static final Integer BAIT_MEORAV = 9999;
	public static final Integer EZOR_LIGA = 2;

	private static Liga instance = new Liga();
	public static SimpleDateFormat sdf;
	private final static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String VERSION = "Version: 3.0";
	private static Integer ligaUsesCounter = 0;
	protected final static String connector = "com.mysql.jdbc.Driver";

	private static ExcelAccessManager excelAccessManager;
	private static MischakimAccessManager mischakimAccessManager;
	private static KvutzotAccessManager kvutzotAccessManager;
	private static LigotAccessManager ligotAccessManager;
	private static LigaDB ligaDB;

	private static EqualMapSet<Integer, ActionManager> actionManagers = null;

	static {
		try {
			actionManagers = new EqualMapSet<Integer, ActionManager>();
			sdf = new SimpleDateFormat(dateFormat);

			ligaDB = new LigaDB();
			LigaDB.kraTavlaotKlaliot();

			excelAccessManager = new ExcelAccessManager();
			mischakimAccessManager = new MischakimAccessManager();
			kvutzotAccessManager = new KvutzotAccessManager();
			ligotAccessManager = new LigotAccessManager();

			File f = new File(groupsFileName);
			File dir = new File(f.getParent());
			if (!dir.exists()) {
				dir.mkdirs();
				FileOutputStream fout = new FileOutputStream(f);
				fout.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			instance = null;
		}
	}

	private Liga() {
	}

	public static Liga getInstance() throws Exception {
		if (instance == null) {
			instance.activateLiga();
		}

		if (instance != null) {
			return instance;
		} else {
			throw new Exception("Bank not active");
		}
	}

	private void activateLiga() {
		instance.getClass();
	}

	public static synchronized Integer getLigaUsesCounter() {
		return ++ligaUsesCounter;
	}

	public static String calendarToString(Calendar cal) {
		String dateString = null;
		if (cal != null) {
			Integer day = cal.get(Calendar.DAY_OF_MONTH);
			Integer month = cal.get(Calendar.MONTH) + 1;
			Integer year = cal.get(Calendar.YEAR);
			dateString = day.toString() + "/" + month.toString() + "/"
					+ year.toString();
		}
		return dateString;
	}

	public static ExcelAccessManager getExcelAccessManager() {
		return excelAccessManager;
	}

	public static MischakimAccessManager getMischakimAccessManager() {
		return mischakimAccessManager;
	}

	public static KvutzotAccessManager getKvutzotAccessManager() {
		return kvutzotAccessManager;
	}

	public static LigotAccessManager getLigotAccessManager() {
		return ligotAccessManager;
	}

	public static LigaDB getLigaDB() {
		return ligaDB;
	}

	public static boolean integerFieldAreEqual(Integer a, Integer b) {
		if (a == null & b == null) {
			return true;
		} else if (a != null && b != null && a.intValue() == b.intValue()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean stringFieldAreEqual(String a, String b) {
		if (a == null & b == null) {
			return true;
		} else if (a != null && b != null && a.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean calendarFieldAreEqual(Calendar a, Calendar b) {
		if (a == null & b == null) {
			return true;
		} else if (a != null && b != null
				&& a.getTimeInMillis() == b.getTimeInMillis()) {
			return true;
		} else {
			return false;
		}
	}

	public static Integer addActionManager() {
		lastActionManagerIDInUse++;
		actionManagers.put(lastActionManagerIDInUse, new ActionManager());
		return lastActionManagerIDInUse;
	}

	public static ActionManager getActionManager(Integer id) {
		if (id == null) {
			return null;
		}

		return actionManagers.get(id);
	}

	public static String getExcelFileName() {
		String filename = null;

		File in = new File(excelFileName);
		BufferedReader bufferedIn = null;
		try {
			bufferedIn = new BufferedReader(new FileReader(in));
			filename = bufferedIn.readLine();
		} catch (Exception e) {
			filename = null;
		} finally {
			if (bufferedIn != null)
				try {
					bufferedIn.close();
				} catch (Exception e) {
				}
		}

		return filename;
	}

	public static void setExcelFileName(String filename) {
		if (filename == null || filename.length() == 0) {
			return;
		}
		int n = filename.lastIndexOf('\\');
		if (n >= 0) {
			filename = filename.substring(0, n);
		}
		FileOutputStream fout = null;
		Writer writerOut = null;
		try {
			File f = new File(excelFileName);
			File dir = new File(f.getParent());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			fout = new FileOutputStream(f);
			writerOut = new BufferedWriter(new OutputStreamWriter(fout));
			writerOut.append(filename).append("\r\n");
			writerOut.flush();
		} catch (Exception e) {

		} finally {
			if (writerOut != null) {
				try {
					writerOut.close();
				} catch (Exception e) {

				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception e) {

				}
			}
		}
	}
}
