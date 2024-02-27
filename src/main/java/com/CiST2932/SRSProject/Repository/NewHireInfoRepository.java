// src/main/java/com/CiST2932/SRSProject/Repository/NewHireInfoRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.NewHireInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewHireInfoRepository extends JpaRepository<NewHireInfo, Integer> {
    List<NewHireInfo> findMenteesByMentorEmployeeId(int mentorEmployeeId);
    List<NewHireInfo> findByName(String name);
    List<NewHireInfo> findByEmploymentType(String employmentType);
    List<NewHireInfo> findByMentor(boolean isMentor);
    List<NewHireInfo> findMenteesByMentorId(int mentorId);
}

