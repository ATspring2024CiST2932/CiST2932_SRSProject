//src/main/java/com/CiST2932/SRSProject/Controller/MentorController.java

package com.CiST2932.SRSProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.CiST2932.SRSProject.Domain.MentorInfo;
import com.CiST2932.SRSProject.Service.MentorService;



@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @GetMapping("/{mentorId}")
    public ResponseEntity<MentorInfo> getMentorInfo(@PathVariable Long mentorId) {
        MentorInfo mentorInfo = mentorService.getMentorInfo(mentorId);
        return ResponseEntity.ok(mentorInfo);
    }
}

