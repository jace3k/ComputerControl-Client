package pl.jacekpiszczek.androidapp.tasks.menu;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.Tasker;
import pl.jacekpiszczek.androidapp.Welcome;
import pl.jacekpiszczek.androidapp.elements.DataManager;

import java.util.concurrent.TimeUnit;

public class WelcomeMenuTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Welcome response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .getShortInfo(Empty.getDefaultInstance());
        TextView user = activity.findViewById(R.id.text_user);
        TextView systemShort = activity.findViewById(R.id.text_system_short);
        user.setText(response.getUser());
        systemShort.setText(response.getSystem());
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        View view = activity.findViewById(R.id.text_user);
        Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
