<%-- 
    Document   : index
    Created on : Aug 17, 2013, 9:06:39 PM
    Author     : Stanley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
    </head>
    <body>
        <h1>Event Booking System - Main Menu</h1>
        <%
            if(request.getSession().getAttribute("systemUserId") == null){%>
                <b>You have not logged in</b>
            <%}else{%>
                <c:url var="linkHref" value="/Controller?action=doLogout" />
                You have login with System User Id:
                <%=request.getSession().getAttribute("systemUserId").toString() %>. <a href="$(linkHref)">Logout</a>
            <%}%>
            <ul>
                <c:url var="linkHref" value="/Controller?action=viewMyEvents" />
                <li><a href="${linkHref}">View My Events</a></li>
                <c:url var="linkHref" value="/Controller?action=viewAllEvents" />
                <li><a href="${linkHref}">View All Events</a></li>
                <c:url var="linkHref" value="/Controller?action=addNewEventAjax" />
                <li><a href="${linkHref}">Add New Event (Ajax)</a></li>
            </ul>
    </body>
</html>
