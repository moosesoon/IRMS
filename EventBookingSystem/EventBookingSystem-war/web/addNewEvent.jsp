<%-- 
    Document   : addNewEvent
    Created on : Aug 18, 2013, 1:00:22 AM
    Author     : Stanley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="status" class="java.lang.String" scope="request"/>
<jsp:useBean id="newEventId" class="java.lang.Long" scope="request"/>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Event Booking System - Add New Event</title>
            <c:url var="scriptSrc" value="/javascript/validation.js" />
            <script type="text/javascript" src="${scriptSrc}"></script>
            <c:url var="scriptSrc" value="/javascript/validation_addNewEvent.js" />
            <script type="text/javascript" src="${scriptSrc}"></script>
            <c:url var="stylesheetSrc" value="/stylesheet/default.css" />
            <link rel="stylesheet" type="text/css" href="${stylesheetSrc}" media="screen, print" />
    </head>
    <body onload="javascript:init('formAddNewEvent')">
        <h1>Event Booking System - Add New Event</h1>
            <c:url var="linkHref" value="/Controller" />
            <a href="${linkHref}">Home</a><br/><br/>
            <c:url var="formAction" value="/Controller?action=saveNewEvent" />
            <form name="formAddNewEvent" id="formAddNewEvent" action="${formAction}" method="post">
            <c:if test="${status == 'success'}">
            <font color="green">New Event ${newEventId} added successfully!</font><br/>
            </c:if>
            <c:if test="${status == 'error'}">
            <font color="red">An error has occurred while adding the new event: ${errorMessage}!</font><br/>
            </c:if>
                <table border="0">
                    <tr>
                        <td>Event Name:</td>
                        <td><input type="text" name="eventName" id="eventName" 
                                   value="<%=(request.getParameter("eventName") != null)?(request.getParameter("eventName")):("")%>" />
                            <span class="errorMessage" id="lblErrEventName"></span></td>
                    </tr>
                    <tr>
                        <td>Start Date/Time:</td>
                        <td><input type="text" name="startDateTime" id="startDateTime" 
                                   value="<%=(request.getParameter("startDateTime") != null)?(request.getParameter("startDateTime")):("")%>" />
                            <span class="errorMessage" id="lblErrStartDateTime"></span></td>
                    </tr>
                    <tr><td>End Date/Time:</td>
                        <td><input type="text" name="endDateTime" id="endDateTime" 
                                   value="<%=(request.getParameter("endDateTime") != null)?(request.getParameter("endDateTime")):("")%>" />
                            <span class="errorMessage" id="lblErrEndDateTime"></span></td>
                    </tr>
                    <tr>
                        <td>Venue:</td>
                        <td><select name=venue id=venue>
                            <%
                                java.util.List<entity.Venue> venues = (java.util.List<entity.Venue>)request.getAttribute("venues");
                                for(entity.Venue venue:venues)
                                {
                                %>
                                    <option value="<%=venue.getVenueId()%>" <%=(request.getParameter("venue") != null)?((request.getParameter("venue").equals(venue.getVenueId().toString()))?("selected"):("")):("")%>>
                                        <%=venue.getVenueName()%> (<%=venue.getVenueLocation()%>)
                                    </option>
                                <%
                                }
                            %>
                            </select></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="Add Event" /> 
                            <input type="reset" value="Clear" /></td>
                    </tr>
</select></td></tr>
    </body>
</html>
