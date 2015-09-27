<%@page import="net.application.GSB"%>
<%@page import="net.ko.framework.KoHttp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
Menu <%=GSB.getActiveVisiteur(request) %>
<%=KoHttp.kajaxIncludes(request)%>