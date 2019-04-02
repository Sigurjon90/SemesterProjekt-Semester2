package main.controllers;

import main.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.services.IUserService;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable String id) {
        Optional user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity("Not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers() {
        return new ResponseEntity(userService.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserDTO user) {
        userService.insertUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
