package net.display;

import javax.servlet.http.HttpServletRequest;

import net.application.GSB;
import net.kernel.KFichefrais;
import net.ko.http.views.KPageList;
import net.ko.kobject.KObject;

public class DispFicheACloturer extends DispFicheAValider {

	@Override
	public void beforeLoading(Class<? extends KObject> clazz, KPageList list, HttpServletRequest request) {
		list.addWhere("idEtat='CR'");
	}

	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		String result = "";
		switch (memberName) {
		case "Estimé":
			result = ((KFichefrais) ko).getMontant() + "";
			break;
		case "ckEtat":
			result = "<div class='toClose'><input class='toCloseCk' type='checkbox' title='Cloturer " + ko + "' name='toClose' id='ck-" + ko.getFirstKeyValue() + "' value='" + ko.getFirstKeyValue() + "'><label for='ck-" + ko.getFirstKeyValue() + "'>&nbsp;</label></div>";
			break;
		case "Mois":
			result = GSB.months[Integer.valueOf(((KFichefrais) ko).getMois())];
			break;
		case "nb FF":
			result = ((KFichefrais) ko).getNbFF() + "";
			break;
		case "nb FHF":
			result = ((KFichefrais) ko).getNbFHF() + "";
			break;
		case "Voir":
			result = "<input class='btn see' id='see-" + ko.getFirstKeyValue() + "' type='button' value='...'>";
			break;
		default:
			result = super.showInList(ko, memberName, request);
			break;
		}
		return result;
	}

}
