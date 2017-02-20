package name.isergius.android.task.hazpro.samplevideochat.data;

import org.webrtc.SessionDescription;

import java.io.Serializable;

/**
 * @author Sergey Kondratyev
 */

public class SDescription implements Serializable {

    private static final long serialVersionUID = -7022463136772269092L;
    private String type;
    private String description;

    public SDescription(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public SessionDescription toSessionDescription() {
        return new SessionDescription(SessionDescription.Type.fromCanonicalForm(type),description);
    }
}
