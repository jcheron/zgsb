package net.display;

import javax.servlet.http.HttpServletRequest;

import net.kernel.KVisiteur;
import net.ko.displays.KObjectDisplay;
import net.ko.http.views.KPageList;
import net.ko.interfaces.IKommande;
import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;

public class DispVisiteur extends KObjectDisplay {

	/* (non-Javadoc)
	 * @see net.ko.displays.KObjectDisplay#afterLoading(net.ko.kobject.KListObject, net.ko.http.views.KPageList, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void afterLoading(KListObject<? extends KObject> kl, KPageList list,HttpServletRequest request) {
		super.afterLoading(kl, list, request);
		kl.appyCommande(new IKommande() {
			
			@Override
			public void execute(KObject ko) {
				((KVisiteur)ko).setLogin("****");
			}
		});
	}


}
