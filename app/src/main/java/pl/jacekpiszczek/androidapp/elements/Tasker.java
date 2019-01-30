package pl.jacekpiszczek.androidapp.elements;

import android.support.v7.app.AppCompatActivity;

public interface Tasker {
    void doTask(AppCompatActivity activity, Object[] params) throws Exception;
    void doWhenError(AppCompatActivity activity, Object[] params);
}
