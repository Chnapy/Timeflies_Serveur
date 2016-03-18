package General.messages.login;

import java.io.Serializable;
import java.util.UUID;

/*
 * LoginAnswer.java
 * Answer to a login request.
 * There's 3 possible answers : 
 * 								- SUCCCESS : the authentification is successful, you can use the given token for your further operations.
 * 								- FAIL : bad login/password, try again.
 * 								- ERROR : somthing went wrong, you can get an error code.
 */
public class LoginAnswer extends LoginMessage implements Serializable {

	private static final long serialVersionUID = -5375108905288101051L;

	public final AnswerType answer;
	public final UUID token;

	public LoginAnswer(AnswerType answer, UUID token) {
		super();
		this.answer = answer;
		this.token = token;
	}

	public enum AnswerType {

		SUCCESS, FAIL, ERROR;

		public int errorCode;
	}
}
