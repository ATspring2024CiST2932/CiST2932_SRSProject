// src/main/java/com/CiST2932/SRSProject/Domain/NewHireInfoDto.java
package com.CiST2932.SRSProject.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewHireInfoDTO {
    private int employeeId;
    private String name;
    private String employmentType;
    private boolean isMentor;

    public NewHireInfoDTO(int employeeId, String name, String employmentType, boolean isMentor) {
        this.employeeId = employeeId;
        this.name = name;
        this.employmentType = employmentType;
        this.isMentor = isMentor;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public boolean isMentor() {
        return isMentor;
    }

    public void setMentor(boolean isMentor) {
        this.isMentor = isMentor;
    }

    private static List<MentorAssignments> mentorAssignmentsList = new ArrayList<>();

    public static Optional<MentorAssignments> findById(int mentorId) {
        return mentorAssignmentsList.stream()
            .filter(mentorAssignment -> mentorAssignment.getId() == mentorId)
            .findFirst();
    }
}
