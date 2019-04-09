/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.entities;

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

public class CitizensDTO {

    private UUID id;
    private UUID addressId;
    private UUID authorId;
    private String name;
    private long CPR_Number;
    private long Tlf_Number;

    @Override
    public String toString() {
        return id.toString();
    }

}
