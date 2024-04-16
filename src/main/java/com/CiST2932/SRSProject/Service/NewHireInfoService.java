// src/main/java/com/CiST2932/SRSProject/Service/NewHireInfoService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfoDTO;
import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Service
public class NewHireInfoService {

    @Autowired
    private NewHireInfoRepository newHireInfoRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<NewHireInfo> findAll() {
        return newHireInfoRepository.findAll();
    }

    public Optional<NewHireInfo> findById(int id) {
        return newHireInfoRepository.findById(id);
    }

    @SuppressWarnings("null")
    public NewHireInfo save(NewHireInfo newHireInfo) {
        return newHireInfoRepository.save(newHireInfo);
    }

    public void deleteById(int id) {
        newHireInfoRepository.deleteById(id);
    }

    public List<NewHireInfo> findMenteesByMentorId(int mentorId) {
        return newHireInfoRepository.findMenteesByMentorId(mentorId);
    }

    public List<NewHireInfo> findUnassignedMentees() {
        return newHireInfoRepository.findUnassignedMentees();
    }   

    public List<NewHireInfo> findAllMentors() {
        return newHireInfoRepository.findAllMentors();
    }

    public List<NewHireInfoDTO> findAllDtos() {
        List<NewHireInfo> newHireInfos = newHireInfoRepository.findAll();
        List<NewHireInfoDTO> newHireInfoDTOs = new java.util.ArrayList<>();
        for (NewHireInfo newHireInfo : newHireInfos) {
            newHireInfoDTOs.add(new NewHireInfoDTO(
                newHireInfo.getEmployeeId(), 
                newHireInfo.getName(), 
                newHireInfo.getEmploymentType(), 
                newHireInfo.getIsMentor()));
            }
        return newHireInfoDTOs;
    }
    
    public List<String> findAllNames() {
        return newHireInfoRepository.findAllNames();
    }

    public NewHireInfo createNewHireInfo(NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo newHireInfo = new NewHireInfo();
        newHireInfo.setName(newEmployeeDTO.getName());
        newHireInfo.setIsMentor(newEmployeeDTO.getIsMentor());
        newHireInfo.setEmploymentType(newEmployeeDTO.getEmploymentType());
    
        // If username and password are provided, create the Users object first
        if (newEmployeeDTO.getUsername() != null && newEmployeeDTO.getPasswordHash() != null) {
            Users user = new Users();
            user.setEmail(newEmployeeDTO.getEmail());
            user.setUsername(newEmployeeDTO.getUsername());
            user.setPasswordHash(newEmployeeDTO.getPasswordHash()); // Consider using a hashed password
            user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
    
            // Associate User with NewHireInfo
            newHireInfo.setUser(user);
            user.setNewHireInfo(newHireInfo);
        }
    
        // Save the NewHireInfo entity, cascade should save Users too
        return newHireInfoRepository.save(newHireInfo);
    }
        
    public NewHireInfo updateEmployee(int id, NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo employee = newHireInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Employee not found with id " + id));
        modelMapper.map(newEmployeeDTO, employee);
        return newHireInfoRepository.save(employee);
    }
    
    @Transactional
    public NewHireInfo updateOrCreateEmployee(int id, NewEmployeeDTO newEmployeeDTO) {
        // First, fetch the existing NewHireInfo or create a new one
        NewHireInfo newHireInfo = newHireInfoRepository.findById(id).orElse(new NewHireInfo());

        // Update properties from DTO
        newHireInfo.setIsMentor(newEmployeeDTO.getIsMentor());
        newHireInfo.setEmploymentType(newEmployeeDTO.getEmploymentType());  // Assuming these are included in DTO
        newHireInfo.setName(newEmployeeDTO.getName()); // Assume name is also updatable

        // Save the NewHireInfo entity and ensure it has an ID
        newHireInfo = newHireInfoRepository.save(newHireInfo);

        // Handle the Users entity
        Users user = usersRepository.findById(id).orElse(new Users());

        // Set properties from the DTO
        user.setEmail(newEmployeeDTO.getEmail());
        user.setUsername(newEmployeeDTO.getUsername());
        user.setPasswordHash(newEmployeeDTO.getPasswordHash());
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

        // Link User to NewHireInfo
        user.setNewHireInfo(newHireInfo);
        newHireInfo.setUser(user);

        // Save or update the User
        usersRepository.save(user);

        return newHireInfo;
    } 


    
}
