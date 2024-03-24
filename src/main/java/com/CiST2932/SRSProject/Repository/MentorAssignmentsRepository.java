//src/main/java/com/CiST2932/SRSProject/Repository/MentorAssignmentsRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorAssignmentsRepository extends JpaRepository<MentorAssignments, Integer> {
    List<MentorAssignments> findByMentorEmployeeId(int mentorEmployeeId);
    
    @Query("SELECT new com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO(ma.mentor.employeeId, ma.mentee.employeeId) FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId")
    List<MentorAssignmentsDTO> findMentorAssignmentsDtoByMentorEmployeeId(@Param("mentorId") int mentorId);

}
