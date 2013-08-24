<%-- 
    Document   : viewAllEvents
    Created on : Aug 18, 2013, 12:55:39 AM
    Author     : Stanley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="events" class="java.util.List<entity.Event>" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Event Booking System - View My Events</h1>
        <c:url var="linkHref" value="/Controller" />
        <a href="${linkHref}">Home</a><br/><br/>
        <table cellspacing="1" cellpadding="1" border="1">
            <tr>
                <th>Event Id</th>
                <th>Event Name</th>
                <th>Start Date/Time</th>
                <th>End Date/Time</th>
                <th>Venue</th><th>System User</th>
                <th>Status</th>
            </tr>
            <c:forEach items="${events}" var="event">
                <tr><td>${event.eventId}</td>
                    <td>${event.eventName}</td>
                    <td>${event.startDateTime}</td>
                    <td>${event.endDateTime}</td>
                    <td>${event.venue.venueName} (${event.venue.venueLocation})</td>
                    <td>${event.systemUser.userName} (${event.systemUser.systemUserId})</td>
                    <td>${event.status}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
