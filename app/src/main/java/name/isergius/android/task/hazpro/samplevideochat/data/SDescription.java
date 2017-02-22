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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SDescription that = (SDescription) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SDescription{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
