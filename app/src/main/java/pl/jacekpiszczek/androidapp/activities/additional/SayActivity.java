package pl.jacekpiszczek.androidapp.activities.additional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.more.SayTask;

public class SayActivity extends AppCompatActivity {

    private EditText sayContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_say);
        sayContent = findViewById(R.id.text_say_content);
    }

    public void onSayClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new SayTask())
                .setParams(sayContent.getText().toString())
                .start();
    }
}
