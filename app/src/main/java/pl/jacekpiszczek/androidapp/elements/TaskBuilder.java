package pl.jacekpiszczek.androidapp.elements;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TaskBuilder {
    private AppCompatActivity activity;
    private Tasker task;
    private Object[] params;
    private Timer timer;
    private int delay = 0;

    private TaskBuilder() {}

    public static TaskBuilder newTask(AppCompatActivity activity) {
        TaskBuilder tb = new TaskBuilder();
        tb.activity = activity;
        return tb;
    }

    public TaskBuilder setTaskWork(Tasker t) {
        this.task = t;
        return this;
    }

    public TaskBuilder setTimer(Timer timer) {
        this.timer = timer;
        return this;
    }

    public TaskBuilder setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public TaskBuilder setParams(Object... params) {
        this.params = params;
        return this;
    }

    public void start() {
        new Task(activity, task, params).execute();
    }

    public void startInterval(int interval) {
        timer.scheduleAtFixedRate(new IntervalTask(activity, task, params), delay, interval);
    }

    private static class Task extends AsyncTask<Void, Void, Void> {
        @SuppressLint("StaticFieldLeak")
        private AppCompatActivity activity;
        private Tasker task;
        private Object[] params;

        Task(AppCompatActivity activity, Tasker task, Object[] params) {
            this.activity = activity;
            this.task = task;
            this.params = params;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            startWorking(task, activity, params);
            return null;
        }
    }

    private static class IntervalTask extends TimerTask {
        private AppCompatActivity activity;
        private Tasker task;
        private Object[] params;

        IntervalTask(AppCompatActivity activity, Tasker task, Object[] params) {
            this.activity = activity;
            this.task = task;
            this.params = params;
        }

        @Override
        public void run() {
            startWorking(task, activity, params);
        }
    }

    private static void startWorking(Tasker task, AppCompatActivity activity, Object[] params) {
        try {
            task.doTask(activity, params);
        } catch (Exception e) {
            task.doWhenError(activity, params);
            Log.e(task.getClass().getSimpleName(), "error");
            e.printStackTrace();
        }
    }
}
