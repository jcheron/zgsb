<%@page import="net.ko.framework.KoHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<fieldset style="width:300px">
	<div>Connexion refusée,</div>
	<div>Login ou mot de passe incorrect.</div>
	<fieldset class="boxButtons">
		<input type="button" id="btTryAgain" value="Réessayer" class="btn">
	</fieldset>
</fieldset>
<%=KoHttp.kajaxIncludes(request)%>