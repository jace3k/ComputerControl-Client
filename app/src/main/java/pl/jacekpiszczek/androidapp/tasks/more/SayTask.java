package pl.jacekpiszczek.androidapp.tasks.more;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class SayTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(8, TimeUnit.SECONDS)
                .say(Command.newBuilder()
                        .setParam((String) params[0])
                        .build());
        View view = activity.findViewById(R.id.text_say_content);
        Snackbar.make(view, "Mowa: " + response.getParam(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        View view = activity.findViewById(R.id.text_say_content);
        Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
