package Serializable.messages.server_login;

import Serializable.messages.Message;

/**
 * @author alexandre
 * ServerLoginMessage.java
 */
public class ServerLoginMessage extends Message {

	private final static MessageType TYPE = MessageType.SERVER_LOGIN;

	public ServerLoginMessage() {
		super(TYPE);
	}

	@Override
	protected String completeString() {
		return "";
	}

}
