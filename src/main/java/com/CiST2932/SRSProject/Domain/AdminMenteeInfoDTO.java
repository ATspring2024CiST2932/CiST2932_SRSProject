// src/main/java/com
// For an administrator viewing a mentee's mentor and tasks:

package com.CiST2932.SRSProject.Domain;

import java.util.List;

public class AdminMenteeInfoDTO {
    private String menteeName;
    private String mentorName;
    private List<TaskDTO> tasks;

    // Getter for menteeName
    public String getMenteeName() {
        return menteeName;
    }
    // Setter for menteeName
    public void setMenteeName(String menteeName) {
        this.menteeName = menteeName;
    }
    // Getter for mentorName
    public String getMentorName() {
        return mentorName;
    }
    // Setter for mentorName
    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
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

