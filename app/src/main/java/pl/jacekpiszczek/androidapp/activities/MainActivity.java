package pl.jacekpiszczek.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.*;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.network.BroadcastTask;
import pl.jacekpiszczek.androidapp.tasks.network.ConnectTask;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DataManager dataManager = DataManager.getInstance();

    private ProgressBar progressBar;
    private Button refreshButton;
    private ListView listHosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareViews();
    }

    private void prepareViews() {
        progressBar = findViewById(R.id.progressBar);
        refreshButton = findViewById(R.id.button_refresh);
        listHosts = findViewById(R.id.list_hosts);

        progressBar.setIndeterminate(true);
        refreshButton.setEnabled(true);
        dataManager.setHostAdapter(new SimpleAdapter(
                        this,
                        dataManager.getMapListHosts(),
                        android.R.layout.simple_list_item_2,
                        new String[]{"text1", "text2"},
                        new int[]{android.R.id.text1, android.R.id.text2}
                )
        );

        listHosts.setAdapter(dataManager.getHostAdapter());
        listHosts.setOnItemClickListener(this::onItemClick);
    }

    public void refresh(View view) {
        refresh();
    }

    private void refresh() {
        TaskBuilder.newTask(this)
                .setTaskWork(new BroadcastTask())
                .start();

        refreshButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        dataManager.clearHostMap();
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedAddress = (String) ((Map) listHosts.getItemAtPosition(i)).get("text2");
        Log.i("MAIN", "lodaded address: " + selectedAddress);

        buildAlertDialog(selectedAddress);
    }

    private void buildAlertDialog(String selectedAddress) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PIN");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("Połącz", (dialog, which) -> {
            DataManager.getInstance().terminateServerSocket();
            progressBar.setVisibility(View.GONE);
            refreshButton.setEnabled(true);

            String pin = input.getText().toString();

            TaskBuilder.newTask(this)
                    .setTaskWork(new ConnectTask())
                    .setParams(selectedAddress, pin)
                    .start();
        });

        builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataManager.terminateChannel();
        refresh();
    }
}
