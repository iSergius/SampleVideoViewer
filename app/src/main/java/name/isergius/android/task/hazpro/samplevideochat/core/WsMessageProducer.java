package name.isergius.android.task.hazpro.samplevideochat.core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.util.List;
import java.util.Set;

import io.socket.client.Socket;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

public class WsMessageProducer implements MessageProducer, PeerMessageProducer {

    private static final String TAG = WsMessageProducer.class.getSimpleName();
    private Socket socket;

    public WsMessageProducer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void sendJoinRoom(RoomConfig roomConfig) {
        String MESSAGE_ID = "join_room";
        JSONObject object = new JSONObject();
        try {
            object
                    .put("roomName", '/' + roomConfig.getName())
                    .put("config", new JSONObject()
                            .put("isVideoEnabled", roomConfig.isVideoEnabled())
                            .put("isAudioEnabled", roomConfig.isAudioEnabled()));
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
        socket.emit(MESSAGE_ID, object);
        Log.v(TAG,MESSAGE_ID+" "+object);
    }

    @Override
    public void sendIceCandidate(String receiverId, IceCandidate iceCandidate) {
        String MESSAGE_ID = "ice_candidate";
        JSONObject object = new JSONObject();
        try {
            object.put("receiverId", receiverId)
                    .put("message", new JSONObject()
                            .put("candidate", iceCandidate.sdp)
                            .put("sdpMid", iceCandidate.sdpMid)
                            .put("sdpMLineIndex", iceCandidate.sdpMLineIndex));
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
        socket.emit(MESSAGE_ID, object);
        Log.v(TAG,MESSAGE_ID+" "+object);
    }

    @Override
    public void sendReadyToReceiveOffer(String receiverId, Set<Server> servers) {
        String MESSAGE_ID = "ready_to_receive_offer";
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
            Log.e(TAG, "Error", e);
        }
        socket.emit(MESSAGE_ID, object);
        Log.v(TAG,MESSAGE_ID+" "+object);
    }

    @Override
    public void sendSdpAnswer(String receiverId, SessionDescription sessionDescription) {
        String MESSAGE_ID = "sdp_answer";
        JSONObject object = prepareSdpMessage(receiverId, sessionDescription);
        socket.emit(MESSAGE_ID, object);
        Log.v(TAG,MESSAGE_ID+" "+object);
    }

    @Override
    public void sendSdpOffer(String receiverId, SessionDescription sessionDescription) {
        String MESSAGE_ID = "sdp_offer";
        JSONObject object = prepareSdpMessage(receiverId, sessionDescription);
        socket.emit(MESSAGE_ID, object);
        Log.v(TAG,MESSAGE_ID+" "+object);
    }

    private JSONObject prepareSdpMessage(String receiverId, SessionDescription sessionDescription) {
        JSONObject result = new JSONObject();
        try {
            result.put("receiverId", receiverId)
                    .put("message", new JSONObject()
                            .put("type", sessionDescription.type.canonicalForm())
                            .put("sdp", sessionDescription.description));
        } catch (JSONException e) {
            Log.e(TAG, "Error", e);
        }
        return result;
    }

}
