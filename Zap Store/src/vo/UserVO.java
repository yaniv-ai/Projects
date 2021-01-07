package vo;

import javax.swing.JPasswordField;

import enums.Ranks;

public class UserVO {
	private String username;
	private JPasswordField password;
	private Ranks rank;

	public UserVO(String username, JPasswordField password, Ranks rank) {
		super();
		this.username = username;
		this.password = password;
		this.rank = rank;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public void setPassword(JPasswordField password) {
		this.password = password;
	}

	public Ranks getRank() {
		return rank;
	}

	public void setRank(Ranks rank) {
		this.rank = rank;
	}
}
