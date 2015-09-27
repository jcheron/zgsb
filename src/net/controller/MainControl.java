package net.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.application.GSB;
import net.ko.http.objects.KRequest;
import net.ko.mapping.IMappingControl;

/**
 * Contrôle principal de l'application
 * Vérifie qu'un utilisateur est logué
 * @author jc
 *
 */
public class MainControl implements IMappingControl {
	/**
	 * Vérifie qu'un utilisateur est logué
	 */
	@Override
	public boolean isValid(HttpServletRequest request,
			HttpServletResponse response) {
		boolean result=GSB.isLogged(request);
		if(KRequest.isPost(request)){
			if(!result&&KRequest.GETPOST("login", request)!=null){
				result=GSB.login(request);
			}
		}
		
		return result;
	}

	/**
	 * Redirige vers les pages invalid...
	 */
	@Override
	public void onInvalidControl(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if(KRequest.isPost(request)){
				KRequest.forward("/invalidVisiteur.do", request, response);
			}else{
				String newURL=request.getRequestURI().replace("/co/", "").replace("/vi/", "");
				if(newURL!=request.getRequestURI())
					KRequest.forward(newURL, request, response);
				else
					KRequest.forward("invalidControl.do", request, response);
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean beforeProcessAction(HttpServletRequest request,
			HttpServletResponse response) {
		String realRequestURL=request.getServletPath();
		realRequestURL=realRequestURL.replace(request.getServletContext().getContextPath(),"");
		realRequestURL=realRequestURL.substring(realRequestURL.lastIndexOf("/")+1);
		request.getSession().setAttribute("activePage", realRequestURL);
		return true;
	}

}
