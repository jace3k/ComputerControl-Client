package pl.jacekpiszczek.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.DelayElement;
import pl.jacekpiszczek.androidapp.tasks.turnoff.ShutdownTask;

public class TurnOffActivity extends AppCompatActivity {

    private Spinner spinnerDelays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_off);

        spinnerDelays = findViewById(R.id.spinner_delay_off);


        ArrayAdapter<DelayElement> delaysAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DataManager.getInstance().getDelays());
        delaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDelays.setAdapter(delaysAdapter);


    }

    public void onShutdownClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new ShutdownTask())
                .setParams(getDelayValue(), ShutdownTask.EXIT, ShutdownTask.NO_CANCEL, view)
                .start();
    }

    public void onRestartClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new ShutdownTask())
                .setParams(getDelayValue(), ShutdownTask.RESTART, ShutdownTask.NO_CANCEL, view)
                .start();
    }

    public void onCancelClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new ShutdownTask())
                .setParams(getDelayValue(), ShutdownTask.EXIT, ShutdownTask.CANCEL, view)
                .start();
    }

    private int getDelayValue() {
        DelayElement delayElement = (DelayElement) spinnerDelays.getSelectedItem();
        return delayElement.getValue();
    }

}
