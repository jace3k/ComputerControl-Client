package pl.jacekpiszczek.androidapp.activities.additional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.KeyLogsTask;

import java.util.Timer;

public class KeyloggerActivity extends AppCompatActivity {

    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keylogger);
        showKeys();
    }

    private void showKeys() {
        TaskBuilder.newTask(this)
                .setTaskWork(new KeyLogsTask())
                .setTimer(timer)
                .startInterval(3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
