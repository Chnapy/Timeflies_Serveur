/*
 * 
 * 
 * 
 */
package Combat;

import Serializable.InCombat.TypeCarac;
import static Serializable.InCombat.TypeCarac.INITIATIVE;
import static Serializable.InCombat.TypeCarac.TEMPSACTION;
import Combat.entite.Entite;
import Combat.entite.EntiteActiveJouable;
import Combat.entite.Equipe;
import Combat.map.Map;
import Combat.map.Tuile;
import Combat.sort.SortLancable;
import Combat.sort.classe.SortActif;
import Combat.sort.classe.SortPassif;
import Combat.sort.effet.Effet;
import Combat.sort.effet.EffetTour.EfDebutCombat;
import Combat.sort.effet.EffetTour.EfDebutTour;
import Combat.sort.effet.EffetTour.EfDebutTourGlobal;
import Combat.sort.effet.EffetTour.EfFinTour;
import Combat.sort.effet.EffetTour.EfFinTourGlobal;
import Combat.sort.envout.Envoutement;
import HorsCombat.Modele.Reseau.Client;
import static Main.ThreadManager.EXEC;
import Serializable.Duo;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.HorsCombat.Map.PosPlacement;
import Serializable.InCombat.ChargementCombat;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.donnee.InEquipe;
import Serializable.InCombat.sort.LancerSort;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.InCombat.tour.DebutTour;
import Serializable.InCombat.tour.DebutTourGlobal;
import Serializable.InCombat.tour.FinTour;
import Serializable.InCombat.tour.FinTourGlobal;
import Serializable.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Combat.java
 *
 */
public class Combat implements Runnable {

	private static long ID = -1;
	private static final int TIME_WAIT = 5000;

	private final long idCombat;
	private final Map map;
	private final ArrayList<Client> clients;
	private final ArrayList<Equipe> equipes;
	private final ArrayList<Entite> entites;

	private boolean enCours;
	private int indexTG;
	private int indexT;
	private long timeT;

	private CombatState state;
	private EntiteActiveJouable entiteEnCours;
	private Duo<SortLancable, Future> sortEnCours;

	public Combat(ArrayList<Equipe> equipes, MapSerializable mapS) {
		this.idCombat = getNewID();
		map = new Map(mapS);
		this.equipes = equipes;
		setEntitesPosition(equipes, mapS.placement);

		entites = new ArrayList();
		equipes.stream().forEach((e) -> {
			entites.addAll(e.entites);
		});
		clients = new ArrayList();
		entites.forEach((e) -> {
			if (!clients.contains(e.client)) {
				clients.add(e.client);
			}
		});

		indexTG = -1;
		indexT = -1;
		timeT = -1;
		sortEnCours = null;
		enCours = true;
		state = CombatState.WAIT;
	}

	@Override
	public void run() {
		sendToAll(new ChargementCombat(allToInEquipe()));
		entites.forEach((e) -> {
			for (SortPassif sp : e.classe.tabSortPassif) {
				for (Effet effet : sp.tabEffets) {
					effet.affecter(map.get(e.position), e, e);
				}
			}
		});
		state = CombatState.ENCOURS;
		debutCombat();
		while (enCours) {
			tourGlobal();
		}
		state = CombatState.END;
	}
	
