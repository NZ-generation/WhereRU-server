package com.example.nzgeneration.global.openai;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenAIService {

    private String apiUrl = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String apiKey;

    RestClient restClient = RestClient.create();

    ObjectMapper objectMapper = new ObjectMapper();

    public Boolean getImageDescription(String imageUrl) throws JsonProcessingException {

        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "text");
        textContent.put("text", "Does the image contain any explicit, violent, or sensitive content such as nudity, violence, or tobacco? Respond with 'TRUE' for no such content and suitable for public display, or 'FALSE' if any such content is present.");

        Map<String, Object> imageContent = new HashMap<>();
        imageContent.put("type", "image_url");
        imageContent.put("image_url", Map.of("url", imageUrl));

        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(textContent);
        contents.add(imageContent);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", contents);

        // Create the outer request structure
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4-turbo");
        requestBody.put("messages", List.of(message));
        requestBody.put("max_tokens", 300);

        ResponseEntity<String> response = restClient.post()
            .uri(apiUrl)
            .contentType(APPLICATION_JSON)
            .header("Authorization", "Bearer " + apiKey)
            .body(requestBody)
            .retrieve()
            .toEntity(String.class);

        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");

        return contentNode.textValue().equals("TRUE");
    }
}