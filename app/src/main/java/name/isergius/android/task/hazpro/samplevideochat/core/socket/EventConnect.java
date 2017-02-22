package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import android.util.Log;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;

/**
 * @author Sergey Kondratyev
 */
public class EventConnect implements Emitter.Listener {
    public static final String EVENT_ID = Socket.EVENT_CONNECT;
    private static final String TAG = EventConnect.class.getSimpleName();

    private MessageConsumer messageConsumer;


    public EventConnect(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void call(Object... args) {
        Log.v(TAG, EVENT_ID);
        messageConsumer.selfConnected();
    }
}
