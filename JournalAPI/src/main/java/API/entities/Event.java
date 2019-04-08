/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.entities;

import java.sql.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author thinkbuntu
 */

@Getter
@Setter
@AllArgsConstructor

public class Event {
    private UUID id;
    private UUID journal_id;
    private String content;
    private String type;
    private Date dateStamp;
    private UUID authorId;
    
}
