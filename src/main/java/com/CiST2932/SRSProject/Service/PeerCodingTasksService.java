package com.CiST2932.SRSProject.Service;

import com.CiST2932.SRSProject.Domain.CreatePeerCodingTasksDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Domain.TaskDTO;
import com.CiST2932.SRSProject.Domain.TaskWithAssigneeDTO;
import com.CiST2932.SRSProject.Domain.UpdatePeerCodingTasksDTO;
import com.CiST2932.SRSProject.Repository.NewHireInfoRepository;
import com.CiST2932.SRSProject.Repository.PeerCodingTasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<PeerCodingTasks> getTasksByAssignee(int employeeId) {
        return peerCodingTasksRepository.findByAssigneeEmployeeId(employeeId);
    }

    public NewHireInfo getAssigneeInfo(int employeeId) {
        return newHireInfoRepository.findById(employeeId).orElse(null);
    }

    public List<PeerCodingTasks> findTasksByMentorAndMentees(int mentorId) {
        return peerCodingTasksRepository.findTasksByMentorAndMentees(mentorId);
    }
    
    public List<TaskWithAssigneeDTO> getTasksByMentorAndMentees(int mentorId) {
        List<PeerCodingTasks> tasks = peerCodingTasksRepository.findByAssigneeEmployeeId(mentorId);
        List<TaskWithAssigneeDTO> taskWithAssigneeDTOs = new ArrayList<>();
    
        for (PeerCodingTasks task : tasks) {
            TaskWithAssigneeDTO dto = new TaskWithAssigneeDTO();
            dto.setTaskId(task.getTaskId());
            dto.setTaskUrl(task.getTaskUrl());
            dto.setTaskNumber(task.getTaskNumber());
            dto.setTaskType(task.getTaskType());
            dto.setTotalHours(task.getTotalHours());
    
            // Retrieve the name of the employee assigned to the task
            NewHireInfo assignee = task.getAssignee();
            if (assignee != null) {
                dto.setAssigneeName(assignee.getName());
            }
    
            taskWithAssigneeDTOs.add(dto);
        }
    
        return taskWithAssigneeDTOs;
    }
    public List<TaskWithAssigneeDTO> findAllTasksWithAssigneeName() {
        return peerCodingTasksRepository.findAll().stream()
            .map(task -> new TaskWithAssigneeDTO(
                task.getTaskId(),
                task.getTaskUrl(),
                task.getTaskNumber(),
                task.getTaskType(),
                task.getTotalHours(),
                task.getAssigneeName() // Use the new method
            ))
            .collect(Collectors.toList());
    }   
    
    public PeerCodingTasks updateTaskWithAssignee(PeerCodingTasks task, UpdatePeerCodingTasksDTO updateDto) {
        // Update the task fields with the data from the DTO
        task.setTaskUrl(updateDto.getTaskUrl());
        task.setTaskNumber(updateDto.getTaskNumber());
        task.setTaskType(updateDto.getTaskType());
        task.setTotalHours(updateDto.getTotalHours());
        
        // Find the assignee by name and set it
        NewHireInfo assignee = newHireInfoRepository.findByName(updateDto.getAssigneeName())
            .orElseThrow(() -> new RuntimeException("Assignee not found"));
        task.setAssignee(assignee);

        // Save and return the updated task
        return peerCodingTasksRepository.save(task);
    }

    // public TaskDTO createTaskWithAssignee(CreatePeerCodingTasksDTO createDto) {
    //     // Find the assignee by name
    //     NewHireInfo assignee = newHireInfoRepository.findByName(createDto.getAssigneeName())
    //             .orElseThrow(() -> new RuntimeException("Assignee not found"));

    //     // Create a new task
    //     PeerCodingTasks task = new PeerCodingTasks();
    //     task.setTaskUrl(createDto.getTaskUrl());
    //     // Set other fields
    //     task.setAssignee(assignee);

    //     // Save the task
    //     PeerCodingTasks savedTask = peerCodingTasksRepository.save(task);

    //     // Convert to DTO and return
    //     return convertToDto(savedTask);
    // }

    public PeerCodingTasks createTaskWithAssignee(CreatePeerCodingTasksDTO createDto) {
        NewHireInfo assignee = newHireInfoRepository.findByName(createDto.getAssigneeName())
                .orElseThrow(() -> new RuntimeException("Assignee not found"));
    
        PeerCodingTasks newTask = new PeerCodingTasks();
        newTask.setTaskUrl(createDto.getTaskUrl());
        newTask.setTaskNumber(createDto.getTaskNumber());
        newTask.setTaskType(createDto.getTaskType());
        newTask.setTotalHours(createDto.getTotalHours());
        newTask.setAssignee(assignee);
    
        return peerCodingTasksRepository.save(newTask);
    }
    
    

    public TaskDTO convertToDto(PeerCodingTasks task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskUrl(task.getTaskUrl());
        // Set other fields
        if (task.getAssignee() != null) {
            dto.setAssigneeName(task.getAssignee().getName());
        }
        return dto;
    }

    public List<TaskDTO> findAllTasksWithAssignee() {
        List<PeerCodingTasks> tasks = peerCodingTasksRepository.findAll();
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
}
