// src/main/java/com/CiST2932/SRSProject/Repository/NewHireInfoRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NewHireInfoRepository extends JpaRepository<NewHireInfo, Integer> {
    
//find all newEmployeeDTO fields, mentorasignments, and peercodingtasks
@Query("SELECT new com.CiST2932.SRSProject.Domain.NewEmployeeDTO(n.employeeId, n.name, n.employmentType, n.email, n.isMentor, n.assignmentsAsMentor, n.assignmentsAsMentee, n.assignedTasks) FROM NewHireInfo n")
List<NewEmployeeDTO> findAllNewEmployeeDTO();

    // @Query("SELECT n FROM NewHireInfo n JOIN FETCH n.developer WHERE n.employeeId = :id")
    // Optional<NewHireInfo> findById(@Param("id") int id);
    
    // find All New HireInfo With Details from the MentorAssignments and PeerCodingTasks
    @Query("SELECT n FROM NewHireInfo n JOIN FETCH n.assignmentsAsMentor JOIN FETCH n.assignmentsAsMentee JOIN FETCH n.assignedTasks")
    List<NewHireInfo> findAllNewHireInfoWithDetails();
    

    // @Query("SELECT n FROM NewHireInfo n JOIN FETCH n.developer WHERE n.employeeId = :id")
    // Optional<NewHireInfo> findByIdWithDetails(@Param("id") int id);

    @Query("SELECT ma.mentee FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId")
    List<NewHireInfo> findMenteesByMentorId(@Param("mentorId") int mentorId);
    
    @Query("SELECT ma.mentor FROM MentorAssignments ma WHERE ma.mentee.employeeId = :menteeId")
    List<NewHireInfo> findMentorByMenteeId(@Param("menteeId") int menteeId);

    @Query("SELECT n FROM NewHireInfo n WHERE n.isMentor = false AND n.id NOT IN (SELECT ma.mentee.id FROM MentorAssignments ma)")
    List<NewHireInfo> findUnassignedMentees(); 
    
    //getMentorAssignments
    @Query("SELECT DISTINCT ma.mentor FROM MentorAssignments ma WHERE ma.mentor.isMentor = true")
    List<NewHireInfo> findMentorAssignments();

    //getMenteeAssignments
    @Query("SELECT DISTINCT ma.mentee FROM MentorAssignments ma WHERE ma.mentee.isMentor = false")
    List<NewHireInfo> findMenteeAssignments();
    
    @Query("SELECT n FROM NewHireInfo n WHERE n.isMentor = true")
    List<NewHireInfo> findAllMentors();

    Optional<NewHireInfo> findByName(String name);

    @Query("SELECT n.name FROM NewHireInfo n")
    List<String> findAllNames();


    @Transactional
    @Modifying
    @Query("DELETE FROM NewHireInfo n WHERE n.id = :id")    
    void deleteById(@Param("id") int id);


}