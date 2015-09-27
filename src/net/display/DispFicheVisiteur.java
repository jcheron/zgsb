package net.display;

import javax.servlet.http.HttpServletRequest;

import net.application.GSB;
import net.kernel.KFichefrais;
import net.ko.controller.KObjectController;
import net.ko.displays.KObjectDisplay;
import net.ko.http.objects.KRequest;
import net.ko.http.views.KFieldControl;
import net.ko.http.views.KHtmlFieldControl;
import net.ko.http.views.KPageList;
import net.ko.kobject.KObject;
import net.ko.types.HtmlControlType;

public class DispFicheVisiteur extends KObjectDisplay {

	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		String result = "";
		String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
		KFichefrais fiche = null;
		if (ko instanceof KFichefrais)
			fiche = (KFichefrais) ko;
		switch (memberName) {
		case "visiteur":
			String color = "black";
			if (fiche.getMontantValide() > 100)
				color = "red";
			result = "<span style='color:" + color + ";'>" + fiche.getVisiteur() + "</span>";
			break;
		case "Mois":
			result = mois[Integer.valueOf(fiche.getMois())];
			break;
		case "Voir":
			result = "<span class='btn' id='lnk-" + fiche.getId() + "'>Consulter...</span>";
			break;
		default:
			result = super.showInList(ko, memberName, request);
			break;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getFormCaption(java.lang.Class,
	 * net.ko.controller.KObjectController)
	 */
	@Override
	public String getFormCaption(Class<? extends KObject> clazz, KObjectController koc) {
		return "Fiche de frais";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getControl(net.ko.kobject.KObject,
	 * java.lang.String, net.ko.controller.KObjectController,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public KFieldControl getControl(KObject ko, String memberName, KObjectController koc, HttpServletRequest request) {
		KFieldControl result = super.getControl(ko, memberName, koc, request);
		switch (memberName) {
		case "idVisiteur":
			result.setFieldType(HtmlControlType.khcReadOnlyList);
			((KHtmlFieldControl) result).setClassName("vous");
			((KHtmlFieldControl) result).setLabelClassName("inlineLabel");
			result.setCaption("Vous :");
			if (GSB.isLogged(request)) {
				String strIdVisiteur = GSB.getActiveVisiteur(request).getId() + "";
				result.setValue(strIdVisiteur);
			}
			break;
		case "idEtat":
			result.setFieldType(HtmlControlType.khcReadOnlyList);
			((KHtmlFieldControl) result).setOptions("style='width:250px'");
			break;
		case "dateModif":
		case "montantValide":
		case "nbJustificatifs":
			result.setFieldType(HtmlControlType.khcReadOnlyText);
			break;
		case "mois":
			result.setFieldType(HtmlControlType.khcReadOnlyList);
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public String getCaption(Class<? extends KObject> clazz) {
		return "Fiches de frais";
	}

	@Override
	public String getCaption(KObject ko, String memberName) {
		String result = "";
		switch (memberName) {
		case "lignefraishorsforfaits":
			result = "Hors forfait";
			break;
		case "lignefraisforfaits":
			result = "Forfaitaire";
			break;
		case "Voir":
			result = "";
			break;
		default:
			result = super.getCaption(ko, memberName);
			break;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.ko.displays.KObjectDisplay#getReadOnlyControl(net.ko.kobject.KObject,
	 * java.lang.String, net.ko.controller.KObjectController,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public KFieldControl getReadOnlyControl(KObject ko, String memberName, KObjectController koc, HttpServletRequest request) {
		return super.getReadOnlyControl(ko, memberName, koc, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#beforeLoading(java.lang.Class,
	 * net.ko.http.views.KPageList, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void beforeLoading(Class<? extends KObject> clazz, KPageList list, HttpServletRequest request) {
		list.addWhere("idVisiteur=" + KRequest.GETPOST("idVisiteur", request));
	}

}
