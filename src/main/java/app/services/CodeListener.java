package app.services;

import app.contact.Contact;
import app.contact.Contacts;
import app.contact.Method;
import app.notification.Notifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CodeListener {

    private static final BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in));
    private static final Scanner scanner = new Scanner(System.in);

    private final Contacts contacts;
    private final Notifications notifications;

    private final Sender sender;
    public CodeListener(Contacts contacts, Notifications notifications, Sender sender){
        this.contacts = contacts;
        this.notifications = notifications;;
        this.sender = sender;
    }
    public void start() {
        int counter = 1;
        printCodeList();
        whileLabel:while(true) {
            System.out.print("Input your code: ");
            String code = "";
            if(scanner.hasNext())
                code = scanner.next();
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
                case "40":
                    sendOneNotification();
                    break;
                case "41":
                    sendAllNotifications();
                    break;
                case "100":
                    break whileLabel;
                default:
                    System.out.println("Invalid code, please reenter");
                    if((counter++)%5==0) {
                        printCodeList();
                    }
            }
        }
    }
    private void createContact(){
        try {
            String method = askMethod();
            String contactId = ask("contact id");
            String notificationName = askLine("notification name or empty space");
            System.out.println(contacts.create(method, contactId, notificationName) + " added to Database");
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void updateContact(){
        try {
            String method = askMethod();
            String contactId = ask("contact id");
            String notificationName = askLine("notification name or empty space");
            System.out.println(contacts.update(method, contactId, notificationName) + " updated in Database");
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void deleteContact(){
        try {
            String method = askMethod();
            String contactId = ask("contact id");
            System.out.println(contacts.delete(method, contactId) + " deleted from Database");
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void getContactsByUN(){
        System.out.println(contacts.getAllByUsername());
    }
    private void getContactsByPK(){
        try {
            String method = askMethod();
            String contactId = ask("contact id");
            System.out.println(contacts.getAllByPrimaryKey(method, contactId));
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void getContactsByNN(){
        String notificationName = askLine("notification name or empty space");
        System.out.println(contacts.getAllByNotificationName(notificationName));
    }
    private void createNotification(){
        try {
            String name = askLine("name");
            String text = askLine("text");
            System.out.println(notifications.create(name, text) + " added to Database");
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void updateNotification(){
        try{
        String name = askLine("name");
        String text = askLine("text");
        System.out.println(notifications.update(name,text) + " updated in Database");
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void deleteNotification(){
        try{
        String name = askLine("name");
        System.out.println(notifications.delete(name) + " deleted from Database");
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void sendOneNotification() {
        try {
            String method = askMethod();
            String contactId = ask("contact id");
            System.out.println("sendOne method is executing");
            sender.sendOne(method, contactId);
        }
        catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void sendAllNotifications() {
        System.out.println("sendAll method is executing");
        sender.sendAll();
    }
    private void getNotificationsByUN(){
        System.out.println(notifications.getAllByUsername());
    }
    private void getNotificationsByPK(){
        String name = askLine("name");
        System.out.println(notifications.getAllByPrimaryKey(name));
    }
    private void addContactsFromFile(){
        List<Contact> contactsList = ContactFiles.inputAndReadFile();
        String code = "";
        for (Contact contact : contactsList){
            try {
                System.out.println(contacts.create(contact) + " added to Database");
            }catch (RuntimeException e){
                if (!code.equals("1") && !code.equals("2")) {
                    System.out.println(e.getMessage());
                    code = chooseActionInCaseOfCreateUpdateException();
                }
                if (code.equals("1") || code.equals("3")) {
                    System.out.println(contacts.update(contact) + " updated in Database");
                }
            }
        }
    }

    private String chooseActionInCaseOfCreateUpdateException() {
        System.out.println("Enter one of following codes to choose action to perform in case if contact you trying to add already exists: ");
        System.out.println("1 - update all next already existing contacts");
        System.out.println("2 - don't update all next already existing contacts");
        System.out.println("3 - update this one anyway");
        System.out.println("anything else - don't update this one");
        System.out.print("Input code:");
        return scanner.next();
    }

    private String ask(String what){
        System.out.printf("Input %s :",what);
        return scanner.next();
    }
    private String askLine(String what){
        int c= 0;
        while(c<5){
            System.out.printf("Input %s :", what);
            try {
                String line = lineReader.readLine().trim();
                return line;
            }
            catch (IOException e){
                e.printStackTrace();
                c++;
                continue;
            }
        }
        System.out.println("No valid string was entered, replaced with empty space");
        return "";
    }
    private String askMethod(){
            int c = 0;
            while (c < 5) {
                System.out.println("Input one of method values: " + Arrays.toString(Method.values()));
                String res = ask("contact method");
                try {
                    Method method = Method.valueOf(res);
                    return res;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid enum value, try one more time");
                    c++;
                    continue;
                }
            }
            throw new IllegalArgumentException();
    }

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
        System.out.println("40 - send emergency notification to one of your contacts");
        System.out.println("41 - send emergency notifications to all your contacts");
        System.out.println("100 - finish program");
    }
}
