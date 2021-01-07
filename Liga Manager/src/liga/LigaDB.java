package liga;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;

import enums.KodShem;
import vo.GameVO;
import vo.KvutzaLigaVO;
import vo.SiduriShemVO;
import helpers.EqualMapSet;

public class LigaDB {
	protected static EqualMapSet<Integer, SiduriShemVO> batim;
	protected static EqualMapSet<Integer, SiduriShemVO> ezorim;
	protected static EqualMapSet<Integer, SiduriShemVO> ligot;
	protected static EqualMapSet<Integer, SiduriShemVO> kvutzot;
	protected static EqualMapSet<Integer, KvutzaLigaVO> kvutzotBeLiga;
	protected static EqualMapSet<Integer, GameVO> mischakim;

	protected static final String BATIM_FILENAME = "batim.dat";
	protected static final String EZORIM_FILENAME = "ezorim.dat";
	protected static final String LIGOT_FILENAME = "ligot.dat";
	protected static final String KVUTZOT_FILENAME = "kvutzot.dat";
	protected static final String KVUTZOT_BE_LIGA_FILENAME = "kvutzotbeliga";
	protected static final String MISCHAKIM_FILENAME = "mischakim.dat";
	protected static final String DATA_PATH = "data\\";
	protected static final String EXTENTION = ".dat";

	private static Integer ligaId = null;

	public LigaDB() {
	}

	public static synchronized void kraTavlaotKlaliot() throws Exception {
		kvutzotBeLiga = new EqualMapSet<Integer, KvutzaLigaVO>();
		mischakim = new EqualMapSet<Integer, GameVO>();
		kraBatim();
		kraEzorim();
		kraLigot();
		kraKvutzot();
	}

	public static synchronized void kraTavlaotLiga() throws Exception {
		kraMischakim();
		kraKvutzotBeLiga();
	}

