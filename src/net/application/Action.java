package net.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kernel.KArchive;
import net.kernel.KFichefrais;
import net.kernel.KHelp;
import net.kernel.KLignefraisforfait;
import net.kernel.KLignefraishorsforfait;
import net.kernel.KVisiteur;
import net.ko.cache.KCache;
import net.ko.framework.Ko;
import net.ko.framework.KoHttp;
import net.ko.framework.KoSession;
import net.ko.http.objects.KRequest;
import net.ko.kobject.KListObject;
import net.ko.kobject.KRecordStatus;
import net.ko.utils.KString;

import org.apache.catalina.Globals;

/**
 * Liste des actions de l'application (Servlet)
 */
@WebServlet(name = "Action", urlPatterns = { "*.act" })
public class Action extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Action() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retourne l'action correspondant à la requête
	 * 
	 * @param request
	 *            Requête
	 * @return action correspondante
	 */
	public String getAction(HttpServletRequest request) {
		String action = (String) request.getAttribute(Globals.DISPATCHER_REQUEST_PATH_ATTR);
		action = KString.getLastAfter(action, "/");
		action = action.replace(".act", "");
		return action;
	}

	/**
	 * Traitement des actions sur les vues à affichage limité
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF8");
		PrintWriter out = response.getWriter();
		String action = getAction(request);
		boolean result = false;
		String id;
		KFichefrais fiche = null;
		try {
			switch (action) {
			case "dFiche":
				id = KRequest.GET("request.id", request);
				id = id.replace("lnk-", "");
				KRequest.forward("displayFiche.do", request, response, "id=" + id);
				break;

			case "activeFiche":
				fiche = GSB.getActiveFiche(request);
				if (fiche != null)
					KRequest.forward("activeFiche.do", request, response, "id=" + fiche.getId());
				break;

			case "deleteFiche":
				fiche = GSB.deleteActiveFiche(request);
				if (fiche.getRecordStatus().equals(KRecordStatus.rsLoaded)) {
					out.print("Fiche supprimée : <br>" + fiche);
					result = true;
				}
				if (!result)
					out.print("Enregistrement impossible");
				break;

			case "mesFiches":
				KVisiteur vi = GSB.getActiveVisiteur(request);
				if (vi != null)
					KRequest.forward("mesFiches.do", request, response, "idVisiteur=" + vi.getId());
				break;

			case "valideFiche":
				Object of = request.getAttribute("fiche");
				if (of != null) {
					out.print(of + "<br>Enregistrement effectué");
				} else
					out.print("Problème lors de l'enregistrement");
				break;

			case "updateFicheStatut":
				id = KRequest.GETPOST("request.id", request);
				String etat = KRequest.GETPOST("etat", request);
				if (id != null) {
					id = id.replace("ck-", "");
					KFichefrais fi = KoSession.kloadOne(KFichefrais.class, (Object) id);
					if (Ko.useCache)
						KCache.removeKLFrom(fi.getClass());
					fi.setIdEtat(etat);
					KoSession.update(fi);
					if (etat.equals("VA")) {
						out.print("<div class='isValid'>La fiche <br><b>" + fi + "</b><br> a été validée. <input type='button' value='Annuler la validation' id='btCancelValidation' class='btn'>");
						out.print("<input type='hidden' id='idFiche' value='" + id + "'></div>");
					} else
						out.print("<div class='isInvalid'>Validation de <br><b>" + fi + "</b></br> annulée.</div>");
					out.print(KoHttp.kajaxIncludes(request));
				}
				break;

			case "updateOneFF":
				String k = KRequest.GETPOST("request.id", request);
				if (k != null) {
					k = k.replace("ck-", "");
					String[] keys = k.split("_");
					if (keys.length > 1) {
						String idFraisforfait = keys[0];
						String idFicheFrais = keys[1];
						KLignefraisforfait lff = new KLignefraisforfait();
						lff.setIdFichefrais(Integer.valueOf(idFicheFrais));
						lff.setIdFraisforfait(idFraisforfait);
						KoSession.kloadOne(lff);
						lff.setValid(!lff.isValid());
						KoSession.update(lff);
						if (lff.isValid()) {
							out.print("<span class='isValid'><b>" + lff + "</b> a été validé.</span>");
						} else
							out.print("<span class='isInvalid'><b>" + lff + "</b> n'a pas été validé.</span>");

					}
				}
				break;

			case "updateOneHF":
				String kHF = KRequest.GETPOST("request.id", request);
				if (kHF != null) {
					kHF = kHF.replace("ck-", "");
					KLignefraishorsforfait lfhf = KoSession.kloadOne(KLignefraishorsforfait.class, (Object) kHF);
					lfhf.setValid(!lfhf.isValid());
					KoSession.update(lfhf);
					if (lfhf.isValid()) {
						out.print("<span class='isValid'><b>" + lfhf + "</b> a été validé.</span>");
					} else
						out.print("<span class='isInvalid'><b>" + lfhf + "</b> n'a pas été validé.</span>");
				}
				break;

			case "cloturerFiches":
				updateFichesEtat(out, request, "toClose", new String[] { "CR", "CL" }, new String[] { "fiche cloturée", "fiche réouverte" });
				break;

			case "validateSelected":
				updateFichesEtat(out, request, "toValidate", new String[] { "CL", "VA" }, new String[] { "fiche validée", "fiche non validée" });
				break;

			case "deleteZero":
				int nbDelete = 0;
				KListObject<KFichefrais> liste = KoSession.kloadMany(KFichefrais.class, "idEtat='CR'");
				for (KFichefrais ff : liste) {
					if (ff.isEmpty()) {
						ff.toDelete();
						nbDelete++;
					}
				}
				KoSession.persist(liste);
				out.print(KString.pluriel(nbDelete, "fiche vide supprimée"));
				break;

			case "rembourserFiches":
				updateFichesEtat(out, request, "toPay", new String[] { "VA", "RB" }, new String[] { "fiche remboursée", "fiche annulée" });
				break;

			case "archiverFiches":
				archiveFiches(out, request);
				break;

			case "archiverFichesParMois":
				archiveFichesParMois(out, request);
				break;

			case "invalidControl":
				out.print(KoHttp.kajaxIncludes(request));
				break;

			case "help":
				String activePage = request.getSession().getAttribute("activePage") + "";
				showHelp(activePage, out);

				break;
			case "HELP":
				showHelps("*", request, out);
				break;

			case "helpIndex":
				KListObject<KHelp> helpIndex = (KListObject<KHelp>) KoSession.kloadMany(KHelp.class, "access='" + getAccessHelp(request) + "'", "ordre");
				out.print("<div class='menu' id='indexHelp'><div id='title'>Index</div>");
				String maskH = "<a class='iHelp' id='{key}'>{caption}</a>";
				out.print(helpIndex.showWithMask(maskH));
				out.print("<a class='iHelp' id='allId'>Tous les items</a>");
				out.print("</div>");
				out.print(KoHttp.kajaxIncludes(request));
				break;

			case "showHelpByIndex":
				String helpId = request.getParameter("helpId");
				showHelps(helpId, request, out);
				break;

			case "visiteurAleatoire":
				Random rand = new Random();
				int nombreAleatoire = rand.nextInt(27) + 1;
				KVisiteur v = KoSession.kloadOne(KVisiteur.class, nombreAleatoire);
				if (v.isLoaded() && !v.isComptable()) {
					out.print("{\"login\":\"" + v.getLogin() + "\",\"mdp\":\"" + v.getMdp() + "\"}");
				} else
					out.print("{\"login\":\"dandre\",\"mdp\":\"oppg5\"}");
				break;

			default:
				break;
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Lance l'archivage des fiches des mois sélectionnés
	 * 
	 * @param out
	 *            Flux de sortie
	 * @param request
	 *            Requête contenant le paramètre toArchiveParMois
	 */
	private void archiveFichesParMois(PrintWriter out, HttpServletRequest request) {
		String toUpdate = KRequest.GETPOSTValues("toArchiveParMois", request, "");
		int nbUpdate = 0;
		if (!"".equals(toUpdate)) {
			String[] months = toUpdate.split(";");
			if (months.length > 0) {
				String where = "";
				for (String m : months) {
					if (!"".equals(where))
						where += " or mois=" + m;
					else
						where += " mois=" + m;
				}
				if ("".equals(where))
					where = "mois=13";

				try {
					KListObject<KFichefrais> ff = (KListObject<KFichefrais>) KoSession.kloadMany(KFichefrais.class, "idEtat='RB' and (" + where + ")");
					nbUpdate = ff.count();
					archiveFiches(ff);
				} catch (Exception e) {
				}
			}
		}
		out.print(KString.pluriel(nbUpdate, "Fiche archivée"));
	}

	/**
	 * /**Lance l'archivage de fiches sélectionnées
	 * 
	 * @param out
	 *            Flux de sortie
	 * @param request
	 *            Requête contenant le paramètre toArchive
	 */
	private void archiveFiches(PrintWriter out, HttpServletRequest request) {
		String toUpdate = KRequest.GETPOSTValues("toArchive", request, "");
		int nbUpdate = 0;
		if (!"".equals(toUpdate)) {
			String[] keys = toUpdate.split(";");
			if (keys.length > 0) {
				KListObject<KFichefrais> ff = null;
				try {
					ff = KoSession.kloadMany(KFichefrais.class, (Object[]) keys);
					archiveFiches(ff);
					nbUpdate = ff.count();
				} catch (Exception e) {
				}
			}
		}
		out.print(KString.pluriel(nbUpdate, "Fiche archivée"));
	}

	/**
	 * Procède à l'archivage de la liste des fiches passée en paramètre
	 * 
	 * @param ff
	 *            Liste des fiches
	 */
	private void archiveFiches(KListObject<KFichefrais> ff) {
		KListObject<KArchive> archives = new KListObject<>(KArchive.class);
		try {
			for (KFichefrais f : ff) {
				KArchive ar = new KArchive();
				ar.archive(f);
				archives.add(ar);
				ar.toAdd();
				f.toDelete();
			}
			KoSession.persist(archives);
			KoSession.persist(ff);
		} catch (Exception e) {
		}
	}

	/**
	 * @param out
	 *            Flux de sortie
	 * @param request
	 *            Requête
	 * @param requestId
	 *            id des fiches à mettre à jour issu d'un paramètre de la
	 *            requête
	 * @param states
	 *            Tableau des états (origine -> destination)
	 * @param messages
	 *            Tableau des messages à afficher (destination <- origine)
	 */
	private void updateFichesEtat(PrintWriter out, HttpServletRequest request, String requestId, String[] states, String[] messages) {
		String toUpdate = KRequest.GETPOSTValues(requestId, request, "");
		int nbUpdate = 0, nbNotUpdate = 0;
		float montantUpdate = 0, montantNotUpdate = 0;
		if (!"".equals(toUpdate)) {
			String[] keys = toUpdate.split(";");
			if (keys.length > 0) {
				KListObject<KFichefrais> ff = KoSession.kloadMany(KFichefrais.class, (Object[]) keys);
				for (KFichefrais f : ff) {
					if (f.getIdEtat().equals(states[0])) {
						nbUpdate++;
						f.setIdEtat(states[1]);
						montantUpdate += f.getMontantToValide();
					} else {
						nbNotUpdate++;
						f.setIdEtat(states[0]);
						montantNotUpdate += f.getMontantToValide();
					}
					f.toUpdate();
				}
				KoSession.persist(ff);
			}
		}
		out.print(KString.pluriel(nbUpdate, messages[0]) + " (" + montantUpdate + " euros)<br>");
		out.print(KString.pluriel(nbNotUpdate, messages[1]) + " (" + montantNotUpdate + " euros)");
		if (nbUpdate > 0 || nbNotUpdate > 0) {
			out.print("<form name='frmCancel' id='frmCancel'>");
			out.print("<input type='hidden' value='" + toUpdate + "' id='" + requestId + "' name='" + requestId + "'>");
			out.print("<input id='btCancel' type='button' class='btn' value=\"Annuler l'opération\"></form>");
		}
		out.print(KoHttp.kajaxIncludes(request));
	}

	private void showHelp(Object key, PrintWriter out) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		KHelp help = KoSession.kloadOne(KHelp.class, key);
		out.print("<div id='innerHelp' class='innerHelp'><div id='anchor' onclick='Forms.Utils.Anchor(\"lb-Aide_contextuelle-box-boxContent\",\"fixedHelp\");$(\"lb-Aide_contextuelle-box-boxContent\").lb.showHide();'>&nbsp;</div>");
		if (help.getRecordStatus().equals(KRecordStatus.rsLoaded)) {
			out.print("<div class='titleHelp'>Aide sur la page <u>" + help.getCaption() + "</u></div>");
			out.print("<div class='contentHelp'>" + help.getMessage() + "</div>");
		} else
			out.print("<div class='titleHelp'>Aucune aide disponible sur la page " + key + "</div>");
		out.print("</div>");
	}

	private void showHelps(String key, HttpServletRequest request, PrintWriter out) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		KListObject<KHelp> helps = null;
		if ("allId".equals(key))
			helps = KoSession.kloadMany(KHelp.class, "access='" + getAccessHelp(request) + "'", "ordre");
		else
			helps = KoSession.kloadMany(KHelp.class, "access='" + getAccessHelp(request) + "' and key='" + key + "'");
		String mask = "<div class='sHelpBorder'><span class='sHelp'>{caption}</span></div>";
		mask += "<div class='contentHelp'>{getMessage}</div>";
		out.print(helps.showWithMask(mask));
	}

	public String getAccessHelp(HttpServletRequest request) {
		String result = "no";
		KVisiteur vi = GSB.getActiveVisiteur(request);
		if (vi != null) {
			result = "vi";
			if (vi.isComptable())
				result = "co";
		}
		return result;
	}
}
