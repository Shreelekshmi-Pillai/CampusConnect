package service;

import dao.AlumniDAO;
import dao.MentorshipDAO;
import dao.OpportunityDAO;
import java.sql.Date;
import java.util.List;

public class AlumniService {
    private final AlumniDAO alumniDAO=new AlumniDAO();
    private final MentorshipDAO mentorshipDAO=new MentorshipDAO();
    private final OpportunityDAO opportunityDAO=new OpportunityDAO();
    public boolean saveProfile(int userId,int graduationYear,String company,String designation,String industry,String expertise){if(userId<=0||graduationYear<1950||company==null||company.isBlank())return false;return alumniDAO.saveProfile(userId,graduationYear,company.trim(),designation,industry,expertise);}
    public List<String[]> viewMentorshipRequests(int alumniId){return mentorshipDAO.getRequestsForAlumni(alumniId);}
    public boolean acceptRequest(int requestId){return mentorshipDAO.updateRequestStatus(requestId,"ACCEPTED");}
    public boolean rejectRequest(int requestId){return mentorshipDAO.updateRequestStatus(requestId,"REJECTED");}
    public boolean postOpportunity(int alumniId,String title,String company,String type,String description,String eligibility,Date deadline,String applicationUrl){if(alumniId<=0||title==null||title.isBlank()||company==null||company.isBlank()||type==null)return false;return opportunityDAO.addOpportunity(alumniId,title.trim(),company.trim(),type,description,eligibility,deadline,applicationUrl);}
    public List<String[]> viewOpportunities(){return opportunityDAO.getAllOpportunities();}
}
