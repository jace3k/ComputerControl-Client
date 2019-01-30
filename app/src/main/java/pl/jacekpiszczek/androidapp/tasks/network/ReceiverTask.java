package pl.jacekpiszczek.androidapp.tasks.network;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import pl.jacekpiszczek.androidapp.R;
import pl.jacekpiszczek.androidapp.elements.DataManager;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverTask implements Tasker {
    private DataManager dataManager = DataManager.getInstance();

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) {
        try {
            ServerSocket serverSocket = dataManager.createServerSocket();
            Log.i("SOCKET", "waiting for hosts...");
            dataManager.addElementToHostList("192.168.1.226", "Jacek");
            //noinspection InfiniteLoopStatement
            while (true) {
                Socket connection = serverSocket.accept();
                String address = connection.getInetAddress().getHostAddress();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String hostname = br.readLine();
                Log.i("SERVER RECEIVED", address + ", " + hostname);

                dataManager.addElementToHostList(address, hostname);
                activity.runOnUiThread(() -> dataManager.getHostAdapter().notifyDataSetChanged());
            }
        } catch (IOException ignored) {
        } finally {
            dataManager.terminateServerSocket();

            activity.runOnUiThread(() -> activity.findViewById(R.id.progressBar).setVisibility(View.GONE));
            activity.runOnUiThread(() -> activity.findViewById(R.id.button_refresh).setEnabled(true));
        }
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        
    }
}
