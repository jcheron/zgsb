<%@page import="net.ko.framework.KoSession"%>
<%@page import="net.ko.kobject.KObject"%>
<%@page import="net.ko.dao.IGenericDao"%>
<%@page import="net.kernel.KFichefrais"%>
<%@page import="net.ko.framework.KoHttp"%>
<%@page import="net.ko.framework.Ko"%>
<%@page import="net.ko.http.objects.KRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id=KRequest.GETPOST("request.id", request);
String etat=KRequest.GETPOST("etat", request);
if(id!=null){
	id=id.replace("ck-", "");
	KFichefrais fi=new KFichefrais();
	
	fi.setId(Integer.valueOf(id));
	KoSession.kloadOne(fi);
	fi.setIdEtat(etat);
	fi.toUpdate();
	KoSession.persist(fi);
	
	if(etat.equals("VA")){
		out.print("La fiche <br><b>"+fi+"</b><br> a été validée. <input type='button' value='Annuler la validation' id='btCancelValidation' class='btn'>");
		out.print("<input type='hidden' id='idFiche' value='"+id+"'></div>");
	}else
		out.print("Validation de <br><b>"+fi+"</b></br> annulée.");
	out.print(KoHttp.kajaxIncludes(request));
}
%>