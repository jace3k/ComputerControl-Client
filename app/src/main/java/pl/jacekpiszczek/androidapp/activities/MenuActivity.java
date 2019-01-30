package pl.jacekpiszczek.androidapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.activities.additional.*;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.tasks.menu.WelcomeMenuTask;
import pl.jacekpiszczek.androidapp.tasks.more.CDROMTask;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TaskBuilder.newTask(this)
                .setTaskWork(new WelcomeMenuTask())
                .start();
    }

    public void onInfoClick(View view) {
        startActivity(new Intent(this, InfoActivity.class));
    }

    public void onRegulationClick(View view) {
        startActivity(new Intent(this, RegulationActivity.class));
    }

    public void onFilesClick(View view) {
        startActivity(new Intent(this, FilesActivity.class));
    }

    public void onTurnOffClick(View view) {
        startActivity(new Intent(this, TurnOffActivity.class));
    }

    public void onOpenCDROMClick(View view) {
        TaskBuilder.newTask(this)
                .setTaskWork(new CDROMTask())
                .start();
    }

    public void onScreenshotClick(View view) {
        startActivity(new Intent(this, ScreenshotActivity.class));
    }

    public void onKeyloggerCLick(View view) {
        startActivity(new Intent(this, KeyloggerActivity.class));
    }

    public void onCommunicateClick(View view) {
        startActivity(new Intent(this, CommunicateActivity.class));
    }

    public void onBrowserClick(View view) {
        startActivity(new Intent(this, WebBrowserActivity.class));
    }

    public void onSayClick(View view) {
        startActivity(new Intent(this, SayActivity.class));
    }

    public void onArrowsClick(View view) { startActivity(new Intent(this, ArrowsActivity.class)); }
}
