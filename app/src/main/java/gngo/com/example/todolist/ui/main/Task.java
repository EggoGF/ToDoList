package gngo.com.example.todolist.ui.main;

public class Task {
    public final static String MAMMAL = "mammal";
    public final static String BIRD = "bird";
    public final static String REPTILE = "reptile";

    private String title="";
    private String location="";
    private String type="";
    protected long id = 0;

    public long getId(){
        return (id);
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return(title);
    }

    public void setName(String title){
        this.title = title;
    }

    public String getLocation(){
        return(location);
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getType(){
        return (type);
    }

    public void setType(String type){
        this.type=type;
    }

    @Override
    public String toString(){
        return "Task{" +
                "name='" + title + "'" +
                ", location='" + location + "'" +
                        ", type='" + type + "'" +
                                '}';
    }
}
