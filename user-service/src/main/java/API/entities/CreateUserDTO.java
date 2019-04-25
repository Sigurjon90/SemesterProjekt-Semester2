/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.entities;

import API.utils.BCryptPasswordDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jacobwowkjorgensen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    
    private String username;
    @JsonDeserialize(using = BCryptPasswordDeserializer.class )
    private String password; 
    private String role;
    private String email; 
    private String Cpr; 
    private String address;

}