	private void debutCombat() {
		applyEffetToAll(new EfDebutCombat());
		sendToAll(new DebutCombat(System.currentTimeMillis() + TIME_WAIT));
		try {
			Thread.sleep(TIME_WAIT);
		} catch (InterruptedException ex) {
			Logger.getLogger(Combat.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void tourGlobal() {
		indexTG++;
		long[] ordre = redefOrdreJeu();
		applyEffetToAll(new EfDebutTourGlobal());
		sendToAll(new DebutTourGlobal(System.currentTimeMillis(), indexTG, ordre));
		entites.forEach((e) -> {
			try {
				if (enCours && e.canPlay()) {
					entiteEnCours = (EntiteActiveJouable) e;
					tour();
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(Combat.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		applyEffetToAll(new EfFinTourGlobal());
		sendToAll(new FinTourGlobal(System.currentTimeMillis(), indexTG));
	}

	private void tour() throws InterruptedException {
		indexT++;
		applyEffetToAll(new EfDebutTour());
		timeT = System.currentTimeMillis();
		sendToAll(new DebutTour(timeT, indexT, entiteEnCours.id));
		
		Thread.sleep(entiteEnCours.caracs.get(TEMPSACTION).actu);
		if (sortEnCours != null && sortEnCours.first.isEnCours()) {
			Thread.sleep(entiteEnCours.caracs.get(TypeCarac.TEMPSSUP).actu);
			if (sortEnCours.first.isEnCours()) {
				sortEnCours.second.cancel(true);
			}
		}
		
		applyEffetToAll(new EfFinTour());
		sendToAll(new FinTour(System.currentTimeMillis(), indexT, entiteEnCours.id));
		sortEnCours = null;
	}

	private long[] redefOrdreJeu() {
		entites.sort((Entite e1, Entite e2) -> {
			if (e1.caracs.get(INITIATIVE).actu < e1.caracs.get(INITIATIVE).actu) {
				return -1;
			}
			if (e1.caracs.get(INITIATIVE).actu > e1.caracs.get(INITIATIVE).actu) {
				return 1;
			}
			return 0;
		});
		
		long[] ordre = new long[entites.size()];
		for(int i = 0; i < entites.size(); i++) {
			ordre[i] = entites.get(i).id;
		}
		return ordre;
	}

	private void lancerSort(SortActif sort, Position pos, long timeStart) {
		if (sort == null || pos == null || !map.inBounds(pos)) {
			errln("Lancer de sort impossible " + sort + " " + pos);
			return;
		}
		if (!canLancerSort(sort, timeStart)) {
			errln("Lancer de sort impossible, pas assez de temps");
			return;
		}

		ArrayList<Position> posCibles = map.getAllPositions(pos, sort.getZoneAction());

		SortLancable sl = new SortLancable(sort, posCibles, timeStart) {
			@Override
			public void applyEffet(Effet effet, Position p) {
				Entite e = getEntiteFromPos(p);
				defApplyEffet(effet, map.get(p), e, entiteEnCours);
			}
		};
		sortEnCours = new Duo(sl, EXEC.submit(sl));
	}

	private void applyEffetToAll(Effet effet) {
		entites.forEach((e) -> {
			e.envoutements.forEach((env) -> {
				checkEnvoutement((Envoutement) env, effet,
						map.get(e.position), e, e);
			});
		});
	}

	public void defApplyEffet(Effet effet, Tuile tuile, Entite entite, Entite lanceur) {
		effet.affecter(tuile, entite, lanceur);
		Envoutement en;
		switch (effet.cible) {
			case ENTITE:
				if (entite != null) {
					for (Object env : entite.envoutements) {
						checkEnvoutement((Envoutement) env, effet,
								map.get(lanceur.position), lanceur, entite);
					}
				}
				break;
			case LANCEUR:
				for (Object env : lanceur.envoutements) {
					checkEnvoutement((Envoutement) env, effet,
							map.get(lanceur.position), lanceur, lanceur);
				}
				break;
		}
	}

	private void checkEnvoutement(Envoutement env, Effet effet,
			Tuile tuile, Entite entite, Entite lanceur) {
		if (env.estDeclenche(effet)) {
			for (Effet ef : env.effets) {
				defApplyEffet(ef, tuile,
						lanceur, entite);
			}
		}
	}

	private boolean canLancerSort(SortActif sort, long timeStart) {
		return getTempsRestantTourWithTS()
				>= sort.tempsAction - (System.currentTimeMillis() - timeStart);
	}

	private long getTempsRestantTourWithTS() {
		return getTempsRestantTour() + entiteEnCours.caracs.get(TypeCarac.TEMPSSUP).actu;
	}

	private long getTempsRestantTour() {
		return System.currentTimeMillis() - timeT;
	}

	private Entite getEntiteFromPos(Position pos) {
		for (Entite e : entites) {
			if (e.position.equals(pos)) {
				return e;
			}
		}
		return null;
	}

	private void setEntitesPosition(ArrayList<Equipe> equipes, ArrayList<PosPlacement> posPlacement) {
		Collections.shuffle(posPlacement);

		for (Equipe equipe : equipes) {
			equipe.entites.forEach((e) -> {
				PosPlacement p;
				for (Iterator<PosPlacement> ite = posPlacement.iterator();
						ite.hasNext();) {
					p = ite.next();
					if (p.numEquipe == equipe.numero) {
						e.position = new Position(p.position.y, p.position.x);
						ite.remove();
						break;
					}
				}
			});
		}
	}

	public void sendToAll(InCombat pack) {
		clients.forEach((c) -> {
			c.sendToClient(pack);
		});
	}

	public void recepLancerSort(LancerSort lancerSort, Client client) {
		if (state != CombatState.ENCOURS
				|| !entiteEnCours.client.equals(client)
				|| entiteEnCours.id != lancerSort.idEntite
				|| lancerSort.tour != indexT) {
			errln("Tentative de lancer de sort refusée " + lancerSort.idClasseSort);
			return;
		}
		SortActif sort = getSortActifFromId(lancerSort.idClasseSort, entiteEnCours.classe.tabSortActif);

		lancerSort(sort, lancerSort.position, lancerSort.beginTime);
	}

	private static SortActif getSortActifFromId(long idClasseSort, SortActif[] sortsActif) {
		for (SortActif sort : sortsActif) {
			if (sort.idClasseSort == idClasseSort) {
				return sort;
			}
		}
		return null;
	}

	private ArrayList<InEquipe> allToInEquipe() {
		ArrayList<InEquipe> ie = new ArrayList();
		equipes.forEach((e) -> {
			ie.add(e.toInEquipe());
		});
		return ie;
	}

	public void errln(String text) {
		System.err.println(getConsoleText(text));
	}

	public void println(String text) {
		System.out.println(getConsoleText(text));
	}

	private String getConsoleText(String text) {
		return "CB" + idCombat + " " + text;
	}

	private static long getNewID() {
		ID++;
		return ID;
	}

	public void removeClient(Client client) {
		int nbrAlive = 0;
		for (Entite e : entites) {
			if (e.client == client) {
				e.setAlive(false);
			} else if (e.isAlive()) {
				nbrAlive++;
			}
		}
		if (nbrAlive == 0) {
			enCours = false;
		}
	}

	public static enum CombatState {
		WAIT,
		ENCOURS,
		END
	}

}
