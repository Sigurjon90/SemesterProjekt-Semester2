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
public class Citizens {
    
    private UUID id;
    private String name;
    private long CPR_Number;
    private UUID AddressId;
    private long Tlf_Number;
    
}
