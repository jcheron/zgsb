package net.application;

import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import net.kernel.KFichefrais;
import net.kernel.KMenu;
import net.kernel.KVisiteur;
import net.ko.dao.IGenericDao;
import net.ko.framework.Ko;
import net.ko.framework.KoSession;
import net.ko.http.objects.KRequest;
import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;
import net.ko.ksql.KParameterizedInstruction;

/**
 * Classe de méthodes utilitaires liées à l'application
 * 
 * @author jc
 * 
 */
public class GSB {
	public static String[] months = new String[] { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
	public static KListObject<KMenu> menus = null;

	/**
	 * Retourne la liste complète des menus
	 * 
	 * @return Liste des menus
	 */
	@SuppressWarnings("unchecked")
	public static KListObject<KMenu> getMenus() {
		// if(menus==null){
		menus = new KListObject<>(KMenu.class);
		IGenericDao<? extends KObject> dao = Ko.getDao(KMenu.class);
		KParameterizedInstruction condition = new KParameterizedInstruction(dao.quote());
		condition.setTableName("Menu");
		condition.setOrderBy("ordre");
		menus = (KListObject<KMenu>) dao.readAll(condition);
		// menus.loadFromDb(Ko.kdatabase(),"SELECT * FROM Menu order by ordre");
		return menus;
	}

	/**
	 * Retourne la liste des menus accessibles pour le type d'utilisateur actif
	 * 
	 * @param request
	 *            Requête
	 * @return Liste des menus
	 */
	public static KListObject<KMenu> getActiveMenus(HttpServletRequest request) {
		KVisiteur vi = getActiveVisiteur(request);
		KListObject<KMenu> result = new KListObject<>(KMenu.class);
		if (vi != null) {
			result = getMenus().select("comptable=" + vi.isComptable());
		}
		return result;
	}

	/**
	 * Fait une tentative de connexion au site en utilisant les paramètres
	 * postés dans request
	 * 
	 * @param request
	 * @return vrai si la connexion a réussi
	 */
	public static boolean login(HttpServletRequest request) {
		boolean result = false;
		String login = KRequest.GETPOST("login", request);
		String password = KRequest.GETPOST("mdp", request);
		boolean useCache = Ko.useCache;
		Ko.useCache = false;
		KVisiteur vi = KoSession.kloadOne(KVisiteur.class, "login='" + login + "'");
		Ko.useCache = useCache;
		if (password != null && password.equals(vi.getMdp())) {
			request.getSession().setAttribute("activeVisiteur", vi);
			result = true;
		}
		return result;
	}

	/**
	 * Retourne le visiteur actif stocké dans la variable de session
	 * activeVisiteur
	 * 
	 * @param request
	 * @return null ou le visiteur connecté
	 */
	public static KVisiteur getActiveVisiteur(HttpServletRequest request) {
		KVisiteur vi = (KVisiteur) request.getSession().getAttribute("activeVisiteur");
		return vi;
	}

	/**
	 * Vérifie qu'un utilisateur est logué
	 * 
	 * @param request
	 * @return vrai si un visiteur est logué
	 */
	public static boolean isLogged(HttpServletRequest request) {
		return getActiveVisiteur(request) != null;
	}

	/**
	 * Retourne vrai si l'utilisateur connecté est comptable
	 * 
	 * @param request
	 * @return vrai si comptable
	 */
	public static boolean isComptable(HttpServletRequest request) {
		KVisiteur vi = getActiveVisiteur(request);
		boolean result = false;
		if (vi != null)
			result = vi.isComptable();
		return result;
	}

	/**
	 * Retourne le numéro du mois actif
	 * 
	 * @return numéro du mois
	 */
	public static int getMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH);
	}

	/**
	 * Retourne la fiche de frais du mois en cours, pour le visiteur connecté
	 * 
	 * @param request
	 * @return Fiche de frais du mois
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static KFichefrais getActiveFiche(HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getActiveFiche(request, GSB.getMonth());
	}

	private static KFichefrais getActiveFiche(HttpServletRequest request, int month) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		KFichefrais fiche = null;
		KVisiteur vi = GSB.getActiveVisiteur(request);
		if (vi != null) {
			fiche = KoSession.kloadOne(KFichefrais.class, "idVisiteur=" + vi.getId() + " and mois=" + month);
			if (!fiche.isLoaded() || !"CR".equals(fiche.getIdEtat())) {
				if (fiche.isLoaded() && !"CR".equals(fiche.getIdEtat()))
					return getActiveFiche(request, GSB.getNextMonth(month));
				fiche.setIdVisiteur(Integer.valueOf(vi.getId() + ""));
				fiche.setMois(month + "");
				fiche.setIdEtat("CR");
				KoSession.add(fiche);
			}
		}
		return fiche;
	}

	/**
	 * Retourne le numéro du mois suivant celui passé en paramètre
	 * 
	 * @param month
	 * @return
	 */
	public static int getNextMonth(int month) {
		int nextMonth = month + 1;
		if (nextMonth == 12)
			nextMonth = 0;
		return nextMonth;
	}

	/**
	 * Supprime la fiche de Frais active
	 * 
	 * @param request
	 * @return La fiche supprimée
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static KFichefrais deleteActiveFiche(HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		KFichefrais fiche = getActiveFiche(request);
		if (fiche != null) {
			KoSession.delete(fiche);
		}
		return fiche;
	}

	/**
	 * Charge depuis la base et retourne une fiche par son id
	 * 
	 * @param id
	 * @return La fiche correpondant
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static KFichefrais getFicheById(String id) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		KFichefrais fiche = KoSession.kloadOne(KFichefrais.class, (Object) id);
		return fiche;
	}
}
