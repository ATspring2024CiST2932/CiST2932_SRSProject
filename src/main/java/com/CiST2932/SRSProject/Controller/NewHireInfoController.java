//src/main/java/com/CiST2932/SRSProject/Controller/NewHireInfoController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Service.NewHireInfoService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/newhireinfo")
public class NewHireInfoController {

    @Autowired
    private NewHireInfoService newHireInfoService;

    @GetMapping
    public ResponseEntity<List<NewHireInfo>> findAll() {
        List<NewHireInfo> newHireInfo = newHireInfoService.findAll();
        return ResponseEntity.ok(newHireInfo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllNewHireInfoWithDetails(@PathVariable int id) {
        Optional<NewHireInfo> newHireInfo = newHireInfoService.findById(id);
        if (newHireInfo.isPresent() && newHireInfo.get().getDeveloper() != null) {
            return ResponseEntity.ok(newHireInfo);
        } else {
            // Handle the case where the associated user record is missing
            return ResponseEntity.notFound().build();
        }
    }
    
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewHireInfo(@PathVariable int id) {
        try {
            newHireInfoService.deleteNewHireInfoAndRelatedData(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete new hire info: " + e.getMessage());
        }
    }

    // fetch mentees by mentor id
    @GetMapping("/{mentorId}/mentees")
    public ResponseEntity<List<NewHireInfo>> getMenteesByMentor(@PathVariable int mentorId) {
        List<NewHireInfo> mentees = newHireInfoService.findMenteesByMentorId(mentorId);
        return ResponseEntity.ok(mentees);
    }
    // fetch mentor by mentee id
    @GetMapping("/{menteeId}/mentor")
    public ResponseEntity<List<NewHireInfo>> getMentorByMenteeId(@PathVariable int menteeId) {
        List<NewHireInfo> mentor = newHireInfoService.findMentorByMenteeId(menteeId);
        return ResponseEntity.ok(mentor);
    }
    // fetch list of mentors
    @GetMapping("/fetchMentors")
    public ResponseEntity<List<NewHireInfo>> getAllMentors() {
        List<NewHireInfo> mentors = newHireInfoService.findAllMentors();
        return ResponseEntity.ok(mentors);
    }
    // fetch list of unassigned mentees
    @GetMapping("/fetchMentees")
    public ResponseEntity<List<NewHireInfo>> getUnassignedMentees() {
        List<NewHireInfo> unassignedMentees = newHireInfoService.findUnassignedMentees();
        if (unassignedMentees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unassignedMentees);
    }  
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllNames() {
        List<String> names = newHireInfoService.findAllNames();
        return ResponseEntity.ok(names);
    }
    
    @PostMapping
    public ResponseEntity<NewHireInfo> createNewHireInfo(@RequestBody NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo newHireInfo = newHireInfoService.createNewHireInfo(newEmployeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHireInfo);
    }

    @PutMapping("/{id}")
    //updateOrCreateEmployee
    public ResponseEntity<NewHireInfo> updateOrCreateEmployee(@PathVariable int id, @RequestBody NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo newHireInfo = newHireInfoService.updateOrCreateEmployee(id, newEmployeeDTO);
        return ResponseEntity.ok(newHireInfo);
    }



}