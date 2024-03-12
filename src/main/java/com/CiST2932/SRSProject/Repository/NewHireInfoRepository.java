// src/main/java/com/CiST2932/SRSProject/Repository/NewHireInfoRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.NewHireInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewHireInfoRepository extends JpaRepository<NewHireInfo, Integer> {
    // List<NewHireInfo> findMenteesByMentorEmployeeId(int mentorEmployeeId);
    List<NewHireInfo> findByName(String name);
    List<NewHireInfo> findByEmploymentType(String employmentType);
    List<NewHireInfo> findByMentor(boolean isMentor);

    // @Query("SELECT n FROM NewHireInfo n WHERE n.mentorAssignment.mentorId = :mentorId")
    // List<NewHireInfo> findMenteesByMentorId(int mentorId);    

    // @Query("SELECT n FROM NewHireInfo n WHERE n.mentorAssignment.mentorId = :mentorId")
    // List<NewHireInfo> findMenteesByMentorEmployeeId(int mentorId);

    @Query("SELECT DISTINCT e FROM NewHireInfo e LEFT JOIN MentorAssignments mma ON e.employeeId = mma.mentorId WHERE e.mentor = true OR mma.mentorId IS NOT NULL")
    List<NewHireInfo> findDistinctMentors();

    // @Query("SELECT e FROM NewHireInfo e JOIN MentorAssignments mma ON e.employeeId = mma.menteeId WHERE mma.mentorId = :mentorId")
    // List<NewHireInfo> findMenteesByMentorId(@Param("mentorId") int mentorId);

    @Query("SELECT new com.CiST2932.SRSProject.Domain.NewHireInfoDto(n.employeeId, n.name, n.employmentType) " +
    "FROM NewHireInfo n JOIN MentorAssignments m ON n.employeeId = m.menteeId " +
    "WHERE m.mentorId = :mentorId")
    List<NewHireInfoDto> findMenteesByMentorId(@Param("mentorId") int mentorId);


    @Query("SELECT e FROM NewHireInfo e WHERE e.mentor = false")
    List<NewHireInfo> findAllMentees();

    @Query("SELECT new com.CiST2932.SRSProject.Domain.NewHireInfoDto(n.employeeId, n.name, n.employmentType) " +
       "FROM NewHireInfo n JOIN MentorAssignments m ON n.employeeId = m.menteeId")
    List<NewHireInfoDto> findAllMenteesDto();


}

