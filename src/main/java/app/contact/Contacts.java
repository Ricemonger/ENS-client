package app.contact;

import app.contact.dto.ContactCreUpdRequest;
import app.contact.dto.ContactKeyRequest;
import app.contact.dto.ContactNNRequest;
import app.services.Session;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class Contacts {
    private final WebClient webClient = WebClient
            .builder()
            .baseUrl(URL)
            .build();
    private static final String URL = "http://localhost:8080/api/contacts";
    private final Session session;

    public Contacts(Session session){
        this.session = session;
    }

    public Contact create(String method, String contactId, String notificationName){
        ContactCreUpdRequest body = new ContactCreUpdRequest(method,contactId,notificationName);
        return request(HttpMethod.POST, body);
    }
    public Contact create(Contact contact){
        return create(contact.getMethod().name(), contact.getContactId(), contact.getNotificationName());
    }
    public Contact update(String method, String contactId, String notificationName){
        ContactCreUpdRequest body = new ContactCreUpdRequest(method,contactId,notificationName);
        return request(HttpMethod.PATCH, body);
    }
    public Contact delete(String method, String contactId){
        ContactKeyRequest body = new ContactKeyRequest(method,contactId);
        return request(HttpMethod.DELETE, body);
    }
    public List<Contact> getAllByUsername(){
        return getRequest("","/getByUN");
    }
    public List<Contact> getAllByPrimaryKey(String method, String contactId){
        ContactKeyRequest body = new ContactKeyRequest(method, contactId);
        return getRequest(body,"/getByPK");
    }
    public List<Contact> getAllByNotificationName(String notificationName){
        ContactNNRequest body = new ContactNNRequest(notificationName);
        return getRequest(body,"/getByNN");
    }
    private Contact request(HttpMethod method, Object body){
        return   webClient
                .method(method)
                .header("Authorization", session.bearer())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Contact.class)
                .block();
    }
    private List<Contact> getRequest(Object body, String uriAttribute){
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
