package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */
public class EventReadyToReceiveOffer implements Emitter.Listener {
    public static final String EVENT_ID = "ready_to_receive_offer";
    public static final String EVENT_ALIAS0 = "client_ready";
    private static final String TAG = EventReadyToReceiveOffer.class.getSimpleName();

    private MessageConsumer messageConsumer;

    public EventReadyToReceiveOffer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void call(Object... args) {
        Log.v(TAG, EVENT_ID);
        Log.v(TAG, args[0].toString());
        JSONObject object = (JSONObject) args[0];
        try {
            String clientId = object.getString("clientId");
            JSONArray iceServers = object
                    .getJSONObject("iceServers")
                    .getJSONArray("iceServers");
            for (int i = 0; i < iceServers.length(); i++) {
                messageConsumer.clientIceServer(clientId, convertToServer(iceServers.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
    }

    Server convertToServer(JSONObject server) throws JSONException {
        String credential = server.getString("credential");
        String username = server.getString("username");
        String url = server.getString("url");
        return new Server(credential, username, url);
    }
}
