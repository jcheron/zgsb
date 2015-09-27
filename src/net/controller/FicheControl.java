package net.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kernel.KFichefrais;
import net.ko.http.objects.KRequest;
import net.ko.http.views.KHttpForm;

public class FicheControl extends MainControl {

	@Override
	public boolean isValid(HttpServletRequest request, HttpServletResponse response) {
		boolean result = super.isValid(request, response);
		if (result) {
			if (KRequest.isPost(request)) {
				KHttpForm frm = new KHttpForm(new KFichefrais(), request);
				try {
					if (frm.loadAndSubmit()) {
						request.setAttribute("fiche", frm.getKobject());
					}
				} catch (SecurityException | IllegalArgumentException | NoSuchFieldException | IllegalAccessException | InstantiationException | ClassNotFoundException | IOException e) {
					result = false;
				}

			} else
				result = false;
		}
		return result;
	}

	@Override
	public void onInvalidControl(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out;
		try {
			response.setCharacterEncoding("UTF8");
			out = response.getWriter();
			out.print("Problème pendant l'enregistrement");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean beforeProcessAction(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}
