<%@page import="net.kernel.KVisiteur"%>
<%@page import="net.ko.framework.KoHttp"%>
<%@page import="net.application.GSB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
KVisiteur vi=GSB.getActiveVisiteur(request);
if(vi!=null){
	String statut="visiteur médical";
	if(vi.isComptable())
		statut="comptable";
	out.print(vi+" ("+statut+")&nbsp;<input type='button' class='btn' id='btDisconnect' value=\"Quitter l'application (F4)\">");
}
out.print(KoHttp.kajaxIncludes(request));
%>