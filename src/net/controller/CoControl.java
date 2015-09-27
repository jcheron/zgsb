package net.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.application.GSB;
import net.ko.http.objects.KRequest;

/**
 * Contrôle Vérifiant que l'utilisateur logué est un comptable
 * @author jc
 *
 */
public class CoControl extends MainControl {

	/**
	 * Vérifie que l'utilisateur est comptable
	 */
	@Override
	public boolean isValid(HttpServletRequest request,
			HttpServletResponse response) {
		boolean result=super.isValid(request, response)&&GSB.getActiveVisiteur(request).isComptable();
		return result;
	}

	/**
	 * Redirige vers les pages visiteurs, si elles existent, et vers invalidControl.do dans le cas contraire
	 */
	@Override
	public void onInvalidControl(HttpServletRequest request,
			HttpServletResponse response) {
		String newURL=request.getRequestURI().replace("/co", "/vi");
		try {
			if(newURL!=request.getRequestURI())
				KRequest.forward(newURL, request, response);
			else
				KRequest.forward("invalidControl.do", request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
