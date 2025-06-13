package com.example.questionarios.services;

import com.example.questionarios.dto.PerguntaDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GeminiService {

    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private final String API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiService(@Value("${gemini.api.key}") String apiKey) {
        this.API_KEY = apiKey;
        if (this.API_KEY == null || this.API_KEY.isBlank()) {
            throw new RuntimeException("GEMINI_API_KEY não encontrada no application.properties");
        }
    }

    public PerguntaDTO gerarPergunta(String linguagem, String topico) throws Exception {
        String requestBody = buildRequestBody(linguagem, topico);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Resposta da API Gemini: " + response.body());

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode candidatesNode = root.path("candidates");
        if (!candidatesNode.isArray() || candidatesNode.isEmpty()) {
            throw new RuntimeException("Resposta da API sem 'candidates' ou array vazio");
        }

        JsonNode contentNode = candidatesNode.get(0).path("content");
        JsonNode partsNode = contentNode.path("parts");
        if (!partsNode.isArray() || partsNode.isEmpty()) {
            throw new RuntimeException("Resposta da API sem 'parts' ou array vazio");
        }

        String jsonString = partsNode.get(0).path("text").asText();

        jsonString = jsonString.replaceFirst("^```json\\s*", "").replaceFirst("\\s*```$", "");
        jsonString = jsonString.replaceFirst("^```\\s*", "").replaceFirst("\\s*```$", "");

        return objectMapper.readValue(jsonString, PerguntaDTO.class);
    }

    private static String buildRequestBody(String linguagem, String topico) {
        String prompt = "Gere uma pergunta inédita de múltipla escolha sobre a linguagem de programação '" + linguagem +
                "' com foco no tópico '" + topico + "'. " +
                "A pergunta **não deve ser uma repetição** de nenhuma pergunta anterior. " +
                "Varie a estrutura da questão e o conteúdo para garantir originalidade. " +
                "Responda exatamente neste formato JSON:\n" +
                "{\n" +
                "  \"questao\": \"...\",\n" +
                "  \"opcoes\": [\"...\", \"...\", \"...\", \"...\"],\n" +
                "  \"questaoCorreta\": \"...\",\n" +
                "  \"explicacao\": \"...\"\n" +
                "}\n" +
                "A explicação deve justificar claramente por que a resposta correta está certa, de forma didática e objetiva.";

        String promptEscapado = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        return """
{
  "contents": [
    {
      "parts": [
        { "text": "%s" }
      ]
    }
  ]
}
""".formatted(promptEscapado);
    }

    public PerguntaDTO gerarPerguntaGeral(String topico) throws Exception {
        String requestBody = buildRequestBodyGeral(topico);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Resposta da API Gemini (geral): " + response.body());

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode candidatesNode = root.path("candidates");
        if (!candidatesNode.isArray() || candidatesNode.isEmpty()) {
            throw new RuntimeException("Resposta da API sem 'candidates' ou array vazio");
        }

        JsonNode contentNode = candidatesNode.get(0).path("content");
        JsonNode partsNode = contentNode.path("parts");
        if (!partsNode.isArray() || partsNode.isEmpty()) {
            throw new RuntimeException("Resposta da API sem 'parts' ou array vazio");
        }

        String jsonString = partsNode.get(0).path("text").asText();

        jsonString = jsonString.replaceFirst("^```json\\s*", "").replaceFirst("\\s*```$", "");
        jsonString = jsonString.replaceFirst("^```\\s*", "").replaceFirst("\\s*```$", "");

        return objectMapper.readValue(jsonString, PerguntaDTO.class);
    }

    private static String buildRequestBodyGeral(String topico) {
        String prompt = "Gere uma pergunta inédita de múltipla escolha sobre o tópico '" + topico + "'. " +
                "A pergunta deve ser clara, objetiva e didática. " +
                "Evite perguntas repetitivas ou muito simples. " +
                "Responda exatamente neste formato JSON:\n" +
                "{\n" +
                "  \"questao\": \"...\",\n" +
                "  \"opcoes\": [\"...\", \"...\", \"...\", \"...\"],\n" +
                "  \"questaoCorreta\": \"...\",\n" +
                "  \"explicacao\": \"...\"\n" +
                "}";

        String promptEscapado = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        return """
{
  "contents": [
    {
      "parts": [
        { "text": "%s" }
      ]
    }
  ]
}
""".formatted(promptEscapado);
    }
}
