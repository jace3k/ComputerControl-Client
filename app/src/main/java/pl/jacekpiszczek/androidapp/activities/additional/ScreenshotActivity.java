package pl.jacekpiszczek.androidapp.activities.additional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.ScreenshotTask;

public class ScreenshotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);

        ProgressBar progressBar = findViewById(R.id.progress_screenshot);
        progressBar.setIndeterminate(true);

        TaskBuilder.newTask(this)
                .setTaskWork(new ScreenshotTask())
                .start();

    }
}
