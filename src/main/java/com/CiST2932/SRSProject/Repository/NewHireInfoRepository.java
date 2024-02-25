// src/main/java/com/CiST2932/SRSProject/Repository/NewHireInfoRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.NewHireInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewHireInfoRepository extends JpaRepository<NewHireInfo, Integer> {
    List<NewHireInfo> findByName(String name);
    Optional<NewHireInfo> findByEmployeeId(int employeeId);
    List<NewHireInfo> findMenteesByMentorId(int mentorId);
}
