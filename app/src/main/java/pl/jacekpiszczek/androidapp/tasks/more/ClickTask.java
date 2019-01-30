package pl.jacekpiszczek.androidapp.tasks.more;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class ClickTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .click(Command.newBuilder()
                        .setParam((String) params[0])
                        .build());
        Log.i("ClickTask", response.getParam());
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        try {
            Snackbar.make(activity.findViewById(R.id.arrow_up),
                    R.string.connection_error,
                    Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            Snackbar.make(activity.findViewById(R.id.button_vol_up),
                    R.string.connection_error,
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
