package model;

public class Opportunity {

    private int opportunityId;
    private int alumniId;
    private String title;
    private String company;
    private String description;
    private String opportunityType;
    private String location;
    private String applicationLink;
    private String deadline;
    private String status;

    public Opportunity() {
        this.status = "OPEN";
    }

    public Opportunity(int opportunityId, int alumniId,
            String title, String company, String description,
            String opportunityType, String location,
            String applicationLink, String deadline, String status) {

        this.opportunityId = opportunityId;
        this.alumniId = alumniId;
        this.title = title;
        this.company = company;
        this.description = description;
        this.opportunityType = opportunityType;
        this.location = location;
        this.applicationLink = applicationLink;
        this.deadline = deadline;
        this.status = status;
    }

    public int getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(int opportunityId) {
        this.opportunityId = opportunityId;
    }

    public int getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(int alumniId) {
        this.alumniId = alumniId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        this.opportunityType = opportunityType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApplicationLink() {
        return applicationLink;
    }

    public void setApplicationLink(String applicationLink) {
        this.applicationLink = applicationLink;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}