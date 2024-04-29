//src/main/java/com/CiST2932/SRSProject/Service/MentorAssignmentsService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Repository.MentorAssignmentsRepository;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MentorAssignmentsService {

    @Autowired
    private MentorAssignmentsRepository mentorAssignmentsRepository;
    @Autowired
    private NewHireInfoRepository newHireInfoRepository;

    public List<MentorAssignmentsDTO> findAssignmentsDtoByMentor(int mentorId) {
    List<MentorAssignments> assignments = mentorAssignmentsRepository.findByMentorEmployeeId(mentorId);
    return assignments.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    public List<MentorAssignmentsDTO> findAllAssignmentsDto() {
        List<MentorAssignments> assignments = mentorAssignmentsRepository.findAll();
        return assignments.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public MentorAssignmentsDTO findAssignmentDtoById(int id) {
        return mentorAssignmentsRepository.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new RuntimeException("Mentor assignment not found for ID: " + id));
    }

    public List<MentorAssignments> findAll() {
        return mentorAssignmentsRepository.findAll();
    }

    public Optional<MentorAssignments> findById(int id) {
        return mentorAssignmentsRepository.findById(id);
    }

    @SuppressWarnings("null")
    public MentorAssignments save(MentorAssignments mentorAssignments) {
        return mentorAssignmentsRepository.save(mentorAssignments);
    }

    @Transactional
    public void deleteById(int id) {
        mentorAssignmentsRepository.deleteById(id);
    }

    @Transactional
    public void deleteMentorAssignmentsByEmployeeId(int employeeId) {
        mentorAssignmentsRepository.deleteByEmployeeId(employeeId);
    }
    

    public List<MentorAssignments> findByMentorEmployeeId(int mentorEmployeeId) {
        return mentorAssignmentsRepository.findByMentorEmployeeId(mentorEmployeeId);
    }

    // Convert a MentorAssignments entity to a MentorAssignmentsDTO
    public MentorAssignmentsDTO convertToDto(MentorAssignments mentorAssignments) {
        if (mentorAssignments == null) {
            throw new IllegalArgumentException("Cannot convert null MentorAssignments to DTO");
        }
        MentorAssignmentsDTO dto = new MentorAssignmentsDTO(
            Optional.ofNullable(mentorAssignments.getMentor()).map(NewHireInfo::getEmployeeId).orElseThrow(() -> new IllegalArgumentException("Mentor data is missing")),
            Optional.ofNullable(mentorAssignments.getMentee()).map(NewHireInfo::getEmployeeId).orElseThrow(() -> new IllegalArgumentException("Mentee data is missing"))
        );
        dto.setAssignmentId(mentorAssignments.getAssignmentId());
        return dto;
    }



    public NewHireInfo findNewHireInfoById(int id) {
        return newHireInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("NewHireInfo not found"));
    }

    public MentorAssignments update(int id, MentorAssignmentsDTO dto) {
        MentorAssignments mentorAssignments = findById(id)
            .orElseThrow(() -> new RuntimeException("MentorAssignments not found"));
        
        mentorAssignments.setMentor(findNewHireInfoById(dto.getMentorId()));
        mentorAssignments.setMentee(findNewHireInfoById(dto.getMenteeId()));
        
        return save(mentorAssignments);
    }
    

}