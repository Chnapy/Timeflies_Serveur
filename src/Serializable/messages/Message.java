package Serializable.messages;

import java.io.Serializable;

/**
 * @author alexandre
 * Message.java
 */
public abstract class Message implements Serializable {

	public enum MessageType {

		LOGIN, SERVER_LOGIN, REFRESH, ERROR, COMBAT;
	}

	private final MessageType messageType;

	public Message(MessageType type) {
		messageType = type;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public boolean isEndOfCom() {
		return false;
	}

	@Override
	public String toString() {
		return messageType.name() + "\n" + completeString();
	}
	
	protected abstract String completeString();
}
