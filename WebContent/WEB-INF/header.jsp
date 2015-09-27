<%@ page session="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%String path=request.getServletContext().getContextPath(); 
if((request.getServerName()).contains("kobject"))
	path="";
%>
<!DOCTYPE HTML>
<html>
<head>
<script src="<%=path%>/js/forms.js"></script>
<script src="<%=path%>/js/sizzle.js"></script>
<script src="<%=path%>/js/userJS.js"></script>

<script type="text/javascript" src="messages.kjs"></script>
<link rel="stylesheet" href="<%=path%>/css/lightbox.kcss">
<link rel="stylesheet" href="<%=path%>/css/main.kcss">
<link rel="stylesheet" href="<%=path%>/css/list.kcss">
<link rel="stylesheet" href="<%=path%>/css/userStyle.kcss">
<link rel="stylesheet" href="<%=path%>/css/debug.kcss">
<meta charset="UTF-8" />
<title>GSB-Frais KObject Framework</title>
</head>
<body id="body" class="fond">
<!-- header -->