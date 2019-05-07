/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author thinkbuntu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDTO {
    
    /*
        "CreateDTO" is used specifically to create
        citizens for the database
    */
    
  // private UUID id; // Generates automatically in the repository
    private String name;
    private String address;
    private String city;
    private int zip;
    private String cpr;
    private int phoneNumber;
    private List<String> diagnoses;
}
