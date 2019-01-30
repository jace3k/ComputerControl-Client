package pl.jacekpiszczek.androidapp.tasks.info;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import pl.jacekpiszczek.androidapp.Disk;
import pl.jacekpiszczek.androidapp.DiskInfo;
import pl.jacekpiszczek.androidapp.Empty;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

public class DiskTask implements Tasker {
    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        DiskInfo response = DataManager.getInstance().getStub()
                .getDiskInfo(Empty.newBuilder().build());

        LinearLayout view = activity.findViewById(R.id.disk_view);

        for (Disk d : response.getDisksList()) {

            LinearLayout ll = new LinearLayout(activity.getApplicationContext());

            LinearLayout.LayoutParams layP = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            ll.setLayoutParams(layP);
            ll.setOrientation(LinearLayout.VERTICAL);

            long totalMem = d.getTotalMemory()/(1024*1024);
            long usedMem = d.getUsedMemory()/(1024*1024);
            float percentMemory = d.getPercentMemory();

            TextView textViewLetter = makeTextView("Patrycja: " + d.getMountpoint() + "", activity);
            textViewLetter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textViewLetter.setTextColor(Color.BLACK);
            TextView textViewTotalMemory = makeTextView("- Rozmiar: " + totalMem + " MB", activity);
            TextView textViewUsedMemory = makeTextView("- Używane: " + usedMem + " MB", activity);
            TextView textViewPercent = makeTextView("- " + percentMemory + " % zajętego miejsca", activity);


            activity.runOnUiThread(() -> {
                ll.addView(textViewLetter);
                ll.addView(textViewTotalMemory);
                ll.addView(textViewUsedMemory);
                ll.addView(textViewPercent);

                view.addView(ll);
            });
        }




    }

    private TextView makeTextView(String text, AppCompatActivity activity) {
        TextView textView = new TextView(activity.getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        lp.setMargins(40, 10, 40, 10);
        textView.setLayoutParams(lp);
        activity.runOnUiThread(()-> {
            textView.setText(text);
            textView.setTypeface(ResourcesCompat.getFont(activity.getApplicationContext(), R.font.muli));
        });
        return textView;
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {

    }
}
