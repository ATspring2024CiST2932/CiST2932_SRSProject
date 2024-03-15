// src/main/java/com/CiST2932/SRSProject/Controller/PeerCodingTasksController.java

package com.CiST2932.SRSProject.Controller;

import com.CiST2932.SRSProject.Domain.PeerCodingTasks;
import com.CiST2932.SRSProject.Service.PeerCodingTasksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peercodingtasks")
public class PeerCodingTasksController {

    @Autowired
    private PeerCodingTasksService peerCodingTasksService;

    @GetMapping
    public List<PeerCodingTasks> getAllPeerCodingTasks() {
        return peerCodingTasksService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeerCodingTasks> getPeerCodingTasksById(@PathVariable int id) {
        return peerCodingTasksService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PeerCodingTasks createPeerCodingTasks(@RequestBody PeerCodingTasks peerCodingTasks) {
        return peerCodingTasksService.save(peerCodingTasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeerCodingTasks> updatePeerCodingTasks(@PathVariable int id, @RequestBody PeerCodingTasks peerCodingTasks) {
        if (peerCodingTasksService.findById(id).isPresent()) {
            peerCodingTasks.setTaskId(id);
            return ResponseEntity.ok(peerCodingTasksService.save(peerCodingTasks));
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

    @GetMapping("/tasks/{employeeId}")
    public ResponseEntity<List<PeerCodingTasks>> getTasksByEmployeeId(@PathVariable int employeeId) {
        List<PeerCodingTasks> tasks = peerCodingTasksService.findByEmployeeID(employeeId);
        return ResponseEntity.ok(tasks);
    }    

}
