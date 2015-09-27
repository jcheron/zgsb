package net.display;

import javax.servlet.http.HttpServletRequest;

import net.kernel.KFichefrais;
import net.ko.displays.KObjectDisplay;
import net.ko.framework.Ko;
import net.ko.http.views.KPageList;
import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;

public class DispFicheAValider extends KObjectDisplay {

	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		String result = "";
		if ("ckEtat".equals(memberName)) {
			result = "<div title='Valider " + ko + "' class='btn ckEtat' id='ck-" + ko.getFirstKeyValue() + "'><span class='isValid'>&nbsp;</span></div>";
		} else if ("ckEtatCB".equals(memberName)) {
			KFichefrais ff = (KFichefrais) ko;
			result = "<div title='Valider " + ko + "' class='field'><input name='toValidate' checked type='checkbox' id='ckCB-" + ko.getFirstKeyValue() + "' value='" + ko.getFirstKeyValue() + "'><label for='ckCB-" + ko.getFirstKeyValue() + "'>&nbsp;" + ff.getVisiteur() + " (" + ff.getMontantValide() + " euros)</label></div>";
		} else
			result = super.showInList(ko, memberName, request);
		return result;
	}

	@Override
	public String getCaption(KObject ko, String memberName) {
		String result = "";
		switch (memberName) {
		case "ckEtat":
		case "ckEtatCB":
			result = "";
			break;
		case "lignefraisforfaits":
			result = "Frais forfaitaires";
			break;
		case "lignefraishorsforfaits":
			result = "Frais hors forfait";
			break;
		case "montantValide":
			result = "Montant validé";
			break;
		case "dateModif":
			result = "Date Modif.";
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
	 * @see net.ko.displays.KObjectDisplay#beforeLoading(java.lang.Class,
	 * net.ko.http.views.KPageList, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void beforeLoading(Class<? extends KObject> clazz, KPageList list, HttpServletRequest request) {
		Ko.setTempConstraintDeph(2);
		list.addWhere("idETAT='CL'");
	}

	@Override
	public void afterLoading(KListObject<? extends KObject> kl, KPageList list, HttpServletRequest request) {
		Ko.restoreConstraintDeph();
	}

}
