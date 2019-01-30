package pl.jacekpiszczek.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.info.DiskTask;
import pl.jacekpiszczek.androidapp.tasks.info.FastTask;
import pl.jacekpiszczek.androidapp.tasks.info.SlowTask;
import pl.jacekpiszczek.androidapp.tasks.info.StaticTask;

import java.util.Timer;

public class InfoActivity extends AppCompatActivity {
    private Timer timer;
    private Timer uptimeTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        timer = new Timer();
        uptimeTimer = new Timer();

        setStaticInfo();
        setFastIntervalInfo();
        setSlowIntervalInfo();
        setDiskInfo();
    }

    private void setStaticInfo() {
        TaskBuilder.newTask(this)
                .setTaskWork(new StaticTask())
                .setParams(uptimeTimer)
                .start();

    }

    private void setFastIntervalInfo() {
        TaskBuilder.newTask(this)
                .setTaskWork(new FastTask())
                .setTimer(timer)
                .setDelay(500)
                .startInterval(1000);
    }

    private void setSlowIntervalInfo() {
        TaskBuilder.newTask(this)
                .setTaskWork(new SlowTask())
                .setTimer(timer)
                .setDelay(1000)
                .startInterval(6000);
    }

    private void setDiskInfo() {
        TaskBuilder.newTask(this)
                .setTaskWork(new DiskTask())
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        uptimeTimer.cancel();
    }
}
