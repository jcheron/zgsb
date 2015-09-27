package net.display;

import javax.servlet.http.HttpServletRequest;

import net.application.GSB;
import net.kernel.KLignefraisforfait;
import net.ko.controller.KObjectController;
import net.ko.displays.KObjectDisplay;
import net.ko.http.views.KFieldControl;
import net.ko.http.views.KHtmlFieldControl;
import net.ko.kobject.KObject;

public class DispFraisForfait extends KObjectDisplay {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getCaption(java.lang.Class)
	 */
	@Override
	public String getCaption(Class<? extends KObject> clazz) {
		return "Forfaitaire";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.ko.displays.KObjectDisplay#getCaption(net.ko.kobject.KObject,
	 * java.lang.String)
	 */
	@Override
	public String getCaption(KObject ko, String memberName) {
		if ("validFF".equals(memberName))
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
		if ("validFF".equals(memberName)) {
			if (!GSB.isComptable(request))
				((KHtmlFieldControl) fc).setOptions("disabled");
			((KHtmlFieldControl) fc).setClassName("valid");
		}
		return fc;
	}

	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		if (memberName.contains("listeFrais")) {
			KLignefraisforfait lff = ((KLignefraisforfait) ko);
			if (lff.getQuantite() > 0) {
				String checked = "";
				String cls = "isInvalid";
				if (lff.isValid()) {
					checked = "checked";
					cls = "isValid";
				}
				if ("listeFrais".equals(memberName)) {
					String keys = lff.getIdFraisforfait() + "_" + lff.getIdFichefrais();
					return "<div class='field'><input onclick='this.value=(this.checked?\"true\":\"false\");' type='checkbox' " + checked + " class='validFF' name='ck-" + keys + "' id='ck-" + keys + "' value='true'><label for='ck-" + keys + "'>&nbsp;" + lff.getFraisforfait() + " (" + lff.getQuantite() + ")</label></div>";
				} else
					return "<div class='" + cls + "'><label>&nbsp;" + lff.getFraisforfait() + " (" + lff.getQuantite() + ")</label></div>";
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
		if ("validFF".equals(memberName)) {
			result = getControl(ko, memberName, koc, request);
			((KHtmlFieldControl) result).setOptions("disabled");
		}
		else
			result = super.getReadOnlyControl(ko, memberName, koc, request);
		return result;
	}
}
