//src/main/java/com/CiST2932/SRSProject/Service/MentorService.java

package com.CiST2932.SRSProject.Service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CiST2932.SRSProject.Repository.MentorAssignmentsRepository;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import java.util.List;
import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfo;



@Service
public class MentorService {

    @Autowired
    private MentorAssignmentsRepository mentorAssignmentsRepository;

    @Autowired
    private NewHireInfoRepository newHireInfoRepository;

    public MentorInfo getMentorInfo(Long mentorId) {
        List<MentorAssignments> assignments = mentorAssignmentsRepository.findByMentorId(mentorId);
        List<NewHireInfo> mentees = new ArrayList<>();
        for (MentorAssignments assignment : assignments) {
            newHireInfoRepository.findByEmployeeId(assignment.getMenteeID())
                    .ifPresent(mentees::add);
        }
        return new MentorInfo(mentorId, mentees);
    }
}


