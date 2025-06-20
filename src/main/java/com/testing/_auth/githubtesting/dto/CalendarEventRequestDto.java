/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.dto;

/**
 * CalendarEventRequestDto Class.
 * <p>
 * </p>
 *
 * @author
 */

public class CalendarEventRequestDto {
    private String summary;
    private DateTimeObject start;  // Changed to nested object
    private DateTimeObject end;    // Changed to nested object

    // Nested class
    public static class DateTimeObject {
        private String dateTime;
        private String timeZone;

        // Getters and setters
        public String getDateTime() { return dateTime; }
        public void setDateTime(String dateTime) { this.dateTime = dateTime; }

        public String getTimeZone() { return timeZone; }
        public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
    }

    // Original getters/setters for summary
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    // New getters/setters
    public DateTimeObject getStart() { return start; }
    public void setStart(DateTimeObject start) { this.start = start; }

    public DateTimeObject getEnd() { return end; }
    public void setEnd(DateTimeObject end) { this.end = end; }
}