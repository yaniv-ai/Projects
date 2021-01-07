package vo;

import java.io.Serializable;
import java.util.Calendar;

public class GameVO implements Comparable<GameVO>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7837983886958361473L;
	private Integer gameID;
	private Integer mekomitID;
	private Integer mekomitNekudot;
	private Integer yerivaID;
	private Integer yerivaNekudot;
	private Integer ezorID;
	private Integer baitID;
	private Integer machzor;
	private Integer sevev;
	private Calendar gameDate;

	public GameVO(Integer gameID, Integer mekomitID, Integer mekomitNekudot,
			Integer yerivaID, Integer yerivaNekudot, Integer ezorID,
			Integer baitID, Integer machzor, Integer sevev, Calendar gameDate) {
		super();
		this.gameID = gameID;
		this.mekomitID = mekomitID;
		this.mekomitNekudot = mekomitNekudot;
		this.yerivaID = yerivaID;
		this.yerivaNekudot = yerivaNekudot;
		this.ezorID = ezorID;
		this.baitID = baitID;
		this.machzor = machzor;
		this.sevev = sevev;
		this.gameDate = gameDate;
	}

	public Integer getGameID() {
		return gameID;
	}

	public Integer getMekomitID() {
		return mekomitID;
	}

	public Integer getMekomitNekudot() {
		return mekomitNekudot;
	}

	public Integer getYerivaID() {
		return yerivaID;
	}

	public Integer getYerivaNekudot() {
		return yerivaNekudot;
	}

	public Integer getEzorID() {
		return ezorID;
	}

	public Integer getBaitID() {
		return baitID;
	}

	public Integer getMachzor() {
		return machzor;
	}

	public Calendar getGameDate() {
		return gameDate;
	}

	public void setGameID(Integer gameID) {
		this.gameID = gameID;
	}

	public void setMekomitID(Integer mekomitID) {
		this.mekomitID = mekomitID;
	}

	public void setMekomitNekudot(Integer mekomitNekudot) {
		this.mekomitNekudot = mekomitNekudot;
	}

	public void setYerivaID(Integer yerivaID) {
		this.yerivaID = yerivaID;
	}

	public void setYerivaNekudot(Integer yerivaNekudot) {
		this.yerivaNekudot = yerivaNekudot;
	}

	public void setEzorID(Integer ezorID) {
		this.ezorID = ezorID;
	}

	public void setBaitID(Integer baitID) {
		this.baitID = baitID;
	}

	public void setMachzor(Integer machzor) {
		this.machzor = machzor;
	}

	public Integer getSevev() {
		return sevev;
	}

	public void setSevev(Integer sevev) {
		this.sevev = sevev;
	}

	public void setGameDate(Calendar gameDate) {
		this.gameDate = gameDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(GameVO.class)) {
			return false;
		}

		GameVO p = (GameVO) obj;
		if (mekomitID == null || yerivaID == null || p.getMekomitID() == null
				|| p.getYerivaID() == null) {
			return false;
		}
		if (mekomitID.equals(p.getMekomitID())
				&& yerivaID.equals(p.getYerivaID())) {
			return true;
		}
		if (yerivaID.equals(p.getMekomitID())
				&& mekomitID.equals(p.getYerivaID())) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(GameVO o) {
		if (gameID == null && o.getGameID() == null) {
			return 0;
		} else {
			if (gameID == null && o.getGameID() != null) {
				return -1;
			} else {
				if (gameID != null && o.getGameID() == null) {
					return 1;
				}
			}
		}

		if (gameID.intValue() == o.getGameID().intValue()) {
			return 0;
		} else {
			if (gameID.intValue() < o.getGameID().intValue()) {
				return -2;
			} else {
				return 2;
			}
		}
	}
}
