package app.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contact {

    private String username;
    private Method method;
    private String contactId;

    private String notificationName;
}
