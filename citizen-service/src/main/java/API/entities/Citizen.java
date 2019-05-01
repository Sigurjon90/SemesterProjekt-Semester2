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

// Lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Citizen {
    
    private UUID id;
    private String name;
    private String address;
    private String city;
    private int zip;
    private String cpr;
    private int phoneNumber;
    private List<String> diagnoses;
    private boolean archived;
    private UUID careCenterID;
    private String dateCreated;
    private UUID authorId;

    public Citizen(UUID id, String name, String address, String city, int zip, String cpr, int phoneNumber, List<String> diagnoses, boolean archived, String dateCreated, UUID authorId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.cpr = cpr;
        this.phoneNumber = phoneNumber;
        this.diagnoses = diagnoses;
        this.archived = archived;
        this.dateCreated = dateCreated;
        this.authorId = authorId;
    }
    
    
}


