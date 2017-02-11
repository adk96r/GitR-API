package gitr;

import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CourseRetriever implements Runnable{

    String courseLink;
    Map<String, String> cookies;
    int[] cstatus;
    int index;
    Course[] courses;
    boolean gotData = false;
    
    public CourseRetriever(String courseLink, Map<String, String> cookies, int[] cstatus, int index, Course[] courses){
        this.courseLink = courseLink;
        this.cookies = cookies;
        this.cstatus = cstatus;
        this.index = index;
        this.courses = courses;
    }
    
    void getData() throws Exception{


        Document coursePage = Jsoup.connect(courseLink)
                                    .cookies(cookies)
                                    .get();

        String courseName = coursePage.getElementsByClass("headermain").get(0).text();

        String attendancePage = coursePage.getElementsByClass("activity attforblock").get(0).child(0).attr("href");
        String courseId = this.courseLink.split("id=")[1];
        System.out.println("Id: " + courseId);

        AssignmentRetriever asr = new AssignmentRetriever(courseId,cookies);
        AttendanceRetriever ar = new AttendanceRetriever(attendancePage,cookies);
        ResourceRetriever rr = new ResourceRetriever(coursePage);

        new Thread(ar).start();
        new Thread(rr).start();
        new Thread(asr).start();



        while(ar.getAttendance() < 0 || !rr.getStat() || !asr.gotAssignments()){
            Thread.sleep(1000);
        }

        System.out.println("Got" + coursePage.title());
        courses[index] = new Course(courseName,ar.getAttendance(),ar.getAttendanceData(), asr.getAssignments() ,rr.getResouces());
        cstatus[index] = 1;
        gotData = true;


    }
    
    @Override
    public void run(){
        while(!this.gotData){
            try{
                getData();
            }
            catch(Exception e){System.out.println("Error " + e);}
        }
    }
    
}
