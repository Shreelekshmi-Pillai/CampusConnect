package model;

import java.sql.Timestamp;

public class MentorshipSession {

    private int sessionId;
    private int requestId;
    private int studentId;
    private int alumniId;
    private String sessionTitle;
    private Timestamp sessionTime;
    private String meetingLink;
    private String status;

    public MentorshipSession() {
        this.status = "SCHEDULED";
    }

    public MentorshipSession(int sessionId, int requestId,
            int studentId, int alumniId, String sessionTitle,
            Timestamp sessionTime, String meetingLink, String status) {

        this.sessionId = sessionId;
        this.requestId = requestId;
        this.studentId = studentId;
        this.alumniId = alumniId;
        this.sessionTitle = sessionTitle;
        this.sessionTime = sessionTime;
        this.meetingLink = meetingLink;
        this.status = status;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public Timestamp getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Timestamp sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}