package net.display;

import javax.servlet.http.HttpServletRequest;

import net.application.GSB;
import net.kernel.KArchive;
import net.ko.displays.KObjectDisplay;
import net.ko.http.js.KJavaScript;
import net.ko.kobject.KObject;

public class DispArchive extends KObjectDisplay {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#showInList(net.ko.kobject.KObject,
	 * java.lang.String)
	 */
	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		String result = "";
		KArchive ar = (KArchive) ko;
		switch (memberName) {
		case "fraisforfait":
			String s = lnToElement(super.showInList(ko, memberName, request));
			if (!"".equals(s))
				result = KJavaScript.infoBulle("F. forfaitaires", s);
			break;

		case "fraishorsforfait":
			String sh = lnToElement(super.showInList(ko, memberName, request));
			if (!"".equals(sh))
				result = KJavaScript.infoBulle("F. hors forfait", sh);
			break;
		case "Mois":
			result = GSB.months[Integer.valueOf(ar.getMois())];
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
	 * @see net.ko.displays.KObjectDisplay#getCaption(java.lang.Class)
	 */
	@Override
	public String getCaption(Class<? extends KObject> clazz) {
		// TODO Auto-generated method stub
		return "Archives";
	}

	private String lnToElement(String toDisplay) {
		String[] strs = toDisplay.split("\n");
		String result = "";
		for (String l : strs) {
			if (!"".equals(l)) {
				if (l.startsWith("+"))
					l = "<span class='ok'>" + l.substring(1) + "</span>";
				else
					l = "<span class='nook'>" + l + "</span>";
				result += l;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getCaption(net.ko.kobject.KObject,
	 * java.lang.String)
	 */
	@Override
	public String getCaption(KObject ko, String memberName) {
		String result = "";
		switch (memberName) {
		case "fraisforfait":
			result = "F. forfaitaires";
			break;

		case "fraishorsforfait":
			result = "F. hors forfait";
			break;

		case "visiteur":
			result = "Visiteur";
			break;

		case "nbJustificatifs":
			result = "Nb. justificatifs";
			break;

		case "montantestime":
			result = "M. estimé";
			break;

		case "montantValide":
			result = "M. validé";
			break;

		default:
			result = super.getCaption(ko, memberName);
			break;
		}
		return result;
	}

}
