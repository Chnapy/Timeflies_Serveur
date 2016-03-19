/*
 * 
 * 
 * 
 */
package Serializable.messages.combat;

/**
 * StartCombatRequest.java
 *
 */
public class StartCombatRequest extends CombatMessage {

	private final static CombatType TYPE = CombatType.START_COMBAT;

	public StartCombatRequest() {
		super(TYPE);
	}

	@Override
	protected String completeString2() {
		return "";
	}

}
