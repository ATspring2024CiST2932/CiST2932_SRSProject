// src/main/java/com/CiST2932/SRSProject/Repository/NewHireInfoRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.NewHireInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NewHireInfoRepository extends JpaRepository<NewHireInfo, Integer> {
    @Query("SELECT ma.mentee FROM MentorAssignments ma WHERE ma.mentor.employeeId = :mentorId")
    List<NewHireInfo> findMenteesByMentorId(@Param("mentorId") int mentorId);

}
