package pl.jacekpiszczek.androidapp.tasks.more;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import com.google.protobuf.ByteString;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.File;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class ScreenshotTask implements Tasker {

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        File response = DataManager.getInstance().getStub()
                .withDeadlineAfter(6, TimeUnit.SECONDS)
                .screenshot(Empty.getDefaultInstance());
        ByteString bytesImg = response.getContent();
        Bitmap screen = BitmapFactory.decodeByteArray(bytesImg.toByteArray(), 0, bytesImg.toByteArray().length);

        final ImageView imageView = new ImageView(activity);
        imageView.setImageBitmap(screen);

        final HorizontalScrollView scrollView = activity.findViewById(R.id.scroll_screenshot);

        activity.runOnUiThread(() -> {
            scrollView.addView(imageView);
            activity.findViewById(R.id.progress_screenshot).setVisibility(View.GONE);
        });
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        View view = activity.findViewById(R.id.progress_screenshot);
        try {
            activity.runOnUiThread(()->view.setVisibility(View.GONE));
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Nie można ukryć paska ładowania.");
        }
        Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
