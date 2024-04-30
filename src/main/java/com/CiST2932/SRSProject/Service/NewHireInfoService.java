// src/main/java/com/CiST2932/SRSProject/Service/NewHireInfoService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfoDTO;
import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Domain.TaskDTO;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
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
                newHireInfo.getEmail();
            }
        return newHireInfoDTOs;
    }
    
    public List<String> findAllNames() {
        return newHireInfoRepository.findAllNames();
    }

    @Transactional
    public NewHireInfo createNewHireInfo(NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo newHireInfo = new NewHireInfo();
        newHireInfo.setName(newEmployeeDTO.getName());
        newHireInfo.setIsMentor(newEmployeeDTO.getIsMentor());
        newHireInfo.setEmploymentType(newEmployeeDTO.getEmploymentType());
        newHireInfo.setEmail(newEmployeeDTO.getEmail());
        
        // Save the NewHireInfo entity first to get an employeeId
        NewHireInfo savedNewHireInfo = newHireInfoRepository.save(newHireInfo);
    
        // Handle Mentor/Mentee Assignment
        if (newEmployeeDTO.getMentorOrMenteeId() != null) {
            handleMentorOrMenteeAssignment(savedNewHireInfo, newEmployeeDTO);
        }
    
        return savedNewHireInfo;
    }

private void handleMentorOrMenteeAssignment(NewHireInfo newHireInfo, NewEmployeeDTO newEmployeeDTO) {
    Optional<NewHireInfo> mentorOrMenteeOpt = newHireInfoRepository.findById(newEmployeeDTO.getMentorOrMenteeId());
    System.out.println("Mentor/Mentee ID: " + newEmployeeDTO.getMentorOrMenteeId());

    if (mentorOrMenteeOpt.isPresent()) {
        NewHireInfo mentorOrMentee = mentorOrMenteeOpt.get();
        MentorAssignments mentorAssignment = new MentorAssignments();
        if (newHireInfo.getIsMentor()) {
            mentorAssignment.setMentor(newHireInfo);
            mentorAssignment.setMentee(mentorOrMentee);
        } else {
            mentorAssignment.setMentor(mentorOrMentee);
            mentorAssignment.setMentee(newHireInfo);
        }
        mentorAssignmentsRepository.save(mentorAssignment);
        System.out.println("Mentor/Mentee assignment created successfully");
    } else {
        System.out.println("Mentor/Mentee ID not found: " + newEmployeeDTO.getMentorOrMenteeId());
    }
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
    newHireInfo.setEmail(newEmployeeDTO.getEmail());

    // Save the NewHireInfo entity to get or refresh an employeeId
    NewHireInfo savedNewHireInfo = newHireInfoRepository.save(newHireInfo);

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
    dto.setEmail(employee.getEmail());

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

        public List<NewEmployeeDTO> findAllNewEmployeeDTO() {
            return newHireInfoRepository.findAllNewEmployeeDTO();
        }
    }
