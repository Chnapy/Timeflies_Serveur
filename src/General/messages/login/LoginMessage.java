package General.messages.login;

import General.messages.Message;

/**
 * @author alexandre
 * LoginMessage.java
 */
public abstract class LoginMessage extends Message {

	private final static MessageType TYPE = MessageType.LOGIN;

	public LoginMessage() {
		super(TYPE);
	}
}
