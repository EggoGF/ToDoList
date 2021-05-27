package gngo.com.example.todolist.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ZooListViewModel extends ViewModel {
    public final static String TAG = "ZooListViewModel";
    private DBHelper dbHelper = null;

    List<Animal> animals=new ArrayList<>();

    public List<Animal> getAnimals(){
        return animals;
    }

    public Animal addAnimal(String animalName, String location, String type){
        Animal animal=new Animal();
        animal.setName(animalName);
        animal.setLocation(location);
        animal.setType(type);
        animals.add(animal);

        long animalId;
        if (dbHelper != null){
            animalId = dbHelper.insert(animal);
            animal.setId(animalId);
        }

        return animal;
    }

    public Animal removeAnimal(Animal animal){
        animals.remove(animal);

        // database delete record
        if (dbHelper != null){
            dbHelper.deleteRecord(animal.getId());
        }
        return animal;
    }

    public void init_database(Context context){
        try {
            if(dbHelper == null){
                Log.d(TAG, "init_database: DB Helper null, create one");
                dbHelper = new DBHelper(context);
                animals = dbHelper.selectAll();
            } else{
                Log.d(TAG, "init_database: DBHelper already exists");
            }
            if (!animals.isEmpty()){
                Log.d(TAG," animals list is not empty size: " + animals.size());
            }
        } catch (Exception e){
            Log.d(TAG, "init_database: DBHelper threw exception: " + e);
            e.printStackTrace();
        }
    }
}