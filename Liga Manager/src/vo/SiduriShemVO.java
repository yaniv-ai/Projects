package vo;

import java.io.Serializable;

public class SiduriShemVO implements Comparable<SiduriShemVO>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6177374479363967366L;
	private Integer siduri;
	private String shem;

	public SiduriShemVO(Integer siduri, String shem) {
		super();
		this.siduri = siduri;
		this.shem = shem;
	}

	public SiduriShemVO(SiduriShemVO ezorLiga) {
		super();
		this.siduri = ezorLiga.getSiduri();
		this.shem = ezorLiga.getShem();
	}

	public Integer getSiduri() {
		return siduri;
	}

	public String getShem() {
		return shem;
	}

	public void setSiduri(Integer siduri) {
		this.siduri = siduri;
	}

	public void setShem(String shem) {
		this.shem = shem;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(SiduriShemVO.class)) {
			return false;
		}

		if (siduri == null) {
			return false;
		}

		SiduriShemVO p = (SiduriShemVO) obj;
		if (p.getSiduri() == null) {
			return false;
		}

		if (siduri.equals(p.getSiduri())) {
			return true;
		}

		return false;
	}

	@Override
	public int compareTo(SiduriShemVO o) {
		if (siduri == null && o.getSiduri() == null) {
			return 0;
		} else {
			if (siduri == null && o.getSiduri() != null) {
				return -1;
			} else {
				if (siduri != null && o.getSiduri() == null) {
					return 1;
				}
			}
		}

		if (siduri.intValue() == o.getSiduri().intValue()) {
			return 0;
		} else {
			if (siduri.intValue() < o.getSiduri().intValue()) {
				return -2;
			} else {
				return 2;
			}
		}
	}
}
