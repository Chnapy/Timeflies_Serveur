/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MoteurJeu.gameplay.map;

/**
 *
 * @author Richard Haddad <richardhaddad@hotmail.fr>
 */
public enum EtatTuile {

	//Petite tuile
	NULL,
	PATH,
	ZONEPORTEE,
	//Grande tuile
	NORMAL,
	GHOSTZONEACTION,
	GHOSTPATH,
	GHOSTDESTINATION
}
