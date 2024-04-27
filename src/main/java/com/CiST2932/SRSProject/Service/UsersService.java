// src/main/java/com/CiST2932/SRSProject/Service/UsersService.java
package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.common.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

@NonNullApi
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> findById(int id) {
        return usersRepository.findById(id);
    }

    @Transactional
    public void deleteById(int id) {
        usersRepository.deleteByEmployeeId(id);
    }

    //UPDATED save method for user validation

    @SuppressWarnings("null")
    public Users save(Users user) {
        // Perform data validation here
        if (isValidUser(user)) {
            // Additional business logic processing
            // Example: Hashing passwords before saving
            // user.setPassword(hashPassword(user.getPassword()));
            
            return usersRepository.save(user);
        } else {
            // Handle invalid user data
            throw new IllegalArgumentException("Invalid user data");
        }
    }
    
    private boolean isValidUser(Users user) {
        // Perform validation logic here
        // Example: Check if required fields are not null
        return user.getUsername() != null && user.getPasswordHash() != null && user.getEmail() != null;
    }
    
    
}
