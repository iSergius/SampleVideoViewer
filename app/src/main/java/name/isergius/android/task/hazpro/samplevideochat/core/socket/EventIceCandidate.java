package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;
import name.isergius.android.task.hazpro.samplevideochat.core.StoreException;
import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;

/**
 * @author Sergey Kondratyev
 */

public class EventIceCandidate implements Emitter.Listener {

    public static final String EVENT_ID = "ice_candidate";

    private static final String TAG = EventIceCandidate.class.getSimpleName();

    private ClientStore clientStore;

    public EventIceCandidate(ClientStore clientStore) {
        this.clientStore = clientStore;
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
            clientStore.addCandidateServer(clientId,candidateServer);
        } catch (JSONException | StoreException e) {
            Log.e(TAG, "Error", e);
        }
    }
}
