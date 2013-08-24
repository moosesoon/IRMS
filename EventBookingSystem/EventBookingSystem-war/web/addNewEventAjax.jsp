<%-- 
    Document   : addNewEventAjax
    Created on : Aug 18, 2013, 11:54:14 PM
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
        <title>Event Booking System - Add New Event (Ajax)</title>
        <c:url var="scriptSrc" value="/javascript/validation.js" />
        <script type="text/javascript" src="${scriptSrc}"></script>
        <c:url var="scriptSrc" value="/javascript/validation_addNewEvent.js" />
        <script type="text/javascript" src="${scriptSrc}"></script>
        <c:url var="scriptSrc" value="/javascript/ajax.js" />
        <script type="text/javascript" src="${scriptSrc}"></script>
        <c:url var="stylesheetSrc" value="/stylesheet/default.css" />
        <link rel="stylesheet" type="text/css" href="${stylesheetSrc}" media="screen, print" />
    </head>
    <body>
        <h1>Event Booking System - Add New Event (Ajax)</h1>
        <c:url var="formAction" value="/Controller?action=saveNewEventAjax" />
        <form name="formAddNewEvent" id="formAddNewEvent" action="${formAction}" method="post">
            <c:url var="imgSrc" value="/images/spinner.gif" />
            <img style="visibility:hidden;" id="ajaxSpinner" src="${imgSrc}" />
            <span id="spanStatus"></span><br/>
            
            <table id="formTable" border="0">
                <tr>
                    <td>Event Name:</td>
                    <td><input type="text" name="eventName" id="eventName" value="" />
                        <span class="errorMessage" id="lblErrEventName"></span></td>
                </tr>
                <tr>
                    <td>Start Date/Time:</td>
                    <td><input type="text" name="startDateTime" id="startDateTime" value="" />
                        <span class="errorMessage" id="lblErrStartDateTime"></span></td>
                </tr>
                <tr>
                    <td>End Date/Time:</td>
                    <td><input type="text" name="endDateTime" id="endDateTime" value="" />
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
                        <option value="<%=venue.getVenueId()%>" 
                        <%=(request.getParameter("venue") != null)?((request.getParameter("venue").equals(venue.getVenueId().toString()))?("selected"):("")):("")%>><%=venue.getVenueName()%> (<%=venue.getVenueLocation()%>)
                        </option>
                        <%
                        }
                        %>
                        </select></td></tr>
                <tr>
                    <td colspan="2">
                        <input type="button" value="Add Event" onclick="javascript:ajax_ActionHandler_Invoke();" /> 
                        </td>
                </tr>
            </table>
        </form>
    </body>
</html>
