package service;

import dao.MentorshipDAO;
import dao.NotificationDAO;
import java.util.List;

public class MentorshipService {
    private final MentorshipDAO mentorshipDAO=new MentorshipDAO();
    private final NotificationDAO notificationDAO=new NotificationDAO();
    public boolean sendRequest(int studentId,int alumniId,String message){return mentorshipDAO.sendRequest(studentId,alumniId,message);}
    public List<String[]> getStudentRequests(int studentId){return mentorshipDAO.getRequestsForStudent(studentId);}
    public List<String[]> getAlumniRequests(int alumniId){return mentorshipDAO.getRequestsForAlumni(alumniId);}
    public boolean respondToRequest(int requestId,int alumniId,String decision){
        // Verify ownership before changing status.
        List<String[]> requests=mentorshipDAO.getRequestsForAlumni(alumniId);
        boolean owned=false; for(String[] r:requests) if(Integer.parseInt(r[0])==requestId){owned=true;break;}
        if(!owned||decision==null)return false;
        boolean ok=mentorshipDAO.updateRequestStatus(requestId,decision);
        if(ok) notificationDAO.createNotification(alumniId,"Mentorship request #"+requestId+" was "+decision.toLowerCase()+".");
        return ok;
    }
}
