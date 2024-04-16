//src/main/java/com/CiST2932/SRSProject/Controller/NewHireInfoController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.NewEmployeeDTO;
import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Service.NewHireInfoService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newhireinfo")
public class NewHireInfoController {

    @Autowired
    private NewHireInfoService newHireInfoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<NewHireInfo>> getAllNewHireInfo() {
        List<NewHireInfo> newHireInfoList = newHireInfoService.findAll();
        return ResponseEntity.ok(newHireInfoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewHireInfo> getNewHireInfoById(@PathVariable int id) {
        return newHireInfoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<NewHireInfo> createNewHireInfo(@RequestBody NewHireInfo newHireInfo) {
        // Save the new hire information to the database
        NewHireInfo savedNewHireInfo = newHireInfoService.save(newHireInfo);

        // Return a response entity with the saved object and a status of CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNewHireInfo);
    }    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewHireInfo(@PathVariable int id) {
        if (newHireInfoService.findById(id).isPresent()) {
            newHireInfoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{mentorId}/mentees")
    public ResponseEntity<List<NewHireInfo>> getMenteesByMentor(@PathVariable int mentorId) {
        List<NewHireInfo> mentees = newHireInfoService.findMenteesByMentorId(mentorId);
        return ResponseEntity.ok(mentees);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<NewHireInfo>> getAllMentors() {
        List<NewHireInfo> mentors = newHireInfoService.findAllMentors();
        return ResponseEntity.ok(mentors);
    }
    @GetMapping("/unassigned-mentees")
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
    
    @PostMapping("/newemployee")
    public ResponseEntity<NewHireInfo> createNewHireInfo(@RequestBody NewEmployeeDTO newEmployeeDTO) {
        NewHireInfo newHireInfo = newHireInfoService.createNewHireInfo(newEmployeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHireInfo);
    }
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<NewHireInfo> updateOrCreateEmployee(@PathVariable int id, @RequestBody NewEmployeeDTO employeeDTO) {
        try {
            NewHireInfo updatedInfo = newHireInfoService.updateOrCreateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updatedInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
}



}
