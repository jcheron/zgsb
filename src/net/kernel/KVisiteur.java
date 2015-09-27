package net.kernel;

import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;

/**
 * Classe KVisiteur
 */
@SuppressWarnings("serial")
@Entity
public class KVisiteur extends KObject {
	private String prenom;
	private java.sql.Date dateEmbauche;
	private String mdp;
	private String cp;
	private String adresse;
	private KListObject<KFichefrais> fichefraiss;
	private String ville;
	private String login;
	private String nom;
	private boolean comptable;

	public KVisiteur() {
		super();
		ville="";
		//hasMany(KFichefrais.class);

	}

	/**
	 * return the value of prenom
	 * 
	 * @return prenom
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * return the value of dateEmbauche
	 * 
	 * @return dateEmbauche
	 */
	public java.sql.Date getDateEmbauche() {
		return this.dateEmbauche;
	}

	/**
	 * return the value of mdp
	 * 
	 * @return mdp
	 */
	public String getMdp() {
		return this.mdp;
	}

	/**
	 * return the value of cp
	 * 
	 * @return cp
	 */
	public String getCp() {
		return this.cp;
	}

	/**
	 * return the value of adresse
	 * 
	 * @return adresse
	 */
	public String getAdresse() {
		return this.adresse;
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
	 * return the value of ville
	 * 
	 * @return ville
	 */
	public String getVille() {
		return this.ville;
	}

	/**
	 * return the value of login
	 * 
	 * @return login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * return the value of nom
	 * 
	 * @return nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * set the value of prenom
	 * 
	 * @param aPrenom
	 */
	public void setPrenom(String aPrenom) {
		this.prenom = aPrenom;
	}

	/**
	 * set the value of dateEmbauche
	 * 
	 * @param aDateEmbauche
	 */
	public void setDateEmbauche(java.sql.Date aDateEmbauche) {
		this.dateEmbauche = aDateEmbauche;
	}

	/**
	 * set the value of mdp
	 * 
	 * @param aMdp
	 */
	public void setMdp(String aMdp) {
		this.mdp = aMdp;
	}

	/**
	 * set the value of cp
	 * 
	 * @param aCp
	 */
	public void setCp(String aCp) {
		this.cp = aCp;
	}

	/**
	 * set the value of adresse
	 * 
	 * @param aAdresse
	 */
	public void setAdresse(String aAdresse) {
		this.adresse = aAdresse;
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
	 * set the value of ville
	 * 
	 * @param aVille
	 */
	public void setVille(String aVille) {
		this.ville = aVille;
	}

	/**
	 * set the value of login
	 * 
	 * @param aLogin
	 */
	public void setLogin(String aLogin) {
		this.login = aLogin;
	}

	/**
	 * set the value of nom
	 * 
	 * @param aNom
	 */
	public void setNom(String aNom) {
		this.nom = aNom;
	}

	/**
	 * @return the comptable
	 */
	public boolean isComptable() {
		return comptable;
	}

	/**
	 * @param comptable
	 *            the comptable to set
	 */
	public void setComptable(boolean comptable) {
		this.comptable = comptable;
	}

	@Override
	public String toString() {
		return nom + " " + prenom;
	}
}