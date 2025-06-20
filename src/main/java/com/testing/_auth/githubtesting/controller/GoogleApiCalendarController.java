/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.controller;

import com.testing._auth.githubtesting.dto.CalendarEventRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * GoogleApiCalendarController Class.
 * <p>
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/calendar")
public class GoogleApiCalendarController {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(GoogleApiCalendarController.class);

// Inside your method:

    @PostMapping("/createEvent")
    public ResponseEntity<String> createEventApi(
            @RequestHeader("Authorization") String authorization) {

        try {
            String accessToken = authorization.replace("Bearer ", "").trim();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Hardcoded event data
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("summary", "Test Event from Hardcoded API");

            // Hardcoded start time (current time + 1 hour)
            Map<String, String> start = new HashMap<>();
            start.put("dateTime", LocalDateTime.now().plusHours(1)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "+06:30");
            start.put("timeZone", "Asia/Yangon");

            // Hardcoded end time (current time + 2 hours)
            Map<String, String> end = new HashMap<>();
            end.put("dateTime", LocalDateTime.now().plusHours(2)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "+06:30");
            end.put("timeZone", "Asia/Yangon");

            requestBody.put("start", start);
            requestBody.put("end", end);

            logger.info("Sending to Google: {}", requestBody);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://www.googleapis.com/calendar/v3/calendars/primary/events",
                    request,
                    String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("Google API Error: {}", e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Internal Error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getToken")
    @ResponseBody
    public String getToken(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client) {
        String token = client.getAccessToken().getTokenValue();
        logger.info("Issued token with scopes: {}", client.getAccessToken().getScopes());
        return token; // Returns just the raw token string
    }

//    @GetMapping("/getToken")
//    public ResponseEntity<?> getToken(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client){
//        String token = client.getAccessToken().getTokenValue();
//        System.out.println("Token scopes: " + client.getAccessToken().getScopes());
//        return ResponseEntity.ok(token);
//    }

    @GetMapping("/testApiCall")
    public ResponseEntity<?> testCall(){
        return ResponseEntity.ok("OK");
    }

}
