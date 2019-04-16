/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import main.java.Entity.CreateUserDTO;
import main.java.Entity.User;
import main.java.Entity.UserDTO;
import main.java.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jacobwowkjorgensen
 */
@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody CreateUserDTO userDTO) {
        UserDTO createUser = userService.createUser(userDTO);
        if (createUser != null) {

            return new ResponseEntity(createUser, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findUserById(@PathVariable("id") UUID id) {
        UserDTO user = userService.findUserById(id);

        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(path = "/citizens/{id}", method = RequestMethod.GET)
    public ResponseEntity findCitizensById(@PathVariable("id") UUID id) {
        List<UUID> citizens = userService.findCitizensById(id);

        if (citizens != null) {
            return new ResponseEntity(citizens, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/username/{username}", method = RequestMethod.GET)
    public ResponseEntity findUserByUsername(@PathVariable("username") String username) {
        UserDTO user = userService.findUserByUsername(username);

        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserById(@PathVariable("id") UUID id) {
        boolean user = userService.deleteUserById(id);

        if (user == true) {

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser (@RequestBody UserDTO userDTO) {

        UserDTO user = userService.updateUser(userDTO);

        if (user != null) {
            return new ResponseEntity(user , HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    
    
//    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
//    public ResponseEntity updateUser(@RequestBody User user) {
//         userService.updateUser(user);
//
//        if (user != null) {
//            return new ResponseEntity(user, HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }

//    @RequestMapping(path="/{id}", method = Requestmethod.DELETE)
//    public ResponseEntity deleteUser(@PathVariable("active") boolean active ){
//    
//    }
//    
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public void deleteUserByID(@PathVariable("id") int id){
//        userService.getUsereByID(id);
//    }
//    
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void updateUser(@RequestBody User user){
//    userService.updateUser(user);
//    }
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void insertUser(@RequestBody User user){
//    userService.insertUser(user);
//    }
}
