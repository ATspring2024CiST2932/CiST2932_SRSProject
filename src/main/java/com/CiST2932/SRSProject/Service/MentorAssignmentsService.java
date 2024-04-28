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

@Service
@Transactional
public class MentorAssignmentsService {

    @Autowired
    private MentorAssignmentsRepository mentorAssignmentsRepository;
    @Autowired
    private NewHireInfoRepository newHireInfoRepository;

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
        return mentorAssignmentsRepository.findByMentor(mentorEmployeeId);
    }

    // Convert a MentorAssignments to a MentorAssignmentsDTO
// Convert a MentorAssignments entity to a MentorAssignmentsDTO
@SuppressWarnings("unused")
private MentorAssignmentsDTO convertToDto(MentorAssignments mentorAssignments) {
    if (mentorAssignments == null) {
        return null;
    }
    MentorAssignmentsDTO dto = new MentorAssignmentsDTO(0, 0, 0);
    dto.setAssignmentId(mentorAssignments.getAssignmentId());
    if (mentorAssignments.getMentor() != null) {
        dto.setMentorId(mentorAssignments.getMentor().getEmployeeId());
    }
    if (mentorAssignments.getMentee() != null) {
        dto.setMenteeId(mentorAssignments.getMentee().getEmployeeId());
    }
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