// src/main/java/com/CiST2932/SRSProject/Controller/PeerCodingTasksController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.CreatePeerCodingTasksDTO;
import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Domain.TaskAssigneeDTO;
import com.CiST2932.SRSProject.Domain.TaskWithAssigneeDTO;
import com.CiST2932.SRSProject.Domain.UpdatePeerCodingTasksDTO;
import com.CiST2932.SRSProject.Service.PeerCodingTasksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/peercodingtasks")
public class PeerCodingTasksController {

    @Autowired
    private PeerCodingTasksService peerCodingTasksService;

    @GetMapping
    public List<TaskWithAssigneeDTO> getAllPeerCodingTasks() {
        return peerCodingTasksService.findAllTasksWithAssigneeName();
    }    
    
    @GetMapping("/{id}")
    public ResponseEntity<PeerCodingTasks> getPeerCodingTasksById(@PathVariable int id) {
        return peerCodingTasksService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PeerCodingTasks> createPeerCodingTask(@RequestBody CreatePeerCodingTasksDTO createDto) {
        PeerCodingTasks newTask = peerCodingTasksService.createTaskWithAssignee(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }    

    @PutMapping("/{id}")
    public ResponseEntity<PeerCodingTasks> updatePeerCodingTasks(@PathVariable int id, @RequestBody UpdatePeerCodingTasksDTO updateDto) {
        Optional<PeerCodingTasks> existingTask = peerCodingTasksService.findById(id);
        if (existingTask.isPresent()) {
            PeerCodingTasks updatedTask = peerCodingTasksService.updateTaskWithAssignee(existingTask.get(), updateDto);
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeerCodingTasks(@PathVariable int id) {
        if (peerCodingTasksService.findById(id).isPresent()) {
            peerCodingTasksService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/mentor/{mentorId}/tasks")
    public ResponseEntity<List<PeerCodingTasks>> getTasksByMentorAndMentees(@PathVariable int mentorId) {
        List<PeerCodingTasks> tasks = peerCodingTasksService.findTasksByMentorAndMentees(mentorId);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }
}
