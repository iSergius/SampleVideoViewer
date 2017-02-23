package name.isergius.android.task.hazpro.samplevideochat.core;

import org.webrtc.VideoTrack;

/**
 * @author Sergey Kondratyev
 */

public interface VideoWindow {

    void show(VideoTrack videoTrack);

    void hide();
}
