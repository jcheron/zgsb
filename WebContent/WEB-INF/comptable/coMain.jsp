<%@page import="net.ko.framework.KoHttp"%>
<%@page import="net.application.GSB"%>
<%@page import="net.ko.http.objects.KRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div class="zone">
		<div class="inline">
			<div id="menuMessage"></div>
			<div id="divActionMessage">Prêt</div>
		</div>
		<div class="bigHelp inline" id="btShowHelp">&nbsp;</div>
	</div>
	<div id="divAction">
		<div style="padding:20px">
			<div class="inline" id="mainComptable">
			<%=GSB.getActiveMenus(request).showWithMask("{getMainItem}") %>
			</div>
			<div class="inline" style="vertical-align: top;">
				<div class="innerDlg"><div id="mainInfo" class="mainInfo">&nbsp;</div></div>
			</div>
		</div>
	</div>
<%=KoHttp.kajaxIncludes(request)%>