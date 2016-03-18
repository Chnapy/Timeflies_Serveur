package General.messages.login;

import java.io.Serializable;

/*
 * LoginRequest.java
 * This message is the way to give the login and password to the server. They must be hashed before.
 */
public class LoginRequest extends LoginMessage implements Serializable {

	private static final long serialVersionUID = -433397453205335783L;

	public final String login;
	public final String pwd;

	public LoginRequest(String login, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
	}

}
