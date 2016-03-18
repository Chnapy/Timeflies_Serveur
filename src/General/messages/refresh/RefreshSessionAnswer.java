package General.messages.refresh;

import java.io.Serializable;

/**
 * @author alexandre
 * RefreshSessionAnswer.java
 */
public class RefreshSessionAnswer extends RefreshMessage implements Serializable {

	private static final long serialVersionUID = 4264015399583852116L;

	public final Answer answer;

	public RefreshSessionAnswer(Answer answer) {
		super();
		this.answer = answer;
	}

	public enum Answer {

		SUCCESS, TIME_OUT, BAD_ADDRESS
	}
}
