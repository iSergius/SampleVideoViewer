package name.isergius.android.task.hazpro.samplevideochat.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.NetworkMonitor;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RendererCommon;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.ThreadUtils;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;
import org.webrtc.VideoTrack;

import name.isergius.android.task.hazpro.samplevideochat.R;
import name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;

import static name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService.INTENT_FILTER;

/**
 * @author Sergey Kondratyev
 */

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();

    private MediaConstraints mediaConstraints = new MediaConstraints();
    private PeerConnectionObserver peerConnectionObserver = new PeerConnectionObserver();
    private PeerConnectionFactory peerConnectionFactory;
    private PeerConnection peerConnection;

    private VideoView videoView;

    private ServiceConnectionHandler serviceConnectionHandler;
    private ClientObserver clientObserver;
    private SignalingChannelService.SignalingChanelBinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = new VideoView();
        Client client = (Client) getIntent().getExtras().getSerializable(Client.PROPERTY);
        this.clientObserver = new ClientObserver(client);
        bindService();
        PeerConnectionFactory.initializeAndroidGlobals(this, true, true, true);
        peerConnectionFactory = new PeerConnectionFactory();
        peerConnection = peerConnectionFactory.createPeerConnection(client.getIceServers(), mediaConstraints, new PeerConnectionObserver());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
        binder.unregister(clientObserver);
        if (peerConnection != null) {
            peerConnection.close();
        }
    }

    private void bindService() {
        this.serviceConnectionHandler = new ServiceConnectionHandler();
        Intent intent = new Intent(this, SignalingChannelService.class);
        bindService(intent, serviceConnectionHandler, BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(serviceConnectionHandler);
    }

    private class VideoView {
        private GLSurfaceView videoView = (GLSurfaceView) findViewById(R.id.video_view);
        private VideoRenderer videoRenderer;

        VideoView() {
            VideoRendererGui.setView(videoView, null);
        }

        void show(VideoTrack videoTrack) throws Exception {
            Log.v(TAG, "show");
            videoRenderer = VideoRendererGui.createGui(0, 0, 100, 100, RendererCommon.ScalingType.SCALE_ASPECT_FILL, false);
            videoTrack.addRenderer(videoRenderer);
        }

    }

    private class ClientObserver implements SignalingChannelService.ClientListener {
        private Client client;

        ClientObserver(Client client) {
            this.client = client;
        }

        @Override
        public String clientId() {
            return client.getId();
        }

        @Override
        public void iceCandidate(IceCandidate iceCandidate) {
            peerConnection.addIceCandidate(iceCandidate);
        }

        @Override
        public void offer(SessionDescription sessionDescription) {
            Log.v(TAG, "offer");
            Log.v(TAG, sessionDescription.description);
            peerConnection.setRemoteDescription(new Spd(), sessionDescription);
            peerConnection.createAnswer(new SpdAnswer(), mediaConstraints);
        }

        @Override
        public void answer(SessionDescription sessionDescription) {
            Log.v(TAG, "answer");
            Log.v(TAG, sessionDescription.description);
            peerConnection.setRemoteDescription(new Spd(), sessionDescription);
        }

        @Override
        public void disconnect() {
            peerConnection.close();
        }

    }

    private class Spd implements SdpObserver {

        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {
            Log.v(TAG, "Sdp onCreateSuccess " + sessionDescription.description);
        }

        @Override
        public void onSetSuccess() {
            Log.v(TAG, "Sdp onSetSuccess");
        }

        @Override
        public void onCreateFailure(String s) {
            Log.v(TAG, "Sdp onCreateFailure" + s);
        }

        @Override
        public void onSetFailure(String s) {
            Log.v(TAG, "Sdp onSetFailure" + s);
        }
    }

    private class SpdOffer extends Spd {
        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {
            peerConnection.setLocalDescription(new Spd(), sessionDescription);
            binder.sendSdpOffer(clientObserver.clientId(), sessionDescription);
        }
    }

    private class SpdAnswer extends Spd {
        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {
            peerConnection.setLocalDescription(new Spd(), sessionDescription);
            binder.sendSdpAnswer(clientObserver.clientId(), sessionDescription);
        }
    }

    private class ServiceConnectionHandler implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (SignalingChannelService.SignalingChanelBinder) service;
            binder.register(clientObserver);
            binder.sendReadyToReceiveOffer(clientObserver.clientId());
//            peerConnection.createOffer(new SpdOffer(), mediaConstraints);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private class PeerConnectionObserver implements PeerConnection.Observer {

        @Override
        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
            Log.v(TAG, "onSignalingChange");
            Log.v(TAG, signalingState.toString());
        }

        @Override
        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            Log.v(TAG, "onIceConnectionChange");
            Log.v(TAG, iceConnectionState.toString());
        }

        @Override
        public void onIceConnectionReceivingChange(boolean b) {
            Log.v(TAG, "onIceConnectionReceivingChange");
            Log.v(TAG, String.valueOf(b));
        }

        @Override
        public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
            Log.v(TAG, "onIceGatheringChange");
            Log.v(TAG, iceGatheringState.toString());
        }

        @Override
        public void onIceCandidate(IceCandidate iceCandidate) {
            Log.v(TAG, "onIceCandidate");
            Log.v(TAG, iceCandidate.toString());
            binder.sendIceCandidate(clientObserver.clientId(), iceCandidate);
        }

        @Override
        public void onAddStream(MediaStream mediaStream) {
            try {
                Log.v(TAG, " Video Tracks");
                Log.v(TAG, String.valueOf(mediaStream.videoTracks.size()));
                for (VideoTrack videoTrack : mediaStream.videoTracks) {
                    Log.v(TAG, "id " + videoTrack.id());
                    Log.v(TAG, "enabled " + videoTrack.enabled());
                    Log.v(TAG, "kind " + videoTrack.kind());
                    Log.v(TAG, "state " + videoTrack.state());
                }
                videoView.show(mediaStream.videoTracks.getFirst());
            } catch (Exception e) {
                Log.e(TAG,"Error",e);
            }
        }

        @Override
        public void onRemoveStream(MediaStream mediaStream) {
            Log.v(TAG, "onRemoveStream");
            Log.v(TAG, mediaStream.toString());
        }

        @Override
        public void onDataChannel(DataChannel dataChannel) {
            Log.v(TAG, "onDataChannel");
            Log.v(TAG, dataChannel.toString());
        }

        @Override
        public void onRenegotiationNeeded() {
            Log.v(TAG, "onRenegotiationNeeded");
        }
    }

}
