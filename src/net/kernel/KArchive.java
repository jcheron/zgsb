package net.kernel;

import java.util.Calendar;
import java.util.LinkedHashMap;

import net.ko.kobject.KListConstraint;
import net.ko.persistence.annotation.Entity;

/**
 * Classe KArchive
 */
@Entity
@SuppressWarnings("serial")
public class KArchive extends KFichefrais {
	private String fraisforfait;
	private float montantestime;
	private String fraishorsforfait;
	private int annee;

	public KArchive() {
		row = new LinkedHashMap<String, Object>();
		constraints = new KListConstraint();
		belongsTo(KVisiteur.class);
	}

	/**
	 * return the value of fraisforfait
	 * 
	 * @return fraisforfait
	 */
	public String getFraisforfait() {
		return this.fraisforfait;
	}

	/**
	 * return the value of montantestime
	 * 
	 * @return montantestime
	 */
	public float getMontantestime() {
		return this.montantestime;
	}

	/**
	 * return the value of fraishorsforfait
	 * 
	 * @return fraishorsforfait
	 */
	public String getFraishorsforfait() {
		return this.fraishorsforfait;
	}

	/**
	 * return the value of annee
	 * 
	 * @return annee
	 */
	public int getAnnee() {
		return this.annee;
	}

	/**
	 * set the value of fraisforfait
	 * 
	 * @param aFraisforfait
	 */
	public void setFraisforfait(String aFraisforfait) {
		this.fraisforfait = aFraisforfait;
	}

	/**
	 * set the value of montantestime
	 * 
	 * @param aMontantestime
	 */
	public void setMontantestime(float aMontantestime) {
		this.montantestime = aMontantestime;
	}

	/**
	 * set the value of fraishorsforfait
	 * 
	 * @param aFraishorsforfait
	 */
	public void setFraishorsforfait(String aFraishorsforfait) {
		this.fraishorsforfait = aFraishorsforfait;
	}

	/**
	 * set the value of annee
	 * 
	 * @param aAnnee
	 */
	public void setAnnee(int aAnnee) {
		this.annee = aAnnee;
	}

	@Override
	public String toString() {
		return super.toString() + " (AR)";
	}

	/**
	 * Archive la fiche passée en paramétre
	 * 
	 * @param f
	 *            Fiche à archiver
	 */
	public void archive(KFichefrais f) {
		Calendar cal = Calendar.getInstance();
		setAnnee(cal.get(Calendar.YEAR));
		idVisiteur = f.getIdVisiteur();
		setDateModif(f.getDateModif());
		setIdEtat(f.getIdEtat());
		setMois(f.getMois());
		String strLff = "";
		montantValide = f.getMontantValide();
		montantestime = f.getMontant();
		nbJustificatifs = f.getNbJustificatifs();
		for (KLignefraisforfait lff : f.getLignefraisforfaits()) {
			String deb = "";
			if (lff.isValid())
				deb = "+";
			strLff += deb + lff + " = " + lff.getValidMontant() + "\n";
		}
		setFraisforfait(strLff);
		String strLfhf = "";
		for (KLignefraishorsforfait lfhf : f.getLignefraishorsforfaits()) {
			String deb = "";
			if (lfhf.isValid())
				deb = "+";
			strLfhf += deb + lfhf + " = " + lfhf.getValidMontant() + "\n";
		}
		setFraishorsforfait(strLfhf);
	}
}