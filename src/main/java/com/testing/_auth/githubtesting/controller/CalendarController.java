/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.controller;

import com.testing._auth.githubtesting.dto.EventRequest;
import com.testing._auth.githubtesting.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CalendarController Class.
 * <p>
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/calendars")
public class CalendarController {

    @Autowired
    private CalendarEventService calendarEventService;

    @PostMapping("/events")
    public ResponseEntity<String> createCalendarEvent(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EventRequest eventRequest) {
        try {

            String accessToken = authHeader.replace("Bearer ", "").trim();
            String googleEventId = calendarEventService.createGoogleCalendarEvent(accessToken, eventRequest);
            return ResponseEntity.ok("Event created in Google Calendar with ID: " + googleEventId);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
