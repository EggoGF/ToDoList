package gngo.com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import gngo.com.example.todolist.ui.main.ZooListFragment;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ZooListFragment.newInstance())
                    .commitNow();
        }
    }
}