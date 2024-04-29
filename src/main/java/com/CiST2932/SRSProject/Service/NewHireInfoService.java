// src/main/java/com/CiST2932/SRSProject/Service/NewHireInfoService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfoDTO;
import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Domain.TaskDTO;
import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Repository.UsersRepository;
import com.CiST2932.SRSProject.Repository.MentorAssignmentsRepository;
import com.CiST2932.SRSProject.Repository.PeerCodingTasksRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
public class NewHireInfoService {
    @Autowired
    private NewHireInfoRepository newHireInfoRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MentorAssignmentsRepository mentorAssignmentsRepository;
    @Autowired
    private PeerCodingTasksRepository peerCodingTasksRepository;
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

    @Transactional
    public void deleteNewHireInfoAndRelatedData(int employeeId) {
        NewHireInfo newHireInfo = newHireInfoRepository.findById(employeeId).orElseThrow(() ->
            new IllegalStateException("Employee not found with id: " + employeeId));
    
        // Attempt to delete User first if exists
        try {
            if (newHireInfo.getDeveloper() != null) {
                usersRepository.deleteByEmployeeId(newHireInfo.getEmployeeId());
            }
    
            // Then delete related data to avoid foreign key constraints
            peerCodingTasksRepository.deleteByEmployeeId(employeeId);
            mentorAssignmentsRepository.deleteByEmployeeId(employeeId);
    
            // Finally, delete the NewHireInfo record itself
            newHireInfoRepository.deleteById(employeeId);
        } catch (Exception e) {
            // Log error or handle it as necessary
            throw new RuntimeException("Error deleting employee and related data: " + e.getMessage(), e);
        }
    }
    
    public List<NewHireInfo> findMenteesByMentorId(int mentorId) {
        return newHireInfoRepository.findMenteesByMentorId(mentorId);
    }
    

    public List<NewHireInfo> findMentorByMenteeId(int menteeId) {
        return newHireInfoRepository.findMentorByMenteeId(menteeId);
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
        
        // Save the NewHireInfo entity first to get an employeeId
        NewHireInfo savedNewHireInfo = newHireInfoRepository.save(newHireInfo);
    
        // Create user account
        Users user = new Users();
        user.setDeveloper(savedNewHireInfo);
        user.setEmail(newEmployeeDTO.getEmail());
        user.setUsername(newEmployeeDTO.getUsername());
        user.setPasswordHash(newEmployeeDTO.getPasswordHash());
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        savedNewHireInfo.setDeveloper(user);  // Corrected from setUser to setDeveloper
        usersRepository.save(user);
        
        return savedNewHireInfo; // Make sure to return the saved object
    }
    
private void updateNewHireInfoFromDto(NewHireInfo newHireInfo, NewEmployeeDTO dto) {
    newHireInfo.setName(dto.getName());
    newHireInfo.setIsMentor(dto.getIsMentor());
    newHireInfo.setEmploymentType(dto.getEmploymentType());
}

    private void handleMentorOrMenteeAssignment(NewHireInfo newHireInfo, NewEmployeeDTO newEmployeeDTO) {
        Optional<NewHireInfo> mentorOrMenteeOpt = newHireInfoRepository.findById(newEmployeeDTO.getMentorOrMenteeId());
        if (mentorOrMenteeOpt.isPresent()) {
            MentorAssignments mentorAssignment = new MentorAssignments();
            NewHireInfo mentorOrMentee = mentorOrMenteeOpt.get();
            mentorAssignment.setMentor(newHireInfo.getIsMentor() ? newHireInfo : mentorOrMentee);
            mentorAssignment.setMentee(newHireInfo.getIsMentor() ? mentorOrMentee : newHireInfo);
    
            // Log the assignment details before saving
            System.out.println("Creating Mentor Assignment: " + mentorAssignment);
    
            mentorAssignmentsRepository.save(mentorAssignment);
            System.out.println("Mentor Assignment Saved: " + mentorAssignment);
        } else {
            System.out.println("Mentor/Mentee ID not found: " + newEmployeeDTO.getMentorOrMenteeId());
        }

        // print out the username
        System.out.println("Username: " + newEmployeeDTO.getUsername());
        // refresh the newHireInfo
        newHireInfo = newHireInfoRepository.findById(newHireInfo.getEmployeeId()).get();
        System.out.println("Employee ID: " + newHireInfo.getEmployeeId());

    
        // // Save the NewHireInfo entity, cascade should save Users too
        // return newHireInfoRepository.save(newHireInfo);        
    }

