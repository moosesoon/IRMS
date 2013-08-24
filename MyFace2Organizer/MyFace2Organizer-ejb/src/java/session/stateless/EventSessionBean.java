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
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.VenueConflictException;

/**
 *
 * @author Stanley
 */
@Stateless
@LocalBean
public class EventSessionBean {

    @PersistenceContext
    private EntityManager em;
    
    public List<Venue> getAllVenues(){
        Query query = em.createQuery("SELECT v FROM Venue v");
        return query.getResultList();
    }
    
    private Venue getVenue(Long venueId){
        return em.find(Venue.class, venueId);
    }
    
    private SystemUser getSystemUser(Long systemUserId){
        return em.find(SystemUser.class, systemUserId);
    }
    
    public List<Event> getAllEvents(SystemUser systemUser){
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.systemUser = :inSystemUser");
        query.setParameter("inSystemUser", systemUser);
        return query.getResultList();
    }
    
    private boolean checkVenueConflict(Timestamp startDateTime, Timestamp endDateTime, Long venueId){
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.venue = :venue AND "
                                   + "e.startDateTime <= :endDateTime AND "
                                   + "e.endDateTime >= :startDateTime");
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
    
    public Long addNewEvent(String eventName, Timestamp startDateTime, Timestamp endDateTime, Long venueId, Long systemUserId) throws
            VenueConflictException{
        if(!checkVenueConflict(startDateTime, endDateTime, venueId)){
            SystemUser systemUser = getSystemUser(systemUserId);
            
            Event event = new Event();
            
            event.setEventName(eventName);
            event.setEndDateTime(endDateTime);
            event.setStartDateTime(startDateTime);
            event.setSystemUser(systemUser);
            event.setVenue(getVenue(venueId));
            event.setStatus("Pending");
            
            em.persist(event);
            em.flush();
            
            systemUser.getEvents().add(event);
            em.merge(systemUser);
            
            return event.getId();
        } else{
            throw new VenueConflictException("Venue conflict: " + venueId);
        }
    }
    
    

}
