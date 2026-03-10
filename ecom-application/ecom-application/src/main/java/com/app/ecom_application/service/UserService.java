package com.app.ecom_application.service;

import com.app.ecom_application.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> fetchAllUsers(){
        return userList;

    }

    public void addUser(User user){
        user.setUserId(nextId++);
        userList.add(user);
    }

    public Optional<User> fetchUser(Long id){
        return userList.stream()
                .filter(user->user.getUserId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id,User updatedUser){
        return userList.stream()
                .filter(user->user.getUserId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);

    }
}
