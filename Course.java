package gitr;

import java.util.List;

public class Course {
  
    private final String title;
    private final float attendance;
    private final List<Assignment> assignments;
    private final Content[] content;
    private final AttendanceData[] attendanceData;
    
    Course(String title, float attendance, AttendanceData[] attendanceData, List<Assignment> assignments, Content[] content){
        this.title = title;
        this.attendance = attendance;
        this.assignments = assignments;
        this.content = content;
        this.attendanceData = attendanceData;
    }
        
    public String getTitle(){ return this.title; }
    public boolean hasContent(){
        if(this.content.length == 0) return false;
        else return true;
    }
    public boolean hasAssignments(){
        if(this.assignments.size() == 0) return false;
        else return true;
    }    
    public float getAttendance(){ return this.attendance; }
    public AttendanceData[] getAttendanceData(){ return this.attendanceData; }
    public Content[] getContent(){
       return this.content; 
    }
    public List<Assignment> getAssignments(){ 
        return this.assignments;
    }
    
}
