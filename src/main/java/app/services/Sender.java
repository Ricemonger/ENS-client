package app.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

public class Sender {
    private final WebClient webClient = WebClient
            .builder()
            .baseUrl(URL)
            .defaultStatusHandler(HttpStatusCode::isError, response -> response.bodyToMono(String.class).map(RuntimeException::new))
            .build();
    private static final String URL = "http://localhost:8080/api/send";
    private final Session session;
    public Sender(Session session){
        this.session = session;
    }
    public void sendOne(String method, String contactId) {
        SendOneRequest body = new SendOneRequest(method,contactId);
        webClient
                .method(HttpMethod.POST)
                .uri("/one")
                .header("Authorization", session.bearer())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void sendAll() {
        webClient
                .method(HttpMethod.POST)
                .uri("/all")
                .header("Authorization", session.bearer())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
