package net.kernel;

import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.Id;

@SuppressWarnings("serial")
@Entity(name = "fichefrais")
public class KFicheparmois extends KObject {
	@Id
	protected int mois;
	protected int nb;

	public KFicheparmois() {
		super();
		sql = "SELECT mois, COUNT(*) AS nb FROM fichefrais WHERE idEtat = 'RB' GROUP BY mois";
	}

	/**
	 * @return the mois
	 */
	public int getMois() {
		return mois;
	}

	/**
	 * @param mois
	 *            the mois to set
	 */
	public void setMois(int mois) {
		this.mois = mois;
	}

	/**
	 * @return the nb
	 */
	public int getNb() {
		return nb;
	}

	/**
	 * @param nb
	 *            the nb to set
	 */
	public void setNb(int nb) {
		this.nb = nb;
	}

	public String getMoisStr() {
		String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
		return mois[this.mois];
	}

	public String libelle() {
		return "<div class='field'><input name='toArchiveParMois' checked type='checkbox' id='ckMois-" + mois + "' value='" + mois + "'><label for='ckMois-" + mois + "'>&nbsp;" + getMoisStr() + " (" + nb + ")</label></div>";

	}
}
