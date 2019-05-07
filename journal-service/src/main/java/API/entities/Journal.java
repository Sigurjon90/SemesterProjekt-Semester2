package API.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thinkbuntu
 */
// Lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Journal {

    private UUID id;
    private String startDate;
    private UUID citizensID;
    private String paragraph;
    private String municipality;
    private boolean consent;
    private String content;
    private UUID authorID;
    private String dateModified;

    public Journal(UUID id, String startDate, UUID citizensID, String content, UUID authorID, String dateModified) {
        this.id = id;
        this.startDate = startDate;
        this.citizensID = citizensID;
        this.content = content;
        this.authorID = authorID;
        this.dateModified = dateModified;
    }
    
}
