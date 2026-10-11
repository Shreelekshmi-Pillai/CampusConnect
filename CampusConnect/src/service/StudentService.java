package service;

import dao.AlumniDAO;
import dao.MentorshipDAO;
import dao.OpportunityDAO;
import dao.StudentDAO;
import java.util.*;

public class StudentService {
    private final StudentDAO studentDAO=new StudentDAO();
    private final AlumniDAO alumniDAO=new AlumniDAO();
    private final MentorshipDAO mentorshipDAO=new MentorshipDAO();
    private final OpportunityDAO opportunityDAO=new OpportunityDAO();
    public Map<String,Object> getProfile(int userId){return studentDAO.getStudentProfile(userId);}
    public boolean saveProfile(int userId,String department,int semester,String skills,String careerGoal){if(userId<=0||department==null||department.isBlank()||semester<1)return false;return studentDAO.saveStudentProfile(userId,department.trim(),semester,skills,careerGoal);}
    public List<String[]> viewAlumni(){return alumniDAO.getAllAlumni();}
    public List<String[]> searchAlumni(String keyword){return alumniDAO.searchAlumni(keyword);}
    public boolean requestMentorship(int studentId,int alumniId,String message){if(studentId<=0||alumniId<=0)return false;return mentorshipDAO.sendRequest(studentId,alumniId,message==null?"":message.trim());}
    public List<String[]> viewMyRequests(int studentId){return mentorshipDAO.getRequestsForStudent(studentId);}
    public List<String[]> viewOpportunities(){return opportunityDAO.getAllOpportunities();}
    public List<String[]> searchOpportunities(String keyword){return opportunityDAO.searchOpportunities(keyword);}
    public boolean saveOpportunity(int studentId,int opportunityId){return opportunityDAO.saveOpportunity(studentId,opportunityId);}
    public List<String[]> viewSavedOpportunities(int studentId){return opportunityDAO.getSavedOpportunities(studentId);}
}
