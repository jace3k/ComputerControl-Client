package pl.jacekpiszczek.androidapp.activities.additional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.InfoBoxTask;

public class CommunicateActivity extends AppCompatActivity {

    private EditText title;
    private EditText text;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        title = findViewById(R.id.text_title_box);
        text = findViewById(R.id.text_message_box);
        checkBox = findViewById(R.id.box_notification);
    }

    public void onShowClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new InfoBoxTask())
                .setParams(title.getText().toString(), text.getText().toString(), checkBox.isChecked(), view)
                .start();
    }
}
