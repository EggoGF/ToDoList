package gngo.com.example.zoolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import gngo.com.example.zoolist.ui.main.ZooListFragment;

public class ZooListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoo_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ZooListFragment.newInstance())
                    .commitNow();
        }
    }
}