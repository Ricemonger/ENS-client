package app.services;

import app.contact.Contacts;
import app.notification.Notifications;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;


@Component
public class CodeListener {

    private static final Scanner scanner = new Scanner(System.in);


    private Contacts contacts;
    private Notifications notifications;
    public CodeListener(Contacts contacts, Notifications notifications){
        this.contacts = contacts;
        this.notifications = notifications;;
    }
    public void start() {
        printCodeList();
        whileLabel:while(true) {
            System.out.print("Input your code: ");
            String code = scanner.next();
            switch (code) {
                case "20":
                    createContact();
                    break;
                case "21":
                    updateContact();
                    break;
                case "22":
                    deleteContact();
                    break;
                case "23":
                    getContactsByUN();
                    break;
                case "24":
                    getContactsByPK();
                    break;
                case "25":
                    getContactsByNN();
                    break;
                case "26":
                    addContactsFromFile();
                    break;
                case "30":
                    createNotification();
                    break;
                case "31":
                    updateNotification();
                    break;
                case "32":
                    deleteNotification();
                    break;
                case "33":
                    getNotificationsByUN();
                    break;
                case "34":
                    getNotificationsByPK();
                    break;
                case "100":
                    break whileLabel;
                default:
                    System.out.println("Invalid code, please reenter");
            }
        }
    }
    private void createContact(){
        String method = ask("contact method");
        String contactId = ask("contact id");
        String notificationName = ask("notification name or empty space");
        System.out.println(contacts.create(method,contactId,notificationName) + " added to Database");
    }
    private void updateContact(){
        String method = ask("contact method");
        String contactId = ask("contact id");
        String notificationName = ask("notification name or empty space");
        System.out.println(contacts.update(method,contactId,notificationName) + " updated in Database");
    }
    private void deleteContact(){
        String method = ask("contact method");
        String contactId = ask("contact id");
        System.out.println(contacts.delete(method,contactId) + " deleted from Database");
    }
    private void getContactsByUN(){
        System.out.println(contacts.getAllByUsername());
    }
    private void getContactsByPK(){
        String method = ask("contact method");
        String contactId = ask("contact id");
        System.out.println(contacts.getAllByPrimaryKey(method,contactId));
    }
    private void getContactsByNN(){
        String notificationName = ask("notification name or empty space");
        System.out.println(contacts.getAllByNotificationName(notificationName));
    }
    private void createNotification(){
        String name = ask("name");
        String text = ask("text");
        System.out.println(notifications.create(name,text) + " added to Database");
    }
    private void updateNotification(){
        String name = ask("name");
        String text = ask("text");
        System.out.println(notifications.update(name,text) + " updated in Database");
    }
    private void deleteNotification(){
        String name = ask("name");
        System.out.println(notifications.delete(name) + " deleted from Database");
    }
    private void getNotificationsByUN(){
        System.out.println(notifications.getAllByUsername());
    }
    private void getNotificationsByPK(){
        String name = ask("name");
        System.out.println(notifications.getAllByPrimaryKey(name));
    }
    private void addContactsFromFile(){
        List<Contacts> contactsList = ContactFiles.read();

    }
    private String ask(String what){
        System.out.printf("Input %s :",what);
        return scanner.next();
    }
    //TODO CSV AND XLS READER

    private static void printCodeList(){
        System.out.println("Please enter one of the following codes to perform an action:");
        System.out.println("20 - create contact");
        System.out.println("21 - update contact");
        System.out.println("22 - delete contact");
        System.out.println("23 - get list of all your contact");
        System.out.println("24 - get list of your contact by Primary Key(Contacts' s method and id)");
        System.out.println("25 - get list of your contact by Contact's notification name");
        System.out.println("26 - add contacts from .csv or .xls file");
        System.out.println("30 - create notification template");
        System.out.println("31 - update notification template");
        System.out.println("32 - delete notification template");
        System.out.println("33 - get list of all your notification templates");
        System.out.println("34 - get list of your notification templates by Primary Key(Notification's name)");
        System.out.println("100 - finish program");
    }
}
