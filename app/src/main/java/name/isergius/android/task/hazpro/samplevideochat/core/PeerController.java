package name.isergius.android.task.hazpro.samplevideochat.core;

import android.util.Log;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;

/**
 * @author Sergey Kondratyev
 */

public class PeerController implements PeerListener, SdpObserver, PeerConnection.Observer {

    private static final String TAG = PeerController.class.getSimpleName();
    private final ClientStore clientStore;
    private final PeerConnectionFactory peerConnectionFactory;
    private final PeerMessageProducer peerMessageProducer;
    private Client client;
    private VideoWindow videoWindow;
    private PeerConnection peerConnection;
    private MediaConstraints mediaConstraints = new MediaConstraints();


    public PeerController(String clientId, ClientStore clientStore, PeerConnectionFactory peerConnectionFactory, PeerMessageProducer peerMessageProducer) throws StoreException {
        this.clientStore = clientStore;
        this.client = this.clientStore.read(clientId);
        this.peerConnectionFactory = peerConnectionFactory;
        this.peerMessageProducer = peerMessageProducer;
        this.peerConnection = peerConnectionFactory.createPeerConnection(client.getIceServers(), mediaConstraints, this);
        peerMessageProducer.sendReadyToReceiveOffer(clientId(), clientStore.readSelf().getServers());
    }

    public void setView(VideoWindow videoWindow) {
        Log.v(TAG, "setView " + videoWindow);
        this.videoWindow = videoWindow;
    }

    @Override
    public String clientId() {
        Log.v(TAG, "clientId " + client.getId());
        return client.getId();
    }

    @Override
    public void disconnect() {
        Log.v(TAG, "disconnect");
        peerConnection.close();
    }

    @Override
    public void sessionDescription(SDescription sessionDescription) {
        Log.v(TAG, "sessionDescription " + sessionDescription);
        peerConnection.setRemoteDescription(this, sessionDescription.toSessionDescription());
        peerConnection.createAnswer(this, mediaConstraints);
    }

    @Override
    public void candidateServer(CandidateServer iceCandidate) {
        Log.v(TAG, "candidateServer " + iceCandidate);
        peerConnection.addIceCandidate(iceCandidate.toIceCandidate());
    }


    @Override
    public void onSetFailure(String s) {
        Log.v(TAG, "onSetFailure " + s);
    }

    @Override
    public void onCreateFailure(String s) {
        Log.v(TAG, "onCreateFailure " + s);
    }

    @Override
    public void onSetSuccess() {
        Log.v(TAG, "onSetSuccess ");
    }

    @Override
    public void onCreateSuccess(SessionDescription sessionDescription) {
        Log.v(TAG, "onCreateSuccess " + sessionDescription);
        peerConnection.setLocalDescription(this, sessionDescription);
        peerMessageProducer.sendSdpAnswer(clientId(), sessionDescription);
    }


    @Override
    public void onSignalingChange(PeerConnection.SignalingState signalingState) {
        Log.v(TAG, "onSignalingChange " + signalingState);
    }

    @Override
    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        Log.v(TAG, "onIceConnectionChange " + iceConnectionState);
    }

    @Override
    public void onIceConnectionReceivingChange(boolean b) {
        Log.v(TAG, "onIceConnectionReceivingChange " + b);
    }

    @Override
    public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
        Log.v(TAG, "onIceGatheringChange " + iceGatheringState);
    }

    @Override
    public void onIceCandidate(IceCandidate iceCandidate) {
        Log.v(TAG, "onIceCandidate " + iceCandidate);
        peerMessageProducer.sendIceCandidate(clientId(), iceCandidate);
    }

    @Override
    public void onAddStream(MediaStream mediaStream) {
        Log.v(TAG, "onAddStream " + mediaStream);
        videoWindow.show(mediaStream.videoTracks.getFirst());
    }

    @Override
    public void onRemoveStream(MediaStream mediaStream) {
        Log.v(TAG, "onRemoveStream " + mediaStream);
        videoWindow.hide();
    }

    @Override
    public void onDataChannel(DataChannel dataChannel) {
        Log.v(TAG, "onDataChannel " + dataChannel);
    }

    @Override
    public void onRenegotiationNeeded() {
        Log.v(TAG, "onRenegotiationNeeded ");
    }
}
