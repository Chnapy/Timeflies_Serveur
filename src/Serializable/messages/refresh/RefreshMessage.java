package Serializable.messages.refresh;

import Serializable.messages.Message;

/**
 * @author alexandre
 * RefreshMessage.java
 */
public class RefreshMessage extends Message {

	private final static MessageType TYPE = MessageType.REFRESH;

	public RefreshMessage() {
		super(TYPE);
	}

	@Override
	protected String completeString() {
		return "";
	}

}
