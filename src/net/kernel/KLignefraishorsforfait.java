package net.kernel;

import java.sql.Date;

import net.ko.kobject.KObject;
import net.ko.kobject.KRecordStatus;
import net.ko.persistence.annotation.Column;
import net.ko.persistence.annotation.Entity;

/**
 * Classe KLignefraishorsforfait
 */
@Entity
@SuppressWarnings("serial")
public class KLignefraishorsforfait extends KObject {
	private KFichefrais fichefrais;
	@Column(columnDefinition = "DECIMAL", precision = 8, scale = 2)
	private float montant;
	@Column(length = 25)
	private String libelle;
	private int idFichefrais;
	private java.sql.Date dateHF;
	private boolean validHF;

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return validHF;
	}

	/**
	 * @param valid
	 *            the valid to set
	 */
	public void setValid(boolean valid) {
		this.validHF = valid;
	}

	public KLignefraishorsforfait() {
		super();
		libelle = "-";
		java.util.Date d = new java.util.Date();
		dateHF = new Date(d.getTime());
		// belongsTo(KFichefrais.class);

	}

	/**
	 * return the value of fichefrais
	 * 
	 * @return fichefrais
	 */
	public KFichefrais getFichefrais() {
		return this.fichefrais;
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
	 * return the value of idFichefrais
	 * 
	 * @return idFichefrais
	 */
	public int getIdFichefrais() {
		return this.idFichefrais;
	}

	/**
	 * return the value of dateHF
	 * 
	 * @return date
	 */
	public java.sql.Date getDateHF() {
		return this.dateHF;
	}

	/**
	 * set the value of fichefrais
	 * 
	 * @param aFichefrais
	 */
	public void setFichefrais(KFichefrais aFichefrais) {
		this.fichefrais = aFichefrais;
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

	/**
	 * set the value of idFichefrais
	 * 
	 * @param aIdFichefrais
	 */
	public void setIdFichefrais(int aIdFichefrais) {
		this.idFichefrais = aIdFichefrais;
	}

	/**
	 * set the value of dateHF
	 * 
	 * @param aDate
	 */
	public void setDateHF(java.sql.Date aDate) {
		this.dateHF = aDate;
	}

	@Override
	public String toString() {
		return libelle + " (" + dateHF + ") ->" + montant;
	}

	public float getValidMontant() {
		return getMontant(false);
	}

	public float getMontant() {
		return getMontant(true);
	}

	private float getMontant(boolean all) {
		float result = 0;
		if (all || (validHF && !KRecordStatus.rsDelete.equals(recordStatus)))
			result = montant;
		return result;
	}

	/**
	 * @param validHF
	 *            the validHF to set
	 */
	public void setValidHF(boolean validHF) {
		this.validHF = validHF;
	}
}