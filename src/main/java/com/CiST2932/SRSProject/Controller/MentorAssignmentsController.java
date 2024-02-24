// src/main/java/com/CiST2932/SRSProject/Controller/MentorAssignmentsController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.MentorAssignments;
import com.CiST2932.SRSProject.Service.MentorAssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentorassignments")
public class MentorAssignmentsController {

    @Autowired
    private MentorAssignmentsService mentorAssignmentsService;

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
    public MentorAssignments createMentorAssignments(@RequestBody MentorAssignments mentorAssignments) {
        return mentorAssignmentsService.save(mentorAssignments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorAssignments> updateMentorAssignments(@PathVariable int id, @RequestBody MentorAssignments mentorAssignments) {
        if (mentorAssignmentsService.findById(id).isPresent()) {
            mentorAssignments.setAssignmentID(id);
            return ResponseEntity.ok(mentorAssignmentsService.save(mentorAssignments));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorAssignments(@PathVariable int id) {
        if (mentorAssignmentsService.findById(id).isPresent()) {
            mentorAssignmentsService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
