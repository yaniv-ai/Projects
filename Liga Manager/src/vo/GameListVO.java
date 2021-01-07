package vo;

import java.util.Calendar;

public class GameListVO extends GameVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5190337249858578899L;
	private String mekomitShem;;
	private String yerivaShem;
	private String ezorShem;
	private String baitShem;

	public GameListVO(Integer gameID, Integer mekomitID,
			Integer mekomitNekudot, Integer yerivaID, Integer yerivaNekudot,
			Integer ezorID, String ezorShem, Integer baitID, String baitShem,
			Integer machzor, Integer sevev, Calendar gameDate,
			String mekomitShem, String yerivaShem) {
		super(gameID, mekomitID, mekomitNekudot, yerivaID, yerivaNekudot,
				ezorID, baitID, machzor, sevev, gameDate);
		this.mekomitShem = mekomitShem;
		this.yerivaShem = yerivaShem;
		this.ezorShem = ezorShem;
		this.baitShem = baitShem;
	}

	public String getMekomitShem() {
		return mekomitShem;
	}

	public String getYerivaShem() {
		return yerivaShem;
	}

	public String getEzorShem() {
		return ezorShem;
	}

	public String getBaitShem() {
		return baitShem;
	}

	public void setMekomitShem(String mekomitShem) {
		this.mekomitShem = mekomitShem;
	}

	public void setYerivaShem(String yerivaShem) {
		this.yerivaShem = yerivaShem;
	}

	public void setEzorShem(String ezorShem) {
		this.ezorShem = ezorShem;
	}

	public void setBaitShem(String baitShem) {
		this.baitShem = baitShem;
	}
}
