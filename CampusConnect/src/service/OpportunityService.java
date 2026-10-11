package service;

import dao.OpportunityDAO;
import java.sql.Date;
import java.util.List;

public class OpportunityService {
    private final OpportunityDAO opportunityDAO=new OpportunityDAO();
    public List<String[]> getAllOpportunities(){return opportunityDAO.getAllOpportunities();}
    public List<String[]> searchOpportunities(String keyword){return opportunityDAO.searchOpportunities(keyword);}
    public boolean addOpportunity(int alumniId,String title,String company,String type,String description,String eligibility,Date deadline,String applicationUrl){return opportunityDAO.addOpportunity(alumniId,title,company,type,description,eligibility,deadline,applicationUrl);}
    public boolean saveOpportunity(int studentId,int opportunityId){return opportunityDAO.saveOpportunity(studentId,opportunityId);}
    public List<String[]> getSavedOpportunities(int studentId){return opportunityDAO.getSavedOpportunities(studentId);}
}
