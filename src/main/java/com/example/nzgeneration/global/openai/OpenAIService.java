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

    @Value("${openai.api.key}")
    private String apiKey;

    RestClient restClient = RestClient.create();

    ObjectMapper objectMapper = new ObjectMapper();

    // 이미지 검사
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
            .uri("https://api.openai.com/v1/chat/completions")
            .contentType(APPLICATION_JSON)
            .header("Authorization", "Bearer " + apiKey)
            .body(requestBody)
            .retrieve()
            .toEntity(String.class);

        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");

        return contentNode.textValue().equals("TRUE");
    }

    // 이미지 생성
    public String createImage(String prompt) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "dall-e-3");
        requestBody.put("prompt", prompt);
        requestBody.put("response_format", "url");
        requestBody.put("n", 1);
        requestBody.put("size", "1024x1024");

        ResponseEntity<String> response = restClient.post()
            .uri("https://api.openai.com/v1/images/generations")
            .contentType(APPLICATION_JSON)
            .header("Authorization", "Bearer " + apiKey)
            .body(requestBody)
            .retrieve()
            .toEntity(String.class);

        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode imageUrlNode = rootNode.path("data").get(0).path("url");
        return imageUrlNode.asText();
    }

    // 프롬프트 구성
    public String buildPrompt(String gender, String action, String animal) {
        //return String.format("Create an NFT-ready image. It should be in a 24 x 24 pixel dot art style, low-resolution, with fewer dots. A character of %s gender is performing %s, accompanied by a %s. The background should give an eco-friendly vibe.", gender, action, animal);
        return String.format("Create an NFT-ready image in a cute, child-friendly style. It should be in a 24 x 24 pixel dot art style, low-resolution, with fewer dots. A character of %s gender, depicted in a whimsical and adorable manner, is actively %s. They are joyfully accompanied by a %s, adding a playful and friendly touch. The background should evoke eco-friendly vibe.", gender, action, animal);

    }


}