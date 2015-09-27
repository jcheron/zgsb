package net.display;

import javax.servlet.http.HttpServletRequest;

import net.ko.http.views.KPageList;
import net.ko.kobject.KObject;

public class DispFicheAArchiver extends DispFicheACloturer {
	@Override
	public void beforeLoading(Class<? extends KObject> clazz, KPageList list, HttpServletRequest request) {
		list.addWhere("idEtat='RB'");
	}

	@Override
	public String showInList(KObject ko, String memberName, HttpServletRequest request) {
		String result = "";
		switch (memberName) {
		case "ckEtat":
			result = "<div class='field'><input class='toArchiveCk' type='checkbox' title='Archiver " + ko + "' name='toArchive' id='ck-" + ko.getFirstKeyValue() + "' value='" + ko.getFirstKeyValue() + "'><label for='ck-" + ko.getFirstKeyValue() + "'>&nbsp;</label></div>";
			break;
		default:
			result = super.showInList(ko, memberName, request);
			break;
		}
		return result;
	}
}
