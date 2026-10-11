package model;

import java.sql.Timestamp;

public class MentorshipRequest {

    private int requestId;
    private int studentId;
    private int alumniId;
    private String message;
    private String status;
    private Timestamp createdAt;

    // Default constructor
    public MentorshipRequest() {
        this.status = "PENDING";
    }

    // Parameterized constructor
    public MentorshipRequest(int requestId, int studentId,
                             int alumniId, String message,
                             String status, Timestamp createdAt) {

        this.requestId = requestId;
        this.studentId = studentId;
        this.alumniId = alumniId;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(int alumniId) {
        this.alumniId = alumniId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}