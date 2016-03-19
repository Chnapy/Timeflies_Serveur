/*
 * 
 * 
 * 
 */
package Serializable.messages.combat;

import Serializable.messages.Message;

/**
 * CombatMessage.java
 *
 */
public abstract class CombatMessage extends Message {

	private final static Message.MessageType TYPE = Message.MessageType.COMBAT;

	public enum CombatType {

		START_COMBAT, END_COMBAT,
		START_TOUR, END_TOUR,
		START_TOUR_GLOB, END_TOUR_GLOB,
		SORT
	}

	private final CombatType combatType;

	public CombatMessage(CombatType ctype) {
		super(TYPE);
		combatType = ctype;
	}

	public CombatType getCombatType() {
		return combatType;
	}

	@Override
	protected String completeString() {
		return combatType + "\n" + completeString2();
	}
	
	protected abstract String completeString2();
}
