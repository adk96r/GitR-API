/*
*   ADK
*/

package gitr;

import java.io.IOException;
import java.util.Map;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class GitR {

    static Document homepage = null;
    static Map<String, String> cookies = null;
    static int[] cstatus;
    static Course[] courses;
    static boolean status = false;
    static MyGit myGit;
    
    
    private static boolean check(int cstatus[]){
        
        /*
        * Check whether all the threads have completed or not.
        */
        
        int i = 0;
        for ( int k : cstatus ){
            i+=k;
        }
        return ((int)i/cstatus.length) == 1;
    }
    
    private static void show(){
        
        /*
        *   Just for debugging purposes . show() will print all the details
        *   for each course .
        */
        
        for (Course each : courses){
            System.out.println(each.getTitle());            
            if(each.hasAssignments()){
                System.out.println("This course has" + each.getAssignments().size() + " assignments.");
                for(int i = 0;i<each.getAssignments().size();i++){
                    System.out.println(each.getAssignments().get(i).getCourseName() + "  " + each.getAssignments().get(i).getDetails());
                }
            }
            System.out.println("Attendance for this course is " + each.getAttendance());
        }
    }
    
    @SuppressWarnings("empty-statement")
    private static MyGit getCourses(){
        
        /*
        *   Main worker !
        *   To know how this works , head to my Github Repo 
        *   https://github.com/Adk0096/GitR-API
        *
        *   A lot of code here wil make proper sense only if you 
        *   head to the X-Learn Page and see the HTML code yourself .
        *   Most of the content there is organised inside divs with
        *   proper classes .
        */
        
        String name = homepage.getElementsByClass("logininfo").get(0).child(0).text().split("-")[0];
        
        Elements courseContainers = homepage.getElementsByClass("box coursebox courseboxcontent boxaligncenter boxwidthwide");
        
        cstatus = new int[courseContainers.size()];
        courses = new Course[courseContainers.size()];
        
        
        for(int i = 0; i < courseContainers.size(); i++){
            new Thread(new CourseRetriever(courseContainers.get(i).child(0).child(0).attr("href"),cookies,cstatus,i,courses)).start();
        }
        
        while(!check(cstatus));
        
        System.out.println("Done Threading");
        Long t = System.currentTimeMillis();
        
        return new MyGit(name,courses,cookies);
    }
       
    public static int login(String username, String password) throws IOException{
                
        String link = "http://xlearn.gitam.edu/moodle/login/index.php";
        Response r = Jsoup.connect(link)
                            .data("username",username)
                            .data("password",password)
                            .method(Method.POST)
                            .execute();
        
        if("Overview of my courses".equals(r.parse().title())){
            // Logged in successfully !
            System.out.println("Login Successful!");
            homepage = r.parse();
            cookies = r.cookies();
            myGit = getCourses();
            status = true;
            return 1;
        }
        else{
            System.out.println("Login Failed.");
            return 0;
        }
    }
    public boolean getStatus(){return status;}
    public MyGit getMyGit(){return myGit;}    
    
    public static void main(String[] args) throws IOException {
        
        String username = "";
        String password = "";
        int status = login(username, password);
        if(status == 1)show();
    }
    
}
