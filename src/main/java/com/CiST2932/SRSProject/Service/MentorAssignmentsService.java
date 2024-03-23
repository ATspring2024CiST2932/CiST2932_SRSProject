//src/main/java/com/CiST2932/SRSProject/Service/MentorAssignmentsService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO;
import com.CiST2932.SRSProject.Repository.MentorAssignmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorAssignmentsService {

    @Autowired
    private MentorAssignmentsRepository mentorAssignmentsRepository;

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

    public void deleteById(int id) {
        mentorAssignmentsRepository.deleteById(id);
    }

    public List<MentorAssignments> findByMentorEmployeeId(int mentorEmployeeId) {
        return mentorAssignmentsRepository.findByMentorEmployeeId(mentorEmployeeId);
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

}
