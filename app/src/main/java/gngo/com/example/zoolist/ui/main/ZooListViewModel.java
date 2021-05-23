package gngo.com.example.zoolist.ui.main;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ZooListViewModel extends ViewModel {
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
        return animal;
    }

    public Animal removeAnimal(Animal animal){
        animals.remove(animal);
        return animal;
    }
}