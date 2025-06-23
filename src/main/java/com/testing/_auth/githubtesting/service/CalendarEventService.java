/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.service;
import com.testing._auth.githubtesting.dto.EventRequest;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * CalendarEventService Class.
 * <p>
 * </p>
 *
 * @author
 */
@Service
public class CalendarEventService {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private EmailService emailService;

    public String createGoogleCalendarEvent(String accessToken, EventRequest eventRequest) {
        // 1. Prepare Google Calendar API request
        Map<String, Object> googleEvent = new HashMap<>();
        googleEvent.put("summary", eventRequest.getSummary());

        Map<String, String> start = new HashMap<>();
        start.put("dateTime", eventRequest.getStart().getDateTime());
        start.put("timeZone", eventRequest.getStart().getTimeZone());
        googleEvent.put("start", start);

        Map<String, String> end = new HashMap<>();
        end.put("dateTime", eventRequest.getEnd().getDateTime());
        end.put("timeZone", eventRequest.getEnd().getTimeZone());
        googleEvent.put("end", end);

        // Add attendees if present
        if (eventRequest.getAttendees() != null && !eventRequest.getAttendees().isEmpty()) {
            googleEvent.put("attendees", eventRequest.getAttendees());
        }

        // 2. Call Google Calendar API
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(googleEvent, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://www.googleapis.com/calendar/v3/calendars/primary/events",
                HttpMethod.POST,
                request,
                Map.class
        );

        // 3. Send email notifications
        if (response.getStatusCode().is2xxSuccessful()) {
            sendInvitations(eventRequest, (String) response.getBody().get("id"));
        }

        return (String) response.getBody().get("id");
    }

    private void sendInvitations(EventRequest eventRequest, String eventId) {
        String subject = "Invitation: " + eventRequest.getSummary();
        String body = buildEmailBody(eventRequest, eventId);

        eventRequest.getAttendees().stream()
                .filter(attendee -> !attendee.isSelf())
                .forEach(attendee -> {
                    try {
                        emailService.sendSimpleEmail(attendee.getEmail(), subject, body);
                    } catch (Exception e) {
                        // Log email failure but don't fail the whole operation
                    }
                });
    }

    private String buildEmailBody(EventRequest request, String eventId) {
        return String.format(
                "You've been invited to an event:\n\n" +
                        "Title: %s\n" +
                        "Time: %s to %s (%s)\n\n" +
                        "Google Calendar Event ID: %s\n\n" +
                        "Please respond to this invitation.",
                request.getSummary(),
                request.getStart().getDateTime(),
                request.getEnd().getDateTime(),
                request.getStart().getTimeZone(),
                eventId
        );
    }

}
