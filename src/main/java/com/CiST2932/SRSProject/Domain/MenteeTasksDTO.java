// src/main/java/com/CiST2932/SRSProject/Domain/MenteeTasksDTO.java
// For listing tasks associated with a mentee:
package com.CiST2932.SRSProject.Domain;

import java.util.List;

public class MenteeTasksDTO {
    private String menteeName;
    private List<TaskDTO> tasks;

    // Getter for menteeName
    public String getMenteeName() {
        return menteeName;
    }
    // Setter for menteeName
    public void setMenteeName(String menteeName) {
        this.menteeName = menteeName;
    }
    // Getter for tasks
    public List<TaskDTO> getTasks() {
        return tasks;
    }
    // Setter for tasks
    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
    
}

