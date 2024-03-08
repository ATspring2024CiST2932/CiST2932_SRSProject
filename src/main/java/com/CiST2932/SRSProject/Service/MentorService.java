package com.CiST2932.SRSProject.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CiST2932.SRSProject.Repository.MentorAssignmentsRepository;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorInfo;
import com.CiST2932.SRSProject.Domain.NewHireInfo;

@Service
public class MentorService {

    @Autowired
    private MentorAssignmentsRepository mentorAssignmentsRepository;

    @Autowired
    private NewHireInfoRepository newHireInfoRepository;

    // public MentorInfo getMentorInfo(int mentorId) {
    //     List<MentorAssignments> assignments = mentorAssignmentsRepository.findByMentorId(mentorId);
    //     List<NewHireInfo> mentees = new ArrayList<>();
    //     for (MentorAssignments assignment : assignments) {
    //         mentees.addAll(newHireInfoRepository.findMenteesByMentorEmployeeId(assignment.getMenteeId()));
    //     }
    //     return new MentorInfo(mentorId, mentees);
    // }
}
