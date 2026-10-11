package model;

public class ExperiencePost {

    private int postId;
    private int alumniId;
    private String title;
    private String content;
    private String category;
    private String company;
    private String postDate;
    private String status;

    public ExperiencePost() {
        this.status = "PUBLISHED";
    }

    public ExperiencePost(int postId, int alumniId,
            String title, String content, String category,
            String company, String postDate, String status) {

        this.postId = postId;
        this.alumniId = alumniId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.company = company;
        this.postDate = postDate;
        this.status = status;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(int alumniId) {
        this.alumniId = alumniId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}