package pl.jacekpiszczek.androidapp.elements;

import android.util.Log;
import android.widget.SimpleAdapter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pl.jacekpiszczek.androidapp.CommanderGrpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final int GRPC_PORT = 12344;
    private static final int RECEIVER_PORT = 12343;
    private static final int RECEIVER_TIMEOUT_MS = 5000;

    public static final int BROADCAST_PORT = 12342;
    public static final String BROADCAST_MESSAGE = "pl.jacekpiszczek.remoteapp.client";

    private static DataManager ourInstance = new DataManager();

    private List<Map<String, String>> mapListHosts = new ArrayList<>();
    private SimpleAdapter hostAdapter;

    private List<Map<String, String>> mapListFiles = new ArrayList<>();
    private SimpleAdapter fileAdapter;

    private ManagedChannel channel;
    private CommanderGrpc.CommanderBlockingStub stub;
    private ServerSocket serverSocket;
    private List<DelayElement> delays;



    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        initializeDelayElements();
    }

    private void initializeDelayElements() {
        delays = new ArrayList<>();
        delays.add(new DelayElement("Brak", 0));
        delays.add(new DelayElement("10 sekund", 10));
        delays.add(new DelayElement("30 sekund", 30));
        delays.add(new DelayElement("1 minuta", 60));
        delays.add(new DelayElement("3 minuty", 60*3));
        delays.add(new DelayElement("5 minut", 60*5));
        delays.add(new DelayElement("10 minut", 600));
        delays.add(new DelayElement("30 minut", 60*30));
        delays.add(new DelayElement("1 godzina", 60*60));
        delays.add(new DelayElement("2 godziny", 60*60*2));
        delays.add(new DelayElement("3 godziny", 60*60*3));
        delays.add(new DelayElement("5 godzin", 60*60*5));
    }

    public List<DelayElement> getDelays() {
        return delays;
    }

    public void clearHostMap() {
        mapListHosts.clear();
    }

    public void clearFilesMap() {
        mapListFiles.clear();
    }

    public CommanderGrpc.CommanderBlockingStub getStub() {
        return stub;
    }

    public CommanderGrpc.CommanderBlockingStub newStub(String pin) {
        Credentials callCredential = new Credentials(pin);
        stub = CommanderGrpc.newBlockingStub(channel)
                .withCallCredentials(callCredential)
                .withMaxInboundMessageSize(10000000)
                .withMaxOutboundMessageSize(10000000);
        return stub;
    }

    public ManagedChannel newChannel(String selectedAddress) {
        channel = ManagedChannelBuilder.forAddress(selectedAddress, GRPC_PORT)
                .usePlaintext()
                .build();
        return channel;
    }

    public void terminateChannel() {
        try {
            channel.shutdown();
        } catch (Exception e) {
            Log.i("CHANNEL", "channel already closed");
        }
    }

    public ServerSocket createServerSocket() throws IOException {
        serverSocket = new ServerSocket(RECEIVER_PORT);
        serverSocket.setSoTimeout(RECEIVER_TIMEOUT_MS);
        return serverSocket;
    }

    public void terminateServerSocket() {
        try {
            serverSocket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void addElementToHostList(String address, String hostname) {
        final Map<String, String> map = new HashMap<>();
        map.put("text1", hostname);
        map.put("text2", address);
        mapListHosts.add(map);
    }

    public void addElementToFilesList(String name, String type) {
        final Map<String, String> map = new HashMap<>();
        map.put("text1", name);
        map.put("text2", type);
        mapListFiles.add(map);
    }

    public List<Map<String, String>> getMapListFiles() {
        return mapListFiles;
    }

    public List<Map<String, String>> getMapListHosts() {
        return mapListHosts;
    }

    public SimpleAdapter getHostAdapter() {
        return hostAdapter;
    }

    public void setHostAdapter(SimpleAdapter hostAdapter) {
        this.hostAdapter = hostAdapter;
    }

    public SimpleAdapter getFileAdapter() {
        return fileAdapter;
    }

    public void setFileAdapter(SimpleAdapter fileAdapter) {
        this.fileAdapter = fileAdapter;
    }
}
