//src/main/java/com/CiST2932/SRSProject/Repository/PeerCodingTasksRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerCodingTasksRepository extends JpaRepository<PeerCodingTasks, Integer> {
    @Query(value = "SELECT DISTINCT t.task_id, t.task_url, t.task_number, t.task_type, t.EmployeeId " +
               "FROM peercodingtasks t " +
               "WHERE t.EmployeeId = :mentorId " +
               "OR t.EmployeeID IN (" +
               "  SELECT mma.MenteeID " +
               "  FROM mentorassignments mma " +
               "  WHERE mma.MentorID = :mentorId" +
               ")", nativeQuery = true)
    List<Object[]> findTasksByMentorId(@Param("mentorId") int mentorId);
    List<PeerCodingTasks> findByEmployeeId(int employeeId);

}