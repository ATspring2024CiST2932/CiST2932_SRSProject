// src/main/java/com/CiST2932/SRSProject/Controller/UsersController.java
package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> usersList = usersService.findAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        return usersService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users user) {
        if (usersService.findById(id).isPresent()) {
            user.setEmployeeId(id);
            return ResponseEntity.ok(usersService.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (usersService.findById(id).isPresent()) {
            usersService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // NEW ENDPOINTS FOR USER AUTHENTICATION AND REGISTRATION
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        // Authentication logic goes here
        return ResponseEntity.ok("Login endpoint placeholder");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        // Registration logic goes here
        return ResponseEntity.ok("Register endpoint placeholder");
    }
}
