package API.entities;

import java.sql.Date;
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
    private String content;
    private UUID authorID;
    private String dateModified;
    
}
