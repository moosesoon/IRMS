/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Event;
import entity.Venue;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;
import util.exception.VenueConflictException;

/**
 *
 * @author Stanley
 */
@Local
public interface EventSessionLocal {
    public List<Venue> getAllVenues();
    public List<Event> getAllEvents();
    public List<Event> getMyEvents(Long systemUserId);
    public Long addNewEvent(String eventName, Timestamp startDateTime, Timestamp endDateTime,
                            Long venueId, Long systemUserId) throws VenueConflictException;
}
