package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;
import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;

/**
 * @author Sergey Kondratyev
 */

public class EventIceCandidate implements Emitter.Listener {

    public static final String EVENT_ID = "ice_candidate";

    private static final String TAG = EventIceCandidate.class.getSimpleName();

    private MessageConsumer messageConsumer;

    public EventIceCandidate(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void call(Object... args) {
        Log.v(TAG, EVENT_ID);
        Log.v(TAG, args[0].toString());
        JSONObject object = (JSONObject) args[0];
        try {
            String clientId = object.getString("clientId");
            JSONObject message = object.getJSONObject("message");
            CandidateServer candidateServer = new CandidateServer(
                    message.getString("sdpMid"),
                    message.getInt("sdpMLineIndex"),
                    message.getString("candidate"));
            messageConsumer.clientIceCandidateServer(clientId,candidateServer);
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
    }
}
