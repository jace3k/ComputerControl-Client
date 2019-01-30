package pl.jacekpiszczek.androidapp.activities.additional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.ClickTask;

public class ArrowsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrows);
    }


    public void onArrowUp(View view) {
        arrowTask("up");
    }

    public void onArrowDown(View view) {
        arrowTask("down");
    }

    public void onArrowLeft(View view) {
        arrowTask("left");
    }

    public void onArrowRight(View view) {
        arrowTask("right");
    }

    private void arrowTask(String arrow) {
        TaskBuilder.newTask(this)
                .setTaskWork(new ClickTask())
                .setParams(arrow)
                .start();
    }
}
