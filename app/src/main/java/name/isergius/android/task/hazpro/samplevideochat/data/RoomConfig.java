package name.isergius.android.task.hazpro.samplevideochat.data;

import java.io.Serializable;

/**
 * @author Sergey Kondratyev
 */
public class RoomConfig implements Serializable {

    private static final long serialVersionUID = -6502762862191049844L;

    private String name;
    private boolean audioEnabled;
    private boolean videoEnabled;

    public RoomConfig(String name, boolean audioEnabled, boolean videoEnabled) {
        this.name = name;
        this.audioEnabled = audioEnabled;
        this.videoEnabled = videoEnabled;
    }

    public String getName() {
        return name;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomConfig that = (RoomConfig) o;

        if (audioEnabled != that.audioEnabled) return false;
        if (videoEnabled != that.videoEnabled) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (audioEnabled ? 1 : 0);
        result = 31 * result + (videoEnabled ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoomConfig{" +
                "name='" + name + '\'' +
                ", audioEnabled=" + audioEnabled +
                ", videoEnabled=" + videoEnabled +
                '}';
    }
}
