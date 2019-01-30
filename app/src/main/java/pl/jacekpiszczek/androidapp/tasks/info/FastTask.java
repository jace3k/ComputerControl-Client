package pl.jacekpiszczek.androidapp.tasks.info;

import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.FastIntervalInfo;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class FastTask implements Tasker {

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        FastIntervalInfo response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .getFastIntervalInfo(Empty.getDefaultInstance());

        TextView currentFreqText = activity.findViewById(R.id.text_current_freq);
        int currentFreq = (int) response.getCurrentFreq();
        int usageSet = (int) response.getUsagePercent();
        String labelUsage = currentFreq + "MHz / " + usageSet + "%";
        ProgressBar cpuUsageBar = activity.findViewById(R.id.progress_processor);


        activity.runOnUiThread(()-> {
            currentFreqText.setText(labelUsage);
            cpuUsageBar.setProgress(usageSet);
        });
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {

    }
}
