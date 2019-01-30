package pl.jacekpiszczek.androidapp.tasks.more;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import pl.jacekpiszczek.androidapp.Alert;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.Tasker;
import pl.jacekpiszczek.androidapp.elements.DataManager;

import java.util.concurrent.TimeUnit;

public class InfoBoxTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .openInfoWindow(Alert.newBuilder()
                        .setTitle((String) params[0])
                        .setText((String) params[1])
                        .setIsNotification((boolean) params[2])
                        .build());
        Snackbar.make((View) params[3], response.getParam(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make((View) params[3], R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
