package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;

/**
 * @author Sergey Kondratyev
 */
public class EventClientLeft implements Emitter.Listener {
    public static final String EVENT_ID = "client_left";
    private static final String TAG = EventClientLeft.class.getSimpleName();

    private MessageConsumer messageConsumer;

    public EventClientLeft(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void call(Object... args) {
        Log.v(TAG, EVENT_ID);
        Log.v(TAG, args[0].toString());
        JSONObject object = (JSONObject) args[0];
        try {
            String clientId = object.getString("clientId");
            messageConsumer.clientLogout(clientId);
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
    }
}
