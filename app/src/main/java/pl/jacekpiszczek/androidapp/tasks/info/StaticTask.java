package pl.jacekpiszczek.androidapp.tasks.info;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.StaticInfo;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class StaticTask implements Tasker {

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        StaticInfo response = DataManager.getInstance().getStub()
                .withDeadlineAfter(10, TimeUnit.SECONDS)
                .getStaticInfo(Empty.getDefaultInstance());
        TextView textSystem = activity.findViewById(R.id.text_system);
        String text = response.getSystem()
                + " "
                + response.getRelease();
        activity.runOnUiThread(() -> textSystem.setText(text));

        TextView textArchitecture = activity.findViewById(R.id.text_architecture);
        String text2 = response.getArchitecture();
        activity.runOnUiThread(() -> textArchitecture.setText(text2));

        TextView textVersion = activity.findViewById(R.id.text_version_build);
        String text3 = response.getVersion();
        activity.runOnUiThread(() -> textVersion.setText(text3));

        TextView textProcessor = activity.findViewById(R.id.text_processor_name);
        String text4 = response.getCpuName();
        activity.runOnUiThread(() -> textProcessor.setText(text4));

        TextView textCoreCount = activity.findViewById(R.id.text_core_count);
        String text5 = response.getCoreCount() + "";
        activity.runOnUiThread(() -> textCoreCount.setText(text5));

        TextView textCoreCountLogical = activity.findViewById(R.id.text_core_count_logical);
        String text6 = response.getCoreCountLogical() + "";
        activity.runOnUiThread(() -> textCoreCountLogical.setText(text6));



        long uptime = (long) response.getUptime();

        TaskBuilder.newTask(activity)
                .setTaskWork(new UptimeTask())
                .setTimer((Timer) params[0])
                .setParams(uptime)
                .startInterval(1000);
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make(activity.findViewById(R.id.text_system), R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
