package model;

public class Alumni extends User {

    private String registrationNumber;
    private String department;
    private String degree;
    private int graduationYear;
    private String company;
    private String designation;
    private String industry;
    private String expertise;
    private String verificationStatus;

    public Alumni(
            int userId,
            String name,
            String email,
            String passwordHash,
            String role,
            String status,
            String registrationNumber,
            String department,
            String degree,
            int graduationYear,
            String company,
            String designation,
            String industry,
            String expertise,
            String verificationStatus
    ) {
        super(userId, name, email, passwordHash, role, status);

        this.registrationNumber = registrationNumber;
        this.department = department;
        this.degree = degree;
        this.graduationYear = graduationYear;
        this.company = company;
        this.designation = designation;
        this.industry = industry;
        this.expertise = expertise;
        this.verificationStatus = verificationStatus;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public boolean isVerified() {
        return "VERIFIED".equalsIgnoreCase(verificationStatus);
    }
}