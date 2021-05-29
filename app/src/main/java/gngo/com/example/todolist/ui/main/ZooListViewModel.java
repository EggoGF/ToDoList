package gngo.com.example.todolist.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ZooListViewModel extends ViewModel {
    public final static String TAG = "ZooListViewModel";
    private DBHelper dbHelper = null;

    List<Task> tasks =new ArrayList<>();

    public List<Task> getAnimals(){
        return tasks;
    }

    public Task addAnimal(String animalName, String description){
        Task task =new Task();
        task.setName(animalName);
        task.setDescription(description);
        //task.setType(type);
        tasks.add(task);

        long animalId;
        if (dbHelper != null){
            animalId = dbHelper.insert(task);
            task.setId(animalId);
        }

        return task;
    }

    public Task removeAnimal(Task task){
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