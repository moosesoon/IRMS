/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Event;
import entity.SystemUser;
import entity.Venue;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.VenueConflictException;

/**
 *
 * @author Stanley
 */
@Stateless
public class EventSession implements EventSessionLocal {
    @PersistenceContext
    private EntityManager em;
    
    public List<Venue> getAllVenues(){
        Query query = em.createQuery("SELECT v FROM Venue v");
        return query.getResultList();
    }
    
    private Venue getVenue(Long venueId){
        Venue venue = em.find(Venue.class, venueId);
        return venue;
    }
    
    public List<Event> getAllEvents(){
        Query query = em.createQuery("SELECT e FROM Event e");
        return query.getResultList();
    }
    
    private Event getEvent(Long eventId){
        Event event = em.find(Event.class, eventId);
        return event;
    }
    
    private SystemUser getSystemUser(Long systemUserId){
        SystemUser systemUser = em.find(SystemUser.class, systemUserId);
        return systemUser;
    }
    
    public List<Event> getMyEvents(Long systemUserId){
        SystemUser systemUser = getSystemUser(systemUserId);
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.systemUser = :inSystemUser");
        query.setParameter("inSystemUser", systemUser);
        return query.getResultList();
    }
    
    private boolean checkVenueConflict(Timestamp startDateTime, Timestamp endDateTime, Long venueId){
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.venue = :venue AND e.startDateTime "
                + "<= :endDateTime AND e.endDateTime >= :startDateTime");
        query.setParameter("venue", getVenue(venueId));
        query.setParameter("endDateTime", endDateTime);
        query.setParameter("startDateTime", startDateTime);
        
        List resultList = query.getResultList();
        
        if(resultList.isEmpty()){
            return false;
        } else{
            return true;
        }
    }
    
    public Long addNewEvent(String eventName, Timestamp startDateTime, Timestamp endDateTime,
                            Long venueId, Long systemUserId) throws VenueConflictException{
        if(!checkVenueConflict(startDateTime, endDateTime, venueId)){
            SystemUser systemUser = getSystemUser(systemUserId);
            
            Event event = new Event();
            
            event.setEventName(eventName);
            event.setStartDateTime(startDateTime);
            event.setEndDateTime(endDateTime);
            event.setVenue(getVenue(venueId));
            event.setSystemUser(systemUser);
            event.setStatus("pending");
            
            em.persist(event);
            em.flush();
            
            systemUser.getEvents().add(event);
            em.merge(systemUser);
            
            return event.getEventId();
        } else{
            throw new VenueConflictException("Venue Conflict: " + venueId);
        }
    }
}
