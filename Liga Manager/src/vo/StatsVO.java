package vo;

import java.io.Serializable;
import java.util.Calendar;

public class StatsVO implements Comparable<StatsVO>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1892198606873208090L;
	private Integer groupID;
	private String groupName;
	private Integer ezorID;
	private String ezorShem;
	private Integer baitID;
	private String baitShem;
	private Integer machzor;
	private Integer golimZchut;
	private Integer golimChova;
	private Integer hefreshNikud;
	private Integer wins;
	private Integer losses;
	private Integer draws;
	private Integer nikudKolel;
	private Calendar gameDate;

	public StatsVO(DBStatsVO dbStats) {
		super();
		this.groupID = dbStats.getGroupID();
		this.groupName = dbStats.getGroupName();
		this.ezorID = dbStats.getEzorID();
		this.ezorShem = dbStats.getEzorShem();
		this.baitID = dbStats.getBaitID();
		this.baitShem = dbStats.getBaitShem();
		this.machzor = dbStats.getMachzor();
		this.golimZchut = 0;
		this.golimChova = 0;
		this.hefreshNikud = 0;
		this.wins = 0;
		this.losses = 0;
		this.draws = 0;
		this.nikudKolel = 0;
		this.gameDate = dbStats.getGameDate();
	}

	public Integer getGroupID() {
		return groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public Integer getEzorID() {
		return ezorID;
	}

	public String getEzorShem() {
		return ezorShem;
	}

	public Integer getBaitID() {
		return baitID;
	}

	public String getBaitShem() {
		return baitShem;
	}

	public Integer getMachzor() {
		return machzor;
	}

	public Integer getGolimZchut() {
		return golimZchut;
	}

	public Integer getGolimChova() {
		return golimChova;
	}

	public Integer getHefreshNikud() {
		return hefreshNikud;
	}

	public Integer getWins() {
		return wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public Integer getDraws() {
		return draws;
	}

	public Integer getNikudKolel() {
		return nikudKolel;
	}

	public Calendar getGameDate() {
		return gameDate;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setEzorID(Integer ezorID) {
		this.ezorID = ezorID;
	}

	public void setEzorShem(String ezorShem) {
		this.ezorShem = ezorShem;
	}

	public void setBaitID(Integer baitID) {
		this.baitID = baitID;
	}

	public void setBaitShem(String baitShem) {
		this.baitShem = baitShem;
	}

	public void setMachzor(Integer machzor) {
		this.machzor = machzor;
	}

	public void setGolimZchut(Integer golimZchut) {
		this.golimZchut = golimZchut;
	}

	public void setGolimChova(Integer golimChova) {
		this.golimChova = golimChova;
	}

	public void setHefreshNikud(Integer hefreshNikud) {
		this.hefreshNikud = hefreshNikud;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public void setDraws(Integer draws) {
		this.draws = draws;
	}

	public void setNikudKolel(Integer nikudKolel) {
		this.nikudKolel = nikudKolel;
	}

	public void setGameDate(Calendar gameDate) {
		this.gameDate = gameDate;
	}

	public boolean equalsLigaRegionBaitMachzor(StatsVO obj) {
		if (obj == null) {
			return false;
		}

		if (this.getEzorID() == null || this.getBaitID() == null
				|| this.getMachzor() == null) {
			return false;
		}

		StatsVO p = (StatsVO) obj;
		if (p.getEzorID() == null || p.getBaitID() == null
				|| p.getMachzor() == null) {
			return false;
		}

		if (this.getEzorID().intValue() == p.getEzorID().intValue()
				&& this.getBaitID().intValue() == p.getBaitID().intValue()
				&& this.getMachzor().intValue() == p.getMachzor().intValue()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(StatsVO.class)) {
			return false;
		}

		if (this.getEzorID() == null || this.getBaitID() == null
				|| this.getMachzor() == null || this.getGroupID() == null) {
			return false;
		}

		StatsVO p = (StatsVO) obj;
		if (p.getEzorID() == null || p.getBaitID() == null
				|| p.getMachzor() == null || p.getGroupID() == null) {
			return false;
		}

		if (this.getEzorID().intValue() == p.getEzorID().intValue()
				&& this.getBaitID().intValue() == p.getBaitID().intValue()
				&& this.getMachzor().intValue() == p.getMachzor().intValue()
				&& this.getGroupID().intValue() == p.getGroupID().intValue()) {
			return true;
		}

		return false;
	}

	public int compareDate(StatsVO o) {
		if (gameDate.getTimeInMillis() < o.getGameDate().getTimeInMillis()) {
			return -10;
		} else if (gameDate.getTimeInMillis() == o.getGameDate()
				.getTimeInMillis()) {
			return 0;
		} else {
			return 10;
		}
	}

	@Override
	public int compareTo(StatsVO o) {
		if (ezorID.compareTo(o.getEzorID()) < 0) {
			return -50;
		} else if (ezorID.compareTo(o.getEzorID()) == 0) {

			if (baitID.compareTo(o.getBaitID()) < 0) {
				return -40;
			} else if (baitID.compareTo(o.getBaitID()) == 0) {
				if (machzor.compareTo(o.getMachzor()) < 0) {
					return -30;
				} else if (machzor.compareTo(o.getMachzor()) == 0) {
					if (nikudKolel.compareTo(o.getNikudKolel()) < 0) {
						return -20;
					} else if (nikudKolel.compareTo(o.getNikudKolel()) == 0) {
						if (groupID.compareTo(o.getGroupID()) < 0) {
							return -10;
						} else if (groupID.compareTo(o.getGroupID()) == 0) {
							return 0;
						} else {
							return 10;
						}
					} else {
						return 20;
					}
				} else {
					return 30;
				}
			} else {
				return 40;
			}
		} else {
			return 50;
		}
	}
}
