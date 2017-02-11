package gitr;

import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ResourceRetriever implements Runnable{

    Document coursePage;
    Map<String, String> cookies;
    int stat;
    Content[] resources;
    
            
    public ResourceRetriever(Document coursePage){
        this.coursePage = coursePage;
        this.stat = 0;
    }
    
    public boolean getStat(){
        if(resources.length == 0)return true;
        return (int)(stat/resources.length) == 1;
    }
    
    public Content[] getResouces(){
        return resources;
    }
    
    @Override
    public void run() {
        Elements res = coursePage.getElementsByClass("activity resource");
        resources = new Content[res.size()];
        
        for (int i = 0; i < res.size(); i++){
            stat+=1;
            resources[i] = new Content(res.get(i).child(0).child(1).text().split("<span")[0],res.get(i).child(0).attr("href"));
        }
    }
    
}
