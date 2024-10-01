package com.empresa.projetoapi.api;

import com.empresa.projetoapi.model.User;
import com.empresa.projetoapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Gets user by ID", description = "User must exist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping(value = "/user", produces = {"application/json", "application/xml"})
    public ResponseEntity<User> getUser(@RequestParam Integer id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        }
    }

    @Operation(summary = "Gets users list", description = "Users list must exist")
    @GetMapping(value = "/users", produces = {"application/json", "application/xml"})
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Creates new user", description = "New user parameters must be valid")
    @PostMapping(value = "/user", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> postUser(@RequestBody User user) {
        try {
            userService.postUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created succesfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("" + e);
        }
    }

    @Operation(summary = "Deletes an user", description = "User ID must be valid")
    @DeleteMapping(value = "/user", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> deleteUser(@RequestParam Integer id) {
        if (getUser(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with id: " + id + " does not exist");
        } else {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User deleted succesfully");
        }
    }

    @Operation(summary = "Edits an user", description = "User parameters must be valid")
    @PutMapping("/user")
    public ResponseEntity<String> putUser(@RequestBody User user) {
        try {
            userService.putUser(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User edited succesfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("" + e);
        }
    }

}
