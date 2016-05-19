/*
 * 
 * 
 * 
 */
package Serializable.Personnages.Sort;

/**
 * SortActif.java
 *
 */
public class SortActif extends Sort {

	private static final long serialVersionUID = -8063582855744864546L;

	public final int tempsAction;
	public final int cooldown;
	public final int portee;
	public final int zone;

	public SortActif(String nom, Niveau niveau, String description, int tempsAction, int cooldown, int portee, int zone) {
		super(nom, niveau, description);
		this.tempsAction = tempsAction;
		this.cooldown = cooldown;
		this.portee = portee;
		this.zone = zone;
	}

}
