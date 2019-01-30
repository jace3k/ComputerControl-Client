package pl.jacekpiszczek.androidapp.tasks.regulation;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.Level;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.Tasker;
import pl.jacekpiszczek.androidapp.elements.DataManager;

import java.util.concurrent.TimeUnit;

public class BrightnessTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .setBrightness(Level.newBuilder()
                        .setValue((int) params[0])
                        .build());
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        View view = activity.findViewById(R.id.seek_brightness);
        Snackbar.make(view, "Problem z połączeniem.", Snackbar.LENGTH_LONG).show();
    }
}
