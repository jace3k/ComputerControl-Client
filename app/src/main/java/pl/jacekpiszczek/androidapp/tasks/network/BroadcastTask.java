package pl.jacekpiszczek.androidapp.tasks.network;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import pl.jacekpiszczek.androidapp.elements.TaskBuilder;
import pl.jacekpiszczek.androidapp.elements.Tasker;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static pl.jacekpiszczek.androidapp.elements.DataManager.BROADCAST_MESSAGE;
import static pl.jacekpiszczek.androidapp.elements.DataManager.BROADCAST_PORT;

public class BroadcastTask implements Tasker {

    private DatagramSocket socket;

    @Override
    public void doTask(AppCompatActivity activity, Object[] params) throws Exception {
        TaskBuilder.newTask(activity)
                .setTaskWork(new ReceiverTask())
                .start();

        socket = new DatagramSocket(BROADCAST_PORT);
        socket.setBroadcast(true);
        byte[] buf = BROADCAST_MESSAGE.getBytes();

        DatagramPacket packet = new DatagramPacket(
                buf,
                buf.length,
                InetAddress.getByName("255.255.255.255"),
                BROADCAST_PORT);

        socket.send(packet);
        Log.i("PACKET", "Broadcast packet sent to 255.255.255.255");
        socket.close();
    }

    @Override
    public void doWhenError(AppCompatActivity activity, Object[] params) {
        socket.close();
    }
}
