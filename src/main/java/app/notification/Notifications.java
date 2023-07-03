package app.notification;

import app.notification.dto.NotificationCreUpdRequest;
import app.notification.dto.NotificationPKRequest;
import app.services.Session;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class Notifications {
    private final WebClient webClient = WebClient
            .builder()
            .baseUrl(URL)
            .build();
    private static final String URL = "http://localhost:8080/api/notifications";
    private final Session session;

    public Notifications(Session session){
        this.session = session;
    }

    public Notification create(String name, String text){
        NotificationCreUpdRequest body = new NotificationCreUpdRequest(name, text);
        return request(HttpMethod.POST,body);
    }
    public Notification update(String name, String text){
        NotificationCreUpdRequest body = new NotificationCreUpdRequest(name, text);
        return request(HttpMethod.PATCH,body);
    }
    public Notification delete(String name){
        NotificationPKRequest body = new NotificationPKRequest(name);
        return request(HttpMethod.DELETE,body);
    }
    public List<Notification> getAllByUsername(){
        return getRequest("","/getByUN");
    }
    public List<Notification> getAllByPrimaryKey(String name){
        NotificationPKRequest body = new NotificationPKRequest(name);
        return getRequest(body,"/getByPK");
    }
    private Notification request(HttpMethod method, Object body){
        return   webClient
                .method(method)
                .header("Authorization", session.bearer())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Notification.class)
                .block();
    }
    private List<Notification> getRequest(Object body, String uriAttribute){
        return   webClient
                .method(HttpMethod.GET)
                .uri(uriAttribute)
                .header("Authorization", session.bearer())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
