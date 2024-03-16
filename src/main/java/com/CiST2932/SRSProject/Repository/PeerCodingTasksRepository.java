// src/main/java/com/CiST2932/SRSProject/Repository/PeerCodingTasksRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeerCodingTasksRepository extends JpaRepository<PeerCodingTasks, Integer> {
    List<PeerCodingTasks> findByEmployeeId(int employeeId);

    @Query("SELECT t FROM PeerCodingTasks t WHERE t.employeeId = :mentorId OR t.employeeId IN (SELECT m.mentee.employeeId FROM MentorAssignments m WHERE m.mentor.employeeId = :mentorId)")
    List<PeerCodingTasks> findTasksByMentorAndMentees(@Param("mentorId") int mentorId);
}
