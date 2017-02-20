package name.isergius.android.task.hazpro.samplevideochat.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLContext;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;


/**
 * @author Sergey Kondratyev
 */

public class SignalingChannelService extends Service {

    public static final String INTENT_FILTER = "name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService";
    public static final String PROPERTY_ROOM_NAME = "roomName";

    private static final String OPTIONS_PATH = "/protocol/socket.io/v1/";
    private static final String OPTIONS_QUERY = "socketType=room";
    private static final String URI = "https://appear.in";
    private static final String[] TRANSPORTS = {"polling", "websocket"};
    public static final String PROTOCOL = "TLS";

    private static final String TAG = SignalingChannelService.class.getSimpleName();

    private Socket socket;
    private String roomName;
    private String selfId;
    private String uid;
    private String hmac;
    private List<Server> myIceServersList = new ArrayList<>();

    private Map<String, Client> clients = new ConcurrentHashMap<>();
    private Map<String, Client> newClients = new ConcurrentHashMap<>();
    private Set<ClientListListener> clientListListeners = new HashSet<>();
    private Map<String, ClientListener> peerClientListeners = new HashMap<>();
    private SignalingChanelBinder signalingChanelBinder = new SignalingChanelBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (roomName == null && intent.hasExtra(PROPERTY_ROOM_NAME)) {
            roomName = intent.getExtras().getString(PROPERTY_ROOM_NAME);
            startSocket();
        }
        return signalingChanelBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (socket.connected()) {
            socket.close();
        }
    }

    private void startSocket() {
        try {
            IO.Options options = prepare();
            socket = IO.socket(URI, options);
            socket
                    .on(Socket.EVENT_CONNECT, new MessageLoinRoom())
                    .on(EventDeviceIdentified.EVENT_ID, new EventDeviceIdentified())
                    .on(EventRoomJoined.EVENT_ID, new EventRoomJoined())
                    .on(EventNewClient.EVENT_ID, new EventNewClient())
                    .on(EventReadyToReceiveOffer.EVENT_ID, new EventReadyToReceiveOffer())
                    .on(EventReadyToReceiveOffer.EVENT_ALIAS0, new EventReadyToReceiveOffer())
                    .on(EventIceCandidate.EVENT_ID, new EventIceCandidate())
                    .on(EventSdpAnswer.EVENT_ID, new EventSdpAnswer())
                    .on(EventSpdOffer.EVENT_ID, new EventSpdOffer())
                    .on(EventClientLeft.EVENT_ID, new EventClientLeft());
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private IO.Options prepare() {
        IO.Options options = new IO.Options();
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(PROTOCOL);
            sslContext.init(null, null, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        options.sslContext = sslContext;
        options.secure = true;
        options.transports = TRANSPORTS;
        options.reconnectionAttempts = 5;
        options.path = OPTIONS_PATH;
        options.query = OPTIONS_QUERY;
        return options;
    }

    private void notifyClientListeners() {
        for (ClientListListener listener : clientListListeners) {
            List<Client> data = new ArrayList<>(clients.values());
            listener.updateClients(data);
        }
    }

    private void lockClientListListeners() {
        for(ClientListListener clientListListener : clientListListeners) {
            clientListListener.lock();
        }
    }
    private void unlockClientListListeners() {
        for(ClientListListener clientListListener : clientListListeners) {
            clientListListener.unlock();
        }
    }

    public interface ClientListListener {
        void updateClients(Collection<Client> clients);
        void lock ();
        void unlock();
    }

    public interface ClientListener {

        String clientId();

        void iceCandidate(IceCandidate iceCandidate);

        void answer(SessionDescription sessionDescription);

        void disconnect();

        void offer(SessionDescription sessionDescription);
    }

    private static class Util {
        static Client extractClient(JSONObject client) throws JSONException {
            String id = client.getString("id");
            String deviceId = client.getString("deviceId");
            String userId = client.getString("userId");
            String name = client.getString("name");
            return new Client(id, deviceId, userId, name);
        }

        static Server extractServer(JSONObject server) throws JSONException {
            String credential = server.getString("credential");
            String username = server.getString("username");
            String url = server.getString("url");
            return new Server(credential, username, url);
        }
    }

    private static class MessageReadyToReceiveOffer {
        static final String MESSAGE_ID = "ready_to_receive_offer";

        static JSONObject prepareMessage(String receiverId, List<Server> servers) {
            System.out.println(MESSAGE_ID);
            JSONObject object = null;
            try {
                JSONArray jsonArray = new JSONArray();
                for (Server server : servers) {
                    jsonArray.put(new JSONObject()
                            .put("url", server.getUrl())
                            .put("urls", server.getUrl())
                            .put("username", server.getUsername())
                            .put("credential", server.getCredential()));
                }
                object = new JSONObject()
                        .put("receiverId", receiverId)
                        .put("iceServers", new JSONObject()
                                .put("iceServers", jsonArray));
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
            return object;
        }
    }

    private static class MessageSdp {
        static JSONObject prepareMessage(String receiverId, SessionDescription sessionDescription) {
            JSONObject result = new JSONObject();
            try {
                result.put("receiverId", receiverId)
                        .put("message", new JSONObject()
                                .put("type", sessionDescription.type.canonicalForm())
                                .put("sdp", sessionDescription.description));
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
            return result;
        }
    }

    private static class MessageSdpAnswer extends MessageSdp {
        static final String MESSAGE_ID = "sdp_answer";
    }

    private static class MessageSdpOffer extends MessageSdp {
        static final String MESSAGE_ID = "sdp_offer";
    }

    private static class MessageIceCandidate {
        static final String MESSAGE_ID = "ice_candidate";

        static JSONObject prepareMessage(String receiverId, IceCandidate iceCandidate) {
            JSONObject object = new JSONObject();
            try {
                object.put("receiverId", receiverId)
                        .put("message", new JSONObject()
                                .put("candidate", iceCandidate.sdp)
                                .put("sdpMid", iceCandidate.sdpMid)
                                .put("sdpMLineIndex",iceCandidate.sdpMLineIndex));
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
            return object;
        }
    }

    public class SignalingChanelBinder extends Binder {

        public void register(ClientListListener clientListListener) {
            clientListListeners.add(clientListListener);
        }

        public void unregister(ClientListListener clientListListener) {
            clientListListeners.remove(clientListListener);
        }

        public void register(ClientListener clientListener) {
            peerClientListeners.put(clientListener.clientId(), clientListener);
        }

        public void unregister(ClientListener clientListener) {
            peerClientListeners.remove(clientListener.clientId());
        }

        public void sendReadyToReceiveOffer(String receiverId) {
            socket.emit(MessageReadyToReceiveOffer.MESSAGE_ID,
                    MessageReadyToReceiveOffer.prepareMessage(receiverId, myIceServersList));
        }

        public void sendSdpAnswer(String receiverId, SessionDescription sessionDescription) {
//            clients.get(receiverId).setLocalDescription(new SDescription(sessionDescription));
            socket.emit(MessageSdpAnswer.MESSAGE_ID, MessageSdpAnswer.prepareMessage(receiverId, sessionDescription));
        }

        public void sendSdpOffer(String receiverId, SessionDescription sessionDescription) {
            socket.emit(MessageSdpOffer.MESSAGE_ID, MessageSdpAnswer.prepareMessage(receiverId, sessionDescription));
        }

        public void sendIceCandidate(String receiverId, IceCandidate iceCandidate) {
            socket.emit(MessageIceCandidate.MESSAGE_ID, MessageIceCandidate.prepareMessage(receiverId, iceCandidate));
        }

        public void resetConnection() {
            lockClientListListeners();
            socket.disconnect();
            myIceServersList = new ArrayList<>();
            clients = new ConcurrentHashMap<>();
            newClients = new ConcurrentHashMap<>();
            socket.connect();
            unlockClientListListeners();
        }
    }

    private class MessageLoinRoom implements Emitter.Listener {
        static final String MESSAGE_ID = "join_room";

        @Override
        public void call(Object... args) {
            System.out.println(MESSAGE_ID);
            try {
                JSONObject object = new JSONObject()
                        .put("roomName", "/" + roomName)
                        .put("config", new JSONObject()
                                .put("isAudioEnabled", false)
                                .put("isVideoEnabled", true));
                socket.emit(MESSAGE_ID, object);
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }
    }

    private class EventNewClient implements Emitter.Listener {
        static final String EVENT_ID = "new_client";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                Client client = Util.extractClient(object.getJSONObject("client"));
                newClients.put(client.getId(), client);
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }

    }

    private class EventClientLeft implements Emitter.Listener {
        static final String EVENT_ID = "client_left";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                String clientId = object.getString("clientId");
                if (peerClientListeners.get(clientId) != null) {
                    peerClientListeners.get(clientId).disconnect();
                }
                SignalingChannelService.this.clients.remove(clientId);
                newClients.remove(clientId);
                notifyClientListeners();
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }

    }

    private class EventDeviceIdentified implements Emitter.Listener {
        static final String EVENT_ID = "device_identified";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                hmac = object
                        .getJSONObject("deviceCredentials")
                        .getString("hmac");
                uid = object
                        .getJSONObject("deviceCredentials")
                        .getJSONObject("credentials")
                        .getString("uuid");
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }

    }

    private class EventRoomJoined implements Emitter.Listener {
        static final String EVENT_ID = "room_joined";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            try {
                JSONObject object = (JSONObject) args[0];
                SignalingChannelService.this.selfId = object.getString("selfId");
                JSONArray clients = object
                        .getJSONObject("room")
                        .getJSONArray("clients");
                for (int i = 0; i < clients.length(); i++) {
                    Client client = Util.extractClient(clients.getJSONObject(i));
                    if (!client.getId().equals(SignalingChannelService.this.selfId)) {
                        newClients.put(client.getId(), client);
                    }
                }
                JSONArray iceServers = object
                        .getJSONObject("room")
                        .getJSONObject("iceServers")
                        .getJSONArray("iceServers");
                for (int i = 0; i < iceServers.length(); i++) {
                    myIceServersList.add(Util.extractServer(iceServers.getJSONObject(i)));
                }
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }
    }

    private class EventReadyToReceiveOffer implements Emitter.Listener {
        static final String EVENT_ID = "ready_to_receive_offer";
        static final String EVENT_ALIAS0 = "client_ready";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                String clientId = object.getString("clientId");
                if (newClients.containsKey(clientId) && !clients.containsKey(clientId)) {
                    Client client = newClients.remove(clientId);
                    JSONArray iceServers = object
                            .getJSONObject("iceServers")
                            .getJSONArray("iceServers");
                    for (int i = 0; i < iceServers.length(); i++) {
                        client.add(Util.extractServer(iceServers.getJSONObject(i)));
                    }
                    clients.put(clientId, client);
                    notifyClientListeners();
                }
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }
    }

    private class EventIceCandidate implements Emitter.Listener {
        static final String EVENT_ID = "ice_candidate";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                String clientId = object.getString("clientId");
                JSONObject message = object.getJSONObject("message");
                Client client = clients.get(clientId);
                CandidateServer candidateServer = new CandidateServer(message.getString("sdpMid"), message.getInt("sdpMLineIndex"), message.getString("candidate"));
                client.add(candidateServer);
                peerClientListeners.get(clientId).iceCandidate(candidateServer.toIceCandidate());
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }
    }

    private class EventSdpAnswer implements Emitter.Listener {
        static final String EVENT_ID = "sdp_answer";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                String clientId = object.getString("clientId");
                JSONObject message = object.getJSONObject("message");
                SDescription sDescription = new SDescription(message.getString("type"), message.getString("sdp"));
                clients.get(clientId).setSDescription(sDescription);
                peerClientListeners.get(clientId).answer(sDescription.toSessionDescription());
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }
    }

    private class EventSpdOffer implements Emitter.Listener {
        static final String EVENT_ID = "sdp_offer";

        @Override
        public void call(Object... args) {
            Log.v(TAG,EVENT_ID);
            Log.v(TAG, args[0].toString());
            JSONObject object = (JSONObject) args[0];
            try {
                String clientId = object.getString("clientId");
                JSONObject message = object.getJSONObject("message");
                SDescription sDescription = new SDescription(message.getString("type"), message.getString("sdp"));
                clients.get(clientId).setSDescription(sDescription);
                peerClientListeners.get(clientId).offer(sDescription.toSessionDescription());
            } catch (JSONException e) {
                Log.e(TAG,"Error",e);
            }
        }
    }
}
