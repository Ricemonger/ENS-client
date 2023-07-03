package app.contact;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contact {

    private String username;
    @CsvBindByName(column = "method", required = true)
    private Method method;
    @CsvBindByName(column = "id", required = true)
    private String contactId;
    @CsvBindByName(column = "notification name")
    private String notificationName;

    public Contact(String method, String contactId, String notificationName){
        this.method =Method.valueOf(method.toUpperCase(Locale.ROOT));
        this.contactId = contactId;
        this.notificationName = notificationName;
    }
}
