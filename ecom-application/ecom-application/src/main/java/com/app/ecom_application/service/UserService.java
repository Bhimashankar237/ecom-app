package com.app.ecom_application.service;

import com.app.ecom_application.dto.AddressDto;
import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.model.Address;
import com.app.ecom_application.model.User;
import com.app.ecom_application.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*List<User> userList = new ArrayList<>();
    private Long nextId = 1L;*/

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .toList();

    }

    public void addUser(UserRequest userRequest){
       /* user.setUserId(nextId++);*/
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }



    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

  public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
      .map(existingUser -> {
          updateUserFromRequest(existingUser, updatedUserRequest);
                userRepository.save(existingUser);
                return true;
            }).orElse(false);
        }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
           Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());
            user.setAddress(address);
        }
    }

      private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setUserId(String.valueOf(user.getUserId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setUserRole(user.getUserRole());

        if(user.getAddress() != null){
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipCode(user.getAddress().getZipCode());
            response.setAddress(addressDto);
        }
        return response;
      }
}
