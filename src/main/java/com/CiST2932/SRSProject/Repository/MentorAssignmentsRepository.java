//src/main/java/com/CiST2932/SRSProject/Repository/MentorAssignmentsRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.MentorAssignments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorAssignmentsRepository extends JpaRepository<MentorAssignments, Integer> {
    List<MentorAssignments> findByMentorId(Long mentorId);
}
