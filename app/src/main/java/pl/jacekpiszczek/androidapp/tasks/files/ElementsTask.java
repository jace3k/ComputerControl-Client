package pl.jacekpiszczek.androidapp.tasks.files;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.Element;
import pl.jacekpiszczek.androidapp.Elements;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class ElementsTask implements Tasker {
    private DataManager dataManager = DataManager.getInstance();

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        Elements response = dataManager.getStub()
                .withDeadlineAfter(3, TimeUnit.SECONDS)
                .getElementsInPath(Command.newBuilder().setParam((String) params[0]).build());

        String currentPath = response.getCurrentPath();
        TextView textPath = activity.findViewById(R.id.text_file_path);

        for (Element element : response.getElementsInPathList()) {
            dataManager.addElementToFilesList(element.getName(), element.getType());
        }

        activity.runOnUiThread(() -> {
            textPath.setText(currentPath);
            dataManager.getFileAdapter().notifyDataSetChanged();
        });

        Log.i("ElementsTask", "Elements loaded.");
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {

    }
}
