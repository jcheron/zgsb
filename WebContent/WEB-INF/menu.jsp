<%@page import="net.ko.framework.KoHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="menu" id="menuInvite">
	<div id="title">Invité</div>
		<a title="Entrer dans l'application" id="btEnter">Connexion</a>
		<a title="Documentation" id="btDocumentation">Documentation</a>		
</div>
<%out.flush(); %>
<%=KoHttp.kajaxIncludes(request)%>