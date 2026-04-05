package com.careerthon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
@Service
public class LlmService {

    private final RestClient restClient;

    @Value("${llm.api.url:http://localhost:11434/api/generate}")
    private String llmUrl;

    @Value("${llm.model:llama3}")
    private String llmModel;

    @Value("${llm.api.key:}")
    private String llmApiKey;

    public LlmService() {
        this.restClient = RestClient.create();
    }

    public String generateResponse(String systemPrompt, String userPrompt) {
        try {
            boolean isOpenAiCompatible = llmUrl.contains("groq.com") || llmUrl.contains("openai.com") || llmUrl.contains("v1/chat/completions");
            
            if (isOpenAiCompatible) {
                return callOpenAiCompatible(systemPrompt, userPrompt);
            } else {
                return callOllama(systemPrompt, userPrompt);
            }
        } catch (Exception e) {
            String diagnostic = "LLM Error: ";
            if (llmUrl.contains("localhost")) {
                diagnostic += "Ollama not running locally on port 11434. ";
            } else if (llmApiKey.isEmpty() && !llmUrl.contains("localhost")) {
                diagnostic += "Cloud API URL detected but API Key is missing. ";
            }
            return diagnostic + "Details: " + e.getMessage();
        }
    }

    private String callOllama(String systemPrompt, String userPrompt) {
        String fullPrompt = systemPrompt + "\n\nUser: " + userPrompt + "\n\nAI:";
        Map<String, Object> request = new HashMap<>();
        request.put("model", llmModel);
        request.put("prompt", fullPrompt);
        request.put("stream", false);

        Map<String, Object> response = restClient.post()
                .uri(llmUrl)
                .body(request)
                .retrieve()
                .body(Map.class);

        return (response != null && response.containsKey("response")) ? (String) response.get("response") : "Error: Invalid Ollama response.";
    }

    private String callOpenAiCompatible(String systemPrompt, String userPrompt) {
        Map<String, Object> messageSystem = Map.of("role", "system", "content", systemPrompt);
        Map<String, Object> messageUser = Map.of("role", "user", "content", userPrompt);
        
        Map<String, Object> request = new HashMap<>();
        request.put("model", llmModel);
        request.put("messages", List.of(messageSystem, messageUser));

        Map<String, Object> response = restClient.post()
                .uri(llmUrl)
                .header("Authorization", "Bearer " + llmApiKey)
                .body(request)
                .retrieve()
                .body(Map.class);

        if (response != null && response.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        }
        return "Error: Invalid response from Cloud AI.";
    }
    
    /**
     * Overloaded method for simpler prompts
     */
    public String generateResponse(String userPrompt) {
        return generateResponse("You are Careerthon AI, a helpful professional career advisor.", userPrompt);
    }
}
