package API.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor

public class DiaryDTO {
    private UUID Id;
    private String content;
    private UUID authorID;
    private UUID citizenID;
 //   private Date dateCreated;
   // private Date dateModified;

    public DiaryDTO(UUID id, String content, UUID authorID, UUID citizenID/*, Date dateCreated, Date dateModified*/) {
        this.Id = id;
        this.content = content;
        this.authorID = authorID;
        this.citizenID = citizenID;
     /*   this.dateCreated = dateCreated;
        this.dateModified = dateModified; */
    }


}
