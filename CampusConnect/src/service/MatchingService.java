package service;

import dao.AlumniDAO;
import java.util.*;

public class MatchingService {
    private final AlumniDAO alumniDAO=new AlumniDAO();
    // Rule-based starter: finds alumni whose company/designation/industry/expertise text matches student skills or career goal.
    public List<String[]> findMatches(String skills,String careerGoal){
        Set<String> terms=new LinkedHashSet<>(); addTerms(terms,skills); addTerms(terms,careerGoal);
        List<String[]> all=alumniDAO.getAllAlumni(); List<String[]> matches=new ArrayList<>();
        for(String[] a:all){String searchable=String.join(" ",a).toLowerCase(Locale.ROOT);for(String t:terms)if(searchable.contains(t.toLowerCase(Locale.ROOT))){matches.add(a);break;}}
        return matches;
    }
    private void addTerms(Set<String> terms,String value){if(value!=null)for(String part:value.split("[,;/ ]+")){String t=part.trim();if(t.length()>2)terms.add(t);}}
}
