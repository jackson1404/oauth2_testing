/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.dto;

import java.util.List;

/**
 * EmailInvitationRequest Class.
 * <p>
 * </p>
 *
 * @author
 */

public class EmailInvitationRequest {
        private String eventSummary;
        private String startDateTime;
        private String endDateTime;
        private String timeZone;
        private List<String> attendeeEmails;

        public String getEventSummary() {
                return eventSummary;
        }

        public void setEventSummary(String eventSummary) {
                this.eventSummary = eventSummary;
        }

        public String getStartDateTime() {
                return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
                this.startDateTime = startDateTime;
        }

        public String getEndDateTime() {
                return endDateTime;
        }

        public void setEndDateTime(String endDateTime) {
                this.endDateTime = endDateTime;
        }

        public String getTimeZone() {
                return timeZone;
        }

        public void setTimeZone(String timeZone) {
                this.timeZone = timeZone;
        }

        public List<String> getAttendeeEmails() {
                return attendeeEmails;
        }

        public void setAttendeeEmails(List<String> attendeeEmails) {
                this.attendeeEmails = attendeeEmails;
        }
}