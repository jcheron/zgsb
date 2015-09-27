<%@page import="net.application.GSB"%>
<%@page import="net.ko.framework.KoHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="menu" id="menuComptable"><div>Comptable</div>
	<%=GSB.getActiveMenus(request).showWithMask("{getMenuItem}") %>
</div>
<%=KoHttp.kajaxIncludes(request)%>