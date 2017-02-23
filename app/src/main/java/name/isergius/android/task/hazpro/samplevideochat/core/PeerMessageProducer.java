package name.isergius.android.task.hazpro.samplevideochat.core;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.util.Set;

import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

public interface PeerMessageProducer {

    void sendIceCandidate(String receiverId, IceCandidate iceCandidate);

    void sendReadyToReceiveOffer(String receiverId, Set<Server> servers);

    void sendSdpAnswer(String receiverId, SessionDescription sessionDescription);

    void sendSdpOffer(String receiverId, SessionDescription sessionDescription);
}
