package name.isergius.android.task.hazpro.samplevideochat;

import android.app.Application;

import org.webrtc.PeerConnectionFactory;

/**
 * @author Sergey Kondratyev
 */

public class App extends Application {

    PeerConnectionFactory peerConnectionFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        PeerConnectionFactory.initializeAndroidGlobals(this, true, true, true);
        this.peerConnectionFactory = new PeerConnectionFactory();
    }

    public PeerConnectionFactory getPeerConnectionFactory() {
        return peerConnectionFactory;
    }
}
