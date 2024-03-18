package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Domain.TaskWithAssigneeDTO;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Repository.PeerCodingTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeerCodingTasksService {

    @Autowired
    private PeerCodingTasksRepository peerCodingTasksRepository;

    @Autowired
    private NewHireInfoRepository newHireInfoRepository;  // Add this line

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

    public List<PeerCodingTasks> findByEmployeeId(int employeeId) {
        return peerCodingTasksRepository.findByEmployeeId(employeeId);
    }

    public NewHireInfo getAssigneeInfo(int employeeId) {
        return newHireInfoRepository.findById(employeeId).orElse(null);
    }

    public List<PeerCodingTasks> findTasksByMentorAndMentees(int mentorId) {
        return peerCodingTasksRepository.findTasksByMentorAndMentees(mentorId);
    }

    public List<TaskWithAssigneeDTO> getTasksByMentorAndMentees(int mentorId) {
        List<PeerCodingTasks> tasks = peerCodingTasksRepository.findTasksByMentorAndMentees(mentorId);
        List<TaskWithAssigneeDTO> taskWithAssigneeDTOs = new ArrayList<>();

        for (PeerCodingTasks task : tasks) {
            TaskWithAssigneeDTO dto = new TaskWithAssigneeDTO();
            dto.setTaskId(task.getTaskId());
            dto.setTaskUrl(task.getTaskUrl());
            dto.setTaskNumber(task.getTaskNumber());
            dto.setTaskType(task.getTaskType());
            dto.setTotalHours(task.getTotalHours());

            // Retrieve the name of the employee assigned to the task
            NewHireInfo assignee = newHireInfoRepository.findById(task.getEmployeeId()).orElse(null);
            if (assignee != null) {
                dto.setAssigneeName(assignee.getName());
            }

            taskWithAssigneeDTOs.add(dto);
        }

        return taskWithAssigneeDTOs;
    }
}
