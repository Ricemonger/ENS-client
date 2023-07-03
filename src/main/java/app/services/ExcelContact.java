package app.services;

import app.contact.Contact;
import com.poiji.annotation.ExcelCellName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExcelContact {
    @ExcelCellName("method")
    private String method;
    @ExcelCellName("id")
    private String contactId;
    @ExcelCellName("notification name")
    private String notificationName;
    public Contact toContact(){
        return new Contact(method,contactId,notificationName);
    }
}
