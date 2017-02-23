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

import org.webrtc.RendererCommon;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;
import org.webrtc.VideoTrack;

import name.isergius.android.task.hazpro.samplevideochat.R;
import name.isergius.android.task.hazpro.samplevideochat.core.ChannelService;
import name.isergius.android.task.hazpro.samplevideochat.core.PeerController;
import name.isergius.android.task.hazpro.samplevideochat.core.PeerListener;
import name.isergius.android.task.hazpro.samplevideochat.core.VideoWindow;

/**
 * @author Sergey Kondratyev
 */

public class PeerVideoActivity extends AppCompatActivity {

    private static final String TAG = PeerVideoActivity.class.getSimpleName();

    private ChannelService.Facade facade;
    private VideoView videoView;
    private SrvCon srvConn;
    private PeerController peerController;
    private String clientId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        this.videoView = new VideoView();
        if (getIntent().hasExtra(PeerListActivity.PROPERTY_CLIENT_ID)) {
            clientId = getIntent().getExtras().getString(PeerListActivity.PROPERTY_CLIENT_ID);
        }
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(srvConn);
    }

    private void bindService() {
        Intent intent = new Intent(this, ChannelService.class);
        srvConn = new SrvCon();
        bindService(intent, srvConn, BIND_AUTO_CREATE);
    }

    private class SrvCon implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            facade = (ChannelService.Facade) service;
            try {
                peerController = facade.build(clientId);
                peerController.setView(videoView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private class VideoView implements VideoWindow {
        private GLSurfaceView videoView = (GLSurfaceView) findViewById(R.id.video_view);
        private VideoRenderer videoRenderer;

        VideoView() {
            VideoRendererGui.setView(videoView, null);
        }

        public void show(VideoTrack videoTrack) {
            Log.v(TAG, "show");
            try {
                videoRenderer = VideoRendererGui.createGui(0, 0, 100, 100, RendererCommon.ScalingType.SCALE_ASPECT_FILL, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoTrack.addRenderer(videoRenderer);
        }

        @Override
        public void hide() {
            videoRenderer.dispose();
        }

    }
}
