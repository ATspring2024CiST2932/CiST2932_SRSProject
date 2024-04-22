// src/main/java/com/CiST2932/SRSProject/Repository/PeerCodingTasksRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeerCodingTasksRepository extends JpaRepository<PeerCodingTasks, Integer> {

    @Query("SELECT t FROM PeerCodingTasks t WHERE t.assignee.employeeId = :employeeId") 
    List<PeerCodingTasks> findByAssigneeEmployeeId(int employeeId);

    @Query("SELECT t FROM PeerCodingTasks t WHERE t.assignee.employeeId = :mentorId OR t.assignee.employeeId IN (SELECT ma.mentee.employeeId FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId)")
    List<PeerCodingTasks> findTasksByMentorAndMentees(@Param("mentorId") int mentorId);
    
    @Modifying
    @Query("DELETE FROM PeerCodingTasks p WHERE p.assignee.employeeId = :employeeId")
    void deleteByAssigneeEmployeeId(@Param("employeeId") int employeeId);

    // @Modifying
    // @Query("UPDATE PeerCodingTasks SET isArchived = true WHERE newHireInfo.employeeId = :employeeId")
    // void archiveTasksByNewHireId(@Param("employeeId") int employeeId);
}