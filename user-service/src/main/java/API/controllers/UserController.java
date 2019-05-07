/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import java.util.List;
import java.util.UUID;
import API.entities.CreateUserDTO;
import API.entities.UserDTO;
import API.entities.ValidatedUserDTO;
import API.services.IUserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.JwtUtils;

import javax.validation.Valid;

/**
 *
 * @author jacobwowkjorgensen
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = UserDTO.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllUsers(@RequestHeader HttpHeaders httpHeaders) {
        String token = httpHeaders.getFirst("authorization");
        UUID careCenterId = jwtUtils.getCareCenterId(token);
        List<UserDTO> users = userService.getAllUsers(careCenterId);
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Non", response = void.class),
        @ApiResponse(code = 201, message = "Successful", response = UserDTO.class)
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody CreateUserDTO userDTO) {
        String token = httpHeaders.getFirst("authorization");
        UUID careCenterId = jwtUtils.getCareCenterId(token);
        UserDTO createUser = userService.createUser(userDTO, careCenterId);
        if (createUser != null) {
            return new ResponseEntity(createUser, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = UserDTO.class)
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findUserById(@PathVariable("id") UUID id) {
        UserDTO user = userService.findUserById(id);

        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = UserDTO.class)
    })
    @RequestMapping(path = "/citizens/{id}", method = RequestMethod.GET)
    public ResponseEntity findCitizensById(@PathVariable("id") UUID id) {
        List<UUID> citizens = userService.findCitizensById(id);

        if (citizens != null) {
            return new ResponseEntity(citizens, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = ValidatedUserDTO.class)
    })
    @RequestMapping(path = "/username/{username}", method = RequestMethod.GET)
    public ResponseEntity findUserByUsername(@PathVariable("username") String username) {
        ValidatedUserDTO user = userService.findUserByUsername(username);

        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = void.class)
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserById(@PathVariable("id") UUID id) {
        boolean user = userService.deleteUserById(id);

        if (user == true) {

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = UserDTO.class)
    })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser (@RequestBody UserDTO userDTO) {

        UserDTO user = userService.updateUser(userDTO);

        if (user != null) {
            return new ResponseEntity(user , HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
