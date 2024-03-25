// src/main/java/com/CiST2932/SRSProject/Controller/MentorAssignmentsController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Domain.MentorAssignmentsDTO;
import com.CiST2932.SRSProject.Domain.TaskWithAssigneeDTO;
import com.CiST2932.SRSProject.Service.MentorAssignmentsService;
import com.CiST2932.SRSProject.Service.PeerCodingTasksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentorassignments")
public class MentorAssignmentsController {

    @Autowired
    private MentorAssignmentsService mentorAssignmentsService;
    private final PeerCodingTasksService peerCodingTasksService;

    @GetMapping
    public List<MentorAssignments> getAllMentorAssignments() {
        return mentorAssignmentsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorAssignments> getMentorAssignmentsById(@PathVariable int id) {
        return mentorAssignmentsService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MentorAssignments createMentorAssignments(@RequestBody MentorAssignmentsDTO dto) {
        MentorAssignments mentorAssignments = new MentorAssignments();
        mentorAssignments.setMentor(mentorAssignmentsService.findNewHireInfoById(dto.getMentorId()));
        mentorAssignments.setMentee(mentorAssignmentsService.findNewHireInfoById(dto.getMenteeId()));
        return mentorAssignmentsService.save(mentorAssignments);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MentorAssignmentsDTO> updateMentorAssignments(
        @PathVariable int id, 
        @RequestBody MentorAssignmentsDTO dto) {
        
        MentorAssignments updatedMentorAssignments = mentorAssignmentsService.update(id, dto);
        MentorAssignmentsDTO updatedDto = MentorAssignmentsDTO.convertToDto(updatedMentorAssignments);
        
        return ResponseEntity.ok(updatedDto);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorAssignments(@PathVariable int id) {
        if (mentorAssignmentsService.findById(id).isPresent()) {
            mentorAssignmentsService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/mentor/{mentorEmployeeId}")
    public ResponseEntity<List<MentorAssignments>> getMentorAssignmentsByMentorEmployeeId(@PathVariable int mentorEmployeeId) {
        List<MentorAssignments> mentorAssignments = mentorAssignmentsService.findByMentorEmployeeId(mentorEmployeeId);
        if (mentorAssignments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mentorAssignments);
    }

    public MentorAssignmentsController(PeerCodingTasksService peerCodingTasksService) {
        this.peerCodingTasksService = peerCodingTasksService;
    }

    @GetMapping("/mentor/{mentorId}/tasks")
    public ResponseEntity<List<TaskWithAssigneeDTO>> getTasksByMentorAndMentees(@PathVariable int mentorId) {
        List<TaskWithAssigneeDTO> tasks = peerCodingTasksService.getTasksByMentorAndMentees(mentorId);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }



}
