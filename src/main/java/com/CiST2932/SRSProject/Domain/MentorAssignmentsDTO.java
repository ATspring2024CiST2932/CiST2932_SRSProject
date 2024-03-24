package com.CiST2932.SRSProject.Domain;

public class MentorAssignmentsDTO {
    private int assignmentId;
    private int mentorId;
    private int menteeId;

    public MentorAssignmentsDTO(int assignmentId, int mentorId, int menteeId) {
        this.assignmentId = assignmentId;
        this.mentorId = mentorId;
        this.menteeId = menteeId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }   

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }

    // Static method to convert MentorAssignments to MentorAssignmentsDTO
    public static MentorAssignmentsDTO convertToDto(MentorAssignments mentorAssignments) {
        return new MentorAssignmentsDTO(
            mentorAssignments.getAssignmentId(),
            mentorAssignments.getMentor().getEmployeeId(),
            mentorAssignments.getMentee().getEmployeeId()
        );
    }
}
