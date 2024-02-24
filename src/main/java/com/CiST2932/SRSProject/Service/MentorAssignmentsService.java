//src/main/java/com/CiST2932/SRSProject/Service/MentorAssignmentsService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
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

    public MentorAssignments save(MentorAssignments mentorAssignments) {
        return mentorAssignmentsRepository.save(mentorAssignments);
    }

    public void deleteById(int id) {
        mentorAssignmentsRepository.deleteById(id);
    }
}