    public NewHireInfo updateEmployee(int id, NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo employee = newHireInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Employee not found with id " + id));
        modelMapper.map(newEmployeeDTO, employee);
        return newHireInfoRepository.save(employee);
    }

@Transactional
public NewHireInfo updateOrCreateEmployee(int id, NewEmployeeDTO newEmployeeDTO) {
    // Fetch the existing NewHireInfo or create a new one
    NewHireInfo newHireInfo = newHireInfoRepository.findById(id).orElse(new NewHireInfo());

    // Update newHireInfo fields
    newHireInfo.setName(newEmployeeDTO.getName());
    newHireInfo.setIsMentor(newEmployeeDTO.getIsMentor());
    newHireInfo.setEmploymentType(newEmployeeDTO.getEmploymentType());

    // Save the NewHireInfo entity to get or refresh an employeeId
    NewHireInfo savedNewHireInfo = newHireInfoRepository.save(newHireInfo);

    // Handling the Users entity
    Users user = usersRepository.findById(newHireInfo.getEmployeeId()).orElse(new Users());
    user.setDeveloper(savedNewHireInfo);  // Corrected method name
    user.setEmail(newEmployeeDTO.getEmail());
    user.setUsername(newEmployeeDTO.getUsername());
    user.setPasswordHash(newEmployeeDTO.getPasswordHash());
    user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

    usersRepository.save(user); // Save the user to persist the changes

    return savedNewHireInfo; // Return the updated or created NewHireInfo
}
@Transactional(readOnly = true)
public NewEmployeeDTO getEmployeeDetails(int employeeId) {
    NewHireInfo employee = newHireInfoRepository.findById(employeeId).orElse(null);
    if (employee == null) {
        return null;
    }
    NewEmployeeDTO dto = new NewEmployeeDTO();
    dto.setEmployeeId(employee.getEmployeeId());
    dto.setName(employee.getName());
    dto.setIsMentor(employee.getIsMentor());
    dto.setEmploymentType(employee.getEmploymentType());

    // Access the Users entity through the NewHireInfo entity
    Users user = employee.getDeveloper();  // Corrected from getUser to getDeveloper
    if (user != null) {
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPasswordHash(user.getPasswordHash());
        dto.setRegistrationDate(user.getRegistrationDate());
    }
    if (employee.getIsMentor()) {
        // Assume that each mentor has multiple mentees
        List<Integer> menteeIds = newHireInfoRepository.findMenteesByMentorId(employeeId)
            .stream()
            .map(NewHireInfo::getEmployeeId)
            .collect(Collectors.toList());
        dto.setAssignmentsAsMentorIds(menteeIds); // If multiple, pick first or adjust as needed
    } else {
        // Assume each mentee has one mentor
        Optional<Integer> mentorId = newHireInfoRepository.findMentorByMenteeId(employeeId)
            .stream()
            .map(NewHireInfo::getEmployeeId)
            .findFirst(); // Only get the first (or only) mentor ID
        dto.setMentorOrMenteeId(mentorId.orElse(null)); // Set the mentor ID in the DTO, handling null if not found
    }

    List<TaskDTO> tasks = peerCodingTasksRepository.findByAssigneeEmployeeId(employeeId)
            .stream()
            .map(this::convertToTaskDto)
            .collect(Collectors.toList());
    dto.setTasks(tasks);

    return dto;
}

        private TaskDTO convertToTaskDto(PeerCodingTasks task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskUrl(task.getTaskUrl());
        dto.setTaskNumber(task.getTaskNumber());
        dto.setTaskType(task.getTaskType());
        dto.setTotalHours(task.getTotalHours());
        dto.setAssigneeName(task.getAssignee().getName()); // Assuming Task has a reference to Assignee which is an Employee
        return dto;
        }

        public List<NewHireInfo> findAllNewHireInfoWithDetails() {
            return newHireInfoRepository.findAllNewHireInfoWithDetails();
        }
    }
