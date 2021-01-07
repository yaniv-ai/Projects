package vo;

import java.io.Serializable;
import java.util.Calendar;

public class DBStatsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2690185248875394903L;
	private Integer groupID;
	private String groupName;
	private Integer ezorID;
	private String ezorShem;
	private Integer baitID;
	private String baitShem;
	private Integer machzor;
	private Integer sevev;
	private Integer golimZchut;
	private Integer golimChova;
	private Calendar gameDate;

	public DBStatsVO(Integer groupID, String groupName, Integer ezorID,
			String ezorShem, Integer baitID, String baitShem, Integer machzor,
			Integer sevev, Integer golimZchut, Integer golimChova,
			Calendar gameDate) {
		super();
		this.groupID = groupID;
		this.groupName = groupName;
		this.ezorID = ezorID;
		this.ezorShem = ezorShem;
		this.baitID = baitID;
		this.baitShem = baitShem;
		this.machzor = machzor;
		this.sevev = sevev;
		this.golimZchut = golimZchut;
		this.golimChova = golimChova;
		this.gameDate = gameDate;
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

	public Integer getSevev() {
		return sevev;
	}

	public void setSevev(Integer sevev) {
		this.sevev = sevev;
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

	public void setGameDate(Calendar gameDate) {
		this.gameDate = gameDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(DBStatsVO.class)) {
			return false;
		}

		if (this.getEzorID() == null || this.getBaitID() == null
				|| this.getMachzor() == null || this.getGroupID() == null) {
			return false;
		}

		DBStatsVO p = (DBStatsVO) obj;
		if (p.getEzorID() == null || p.getBaitID() == null
				|| p.getMachzor() == null || p.getGroupID() == null) {
			return false;
		}

		if (this.getEzorID().intValue() == p.getEzorID().intValue()
				&& this.getBaitID().intValue() == p.baitID.intValue()
				&& this.getMachzor().intValue() == p.getMachzor().intValue()
				&& this.getGroupID().intValue() == p.getGroupID().intValue()) {
			return true;
		}

		return false;
	}
}
