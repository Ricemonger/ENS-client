package app.services;

import app.contact.Contacts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class ContactFiles {

    private static final Scanner consoleScanner = new Scanner(System.in);
    private static final List<String> SUPPORTED_FORMATS_VALUES;

    public static final List<String> SUPPORTED_FORMATS;

    static {
        SUPPORTED_FORMATS_VALUES = new ArrayList<>();
        SUPPORTED_FORMATS_VALUES.add(".csv");
        SUPPORTED_FORMATS_VALUES.add(".xls");
        SUPPORTED_FORMATS = Collections.unmodifiableList(SUPPORTED_FORMATS_VALUES);
    }

    public static List<Contacts> read() {
        String filePath;
        String fileFormat;
        while (true) {
            System.out.println("Input full file path with file's name and format");
            filePath = consoleScanner.next();
            if(filePath.length() >= 4) {
                fileFormat = filePath.substring(filePath.length() - 4);
            }
            else {
                System.out.println("Wrong file path!");
                continue;
            }
            for(String format : ContactFiles.SUPPORTED_FORMATS){
                if(format.equals(fileFormat))
                    break;
            }
            System.out.println("Wrong file path!");
        }
    }
}
