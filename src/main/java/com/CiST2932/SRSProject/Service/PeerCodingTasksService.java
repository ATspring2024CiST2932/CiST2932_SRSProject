//src/main/java/com/CiST2932/SRSProject/Service/PeerCodingTasksService.java

package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Domain.TaskDTO;
import com.CiST2932.SRSProject.Repository.PeerCodingTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @SuppressWarnings("null")
    public PeerCodingTasks save(PeerCodingTasks peerCodingTasks) {
        return peerCodingTasksRepository.save(peerCodingTasks);
    }

    public void deleteById(int id) {
        peerCodingTasksRepository.deleteById(id);
    }

    public List<TaskDTO> getTasksByMentorId(int mentorId) {
    List<Object[]> results = peerCodingTasksRepository.findTasksByMentorId(mentorId);

    return results.stream().map(result -> {
        TaskDTO task = new TaskDTO();
        task.setTaskId((Integer) result[0]);
        task.setTaskUrl((String) result[1]);
        task.setTaskNumber((Integer) result[2]);
        task.setTaskType((String) result[3]);
        task.setEmployeeId((Integer) result[4]);
        return task;
    }).collect(Collectors.toList());
    }

    public List<PeerCodingTasks> findByEmployeeID(int employeeId) {
        return peerCodingTasksRepository.findByEmployeeId(employeeId);
    }
}

