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
@AllArgsConstructor
public class Diary {

    private UUID Id;
    private String content;
    private UUID authorID;
    private UUID citizenID;
    private Date dateCreated;
    private String dateModified;
    private String title;



}

