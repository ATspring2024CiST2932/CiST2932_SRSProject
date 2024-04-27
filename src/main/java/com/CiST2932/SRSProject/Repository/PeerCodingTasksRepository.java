// src/main/java/com/CiST2932/SRSProject/Repository/PeerCodingTasksRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PeerCodingTasksRepository extends JpaRepository<PeerCodingTasks, Integer> {

    @Query("SELECT t FROM PeerCodingTasks t WHERE t.assignee.employeeId = :employeeId") 
    List<PeerCodingTasks> findByAssigneeEmployeeId(Integer employeeId);
    
    // Find tasks by employee ID
    List<PeerCodingTasks> findByEmployeeId(Integer employeeId);

    // Find tasks by assignee's employee ID
    List<PeerCodingTasks> findByAssigneeId(int assigneeId);

    @Query("SELECT t FROM PeerCodingTasks t WHERE t.assignee.employeeId = :mentorId OR t.assignee.employeeId IN (SELECT ma.mentee.employeeId FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId)")
    List<PeerCodingTasks> findTasksByMentorAndMentees(@Param("mentorId") int mentorId);

    //getAssignedTasks
    @Query("SELECT t FROM PeerCodingTasks t WHERE t.assignee.employeeId = :employeeId")
    List<PeerCodingTasks> getAssignedTasks(@Param("employeeId") int employeeId);
    
    //deleteByEmployeeId
    @Modifying
    @Query("DELETE FROM PeerCodingTasks t WHERE t.assignee.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") int employeeId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM PeerCodingTasks t WHERE t.id = :id")    
    void deleteById(@Param("id") int id);

}

