package model;

public class Student extends User {

    private String department;
    private int semester;
    private String skills;
    private String careerGoal;

    public Student() {
        super();
        setRole("STUDENT");
    }

    public Student(int userId, String name, String email,
                   String passwordHash, String status,
                   String department, int semester,
                   String skills, String careerGoal) {

        super(userId, name, email, passwordHash,
              "STUDENT", status);

        this.department = department;
        this.semester = semester;
        this.skills = skills;
        this.careerGoal = careerGoal;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }
}