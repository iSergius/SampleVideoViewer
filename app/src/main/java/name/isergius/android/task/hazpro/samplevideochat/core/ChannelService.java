package name.isergius.android.task.hazpro.samplevideochat.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import io.socket.client.IO;
import io.socket.client.Socket;
import name.isergius.android.task.hazpro.samplevideochat.App;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventClientLeft;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventConnect;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventIceCandidate;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventNewClient;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventReadyToReceiveOffer;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventRoomJoined;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventSdpAnswer;
import name.isergius.android.task.hazpro.samplevideochat.core.socket.EventSpdOffer;
import name.isergius.android.task.hazpro.samplevideochat.data.ChannelConfig;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;
import name.isergius.android.task.hazpro.samplevideochat.ui.PeerVideoActivity;

/**
 * @author Sergey Kondratyev
 */

public class ChannelService extends Service {

    private static final String TAG = PeerVideoActivity.class.getSimpleName();

    private final ClientStore clientStore;
    private final Facade facade;
    private Socket socket;
    private WsMessageProducer messageProducer;
    private WsMessageDispatcher messageConsumer;
    private Map<String, PeerController> peerControllers = new HashMap<>();

    public ChannelService() {
        this.clientStore = new MemoryClientStore();
        facade = new Facade();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroySocket();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return facade;
    }

    private void initSocket() {
        try {
            ChannelConfig channelConfig = clientStore.readChannelConfig();
            IO.Options options = prepare(channelConfig);
            socket = IO.socket(channelConfig.getUri(), options);
            messageProducer = new WsMessageProducer(socket);
            messageConsumer = new WsMessageDispatcher(clientStore, messageProducer);
            socket
                    .on(Socket.EVENT_CONNECT, new EventConnect(messageConsumer))
                    .on(EventRoomJoined.EVENT_ID, new EventRoomJoined(messageConsumer))
                    .on(EventNewClient.EVENT_ID, new EventNewClient(messageConsumer))
                    .on(EventReadyToReceiveOffer.EVENT_ID, new EventReadyToReceiveOffer(messageConsumer))
                    .on(EventReadyToReceiveOffer.EVENT_ALIAS0, new EventReadyToReceiveOffer(messageConsumer))
                    .on(EventIceCandidate.EVENT_ID, new EventIceCandidate(messageConsumer))
                    .on(EventSdpAnswer.EVENT_ID, new EventSdpAnswer(messageConsumer))
                    .on(EventSpdOffer.EVENT_ID, new EventSpdOffer(messageConsumer))
                    .on(EventClientLeft.EVENT_ID, new EventClientLeft(messageConsumer));
            socket.connect();
        } catch (URISyntaxException | StoreException e) {
            e.printStackTrace();
        }
    }

    private IO.Options prepare(ChannelConfig channelConfig) {
        IO.Options options = new IO.Options();
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(channelConfig.getProtocol());
            sslContext.init(null, null, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        options.sslContext = sslContext;
        options.secure = true;
        options.transports = channelConfig.getTransports();
        options.reconnectionAttempts = 5;
        options.path = channelConfig.getPath();
        options.query = channelConfig.getQuery();
        return options;
    }

    private void destroySocket() {
        if (socket != null & socket.connected()) {
            socket.disconnect();
            socket.close();
        }
    }

    public class Facade extends Binder {

        public PeerController build(String clientId) throws Exception {
            if (peerControllers.containsKey(clientId)) {
                return peerControllers.get(clientId);
            }
            App app = (App) getApplication();
            try {
                PeerController peerController = new PeerController(clientId, clientStore, app.getPeerConnectionFactory(), messageProducer);
                peerControllers.put(clientId,peerController);
                messageConsumer.registerPeerListener(peerController);
                return peerController;
            } catch (StoreException e) {
                throw e;
            }
        }

        public void register(StoreListener storeListener) {
            messageConsumer.registerStoreListener(storeListener);
        }

        public void connect(RoomConfig roomConfig) {
            try {
                clientStore.saveRoomConfig(roomConfig);
                initSocket();
            } catch (StoreException e) {
                Log.e(TAG, "Error", e);
            }
        }

        public void disconnect() {
            destroySocket();
        }
    }
}
