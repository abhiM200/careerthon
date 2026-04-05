package com.careerthon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.HashMap;

@Service
public class LlmService {

    private final RestClient restClient;

    @Value("${llm.api.url:http://localhost:11434/api/generate}")
    private String llmUrl;

    @Value("${llm.model:llama3}")
    private String llmModel;

    public LlmService() {
        this.restClient = RestClient.create();
    }

    /**
     * Sends a prompt to the local LLM (Ollama) and returns the response string.
     */
    public String generateResponse(String systemPrompt, String userPrompt) {
        try {
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

            if (response != null && response.containsKey("response")) {
                return (String) response.get("response");
            }
            return "Unable to get a valid response from the LLM.";
        } catch (Exception e) {
            return "LLM Error: Please ensure Ollama is running locally (" + llmUrl + ") or check your connection. Details: " + e.getMessage();
        }
    }
    
    /**
     * Overloaded method for simpler prompts
     */
    public String generateResponse(String userPrompt) {
        return generateResponse("You are Careerthon AI, a helpful professional career advisor.", userPrompt);
    }
}
