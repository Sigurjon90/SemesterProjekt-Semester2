package API.entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO {
    
    private UUID Id;
    private String content;
    private UUID authorID;
    private UUID citizenID;
    private String title;
    private Date dateCreated;
    private Date dateModified;

}
