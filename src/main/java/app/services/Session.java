package app.services;

import lombok.Data;
import app.user.dto.UserLoginRequest;
import app.user.dto.UserRegisterRequest;
import org.springframework.http.HttpStatusCode;
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
            .defaultStatusHandler(HttpStatusCode::isError, response -> response.bodyToMono(String.class).map(RuntimeException::new))
            .build();
    private String bearerToken;

    private boolean isValid;
    private Session(){}

    public static Session register(String username, String password){
        try {
            Mono<String> jwtMono = webClient
                    .post()
                    .uri("/users/register")
                    .bodyValue(new UserRegisterRequest(username, password))
                    .retrieve()
                    .bodyToMono(String.class);
            String jwt = jwtMono.block();
            Session session = new Session();
            session.bearerToken = "Bearer " + jwt;
            return session;
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            return loginOrRegister();
        }
    }
    public static Session login(String username, String password){
        try {
            UserLoginRequest request = new UserLoginRequest(username, password);
            Mono<String> jwtMono = webClient
                    .post()
                    .uri("/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class);
            String jwt = jwtMono.block();
            Session session = new Session();
            session.bearerToken = "Bearer " + jwt;
            return session;
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            return loginOrRegister();
        }

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
        try {
            validateUsername(username);
        }catch (InvalidUsernameException e){
            System.out.println(e.getMessage());
            return loginOrRegister();
        }
        System.out.print("Please insert your password: ");
        String password = consoleScanner.next();
        try {
            validatePassword(password);
        }catch (InvalidPasswordException e){
            System.out.println(e.getMessage());
            return loginOrRegister();
        }
        if(act.equals("login")) {
            session = Session.login(username, password);
        }
        else {
            session = Session.register(username, password);
        }
        return session;
    }
    private static void validateUsername(String username){
        String regex = ".*\\W+.*";
        if(username.length()<6 || username.length()>24 || username.matches(regex))
            throw new InvalidUsernameException();
    }
    private static void validatePassword(String password){
        String regex = ".*[\\{\\}\\[\\]\\(\\):;'\".,<>/|\\\s]+.*";
        if(password.length()<6 || password.length()>16 || password.matches(regex))
            throw new InvalidPasswordException();
    }
    public String bearer(){
        return this.bearerToken;
    }
}
