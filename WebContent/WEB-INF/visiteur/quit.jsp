<%@page import="net.ko.framework.KoHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
session.invalidate();
%>
<fieldset style="width:300px">
	Pour vous connecter à nouveau :
	<fieldset class="boxButtons">
		<input type='button' class='btn' id='btConnect' value="Connexion">
	</fieldset>
</fieldset>
<div id="divAction"></div>
<%=KoHttp.kajaxIncludes(request)%>