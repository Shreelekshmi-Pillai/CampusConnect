package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Optional Level 3 starter. This is rule-based feedback, not an AI model. */
public class ResumeFeedbackService {
    public List<String> reviewText(String resumeText){
        List<String> feedback=new ArrayList<>();
        if(resumeText==null||resumeText.isBlank()){feedback.add("Paste resume text to receive feedback.");return feedback;}
        String text=resumeText.toLowerCase(Locale.ROOT);
        if(!text.contains("education"))feedback.add("Consider adding an Education section.");
        if(!text.contains("project"))feedback.add("Add a Projects section with your role and results.");
        if(!text.contains("skill"))feedback.add("Add a Skills section relevant to the roles you want.");
        if(!text.contains("experience"))feedback.add("If applicable, include internships, volunteering, or work experience.");
        if(feedback.isEmpty())feedback.add("Basic section check passed. Review spelling, dates, and measurable achievements manually.");
        return feedback;
    }
}
