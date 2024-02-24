//src/main/java/com/CiST2932/SRSProject/Repository/PeerCodingTasksRepository.java

package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerCodingTasksRepository extends JpaRepository<PeerCodingTasks, Integer> {
    // Custom query methods
}