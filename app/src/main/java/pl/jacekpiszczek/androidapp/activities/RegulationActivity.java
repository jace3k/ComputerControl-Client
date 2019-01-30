package pl.jacekpiszczek.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.ClickTask;
import pl.jacekpiszczek.androidapp.tasks.regulation.BrightnessTask;

public class RegulationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulation);

        SeekBar seekBrightness = findViewById(R.id.seek_brightness);
        seekBrightness.setOnSeekBarChangeListener(new OnSeekBrightnessListener());
    }

    private class OnSeekBrightnessListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            TaskBuilder.newTask(RegulationActivity.this)
                    .setTaskWork(new BrightnessTask())
                    .setParams(i*10)
                    .start();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public void onVolumeUp(View view) {
        doVolumeTask("volumeup");
    }

    public void onVolumeDown(View view) {
        doVolumeTask("volumedown");
    }

    public void onMute(View view) { doVolumeTask("volumemute"); }

    private void doVolumeTask(String upOrDown) {
        TaskBuilder.newTask(this)
                .setTaskWork(new ClickTask())
                .setParams(upOrDown)
                .start();
    }
}
