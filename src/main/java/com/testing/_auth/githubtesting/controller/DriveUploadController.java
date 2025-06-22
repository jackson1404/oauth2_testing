package com.testing._auth.githubtesting.controller;

import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/drive")
public class DriveUploadController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/uploadForm")
    public String showUploadForm() {
        return "upload-form"; // Renders the form above
    }
//
//    @PostMapping("/uploadDriveFile")
//    public String handleFileUpload(
//            @RequestParam("file") MultipartFile file,
//            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client,
//            Model model) throws IOException {
//
//        String accessToken = client.getAccessToken().getTokenValue();
//
//        String folderId = "1DjqAz5QwMGE-tlmB1WRtz_KvtqBF-7pk"; // your Google Drive folder ID
//
//        String metadata = """
//        {
//          "name": "%s",
//          "mimeType": "%s",
//          "parents": ["%s"]
//        }
//        """.formatted(file.getOriginalFilename(), file.getContentType(), folderId);
//        // Prepare multipart body manually
//        String boundary = "drive-boundary-" + System.currentTimeMillis();
//        String delimiter = "--" + boundary;
//        String closeDelimiter = "--" + boundary + "--";
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
//
//        // Metadata part
//        writer.write(delimiter + "\r\n");
//        writer.write("Content-Type: application/json; charset=UTF-8\r\n\r\n");
//        writer.write(metadata + "\r\n");
//
//        // File part
//        writer.write(delimiter + "\r\n");
//        writer.write("Content-Type: " + file.getContentType() + "\r\n\r\n");
//        writer.flush();
//        outputStream.write(file.getBytes()); // Append file content
//        writer.write("\r\n" + closeDelimiter);
//        writer.flush();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        headers.setContentType(MediaType.parseMediaType("multipart/related; boundary=" + boundary));
//        headers.setContentLength(outputStream.size());
//
//        HttpEntity<byte[]> requestEntity = new HttpEntity<>(outputStream.toByteArray(), headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(
//                "https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart",
//                HttpMethod.POST,
//                requestEntity,
//                Map.class
//        );
//
//        String fileId = (String) response.getBody().get("id");
//        model.addAttribute("fileId", fileId);
//        model.addAttribute("fileName", file.getOriginalFilename());
//
//        return "upload-success";
//    }

    @ResponseBody
    @PostMapping("/upload-drive")
    public ResponseEntity<?> uploadToGoogleDrive(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IOException {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid or missing Bearer token"));
        }
        String accessToken = authorizationHeader.substring("Bearer ".length());

        String folderId = "1DjqAz5QwMGE-tlmB1WRtz_KvtqBF-7pk"; // your Google Drive folder ID

        String metadata = """
        {
          "name": "%s",
          "mimeType": "%s",
          "parents": ["%s"]
        }
        """.formatted(file.getOriginalFilename(), file.getContentType(), folderId);

        String boundary = "----SpringBoundary" + System.currentTimeMillis();
        String delimiter = "--" + boundary;
        String closeDelimiter = "--" + boundary + "--";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        // Metadata
        writer.write(delimiter + "\r\n");
        writer.write("Content-Type: application/json; charset=UTF-8\r\n\r\n");
        writer.write(metadata + "\r\n");

        // File part
        writer.write(delimiter + "\r\n");
        writer.write("Content-Type: " + file.getContentType() + "\r\n\r\n");
        writer.flush();
        outputStream.write(file.getBytes());
        writer.write("\r\n" + closeDelimiter);
        writer.flush();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.parseMediaType("multipart/related; boundary=" + boundary));
        headers.setContentLength(outputStream.size());

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(outputStream.toByteArray(), headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(Map.of(
                    "fileId", response.getBody().get("id"),
                    "fileName", file.getOriginalFilename()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + response);
        }
    }
}

//
//@RestController
//@RequestMapping("/drive")
//public class DriveUploadController {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @GetMapping("/upload-form")
//    public ModelAndView uploadForm() {
//        return new ModelAndView("upload-form"); // Renders the HTML upload form
//    }
//

//}
