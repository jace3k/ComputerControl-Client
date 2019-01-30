package pl.jacekpiszczek.androidapp.activities.additional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.ClickTask;
import pl.jacekpiszczek.androidapp.tasks.more.WebBrowserTask;

public class WebBrowserActivity extends AppCompatActivity {

    private TextView textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        textAddress = findViewById(R.id.text_web_address);
    }

    public void onOpenWebClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new WebBrowserTask())
                .setParams(textAddress.getText().toString())
                .start();
    }

    public void onBackwardClick(View view) {
        onClickTask("hotkey.alt.left");
    }

    public void onForwardClick(View view) {
        onClickTask("hotkey.alt.right");
    }

    public void onRefreshClick(View view) {
        onClickTask("f5");
    }

    private void onClickTask(String button) {
        TaskBuilder.newTask(this)
                .setTaskWork(new ClickTask())
                .setParams(button)
                .start();
    }
}
