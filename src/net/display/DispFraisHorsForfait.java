package net.display;

import javax.servlet.http.HttpServletRequest;

import net.application.GSB;
import net.kernel.KLignefraishorsforfait;
import net.ko.controller.KObjectController;
import net.ko.displays.KObjectDisplay;
import net.ko.http.views.KFieldControl;
import net.ko.http.views.KHtmlFieldControl;
import net.ko.kobject.KObject;

public class DispFraisHorsForfait extends KObjectDisplay {
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getCaption(java.lang.Class)
	 */
	@Override
	public String getCaption(Class<? extends KObject> clazz) {
		return "Hors forfait";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getCaption(net.ko.kobject.KObject,
	 * java.lang.String)
	 */
	@Override
	public String getCaption(KObject ko, String memberName) {
		if ("validFHF".equals(memberName))
			return "Valide ?";
		else
			return super.getCaption(ko, memberName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getControl(net.ko.kobject.KObject,
	 * java.lang.String, net.ko.controller.KObjectController,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public KFieldControl getControl(KObject ko, String memberName,
			KObjectController koc, HttpServletRequest request) {
		KFieldControl fc = super.getControl(ko, memberName, koc, request);
		if ("validHF".equals(memberName)) {
			if (!GSB.isComptable(request))
				((KHtmlFieldControl) fc).setOptions("disabled");
			((KHtmlFieldControl) fc).setClassName("valid");
		}
		return fc;
	}

	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		if (memberName.contains("listeFrais")) {
			KLignefraishorsforfait lfhf = ((KLignefraishorsforfait) ko);
			if (lfhf.getMontant() > 0) {
				String checked = "";
				String cls = "isInvalid";
				if (lfhf.isValid()) {
					checked = "checked";
					cls = "isValid";
				}
				String keys = lfhf.getId() + "";
				if ("listeFrais".equals(memberName))
					return "<div class='field'><input onclick='this.value=(this.checked?\"true\":\"false\");' type='checkbox' " + checked + " class='validHF' name='ck-" + keys + "' id='ck-" + keys + "' value='true'><label for='ck-" + keys + "'>&nbsp;" + lfhf.getLibelle() + " (" + lfhf.getMontant() + " euros)</label></div>";
				else
					return "<div class='" + cls + "'><label>&nbsp;" + lfhf.getLibelle() + " (" + lfhf.getMontant() + " euros)</label></div>";
			}
			else
				return "";
		}
		return super.showInList(ko, memberName, request);
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
	public KFieldControl getReadOnlyControl(KObject ko, String memberName,
			KObjectController koc, HttpServletRequest request) {
		KFieldControl result;
		if ("validHF".equals(memberName)) {
			result = getControl(ko, memberName, koc, request);
			((KHtmlFieldControl) result).setOptions("disabled");
		}
		else
			result = super.getReadOnlyControl(ko, memberName, koc, request);
		return result;
	}
}
