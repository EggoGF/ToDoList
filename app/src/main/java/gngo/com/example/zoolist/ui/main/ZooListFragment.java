package gngo.com.example.zoolist.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import gngo.com.example.zoolist.R;

public class ZooListFragment extends Fragment implements AnimalRecyclerViewAdapter.OnAdapterItemInteraction {

    private ZooListViewModel mViewModel;

    public final static String TAG="ZooList";
    AnimalRecyclerViewAdapter adapter;

    public static ZooListFragment newInstance() {
        return new ZooListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zoo_list_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    public void onDelete(Animal animal) {
        // When clicked, delete the item that was clicked.
        // (Show a toast to indicate what is occurring)
        if (animal != null) {
            String item = "deleting: " + animal.getName();
            Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            Log.d(TAG, " onItemClick: " + animal.getName());

            // Removes the object from the array held in the viewModel
            mViewModel.removeAnimal(animal);
            // Notifies that the underlying data has changed
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Instantiate the viewModel
        mViewModel = new ViewModelProvider(this).get(ZooListViewModel.class);

        // Use database
        mViewModel.init_database(getActivity());

        // Instantiate the recyclerView
        RecyclerView recyclerView = getActivity().findViewById(R.id.zoo_animals);

        // Instantiate the layoutManager and set it into the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Instantiate an AnimalRecyclerViewAdapter, pass the data from the viewModel & the object
        adapter=new AnimalRecyclerViewAdapter(mViewModel.getAnimals(), this);

        // Set the adapter into the recyclerView
        recyclerView.setAdapter(adapter);

        // Instantiate the save button and attach a listener
        Button saveButton = getActivity().findViewById(R.id.zoo_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onSave();
            }
        });
    }

    private void onSave(){
        EditText name=getActivity().findViewById(R.id.zoo_name);
        String animalName = name.getText().toString();

        // Don't enter info which does not contain mandatory items
        if(TextUtils.isEmpty(animalName)){
            showMissingInfoAlert();
        } else {
            //EditText location = getActivity().findViewById(R.id.zoo_location);
            Spinner zoo_area=getActivity().findViewById(R.id.zoo_location);
            String animalLoc = zoo_area.getSelectedItem().toString();
            RadioGroup types = getActivity().findViewById(R.id.zoo_animalType);
            String type = "rock";
            switch (types.getCheckedRadioButtonId()){
                case R.id.zoo_animalTypeMammal:
                    type = Animal.MAMMAL;
                    break;
                case R.id.zoo_animalTypeBird:
                    type = Animal.BIRD;
                    break;
                case R.id.zoo_animalTypeReptile:
                    type = Animal.REPTILE;
                    break;
            }

            // Add the object at the end of the array kept in the viewModel
            mViewModel.addAnimal(animalName, animalLoc, type);

            // Notifies the adapter that the underlying data has changed,
            //          any View reflecting the data should refresh itself.
            adapter.notifyDataSetChanged();

            // Remove the soft keyboard after hitting the save button
            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onItemSelected(Animal animal) {
        onDelete(animal);
    }

    public void showMissingInfoAlert(){
        ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
        alertDialogBuilder.setTitle(getResources().getString(R.string.alert_title));
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.alert_message))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                            public void onClick(DialogInterface dialog,int id){
                                // if this button is clicked, close current activity
                                dialog.cancel();
                            }
                    });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}