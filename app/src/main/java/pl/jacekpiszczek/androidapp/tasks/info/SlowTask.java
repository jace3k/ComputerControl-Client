package pl.jacekpiszczek.androidapp.tasks.info;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.SlowIntervalInfo;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SlowTask implements Tasker {

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        SlowIntervalInfo response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .getSlowIntervalInfo(Empty.getDefaultInstance());

        TextView memoryMaxText = activity.findViewById(R.id.text_memory_max);
        String totalMemoryLabel = "100%";

        TextView textMemoryFull = activity.findViewById(R.id.text_memory_full);
        String text1 = (response.getTotalMemory() / (1024 * 1024)) + "MB";
        activity.runOnUiThread(() -> textMemoryFull.setText(text1));

        TextView textMemoryFree = activity.findViewById(R.id.text_memory_free);
        String text2 = (response.getFreeMemory() / (1024 * 1024)) + "MB";
        activity.runOnUiThread(() -> textMemoryFree.setText(text2));

        TextView memoryCurrentText = activity.findViewById(R.id.text_memory_used);
        long currentMemory = response.getUsedMemory();
        String currentMemoryLabel = (currentMemory / (1024 * 1024)) + "MB";

        ProgressBar memoryBar = activity.findViewById(R.id.progress_memory);
        int percentMemory = (int) response.getPercentMemory();

        TextView batteryInfo = activity.findViewById(R.id.text_battery_time_left);
        boolean powerPlugged = response.getPowerPlugged();
        long secLeft = response.getSecondsLeftBattery();
        StringBuilder batteryLabel = new StringBuilder();
        if (powerPlugged) {
            batteryLabel.append("Podłączony.");
        } else {
            batteryLabel.append("Niepodłączony, ").append(doFancyTime(secLeft));
        }

        TextView batteryCurrent = activity.findViewById(R.id.text_battery_percent);

        ProgressBar batteryBar = activity.findViewById(R.id.progress_battery);
        int percentBattery = (int) response.getPercentBattery();
        String percentBatteryLabel = percentBattery + "%";
        activity.runOnUiThread(() -> {
            memoryMaxText.setText(totalMemoryLabel);
            memoryBar.setProgress(percentMemory);
            memoryCurrentText.setText(currentMemoryLabel);
            batteryInfo.setText(batteryLabel.toString());
            batteryCurrent.setText(percentBatteryLabel);
            batteryBar.setProgress(percentBattery);
        });

    }

    private String doFancyTime(long time) {
        if (time > 4000000000L || time < 0) {
            return "obliczanie pozostałego czasu..";
        }
        long minute = (time / (60)) % 60;
        long hour = (time / (60 * 60));

        return String.format(Locale.getDefault(), "pozostało %02dh %02dmin", hour, minute);
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make(activity.findViewById(R.id.text_system), R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
