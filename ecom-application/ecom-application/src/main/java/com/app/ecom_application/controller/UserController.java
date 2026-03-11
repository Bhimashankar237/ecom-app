package com.app.ecom_application.controller;

import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser( @RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added Successfully");

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserRequest updatedUserRequest){
        boolean updated = userService.updateUser(id,updatedUserRequest);
        if(updated){
            return ResponseEntity.ok("user updated successfully");
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}
