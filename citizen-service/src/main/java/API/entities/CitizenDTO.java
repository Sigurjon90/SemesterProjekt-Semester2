/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.entities;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author sigur
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Mapping Classes always need a NoArgsConstructor to work

public class CitizenDTO {
    
    private UUID id;
    private String name;
    private String address;
    private String city;
    private int zip;
    private int phoneNumber;
    private List<String> diagnoses;
    private boolean archived;
    private String dateCreated;
    private UUID authorId;

    @Override
    public String toString() {
        return id.toString();
    }

}
