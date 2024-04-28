//src/main/java/com/CiST2932/SRSProject/Repository/MentorAssignmentsRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MentorAssignmentsRepository extends JpaRepository<MentorAssignments, Integer> {
    List<MentorAssignments> findByMentor(int mentorId);
    
    @Query("SELECT new com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO(ma.mentor.employeeId, ma.mentee.employeeId) FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId")
    List<MentorAssignmentsDTO> findMentorAssignmentsDtoByMentorEmployeeId(@Param("mentorId") int mentorId);

    //deleteByEmployeeId
    @Transactional
    @Modifying
    @Query("DELETE FROM MentorAssignments m WHERE m.mentor.employeeId = :employeeId OR m.mentee.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") int employeeId);

    //findByMentee
    List<MentorAssignments> findByMentee(int menteeId);
}