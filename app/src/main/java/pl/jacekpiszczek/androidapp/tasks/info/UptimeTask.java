package pl.jacekpiszczek.androidapp.tasks.info;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.Locale;

public class UptimeTask implements Tasker {
    private long uptime;
    private boolean isUptimeSet = false;

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        if (!isUptimeSet) {
            uptime = (long) params[0];
            isUptimeSet = true;
        }
        TextView textUptime = activity.findViewById(R.id.text_uptime);
        activity.runOnUiThread(() -> textUptime.setText(setFancyTime(uptime)));

        uptime++;
    }

    private String setFancyTime(long uptime) {
        long durationInMillis = uptime * 1000;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60));

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make(activity.findViewById(R.id.text_system), "Coś się popsuło.", Snackbar.LENGTH_LONG).show();
    }
}
