package gngo.com.example.todolist.ui.main;

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
import android.widget.Toast;

import gngo.com.example.todolist.R;

public class ToDoListFragment extends Fragment implements TaskRecyclerViewAdapter.OnAdapterItemInteraction {

    private ToDoListViewModel mViewModel;

    public final static String TAG="TaskList";
    TaskRecyclerViewAdapter adapter;

    public static ToDoListFragment newInstance() {
        return new ToDoListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.to_do_list_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    public void onDelete(Task task) {
        // When clicked, delete the item that was clicked.
        // (Show a toast to indicate what is occurring)
        if (task != null) {
            String item = "Completed: " + task.getTitle();
            Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            Log.d(TAG, " onItemClick: " + task.getTitle());

            // Removes the object from the array held in the viewModel
            mViewModel.removeTask(task);
            // Notifies that the underlying data has changed
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Instantiate the viewModel
        mViewModel = new ViewModelProvider(this).get(ToDoListViewModel.class);

        // Use database
        mViewModel.init_database(getActivity());

        // Instantiate the recyclerView
        RecyclerView recyclerView = getActivity().findViewById(R.id.task_list);

        // Instantiate the layoutManager and set it into the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Instantiate an TaskRecyclerViewAdapter, pass the data from the viewModel & the object
        adapter=new TaskRecyclerViewAdapter(mViewModel.getTasks(), this);

        // Set the adapter into the recyclerView
        recyclerView.setAdapter(adapter);

        // Instantiate the save button and attach a listener
        Button saveButton = getActivity().findViewById(R.id.task_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onSave();
            }
        });
    }

    private void onSave(){
        EditText task_title=getActivity().findViewById(R.id.task_title);
        String title = task_title.getText().toString();
        EditText task_description=getActivity().findViewById(R.id.task_description);
        String description = task_description.getText().toString();
        EditText task_due_date=getActivity().findViewById(R.id.task_due_date);
        String duedate = task_due_date.getText().toString();
        EditText task_addinfo=getActivity().findViewById(R.id.task_addinfo);
        String addinfo = task_addinfo.getText().toString();

        // Don't enter info which does not contain mandatory items
        if(TextUtils.isEmpty(title)){
            showMissingTitleAlert();
            }

        if(TextUtils.isEmpty(duedate)){
            showMissingDueDateAlert();
        }

            // Add the object at the end of the array kept in the viewModel
            mViewModel.addTask(title, description, duedate, addinfo);

            // Notifies the adapter that the underlying data has changed,
            //          any View reflecting the data should refresh itself.
            adapter.notifyDataSetChanged();

            // Remove the soft keyboard after hitting the save button
            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }


    @Override
    public void onItemSelected(Task task) {
        showAddInfo();
    }

    @Override
    public void onItemLongClick(Task task) {
        onDelete(task);
    }

    public void showMissingTitleAlert(){
        ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
        alertDialogBuilder.setTitle(getResources().getString(R.string.alert_title_header));


        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.alert_title_message))
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

    public void showMissingDueDateAlert(){
        ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
        alertDialogBuilder.setTitle(getResources().getString(R.string.alert_duedate_header));

        alertDialogBuilder
                .setMessage(getResources().getString(R.string.alert_duedate_message))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showAddInfo(){
        ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
        EditText task_title=getActivity().findViewById(R.id.task_title);
        alertDialogBuilder.setTitle(task_title.getText().toString());


//
        // set dialog message
        EditText task_addinfo=getActivity().findViewById(R.id.task_addinfo);
        alertDialogBuilder
                .setMessage(task_addinfo.getText().toString())
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