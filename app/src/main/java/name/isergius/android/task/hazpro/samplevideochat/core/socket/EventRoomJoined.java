package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;
import name.isergius.android.task.hazpro.samplevideochat.core.StoreException;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */
public class EventRoomJoined implements Emitter.Listener {
    public static final String EVENT_ID = "room_joined";
    private static final String TAG = EventRoomJoined.class.getSimpleName();

    private ClientStore clientStore;


    public EventRoomJoined(ClientStore clientStore) {
        this.clientStore = clientStore;
    }

    @Override
    public void call(Object... args) {
        Log.v(TAG, EVENT_ID);
        Log.v(TAG, args[0].toString());
        try {
            JSONObject object = (JSONObject) args[0];
            String selfId = object.getString("selfId");
            JSONArray clients = object
                    .getJSONObject("room")
                    .getJSONArray("clients");
            for (int i = 0; i < clients.length(); i++) {
                Client client = extractClient(clients.getJSONObject(i));
                if (!client.getId().equals(selfId)) {
                    clientStore.save(client);
                }
            }
            JSONArray iceServers = object
                    .getJSONObject("room")
                    .getJSONObject("iceServers")
                    .getJSONArray("iceServers");
            Client selfClient = new Client(selfId,null,null,null);
            for (int i = 0; i < iceServers.length(); i++) {
                selfClient.add(extractServer(iceServers.getJSONObject(i)));
            }
            clientStore.saveSelf(selfClient);
        } catch (JSONException | StoreException e) {
            Log.e(TAG, "Error", e);
        }
    }

    private Server extractServer(JSONObject server) throws JSONException {
        String credential = server.getString("credential");
        String username = server.getString("username");
        String url = server.getString("url");
        return new Server(credential, username, url);
    }

    private Client extractClient(JSONObject client) throws JSONException {
        String id = client.getString("id");
        String deviceId = client.getString("deviceId");
        Object userId = client.get("userId");
        String name = client.getString("name");
        return new Client(id, deviceId, userId.toString(), name);
    }
}
