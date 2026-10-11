package model;

public class CareerRoadmap {

    private int roadmapId;
    private int studentId;
    private String careerGoal;
    private String requiredSkills;
    private String learningSteps;
    private String recommendedResources;
    private String status;

    public CareerRoadmap() {
        this.status = "IN_PROGRESS";
    }

    public CareerRoadmap(int roadmapId, int studentId,
            String careerGoal, String requiredSkills,
            String learningSteps, String recommendedResources,
            String status) {

        this.roadmapId = roadmapId;
        this.studentId = studentId;
        this.careerGoal = careerGoal;
        this.requiredSkills = requiredSkills;
        this.learningSteps = learningSteps;
        this.recommendedResources = recommendedResources;
        this.status = status;
    }

    public int getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(int roadmapId) {
        this.roadmapId = roadmapId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getLearningSteps() {
        return learningSteps;
    }

    public void setLearningSteps(String learningSteps) {
        this.learningSteps = learningSteps;
    }

    public String getRecommendedResources() {
        return recommendedResources;
    }

    public void setRecommendedResources(String recommendedResources) {
        this.recommendedResources = recommendedResources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}