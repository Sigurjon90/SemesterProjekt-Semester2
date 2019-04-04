/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.entities;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jonas
 */
@Setter
@Getter
@NoArgsConstructor

public class Diary {

    private UUID Id;
    private String content;
    private UUID authorID;
    private UUID citizenID;
    private Date dateCreated;
    private Date dateModified;

    public Diary(UUID id, String content, UUID authorID, UUID citizenID, Date dateCreated, Date dateModified) {
        Id = id;
        this.content = content;
        this.authorID = authorID;
        this.citizenID = citizenID;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }
}

