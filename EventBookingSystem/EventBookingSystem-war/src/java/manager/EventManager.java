/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import entity.Event;
import entity.Venue;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import session.stateless.EventSessionLocal;
import util.exception.VenueConflictException;

/**
 *
 * @author Stanley
 */
public class EventManager {
    private EventSessionLocal eventSessionLocal;
    public EventManager(){
        
    }
    
    public EventManager(EventSessionLocal eventSessionLocal){
        this.eventSessionLocal = eventSessionLocal;
    }
    
    public List<Venue> getAllVenues(){
        return eventSessionLocal.getAllVenues();
    }
    
    public List<Event> getAllEvents(){
        return eventSessionLocal.getAllEvents();
    }
    
    public List<Event> getMyEvents(Long systemUserId){
        return eventSessionLocal.getMyEvents(systemUserId);
    }
    
    public Long addNewEvent(HttpServletRequest request) throws VenueConflictException{
        String eventName = request.getParameter("eventName");
        Timestamp startDateTime = Timestamp.valueOf(request.getParameter("startDateTime"));
        Timestamp endDateTime = Timestamp.valueOf(request.getParameter("endDateTime"));
        Long venueId = Long.valueOf(request.getParameter("venue"));
        Long systemUserId = (Long) request.getSession().getAttribute("systemUserId");
        
        return eventSessionLocal.addNewEvent(eventName, startDateTime, endDateTime, venueId, systemUserId);
    }
}
