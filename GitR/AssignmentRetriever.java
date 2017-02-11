package gitr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AssignmentRetriever implements Runnable{

    String assignmentLink;
    Map<String, String> cookies;
    boolean done;
    List<Assignment> a;
    
    
    public AssignmentRetriever(String assignmentLink, Map<String,String> cookies){
        this.assignmentLink = "http://xlearn.gitam.edu/moodle/mod/assignment/index.php?id=" + assignmentLink;
        this.cookies = cookies;
        this.done = false;
        this.a = new ArrayList<Assignment>();
    }
    
    @Override
    public void run() {
        try {
            Document d = Jsoup.connect(this.assignmentLink)
                    .cookies(this.cookies)
                    .get();
            
            Elements tabs = d.getElementsByClass("generaltable boxaligncenter");
            
            if(tabs.size() != 0){
                Element body = tabs.get(0).child(0);
                for(int i = 1;i<body.children().size();i++ ){
                    //rows
                    Element name = body.children().get(i).child(1);
                    String assignmentName = name.child(0).text();
                    String link = name.child(0).attr("href");
                    
                    String due = body.children().get(i).child(3).text();
                    String submitted = body.children().get(i).child(4).text();
                    
                    System.out.println("Got a name" + assignmentName);
                    System.out.println("Got a link" + link);
                    System.out.println("Got a due" + due);
                    
                    if(submitted.length() > 5){
                        this.a.add(new Assignment(assignmentName,due,link));
                    }
                }
            }else{
                this.a = Collections.emptyList();
            }
            
            this.done = true;
        } catch (IOException ex) {
            Logger.getLogger(AssignmentRetriever.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception");
            this.done = false;
        }
    }
    
    public boolean gotAssignments(){
        return done;
    }
    
    public List<Assignment> getAssignments(){
        return this.a;
    }
    
    
}
