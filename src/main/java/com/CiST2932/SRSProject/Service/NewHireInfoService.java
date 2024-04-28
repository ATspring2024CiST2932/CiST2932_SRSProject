// src/main/java/com/CiST2932/SRSProject/Service/NewHireInfoService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfoDTO;
import com.CiST2932.SRSProject.Domain.Users;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Repository.UsersRepository;
import com.CiST2932.SRSProject.Repository.MentorAssignmentsRepository;
import com.CiST2932.SRSProject.Repository.PeerCodingTasksRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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
            if (newHireInfo.getDeveloperId() != null) {
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
    user.setDeveloperId(savedNewHireInfo);
    user.setEmail(newEmployeeDTO.getEmail());
    user.setUsername(newEmployeeDTO.getUsername());
    user.setPasswordHash(newEmployeeDTO.getPasswordHash());
    user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
    savedNewHireInfo.setDeveloperId(user);
    usersRepository.save(user);

    // Handle mentor or mentee assignment based on the role
    if (newEmployeeDTO.getIsMentor() && newEmployeeDTO.getMentorOrMenteeId() != null) {
        // New hire is a mentor, link to existing mentee
        MentorAssignments mentorAssignment = new MentorAssignments();
        mentorAssignment.setMentor(savedNewHireInfo);
        NewHireInfo mentee = newHireInfoRepository.findById(newEmployeeDTO.getMentorOrMenteeId()).orElseThrow(() -> new ResourceAccessException("Mentee not found with id " + newEmployeeDTO.getMentorOrMenteeId()));
        mentorAssignment.setMentee(mentee);
        mentorAssignmentsRepository.save(mentorAssignment);
    } else if (!newEmployeeDTO.getIsMentor() && newEmployeeDTO.getMentorOrMenteeId() != null) {
        // New hire is a mentee, link to existing mentor
        MentorAssignments mentorAssignment = new MentorAssignments();
        NewHireInfo mentor = newHireInfoRepository.findById(newEmployeeDTO.getMentorOrMenteeId()).orElseThrow(() -> new ResourceAccessException("Mentor not found with id " + newEmployeeDTO.getMentorOrMenteeId()));
        mentorAssignment.setMentor(mentor);
        mentorAssignment.setMentee(savedNewHireInfo);
        mentorAssignmentsRepository.save(mentorAssignment);
    }

    return savedNewHireInfo;
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
    Users user = usersRepository.findByDeveloperId(savedNewHireInfo.getEmployeeId());
    if (user == null) {
        user = new Users(); // If user does not exist, create a new one
    }
    user.setDeveloperId(savedNewHireInfo);
    user.setEmail(newEmployeeDTO.getEmail());
    user.setUsername(newEmployeeDTO.getUsername());
    user.setPasswordHash(newEmployeeDTO.getPasswordHash());
    user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
    
    // Save or update the User
    usersRepository.save(user);

    // Handle mentor or mentee assignment based on the role
    if (newEmployeeDTO.getIsMentor() && newEmployeeDTO.getMentorOrMenteeId() != null) {
        // New hire is a mentor, link to existing mentee or update existing assignment
        List<MentorAssignments> mentorAssignments = mentorAssignmentsRepository.findByMentor(savedNewHireInfo.getEmployeeId());
        MentorAssignments mentorAssignment;
        if (!mentorAssignments.isEmpty()) {
            mentorAssignment = mentorAssignments.get(0); // Assumes one-to-many can be changed as needed
        } else {
            mentorAssignment = new MentorAssignments(); // Create new if none exist
        }
        NewHireInfo mentee = newHireInfoRepository.findById(newEmployeeDTO.getMentorOrMenteeId()).orElseThrow(() -> new ResourceAccessException("Mentee not found with id " + newEmployeeDTO.getMentorOrMenteeId()));
        mentorAssignment.setMentor(savedNewHireInfo);
        mentorAssignment.setMentee(mentee);
        mentorAssignmentsRepository.save(mentorAssignment);
    } else if (!newEmployeeDTO.getIsMentor() && newEmployeeDTO.getMentorOrMenteeId() != null) {
        // New hire is a mentee, link to existing mentor or update existing assignment
        List<MentorAssignments> mentorAssignments = mentorAssignmentsRepository.findByMentee(savedNewHireInfo.getEmployeeId());
        MentorAssignments mentorAssignment;
        if (!mentorAssignments.isEmpty()) {
            mentorAssignment = mentorAssignments.get(0); // Assumes one-to-many can be changed as needed
        } else {
            mentorAssignment = new MentorAssignments(); // Create new if none exist
        }
        NewHireInfo mentor = newHireInfoRepository.findById(newEmployeeDTO.getMentorOrMenteeId()).orElseThrow(() -> new ResourceAccessException("Mentor not found with id " + newEmployeeDTO.getMentorOrMenteeId()));
        mentorAssignment.setMentor(mentor);
        mentorAssignment.setMentee(savedNewHireInfo);
        mentorAssignmentsRepository.save(mentorAssignment);
    }

    return savedNewHireInfo;
}
 
}