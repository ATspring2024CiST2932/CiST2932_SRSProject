//src/main/java/com/CiST2932/SRSProject/Service/UsersService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> findById(int id) {
        return usersRepository.findById(id);
    }

    @SuppressWarnings("null")
    public Users save(Users user) {
        return usersRepository.save(user);
    }

    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }
}
