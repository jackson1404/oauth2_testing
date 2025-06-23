/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.dto;

import java.util.List;

/**
 * EventRequest Class.
 * <p>
 * </p>
 *
 * @author
 */

public class EventRequest {
    private String summary;
    private TimeDetails start;
    private TimeDetails end;
    private List<Attendee> attendees;


    public static class TimeDetails {
        private String dateTime;  // Format: "2025-06-25T10:00:00+06:30"
        private String timeZone;  // Example: "Asia/Yangon"

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        // Getters and setters
    }

    public static class Attendee {
        private String email;
        private boolean self;  // Flag to skip sending email to yourself
        private String responseStatus;  // "needsAction", "accepted", etc.

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        // Getters and setters
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public TimeDetails getStart() {
        return start;
    }

    public void setStart(TimeDetails start) {
        this.start = start;
    }

    public TimeDetails getEnd() {
        return end;
    }

    public void setEnd(TimeDetails end) {
        this.end = end;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }
    // Getters and setters
}
