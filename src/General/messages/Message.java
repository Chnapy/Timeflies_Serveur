package General.messages;

/**
 * @author alexandre
 * Message.java
 */
public abstract class Message {

	public enum MessageType {

		LOGIN, SERVER_LOGIN, REFRESH, ERROR;
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
}
