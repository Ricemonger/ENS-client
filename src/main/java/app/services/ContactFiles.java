package app.services;

import app.contact.Contact;
import com.opencsv.bean.CsvToBeanBuilder;
import com.poiji.bind.Poiji;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class ContactFiles {

    private static final Scanner consoleScanner = new Scanner(System.in);
    private static final List<String> SUPPORTED_FORMATS_VALUES;
    public static final List<String> SUPPORTED_FORMATS;

    static {
        SUPPORTED_FORMATS_VALUES = new ArrayList<>();
        SUPPORTED_FORMATS_VALUES.add(".csv");
        SUPPORTED_FORMATS_VALUES.add(".xls");
        SUPPORTED_FORMATS_VALUES.add(".xlsx");
        SUPPORTED_FORMATS = Collections.unmodifiableList(SUPPORTED_FORMATS_VALUES);
    }

    public static List<Contact> inputAndReadFile() {
        String filePath;
        String fileFormat = "";
        whileLabel: while (true) {
            System.out.println("Input full file path with file's name and format");
            filePath = consoleScanner.nextLine();
            try {
                fileFormat = filePath.substring(filePath.lastIndexOf("."));
            }catch (StringIndexOutOfBoundsException e){
                System.out.println("Wrong file path. File format wasn't entered");
                continue;
            }
            for(String format : ContactFiles.SUPPORTED_FORMATS){
                if(format.equals(fileFormat))
                    break whileLabel;
            }
            System.out.println("Wrong file format entered");
        }
        if(fileFormat.equals(".xls") || fileFormat.equals(".xlsx")) {
            return readExcel(filePath);
        }
        else if(fileFormat.equals(".csv")) {
            return readCSV(filePath);
        }
        else {
            System.out.println("Invalid file format: " + fileFormat + " in file path: " + filePath);
            return Collections.emptyList();
        }
    }
    public static List<Contact> readExcel(String filePath) {
        List<ExcelContact> list = Poiji.fromExcel(new File(filePath), ExcelContact.class);
        return list.stream().map(ExcelContact::toContact).toList();
    }


    public static List<Contact> readCSV(String filePath) {
        try (FileReader fileReader = new FileReader(filePath);
        ) {
            return new CsvToBeanBuilder<Contact>(fileReader)
                    .withType(Contact.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Invalid .csv-file path, FileNotFoundException was thrown");
            fileNotFoundException.printStackTrace();
            return Collections.emptyList();
        } catch (IOException ioException) {
            System.out.println("Input-Output exception was thrown while reading .csv-file");
            ioException.printStackTrace();
            return Collections.emptyList();
        }
    }
}
