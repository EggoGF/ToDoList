package gngo.com.example.todolist.ui.main;

public class Task {
    public final static String MAMMAL = "mammal";
    public final static String BIRD = "bird";
    public final static String REPTILE = "reptile";

    private String title="";
    private String description="";
    //private String type="";
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

    public String getDescription(){
        return(description);
    }

    public void setDescription(String description){
        this.description = description;
    }

/*    public String getType(){
        return (type);
    }

    public void setType(String type){
        this.type=type;
    }*/

    @Override
    public String toString(){
        return "Task{" +
                "name='" + title + "'" +
                ", description='" + description + "'";
    }
}
