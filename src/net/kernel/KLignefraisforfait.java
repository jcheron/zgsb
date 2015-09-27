package net.kernel;

import net.ko.db.KDBForeignKeyDef;
import net.ko.kobject.KObject;
import net.ko.kobject.KRecordStatus;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.Id;
import net.ko.persistence.annotation.Transient;

/**
 * Classe KLignefraisforfait
 */
@Entity
@SuppressWarnings("serial")
public class KLignefraisforfait extends KObject {
	@Id
	private String idFraisforfait;
	@Id
	private int idFichefrais;
	private KFichefrais fichefrais;
	private KFraisforfait fraisforfait;
	private int quantite;

	@Transient
	private float total = 0.00f;
	private boolean validFF;

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return validFF;
	}

	/**
	 * @param valid
	 *            the valid to set
	 */
	public void setValid(boolean valid) {
		this.validFF = valid;
	}

	public KLignefraisforfait() {
		super();
		belongsTo(KFraisforfait.class);
		belongsTo(KFichefrais.class).onDeleteAction(KDBForeignKeyDef.kdCascade);

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
	 * return the value of idFraisforfait
	 * 
	 * @return idFraisforfait
	 */
	public String getIdFraisforfait() {
		return this.idFraisforfait;
	}

	/**
	 * return the value of fraisforfait
	 * 
	 * @return fraisforfait
	 */
	public KFraisforfait getFraisforfait() {
		return this.fraisforfait;
	}

	/**
	 * return the value of quantite
	 * 
	 * @return quantite
	 */
	public int getQuantite() {
		return this.quantite;
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
	 * set the value of fichefrais
	 * 
	 * @param aFichefrais
	 */
	public void setFichefrais(KFichefrais aFichefrais) {
		this.fichefrais = aFichefrais;
	}

	/**
	 * set the value of idFraisforfait
	 * 
	 * @param aIdFraisforfait
	 */
	public void setIdFraisforfait(String aIdFraisforfait) {
		this.idFraisforfait = aIdFraisforfait;
	}

	/**
	 * set the value of fraisforfait
	 * 
	 * @param aFraisforfait
	 */
	public void setFraisforfait(KFraisforfait aFraisforfait) {
		this.fraisforfait = aFraisforfait;
	}

	/**
	 * set the value of quantite
	 * 
	 * @param aQuantite
	 */
	public void setQuantite(int aQuantite) {
		this.quantite = aQuantite;
	}

	/**
	 * set the value of idFichefrais
	 * 
	 * @param aIdFichefrais
	 */
	public void setIdFichefrais(int aIdFichefrais) {
		this.idFichefrais = aIdFichefrais;
	}

	@Override
	public String toString() {
		String result = "";
		if (fraisforfait != null)
			result = fraisforfait.toString() + " : ";
		return result + quantite;
	}

	public String asHtml() {
		String result = "";
		if (fraisforfait != null)
			result = fraisforfait.toString() + " : ";
		return "<div>" + result + quantite + "</div>";
	}

	public float getValidMontant() {
		return getMontant(false);
	}

	public float getMontant() {
		return getMontant(true);
	}

	private float getMontant(boolean all) {
		float montant = 0;
		if (fraisforfait != null)
			if (all || (validFF && !KRecordStatus.rsDelete.equals(recordStatus)))
				montant = fraisforfait.getMontant() * quantite;
		return montant;
	}

	/**
	 * @return the total
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * @param validFF
	 *            the validFF to set
	 */
	public void setValidFF(boolean validFF) {
		this.validFF = validFF;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(float total) {
		this.total = total;
	}
}