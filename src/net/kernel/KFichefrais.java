package net.kernel;

import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.Table;
import net.ko.persistence.annotation.UniqueConstraint;

/**
 * Classe KFichefrais
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "idEtat", "mois", "idVisiteur" }) })
@SuppressWarnings("serial")
public class KFichefrais extends KObject {
	private KEtat etat;
	protected float montantValide;
	protected int idVisiteur;
	protected java.sql.Date dateModif;
	private KListObject<KLignefraisforfait> lignefraisforfaits;
	protected String mois;
	private KVisiteur visiteur;
	private KListObject<KLignefraishorsforfait> lignefraishorsforfaits;
	protected int nbJustificatifs;
	protected String idEtat;

	public KFichefrais() {
		super();
		idEtat = "CR";
		belongsTo(KVisiteur.class);
		hasMany(KLignefraishorsforfait.class);
		hasManyBelongsTo(KLignefraisforfait.class, KFraisforfait.class, "idFraisforfait");
		belongsTo(KEtat.class);
	}

	/**
	 * return the value of etat
	 * 
	 * @return etat
	 */
	public KEtat getEtat() {
		return this.etat;
	}

	/**
	 * return the value of montantValide
	 * 
	 * @return montantValide
	 */
	public float getMontantValide() {
		return this.montantValide;
	}

	/**
	 * return the value of idVisiteur
	 * 
	 * @return idVisiteur
	 */
	public int getIdVisiteur() {
		return this.idVisiteur;
	}

	/**
	 * return the value of dateModif
	 * 
	 * @return dateModif
	 */
	public java.sql.Date getDateModif() {
		return this.dateModif;
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
	 * return the value of mois
	 * 
	 * @return mois
	 */
	public String getMois() {
		return this.mois;
	}

	/**
	 * return the value of visiteur
	 * 
	 * @return visiteur
	 */
	public KVisiteur getVisiteur() {
		return this.visiteur;
	}

	/**
	 * return the value of lignefraishorsforfaits
	 * 
	 * @return lignefraishorsforfaits
	 */
	public KListObject<KLignefraishorsforfait> getLignefraishorsforfaits() {
		return this.lignefraishorsforfaits;
	}

	/**
	 * return the value of nbJustificatifs
	 * 
	 * @return nbJustificatifs
	 */
	public int getNbJustificatifs() {
		return this.nbJustificatifs;
	}

	/**
	 * return the value of idEtat
	 * 
	 * @return idEtat
	 */
	public String getIdEtat() {
		return this.idEtat;
	}

	/**
	 * set the value of etat
	 * 
	 * @param aEtat
	 */
	public void setEtat(KEtat aEtat) {
		this.etat = aEtat;
	}

	/**
	 * set the value of montantValide
	 * 
	 * @param aMontantValide
	 */
	public void setMontantValide(float aMontantValide) {
		this.montantValide = aMontantValide;
	}

	/**
	 * set the value of idVisiteur
	 * 
	 * @param aIdVisiteur
	 */
	public void setIdVisiteur(int aIdVisiteur) {
		this.idVisiteur = aIdVisiteur;
	}

	/**
	 * set the value of dateModif
	 * 
	 * @param aDateModif
	 */
	public void setDateModif(java.sql.Date aDateModif) {
		this.dateModif = aDateModif;
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
	 * set the value of mois
	 * 
	 * @param aMois
	 */
	public void setMois(String aMois) {
		this.mois = aMois;
	}

	/**
	 * set the value of visiteur
	 * 
	 * @param aVisiteur
	 */
	public void setVisiteur(KVisiteur aVisiteur) {
		this.visiteur = aVisiteur;
	}

	/**
	 * set the value of lignefraishorsforfaits
	 * 
	 * @param aLignefraishorsforfaits
	 */
	public void setLignefraishorsforfaits(KListObject<KLignefraishorsforfait> aLignefraishorsforfaits) {
		this.lignefraishorsforfaits = aLignefraishorsforfaits;
	}

	/**
	 * set the value of nbJustificatifs
	 * 
	 * @param aNbJustificatifs
	 */
	public void setNbJustificatifs(int aNbJustificatifs) {
		this.nbJustificatifs = aNbJustificatifs;
	}

	/**
	 * set the value of idEtat
	 * 
	 * @param aIdEtat
	 */
	public void setIdEtat(String aIdEtat) {
		this.idEtat = aIdEtat;
	}

	private void forceToLoad() {
		try {
			getAttribute("lignefraisforfaits");
			getAttribute("lignefraishorsforfaits");
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
		}
	}

	public float getMontantToValide() {
		float montant = 0;
		forceToLoad();
		if (lignefraisforfaits != null) {
			for (KLignefraisforfait ff : lignefraisforfaits) {
				montant += ff.getValidMontant();
			}
		}
		if (lignefraishorsforfaits != null) {
			for (KLignefraishorsforfait fhf : lignefraishorsforfaits) {
				montant += fhf.getValidMontant();
			}
		}
		return montant;
	}

	public void updateMontants() {
		montantValide = getMontantToValide();
	}

	public float getMontant() {
		float montant = 0;
		forceToLoad();
		if (lignefraisforfaits != null) {
			for (KLignefraisforfait ff : lignefraisforfaits) {
				montant += ff.getMontant();
			}
		}
		if (lignefraishorsforfaits != null) {
			for (KLignefraishorsforfait fhf : lignefraishorsforfaits) {
				montant += fhf.getMontant();
			}
		}
		return montant;
	}

	public int getNbFHF() {
		int result = 0;
		forceToLoad();
		if (lignefraishorsforfaits != null)
			result = lignefraishorsforfaits.count();
		return result;
	}

	public int getNbFF() {
		int result = 0;
		forceToLoad();
		if (lignefraisforfaits != null) {
			for (KLignefraisforfait lff : lignefraisforfaits) {
				if (lff.getMontant() > 0)
					result++;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "Fiche (" + etat + ") modifiée le " + dateModif + " pour un montant de prévu de : " + getMontant() + " / validé de : " + getMontantValide();
	}

	public boolean isEmpty() {
		return getMontantValide() == 0 && lignefraishorsforfaits.count() == 0;
	}
}