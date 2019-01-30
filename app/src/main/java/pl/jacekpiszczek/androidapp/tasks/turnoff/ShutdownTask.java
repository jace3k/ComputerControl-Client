package pl.jacekpiszczek.androidapp.tasks.turnoff;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.ShutdownParams;
import pl.jacekpiszczek.androidapp.elements.Tasker;
import pl.jacekpiszczek.androidapp.elements.DataManager;

import java.util.concurrent.TimeUnit;


public class ShutdownTask implements Tasker {
    public static final boolean EXIT = false;
    public static final boolean RESTART = true;
    public static final boolean CANCEL = true;
    public static final boolean NO_CANCEL = false;

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .shutdown(ShutdownParams.newBuilder()
                        .setDelay((int) params[0])
                        .setRestart((boolean) params[1])
                        .setCancel((boolean) params[2])
                        .build());
        Snackbar.make((View) params[3], response.getParam(), Snackbar.LENGTH_LONG).show();
        Log.i("ShutdownMessage", response.getParam());
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make((View) params[3], R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
