//src/main/java/com/CiST2932/SRSProject/Service/PeerCodingTasksService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Repository.PeerCodingTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeerCodingTasksService {

    @Autowired
    private PeerCodingTasksRepository peerCodingTasksRepository;

    public List<PeerCodingTasks> findAll() {
        return peerCodingTasksRepository.findAll();
    }

    public Optional<PeerCodingTasks> findById(int id) {
        return peerCodingTasksRepository.findById(id);
    }

    public PeerCodingTasks save(PeerCodingTasks peerCodingTasks) {
        return peerCodingTasksRepository.save(peerCodingTasks);
    }

    public void deleteById(int id) {
        peerCodingTasksRepository.deleteById(id);
    }
}
