package com.example.nzgeneration.global.openai;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenAIController {

    private final OpenAIService openAIService;

    @GetMapping("test/openai/image-safety")
    public ResponseEntity<Boolean> checkImageSafety(@RequestParam String imageUrl)
        throws JsonProcessingException {
        Boolean result = openAIService.getImageDescription(imageUrl);
        return ResponseEntity.ok(result);
    }

    @PostMapping("test/openai/image")
    public ResponseEntity<String> createImage(@RequestParam String gender, @RequestParam String action,
        @RequestParam String animal) throws JsonProcessingException {
        String result = openAIService.createImage(openAIService.buildPrompt(gender, action, animal));
        return ResponseEntity.ok(result);
    }

}