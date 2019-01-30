package pl.jacekpiszczek.androidapp.tasks.network;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.jaredrummler.android.device.DeviceName;
import io.grpc.ManagedChannel;
import pl.jacekpiszczek.androidapp.Command;
import pl.jacekpiszczek.androidapp.CommanderGrpc;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.Success;
import pl.jacekpiszczek.androidapp.activities.MenuActivity;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.util.concurrent.TimeUnit;

public class ConnectTask implements Tasker {
    private ManagedChannel channel;
    private CommanderGrpc.CommanderBlockingStub stub;

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        String selectedAddress = (String) params[0];
        String pin = (String) params[1];

        channel = DataManager.getInstance().newChannel(selectedAddress);
        stub = DataManager.getInstance().newStub(pin);

        channel.notifyWhenStateChanged(channel.getState(true), () -> {
            try {
                String user = DeviceName.getDeviceName();

                Success response = stub.withDeadlineAfter(3, TimeUnit.SECONDS)
                        .logIn(Command.newBuilder().setParam(user).build());

                startActivityIfSuccess(response.getSuccess(), activity);
            } catch (Exception e) {
                Snackbar.make(activity.findViewById(R.id.button_refresh), "Problemy z serwerem.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void startActivityIfSuccess(boolean success, AppCompatActivity activity) {
        if (success) {
            Intent intent = new Intent(activity, MenuActivity.class);
            activity.startActivity(intent);
        } else {
            channel.shutdown();
            Snackbar.make(activity.findViewById(R.id.button_refresh), "Niepoprawny PIN.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        Snackbar.make(activity.findViewById(R.id.button_refresh), "Błąd.", Snackbar.LENGTH_LONG).show();
    }
}
