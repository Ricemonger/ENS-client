package app;

import app.contact.Contacts;
import app.notification.Notifications;
import app.services.CodeListener;
import app.services.Session;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@SpringBootConfiguration
public class App 
{
    public static void main( String[] args )
    {
        //TODO обработка ошибок при отправке запросов
        Session session = Session.loginOrRegister();
        Contacts contacts = new Contacts(session);
        Notifications notifications = new Notifications(session);
        CodeListener codeListener = new CodeListener(contacts,notifications);
        codeListener.start();
    }
    @Bean
    public Scanner consoleScanner(){
        return new Scanner(System.in);
    }
}
