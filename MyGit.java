/*
*   ADK
*/

package gitr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyGit {
    
    String name;
    List<Course> courses;
    List<Assignment> assignemnts;
    Map<String, String> cookies;
    
    public MyGit(String name, Course[] courses, Map<String, String> cookies){
        this.name = name;
        this.courses = new ArrayList<>();
        this.assignemnts = new ArrayList<>();
        
        for( Course c: courses){
            this.courses.add(c);
            if(c.hasAssignments()){
                for( Assignment a: c.getAssignments()){
                    this.assignemnts.add(a);
                }
            }
        }            
    }
    
    public String getMyName(){
        return this.name;
    }
   
    public List<Course> getCourses(){
        return this.courses;
    }
    
    public List<Assignment> getAssignment(){
        return this.assignemnts;
    }
}
    