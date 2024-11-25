package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.back.Model.ChatCompletionRequest;
import com.example.back.Model.ChatCompletionResponse;

@RestController
public class MainController {
    
    @Autowired
    RestTemplate restTemplate;

@PostMapping("/hitOpenaiApi")
    public ResponseEntity<String> getOpenaiResponse(@RequestBody String promt) {
        try {
            ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-3.5-turbo", promt);

            ChatCompletionResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatCompletionResponse.class
            );

            return ResponseEntity.ok(response.getChoises().get(0).getMessage().getContent());
        } catch (HttpClientErrorException.TooManyRequests e) {
            // Captura del error 429 Too Many Requests
            return ResponseEntity.status(429).body("Error: Excediste tu cuota de solicitudes. Verifica tu plan en OpenAI.");
        } catch (HttpClientErrorException e) {
            // Otros errores HTTP
            return ResponseEntity.status(e.getStatusCode()).body("Error al comunicarse con OpenAI: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Errores generales
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
