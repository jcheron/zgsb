package net.kernel;

import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.IdClass;

/**
 * Classe KFraisforfait
 */
@Entity
@IdClass(value = String.class)
@SuppressWarnings("serial")
public class KFraisforfait extends KObject {
	private KListObject<KLignefraisforfait> lignefraisforfaits;
	private float montant;
	private String libelle;

	public KFraisforfait() {
		super();
		// hasMany(KLignefraisforfait.class);

	}

	/**
	 * return the value of lignefraisforfaits
	 * 
	 * @return lignefraisforfaits
	 */
	public KListObject<KLignefraisforfait> getLignefraisforfaits() {
		return this.lignefraisforfaits;
	}

	/**
	 * return the value of montant
	 * 
	 * @return montant
	 */
	public float getMontant() {
		return this.montant;
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
	 * set the value of lignefraisforfaits
	 * 
	 * @param aLignefraisforfaits
	 */
	public void setLignefraisforfaits(KListObject<KLignefraisforfait> aLignefraisforfaits) {
		this.lignefraisforfaits = aLignefraisforfaits;
	}

	/**
	 * set the value of montant
	 * 
	 * @param aMontant
	 */
	public void setMontant(float aMontant) {
		this.montant = aMontant;
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
		return libelle + " (" + montant + ")";
	}
}