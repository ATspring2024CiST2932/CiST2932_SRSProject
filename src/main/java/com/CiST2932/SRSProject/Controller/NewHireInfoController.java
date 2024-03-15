//src/main/java/com/CiST2932/SRSProject/Controller/NewHireInfoController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.NewHireInfo;
import com.CiST2932.SRSProject.Service.NewHireInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newhireinfo")
public class NewHireInfoController {

    @Autowired
    private NewHireInfoService newHireInfoService;

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
    public NewHireInfo createNewHireInfo(@RequestBody NewHireInfo newHireInfo) {
        return newHireInfoService.save(newHireInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewHireInfo> updateNewHireInfo(@PathVariable int id, @RequestBody NewHireInfo newHireInfo) {
        if (newHireInfoService.findById(id).isPresent()) {
            newHireInfo.setEmployeeId(id);
            return ResponseEntity.ok(newHireInfoService.save(newHireInfo));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewHireInfo(@PathVariable int id) {
        if (newHireInfoService.findById(id).isPresent()) {
            newHireInfoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
