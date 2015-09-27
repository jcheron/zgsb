package net.kernel;

import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.IdClass;

/**
 * Classe KEtat
 */
@Entity
@IdClass(value = String.class)
@SuppressWarnings("serial")
public class KEtat extends KObject {
	private KListObject<KFichefrais> fichefraiss;
	private String libelle;

	public KEtat() {
		super();
		// hasMany(KFichefrais.class);

	}

	/**
	 * return the value of fichefraiss
	 * 
	 * @return fichefraiss
	 */
	public KListObject<KFichefrais> getFichefraiss() {
		return this.fichefraiss;
	}

	/**
	 * return the value of libelle
	 * 
	 * @return libelle
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * set the value of fichefraiss
	 * 
	 * @param aFichefraiss
	 */
	public void setFichefraiss(KListObject<KFichefrais> aFichefraiss) {
		this.fichefraiss = aFichefraiss;
	}

	/**
	 * set the value of libelle
	 * 
	 * @param aLibelle
	 */
	public void setLibelle(String aLibelle) {
		this.libelle = aLibelle;
	}

	@Override
	public String toString() {
		return libelle;
	}
}