	protected static void kraKvutzotBeLiga() throws Exception {
		kvutzotBeLiga = new EqualMapSet<Integer, KvutzaLigaVO>();

		if (ligaId != null) {
			FileInputStream fin = null;
			ObjectInputStream oin = null;
			try {
				fin = new FileInputStream(DATA_PATH + KVUTZOT_BE_LIGA_FILENAME
						+ ligaId.toString() + EXTENTION);
				oin = new ObjectInputStream(fin);
				boolean end = false;
				do {
					KvutzaLigaVO kl = (KvutzaLigaVO) (oin.readObject());
					if (kl != null) {
						kvutzotBeLiga.put(kl.getGroupID(), kl);
					} else {
						end = true;
					}
				} while (!end);

			} catch (FileNotFoundException e) {
			} catch (EOFException e) {
			} catch (Exception e) {
				String message = e.getMessage();
				if (message == null || message.length() == 0) {
					message = "בעיה כללית בקריאת קובץ קבוצות בליגה";
				}
				throw new Exception(message);
			} finally {
				if (oin != null) {
					try {
						oin.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ קבוצות בליגה";
						}
						throw new Exception(message);
					}
				}
				if (fin != null) {
					try {
						fin.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ קבוצות בליגה";
						}
						throw new Exception(message);
					}
				}
			}
		}
	}

	protected synchronized void shmorKvutzotLiga() throws Exception {
		if (ligaId != null) {
			FileOutputStream fout = null;
			ObjectOutputStream oout = null;

			File f = new File(DATA_PATH + KVUTZOT_BE_LIGA_FILENAME
					+ ligaId.toString() + EXTENTION);
			File dir = new File(f.getParent());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			try {
				fout = new FileOutputStream(f);
				oout = new ObjectOutputStream(fout);
				Collection<KvutzaLigaVO> col = kvutzotBeLiga.values();
				Iterator<KvutzaLigaVO> i = col.iterator();
				while (i.hasNext()) {
					KvutzaLigaVO kl = i.next();
					oout.writeObject(kl);
				}
			} catch (Exception e) {
				String message = e.getMessage();
				if (message == null || message.length() == 0) {
					message = "בעיה כללית בשמירת קובץ קבוצות בליגה";
				}
				throw new Exception(message);
			} finally {
				if (oout != null) {
					try {
						oout.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ קבוצות בליגה";
						}
						throw new Exception(message);
					}
				}
				if (fout != null) {
					try {
						fout.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ קבוצות בליגה";
						}
						throw new Exception(message);
					}
				}
			}
		}
	}

	protected static void kraMischakim() throws Exception {
		mischakim = new EqualMapSet<Integer, GameVO>();

		if (ligaId != null) {
			FileInputStream fin = null;
			ObjectInputStream oin = null;
			try {
				fin = new FileInputStream(DATA_PATH + MISCHAKIM_FILENAME
						+ ligaId.toString() + EXTENTION);
				oin = new ObjectInputStream(fin);
				boolean end = false;
				do {
					GameVO game = (GameVO) (oin.readObject());
					if (game != null) {
						mischakim.put(game.getGameID(), game);
					} else {
						end = true;
					}
				} while (!end);
				// Liga.setLastGameIDInUse();
			} catch (FileNotFoundException e) {
			} catch (EOFException e) {
			} catch (Exception e) {
				String message = e.getMessage();
				if (message == null || message.length() == 0) {
					message = "בעיה כללית בקריאת קובץ משחקים";
				}
				throw new Exception(message);
			} finally {
				if (oin != null) {
					try {
						oin.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ משחקים";
						}
						throw new Exception(message);
					}
				}
				if (fin != null) {
					try {
						fin.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ משחקים";
						}
						throw new Exception(message);
					}
				}
			}
		}
	}

	protected void shmorMischakim() throws Exception {
		if (ligaId != null) {
			FileOutputStream fout = null;
			ObjectOutputStream oout = null;
			File f = new File(DATA_PATH + MISCHAKIM_FILENAME
					+ ligaId.toString() + EXTENTION);
			File dir = new File(f.getParent());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			try {
				fout = new FileOutputStream(f);
				oout = new ObjectOutputStream(fout);
				Collection<GameVO> col = mischakim.values();
				Iterator<GameVO> i = col.iterator();
				while (i.hasNext()) {
					GameVO g = i.next();
					oout.writeObject(g);
				}
			} catch (Exception e) {
				String message = e.getMessage();
				if (message == null || message.length() == 0) {
					message = "בעיה כללית בשמירת קובץ משחקים";
				}
				throw new Exception(message);
			} finally {
				if (oout != null) {
					try {
						oout.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ משחקים";
						}
						throw new Exception(message);
					}
				}
				if (fout != null) {
					try {
						fout.close();
					} catch (IOException e) {
						String message = e.getMessage();
						if (message == null || message.length() == 0) {
							message = "בעיה כללית בסגירת קובץ משחקים";
						}
						throw new Exception(message);
					}
				}
			}
		}
	}

	protected static void kraKvutzot() throws Exception {
		kvutzot = new EqualMapSet<Integer, SiduriShemVO>();

		FileInputStream fin = null;
		ObjectInputStream oin = null;

		try {
			fin = new FileInputStream(DATA_PATH + KVUTZOT_FILENAME + EXTENTION);
			oin = new ObjectInputStream(fin);
			boolean end = false;
			do {
				SiduriShemVO sd = (SiduriShemVO) (oin.readObject());
				if (sd != null) {
					kvutzot.put(sd.getSiduri(), sd);
				} else {
					end = true;
				}
			} while (!end);
			// Liga.setLastGroupIDInUse();
		} catch (FileNotFoundException e) {
		} catch (EOFException e) {
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בקריאת קובץ קבוצות";
			}
			throw new Exception(message);
		} finally {
			if (oin != null) {
				try {
					oin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ קבוצות";
					}
					throw new Exception(message);
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ קבוצות";
					}
					throw new Exception(message);

				}
			}
		}
	}

	protected synchronized void shmorKvutzot() throws Exception {
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		File batimF = new File(DATA_PATH + KVUTZOT_FILENAME + EXTENTION);
		File dir = new File(batimF.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			fout = new FileOutputStream(batimF);
			oout = new ObjectOutputStream(fout);
			Collection<SiduriShemVO> col = kvutzot.values();
			Iterator<SiduriShemVO> i = col.iterator();
			while (i.hasNext()) {
				SiduriShemVO sd = i.next();
				oout.writeObject(sd);
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בשמירת קובץ קבוצות";
			}
			throw new Exception(message);
		} finally {
			if (oout != null) {
				try {
					oout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ קבוצות";
					}
					throw new Exception(message);
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ קבוצות";
					}
					throw new Exception(message);
				}
			}
		}
	}

	public static void kraLigot() throws Exception {
		ligot = new EqualMapSet<Integer, SiduriShemVO>();
		FileInputStream fin = null;
		ObjectInputStream oin = null;

		try {
			fin = new FileInputStream(DATA_PATH + LIGOT_FILENAME + EXTENTION);
			oin = new ObjectInputStream(fin);
			boolean end = false;
			do {
				SiduriShemVO sd = (SiduriShemVO) (oin.readObject());
				if (sd != null) {
					ligot.put(sd.getSiduri(), sd);
				} else {
					end = true;
				}
			} while (!end);
			// Liga.setLastLigaIDInUse();
		} catch (FileNotFoundException e) {
		} catch (EOFException e) {
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בקריאת קובץ ליגות";
			}
			throw new Exception(message);
		} finally {
			if (oin != null) {
				try {
					oin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ ליגות";
					}
					throw new Exception(message);
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ ליגות";
					}
					throw new Exception(message);
				}
			}
		}
	}

	protected void shmorLigot() throws Exception {
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		File batimF = new File(DATA_PATH + LIGOT_FILENAME + EXTENTION);
		File dir = new File(batimF.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			fout = new FileOutputStream(batimF);
			oout = new ObjectOutputStream(fout);
			Collection<SiduriShemVO> col = ligot.values();
			Iterator<SiduriShemVO> i = col.iterator();
			while (i.hasNext()) {
				SiduriShemVO sd = i.next();
				oout.writeObject(sd);
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בשמירת קובץ ליגות";
			}
			throw new Exception(message);
		} finally {
			if (oout != null) {
				try {
					oout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ ליגות";
					}
					throw new Exception(message);
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ ליגות";
					}
					throw new Exception(message);
				}
			}
		}
	}

	protected static void kraEzorim() throws Exception {
		ezorim = new EqualMapSet<Integer, SiduriShemVO>();
		FileInputStream fin = null;
		ObjectInputStream oin = null;

		try {
			fin = new FileInputStream(DATA_PATH + EZORIM_FILENAME + EXTENTION);
			oin = new ObjectInputStream(fin);
			boolean end = false;
			do {
				SiduriShemVO sd = (SiduriShemVO) (oin.readObject());
				if (sd != null) {
					ezorim.put(sd.getSiduri(), sd);
				} else {
					end = true;
				}
			} while (!end);

		} catch (FileNotFoundException e) {
		} catch (EOFException e) {
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בסגירת קובץ אזורים";
			}
			throw new Exception(message);
		} finally {
			if (oin != null) {
				try {
					oin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ אזורים";
					}
					throw new Exception(message);
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בקריאת קובץ אזורים";
					}
					throw new Exception(message);
				}
			}
		}
	}

	protected void shmorEzorim() throws Exception {
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		File f = new File(DATA_PATH + EZORIM_FILENAME + EXTENTION);
		File dir = new File(f.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			fout = new FileOutputStream(f);
			oout = new ObjectOutputStream(fout);
			Collection<SiduriShemVO> col = ezorim.values();
			Iterator<SiduriShemVO> i = col.iterator();
			while (i.hasNext()) {
				SiduriShemVO sd = i.next();
				oout.writeObject(sd);
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בשמירת קובץ אזורים";
			}
			throw new Exception(message);
		} finally {
			if (oout != null) {
				try {
					oout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ אזורים";
					}
					throw new Exception(message);
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ בתאזוריםים";
					}
					throw new Exception(message);
				}
			}
		}
	}

	protected static void kraBatim() throws Exception {
		batim = new EqualMapSet<Integer, SiduriShemVO>();
		FileInputStream fin = null;
		ObjectInputStream oin = null;

		try {
			fin = new FileInputStream(DATA_PATH + BATIM_FILENAME + EXTENTION);
			oin = new ObjectInputStream(fin);
			boolean end = false;
			do {
				SiduriShemVO sd = (SiduriShemVO) (oin.readObject());
				if (sd != null) {
					batim.put(sd.getSiduri(), sd);
				} else {
					end = true;
				}
			} while (!end);

		} catch (FileNotFoundException e) {
		} catch (EOFException e) {
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בקריאת קובץ בתים";
			}
			throw new Exception(message);
		} finally {
			if (oin != null) {
				try {
					oin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ בתים";
					}
					throw new Exception(message);
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ בתים";
					}
					throw new Exception(message);
				}
			}
		}
	}

	protected void shmorBatim() throws Exception {
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		File f = new File(DATA_PATH + BATIM_FILENAME + EXTENTION);
		File dir = new File(f.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			fout = new FileOutputStream(f);
			oout = new ObjectOutputStream(fout);
			Collection<SiduriShemVO> col = batim.values();
			Iterator<SiduriShemVO> i = col.iterator();
			while (i.hasNext()) {
				SiduriShemVO sd = i.next();
				oout.writeObject(sd);
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית בשמירת קובץ בתים";
			}
			throw new Exception(message);
		} finally {
			if (oout != null) {
				try {
					oout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ בתים";
					}
					throw new Exception(message);
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					String message = e.getMessage();
					if (message == null || message.length() == 0) {
						message = "בעיה כללית בסגירת קובץ בתים";
					}
					throw new Exception(message);
				}
			}
		}
	}

	protected void resetAllFiles() throws Exception {
		File dir = new File(DATA_PATH);
		try {
			if (dir != null && dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (int i = 0; files != null && i < files.length; i++) {
					files[i].delete();

				}
			}
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "בעיה כללית באיפוס כל הקבצים";
			}
			throw new Exception(message);
		}
	}

	public static void setLigaId(Integer ligaId) {
		LigaDB.ligaId = ligaId;
	}

	public static Integer getLigaId() {
		return ligaId;
	}

	public String getName(Integer siduri, KodShem kodShem) throws Exception {
		if (siduri == null) {
			return null;
		}
		SiduriShemVO vo = null;
		switch (kodShem) {
		case bait:
			vo = batim.get(siduri);
			break;
		case ezor:
			vo = ezorim.get(siduri);
			break;
		case kvutza:
			vo = kvutzot.get(siduri);
			break;
		case liga:
			vo = ligot.get(siduri);
			break;
		default:
			break;
		}
		if (vo == null) {
			throw new Exception("Could not locate the name in database");
		}
		return vo.getShem();
	}

}
