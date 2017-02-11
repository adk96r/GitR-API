package gitr;

public class AttendanceData {
    String attDate;
    String attTime;
    String attStatus;
    
    public AttendanceData(String attDate, String attTime, String attStatus){
        this.attDate = attDate;
        this.attTime = attTime;
        this.attStatus = attStatus;
    }
    public String getDate(){return attDate;}
    public String getTime(){return attTime;}
    public String getStatus(){return attStatus;}
            
}
