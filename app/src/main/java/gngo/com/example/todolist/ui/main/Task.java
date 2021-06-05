package gngo.com.example.todolist.ui.main;

public class Task {

    private String title="";
    private String description="";
    private String duedate="";
    private String addinfo="";
    protected long id = 0;

    public long getId(){
        return (id);
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle(){
        return(title);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return(description);
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDueDate() { return(duedate);}

    public void setDueDate(String duedate) {this.duedate = duedate;}

    public String getAddinfo() {return(addinfo);}

    public void setAddinfo(String addinfo) {this.addinfo = addinfo;}

    @Override
    public String toString(){
        return "Task{" +
                "title='" + title + "'" +
                ", description='" + description + "'" +
                ", due date='" + duedate + "'" +
                ", additional info='" + addinfo + "'" +
                '}';
    }
}
