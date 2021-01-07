package vo;

import java.io.Serializable;

public class GroupVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -845585532904629603L;
	private Integer groupID;
	private String groupName;
	private Integer ezor;
	private String ezorName;
	private Integer bait;
	private String baitName;

	public GroupVO(Integer groupID, String groupName, Integer ezor,
			String ezorName, Integer bait, String baitName) {
		super();
		this.groupID = groupID;
		this.groupName = groupName;
		this.ezor = ezor;
		this.ezorName = ezorName;
		this.bait = bait;
		this.baitName = baitName;
	}

	public GroupVO(GroupVO group) {
		this.groupID = group.getGroupID();
		this.groupName = group.getGroupName();
		this.ezor = group.getEzor();
		this.ezorName = group.getEzorName();
		this.bait = group.getBait();
		this.baitName = group.getBaitName();
	}

	public Integer getGroupID() {
		return groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public Integer getEzor() {
		return ezor;
	}

	public String getEzorName() {
		return ezorName;
	}

	public Integer getBait() {
		return bait;
	}

	public String getBaitName() {
		return baitName;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setEzor(Integer ezor) {
		this.ezor = ezor;
	}

	public void setEzorName(String ezorName) {
		this.ezorName = ezorName;
	}

	public void setBait(Integer bait) {
		this.bait = bait;
	}

	public void setBaitName(String baitName) {
		this.baitName = baitName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(GroupVO.class)) {
			return false;
		}
		GroupVO g = (GroupVO) obj;
		if (getGroupName() == null && g.getGroupName() != null) {
			return false;
		}
		if (getGroupName() != null && g.getGroupName() == null) {
			return false;
		}
		if (!getGroupName().equals(g.getGroupName())) {
			return false;
		}
		return true;
	}

}
