package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;

/**
 * @author Sergey Kondratyev
 */
public class EventSdpAnswer implements Emitter.Listener {
    public static final String EVENT_ID = "sdp_answer";
    private static final String TAG = EventSdpAnswer.class.getSimpleName();

    private MessageConsumer messageConsumer;

    public EventSdpAnswer(MessageConsumer messageConsumer) {
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
            SDescription sDescription = new SDescription(message.getString("type"), message.getString("sdp"));

            messageConsumer.clientSDescription(clientId,sDescription);
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
    }
}
