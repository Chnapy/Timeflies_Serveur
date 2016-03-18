package General.messages.error;

import java.io.Serializable;

import General.messages.Message;

/**
 * @author alexandre
 * ErrorMessage.java
 */
public class ErrorMessage extends Message implements Serializable {

	private static final long serialVersionUID = 4514385579687171951L;

	public final static MessageType TYPE = MessageType.ERROR;

	public final int code;
	public final String text;

	public ErrorMessage(int code, String text) {
		super(TYPE);
		this.code = code;
		this.text = text;
	}

	@Override
	public String toString() {
		return "Error : " + text + "(code # " + code + " )";
	}

}
