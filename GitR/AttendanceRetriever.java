package gitr;

import java.io.IOException;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AttendanceRetriever implements Runnable{

    Document attendancePage;
    String attendancePageLink;
    Map<String, String> cookies;
    AttendanceData[] attendanceData;
    Float att = -1.0f;
            
    public AttendanceRetriever(String attendancePageLink, Map<String, String> cookies){
        this.attendancePageLink = attendancePageLink;
        this.cookies = cookies;
        this.att = -1.0f;
    }
    
    public Float getAttendance(){return this.att;}
    public AttendanceData[] getAttendanceData(){return this.attendanceData;}
    
    void fetchAttendance(){
        try{
            attendancePage = Jsoup.connect(attendancePageLink)
                    .cookies(cookies)
                    .get();

            
            this.att = new Float(attendancePage.getElementsByClass("list").get(0).child(0).child(3).child(1).child(0).text().split(" %")[0]);
            
            Element body = attendancePage.getElementsByClass("submissions").get(0).child(0);
            Elements bc = body.children();
            if(bc.size() > 1){
                attendanceData = new AttendanceData[bc.size()-1];
                
                for(int i = 1;i < bc.size();i++){
                    
                    attendanceData[i-1] = new AttendanceData(String.join(" ", bc.get(i).child(1).text().split("&nbsp;")),
                            bc.get(i).child(2).text(),
                            bc.get(i).child(4).text());
                }
            }else{
                attendanceData = new AttendanceData[0];
            }
            
        }catch(IOException | NumberFormatException e){
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        fetchAttendance();
    }
    
}
