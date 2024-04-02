// src/main/java/com/CiST2932/SRSProject/Service/NewHireInfoService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfoDTO;
import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    
        // Save the NewHireInfo entity
        newHireInfoRepository.save(newHireInfo);
    
        // If there is username and password, create a new Users entity
        if (newEmployeeDTO.getUsername() != null && newEmployeeDTO.getPassword() != null) {
            Users user = new Users();
            user.setEmail(newEmployeeDTO.getEmail());
            user.setUsername(newEmployeeDTO.getUsername());
            user.setPasswordHash(newEmployeeDTO.getPassword()); // Consider using a hashed password
            user.setNewHireInfo(newHireInfo); // Set the relationship
    
            // Save the Users entity
            usersRepository.save(user);
        }
    
        return newHireInfo;
    }
    
    public NewHireInfo updateEmployee(int id, NewEmployeeDTO employeeDTO) {
        NewHireInfo employee = newHireInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Employee not found with id " + id));
        modelMapper.map(employeeDTO, employee);
        return newHireInfoRepository.save(employee);
    }
    
    


    
}
