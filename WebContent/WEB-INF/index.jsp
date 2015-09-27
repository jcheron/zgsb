<%@page import="net.ko.framework.Ko"%>
<%@page import="net.application.GSB"%>
<%@page import="net.ko.http.objects.KRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<div id="head" class="head">Laboratoire pharmaceutique<span>Gestion des frais de visite</span></div>
<div class="mainCadre">
	<div class="leftCadre"><div id="divMenu"></div><div id="fixedHelp" class="innerHelpFixed"></div></div>
	<div class="rightCadre">
		<div id="divVisiteurInfo">
			<div id="divFrmLogin">
			</div>
			<div id="divMessageConnexion"></div>
		</div>
		<div id="divMain">
			<%
			Ko.loadAllEntities();
			out.flush();
			KRequest.include("main.jsp", request, response);
			out.flush();
			%>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>