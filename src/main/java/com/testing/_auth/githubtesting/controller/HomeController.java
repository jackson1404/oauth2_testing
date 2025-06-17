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

//    @PostMapping("/logoutSession")
//    @ResponseBody
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("reach logout");
//        new SecurityContextLogoutHandler().logout(request, response, null); // clears session and security context
//        return ResponseEntity.ok().build();
//    }
//
//


    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("login", principal.getAttribute("login"));
            model.addAttribute("avatar", principal.getAttribute("avatar_url"));
        }
        return "home";
    }

    @GetMapping("/getToken")
    @ResponseBody
    public ResponseEntity<?> getToken(@RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client){
        String token = client.getAccessToken().getTokenValue();
        return ResponseEntity.ok(token);
    }

    @GetMapping("/getRepos")
    public String getUserRepo(@RegisteredOAuth2AuthorizedClient("github")OAuth2AuthorizedClient client, Model model){
        String token = client.getAccessToken().getTokenValue();
        System.out.println("The token is " + token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<GithubResponseDto>> response = restTemplate.exchange(
                "https://api.github.com/user/repos",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<GithubResponseDto>>() {}
        );

        model.addAttribute("repos", response.getBody());
        return "userRepo"; // A Thymeleaf page to render repo list

    }




}