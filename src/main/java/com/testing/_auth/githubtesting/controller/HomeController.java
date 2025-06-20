/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.controller;

import com.testing._auth.githubtesting.dto.GithubResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * HomeController Class.
 * <p>
 * </p>
 *
 * @author
 */

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    private final RestTemplate restTemplate = new RestTemplate();

    //for google
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("avatar", principal.getAttribute("picture"));

            // Additional common Google-specific attributes:
            model.addAttribute("subjectId", principal.getAttribute("sub"));        // Unique ID for the user
            model.addAttribute("firstName", principal.getAttribute("given_name")); // First name
            model.addAttribute("lastName", principal.getAttribute("family_name")); // Last name
            model.addAttribute("emailVerified", principal.getAttribute("email_verified")); // Is email verified? (boolean)
            model.addAttribute("locale", principal.getAttribute("locale"));        // User's locale (e.g., "en")
            model.addAttribute("hostedDomain", principal.getAttribute("hd"));
        }
        return "home";
    }

    @GetMapping("/getCalendarEvent")
    public String fetchCalendarEvent(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client, Model model ){

        String accessToken = client.getAccessToken().getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Call Google Calendar API

        ResponseEntity<String> response = restTemplate.exchange(
                "https://www.googleapis.com/calendar/v3/calendars/primary/events",
                HttpMethod.GET,
                entity,
                String.class
        );

        String calendarResponse = response.getBody();

        // Add to model for Thymeleaf
        model.addAttribute("calendarData", calendarResponse);
        return "calendar"; // return to calendar.html view

    }



//    @GetMapping("/home")
//    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
//        if (principal != null) {
//            model.addAttribute("name", principal.getAttribute("name"));
//            model.addAttribute("login", principal.getAttribute("login"));
//            model.addAttribute("avatar", principal.getAttribute("avatar_url"));
//        }
//        return "home";
//    }

    @GetMapping("/getToken")
    @ResponseBody
    public ResponseEntity<?> getToken(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client){
        String token = client.getAccessToken().getTokenValue();
        return ResponseEntity.ok(token);
    }
//
//    @GetMapping("/getRepos")
//    public String getUserRepo(@RegisteredOAuth2AuthorizedClient("github")OAuth2AuthorizedClient client, Model model){
//        String token = client.getAccessToken().getTokenValue();
//        System.out.println("The token is " + token);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<GithubResponseDto>> response = restTemplate.exchange(
//                "https://api.github.com/user/repos",
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<List<GithubResponseDto>>() {}
//        );
//
//        model.addAttribute("repos", response.getBody());
//        return "userRepo"; // A Thymeleaf page to render repo list
//
//    }




}