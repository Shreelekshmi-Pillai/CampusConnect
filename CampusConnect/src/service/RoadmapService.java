package service;

import dao.RoadmapDAO;
import java.util.List;
import java.util.Map;

public class RoadmapService {
    private final RoadmapDAO roadmapDAO=new RoadmapDAO();
    public List<Map<String,Object>> getAllRoadmaps(){return roadmapDAO.getAllRoadmaps();}
    public boolean addRoadmap(String title,String targetRole,String steps){return title!=null&&!title.isBlank()&&targetRole!=null&&!targetRole.isBlank()&&steps!=null&&!steps.isBlank()&&roadmapDAO.addRoadmap(title.trim(),targetRole.trim(),steps.trim());}
    public boolean updateRoadmap(int id,String title,String targetRole,String steps){return id>0&&roadmapDAO.updateRoadmap(id,title,targetRole,steps);}
}
