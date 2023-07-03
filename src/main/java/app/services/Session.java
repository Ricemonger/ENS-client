package app.services;

import lombok.Data;
import app.user.dto.UserLoginRequest;
import app.user.dto.UserRegisterRequest;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Scanner;


@Data
public class Session {

    private static final String URL = "http://localhost:8080/api/";
    private static final WebClient webClient = WebClient
            .builder()
            .baseUrl(URL)
            .build();
    private String bearerToken;

    private boolean isValid;
    private Session(){}

    public static Session register(String username, String password){
        Mono<String> jwtMono = webClient
                .post()
                .uri("/users/register")
                .bodyValue(new UserRegisterRequest(username,password))
                .retrieve()
                .bodyToMono(String.class);
        String jwt = jwtMono.block();
        Session session = new Session();
        session.bearerToken = "Bearer " + jwt;
        return session;
    }
    public static Session login(String username, String password){
        UserLoginRequest request = new UserLoginRequest(username,password);
        Mono<String> jwtMono = webClient
                .post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                //.body(Mono.just(request), UserLoginRequest.class)
                .retrieve()
                .bodyToMono(String.class);
        String jwt = jwtMono.block();
        Session session = new Session();
        session.bearerToken = "Bearer " + jwt;
        return session;
    }
    public static Session loginOrRegister(){
        Session session;
        Scanner consoleScanner = new Scanner(System.in);
        System.out.println("Please enter \"login\" to login, \"register\" to register:");
        String act = consoleScanner.next();
        while(!act.equals("login") && !act.equals("register")){
            System.out.println("Invalid input! Please enter \"login\" to login, \"register\" to register:");
            act = consoleScanner.next();
        }
        System.out.print("Please insert your username: ");
        String username = consoleScanner.next();
        System.out.print("Please insert your password: ");
        String password = consoleScanner.next();
        if(act.equals("login")) {
            session = Session.login(username, password);
        }
        else {
            session = Session.register(username, password);
        }
        return session;
    }
    public String bearer(){
        return this.bearerToken;
    }
}
