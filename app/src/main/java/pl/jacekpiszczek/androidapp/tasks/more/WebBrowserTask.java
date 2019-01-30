package pl.jacekpiszczek.androidapp.tasks.more;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class WebBrowserTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Command response = DataManager.getInstance().getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .openInternetBrowser(Command.newBuilder()
                        .setParam((String) params[0])
                        .build());
        View view = activity.findViewById(R.id.text_web_address);
        Snackbar.make(view, response.getParam(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        View view = activity.findViewById(R.id.text_web_address);
        Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
