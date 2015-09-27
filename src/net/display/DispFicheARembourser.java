package net.display;

import javax.servlet.http.HttpServletRequest;

import net.application.GSB;
import net.kernel.KFichefrais;
import net.ko.http.views.KPageList;
import net.ko.kobject.KObject;

public class DispFicheARembourser extends DispFicheAValider {
	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		if ("ckEtat".equals(memberName)) {
			return "<div class='field'><input class='toPayCk' type='checkbox' title='Rembourser " + ko + "' name='toPay' id='ck-" + ko.getFirstKeyValue() + "' value='" + ko.getFirstKeyValue() + "'><label for='ck-" + ko.getFirstKeyValue() + "'>&nbsp;</label></div>";
		} else if ("Mois".equals(memberName)) {
			return GSB.months[Integer.valueOf(((KFichefrais) ko).getMois())];
		}
		else
			return super.showInList(ko, memberName, request);
	}

	@Override
	public void beforeLoading(Class<? extends KObject> clazz, KPageList list, HttpServletRequest request) {
		list.addWhere("idEtat='VA'");
	}
}
