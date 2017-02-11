package gitr;

public class Content {
    private final String name;
    private final String link;
    
    Content(String name, String link){
        this.name = name;
        this.link = link;
    }
    
    public String getName(){ return name; }
    public String getLink(){ return link; }
    
}
