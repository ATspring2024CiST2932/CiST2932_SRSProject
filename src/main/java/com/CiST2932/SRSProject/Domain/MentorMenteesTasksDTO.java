// src/
// For listing mentees and tasks assigned to a mentor:

package com.CiST2932.SRSProject.Domain;

import java.util.List;
import java.util.Map;

public class MentorMenteesTasksDTO {
    private String mentorName;
    private List<String> menteeNames;
    private List<TaskDTO> mentorTasks;
    private Map<String, List<TaskDTO>> menteeTasks; // Key: Mentee Name, Value: List of Tasks

    // Getter for mentorName
    public String getMentorName() {
        return mentorName;
    }
    // Setter for mentorName
    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }
    // Getter for menteeNames
    public List<String> getMenteeNames() {
        return menteeNames;
    } 
    // Setter for menteeNames
    public void setMenteeNames(List<String> menteeNames) {
        this.menteeNames = menteeNames;
    }
    // Getter for mentorTasks
    public List<TaskDTO> getMentorTasks() {
        return mentorTasks;
    }
    // Setter for mentorTasks
    public void setMentorTasks(List<TaskDTO> mentorTasks) {
        this.mentorTasks = mentorTasks;
    }
    // Getter for menteeTasks
    public Map<String, List<TaskDTO>> getMenteeTasks() {
        return menteeTasks;
    }
    // Setter for menteeTasks
    public void setMenteeTasks(Map<String, List<TaskDTO>> menteeTasks) {
        this.menteeTasks = menteeTasks;
    }
      
}
