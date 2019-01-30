package pl.jacekpiszczek.androidapp.tasks.more;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.Tasker;
import pl.jacekpiszczek.androidapp.elements.DataManager;

import java.util.concurrent.TimeUnit;

public class CDROMTask implements Tasker {

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .openCDROM(Empty.getDefaultInstance());
        View view = activity.findViewById(R.id.button_open_cd);
        Snackbar.make(view, response.getParam(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        try {
            View view = activity.findViewById(R.id.button_open_cd);
            Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("CDROMTask", "error");
        }
    }
}
