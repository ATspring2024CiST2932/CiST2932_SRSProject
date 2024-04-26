// src/main/java/com/CiST2932/SRSProject/Repository/NewHireInfoRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.NewHireInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NewHireInfoRepository extends JpaRepository<NewHireInfo, Integer> {

    @Query("SELECT n FROM NewHireInfo n WHERE n.isMentor = false AND n.id NOT IN (SELECT ma.mentee.id FROM MentorAssignments ma)")
    List<NewHireInfo> findUnassignedMentees();  
    
    @Query("SELECT n FROM NewHireInfo n WHERE n.isMentor = true")
    List<NewHireInfo> findAllMentors();
    
    Optional<NewHireInfo> findByName(String name);

    @Query("SELECT n.name FROM NewHireInfo n")
    List<String> findAllNames();

    // Find employee by ID with detailed information, including mentor and mentee information
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.mentorAssignments WHERE e.employeeId = :employeeId")
    Optional<NewHireInfo> findEmployeeWithDetailsById(int employeeId);

    // List all mentors
    List<NewHireInfo> findByIsMentorTrue();

    // List all mentees
    List<NewHireInfo> findByIsMentorFalse();


    // @Modifying
    // void archiveNewHireInfoById(@Param("employeeId") int employeeId);

@Query("SELECT ma.mentee FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId")
List<NewHireInfo> findMenteesByMentorId(@Param("mentorId") int mentorId);

@Query("SELECT ma.mentor FROM MentorAssignments ma WHERE ma.mentee.employeeId = :menteeId")
List<NewHireInfo> findMentorByMenteeId(@Param("menteeId") int menteeId);
}