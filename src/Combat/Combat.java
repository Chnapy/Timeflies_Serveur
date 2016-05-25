/*
 * 
 * 
 * 
 */
package Combat;

import Combat.entite.Equipe;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.HorsCombat.Map.PosPlacement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Combat.java
 *
 */
public class Combat {

	public Combat(Equipe[] equipes, MapSerializable mapS) {
		ArrayList<PosPlacement> posPlacement = mapS.placement;
		Collections.shuffle(posPlacement);

		for (Equipe equipe : equipes) {
			equipe.entites.forEach((e) -> {
				PosPlacement p;
				for (Iterator<PosPlacement> ite = posPlacement.iterator();
						ite.hasNext();) {
					p = ite.next();
					if (p.numEquipe == equipe.numero) {
						e.position = p.position;
						ite.remove();
						break;
					}
				}
			});
		}
	}

}
