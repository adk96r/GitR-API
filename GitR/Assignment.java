package gitr;

public class Assignment {
    
    private String courseName;
    private String details;
    private String link;
    
    Assignment(String courseName, String details, String link){
        this.courseName = courseName;
        this.details = details;
        this.link = link;
    }
    
    String getCourseName(){return courseName;}
    String getDetails(){return details;}
    String getlink(){return link;}
    
}
