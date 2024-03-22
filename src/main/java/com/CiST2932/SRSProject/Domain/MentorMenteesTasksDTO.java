// src/
// For listing mentees and tasks assigned to a mentor:

import java.util.List;
import java.util.Map;

package

public class MentorMenteesTasksDTO {
    private String mentorName;
    private List<String> menteeNames;
    private List<TaskDTO> mentorTasks;
    private Map<String, List<TaskDTO>> menteeTasks; // Key: Mentee Name, Value: List of Tasks

    // Constructors, Getters, and Setters
}
