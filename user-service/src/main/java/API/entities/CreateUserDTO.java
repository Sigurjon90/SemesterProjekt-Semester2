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

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "(?:^|\\W)admin|caseworker|caregiver(?:$|\\W)", message = "User must be an admin, caseworker or caregiver")
    private String role;
    @Email
    private String email; 
    private String cpr;
    private String address;

}
