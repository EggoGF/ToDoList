package gngo.com.example.todolist.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoListViewModel extends ViewModel {
    public final static String TAG = "ToDoListViewModel";
    private DBHelper dbHelper = null;

    List<Task> tasks =new ArrayList<>();

    public List<Task> getTasks(){
        return tasks;
    }

    public Task addTask(String title, String description, String duedate, String addinfo){
        Task task =new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(duedate);
        task.setAddinfo(addinfo);
        tasks.add(task);

        long taskId;
        if (dbHelper != null){
            taskId = dbHelper.insert(task);
            task.setId(taskId);
        }

        return task;
    }

    public Task removeTask (Task task){
        tasks.remove(task);

        // database delete record
        if (dbHelper != null){
            dbHelper.deleteRecord(task.getId());
        }
        return task;
    }

    public void init_database(Context context){
        try {
            if(dbHelper == null){
                Log.d(TAG, "init_database: DB Helper null, create one");
                dbHelper = new DBHelper(context);
                tasks = dbHelper.selectAll();
            } else{
                Log.d(TAG, "init_database: DBHelper already exists");
            }
            if (!tasks.isEmpty()){
                Log.d(TAG," tasks list is not empty size: " + tasks.size());
            }
        } catch (Exception e){
            Log.d(TAG, "init_database: DBHelper threw exception: " + e);
            e.printStackTrace();
        }
    }
}