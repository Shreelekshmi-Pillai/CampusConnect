package service;

import dao.UserDAO;
import dao.AlumniDAO;
import dao.MentorshipDAO;
import java.util.*;

/** Optional Level 3 basic dashboard counts. */
public class AnalyticsService {
    private final UserDAO userDAO=new UserDAO();
    private final AlumniDAO alumniDAO=new AlumniDAO();
    private final MentorshipDAO mentorshipDAO=new MentorshipDAO();
    public Map<String,Integer> getBasicCounts(){
        Map<String,Integer> counts=new LinkedHashMap<>();
        List<Map<String,Object>> users=userDAO.getAllUsers();
        int students=0,alumni=0,admins=0;for(Map<String,Object> u:users){String role=String.valueOf(u.get("role"));if("STUDENT".equalsIgnoreCase(role))students++;else if("ALUMNI".equalsIgnoreCase(role))alumni++;else if("ADMIN".equalsIgnoreCase(role))admins++;}
        counts.put("users",users.size());counts.put("students",students);counts.put("alumni",alumni);counts.put("admins",admins);counts.put("verified_alumni",alumniDAO.getAllAlumni().size());
        return counts;
    }
}
