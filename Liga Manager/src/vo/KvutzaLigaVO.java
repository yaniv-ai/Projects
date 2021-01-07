package vo;

import java.io.Serializable;

public class KvutzaLigaVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6144542618559356573L;
	private Integer groupID;
	private Integer ezorID;
	private Integer baitID;

	public KvutzaLigaVO(Integer groupID, Integer ezorID, Integer baitID) {
		super();
		this.groupID = groupID;
		this.ezorID = ezorID;
		this.baitID = baitID;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public Integer getEzorID() {
		return ezorID;
	}

	public Integer getBaitID() {
		return baitID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public void setEzorID(Integer ezorID) {
		this.ezorID = ezorID;
	}

	public void setBaitID(Integer baitID) {
		this.baitID = baitID;
	}

}
