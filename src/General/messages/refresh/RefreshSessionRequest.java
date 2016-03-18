package General.messages.refresh;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author alexandre
 * RefreshSessionRequest.java
 */
public class RefreshSessionRequest extends RefreshMessage implements Serializable {

	private static final long serialVersionUID = -3290257706078934919L;

	public final UUID token;

	public RefreshSessionRequest(UUID token) {
		super();
		this.token = token;
	}

}
