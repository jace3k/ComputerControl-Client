package pl.jacekpiszczek.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.files.ElementsTask;

import java.util.Map;

public class FilesActivity extends AppCompatActivity {
    private ListView listFiles;
    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        dataManager.clearFilesMap();
        listFiles = findViewById(R.id.list_files);

        dataManager.setFileAdapter(new SimpleAdapter(
                this,
                dataManager.getMapListFiles(),
                android.R.layout.simple_list_item_2,
                new String[]{"text1", "text2"},
                new int[]{android.R.id.text1, android.R.id.text2}
        ));

        listFiles.setAdapter(dataManager.getFileAdapter());
        listFiles.setOnItemClickListener(this::onElementClick);

        loadElementsInPath();
    }

    private void onElementClick(AdapterView<?> adapterView, View view, int position, long l) {
        String selectedItem = (String) ((Map) listFiles.getItemAtPosition(position)).get("text1");
        String type = (String) ((Map) listFiles.getItemAtPosition(position)).get("text2");

        if(type.equals("folder")) {
            dataManager.clearFilesMap();
            TaskBuilder.newTask(this)
                    .setTaskWork(new ElementsTask())
                    .setParams(selectedItem)
                    .start();
        }
    }

    public void onBackClick(View view) {
        dataManager.clearFilesMap();
        TaskBuilder.newTask(this)
                .setTaskWork(new ElementsTask())
                .setParams("..")
                .start();
    }

    private void loadElementsInPath() {
        TaskBuilder.newTask(this)
                .setTaskWork(new ElementsTask())
                .setParams(".")
                .start();
    }
}
