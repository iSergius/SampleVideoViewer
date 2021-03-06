package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;

/**
 * @author Sergey Kondratyev
 */

public class EventNewClient implements Emitter.Listener {
    public static final String EVENT_ID = "new_client";
    private static final String TAG = EventNewClient.class.getSimpleName();

    private final MessageConsumer messageConsumer;

    public EventNewClient(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void call(Object... args) {
        Log.v(TAG, EVENT_ID);
        Log.v(TAG, args[0].toString());
        JSONObject object = (JSONObject) args[0];
        try {
            JSONObject client = object.getJSONObject("client");
            String id = client.getString("id");
            String deviceId = client.getString("deviceId");
            Object userId = client.get("userId");
            String name = client.getString("name");
            messageConsumer.clientData(new Client(id, deviceId, userId.toString(), name));
        } catch (JSONException  e) {
            Log.e(TAG, "Error", e);
        }
    }

}
