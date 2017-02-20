package name.isergius.android.task.hazpro.samplevideochat.data;

import org.webrtc.IceCandidate;

import java.io.Serializable;

/**
 * @author Sergey Kondratyev
 */

public class CandidateServer implements Serializable {

    private static final long serialVersionUID = -388817744922852710L;
    private String sdpMid;
    private int sdpMLineIndex;
    private String sdp;

    public CandidateServer(String sdpMid, int sdpMLineIndex, String sdp) {
        this.sdpMid = sdpMid;
        this.sdpMLineIndex = sdpMLineIndex;
        this.sdp = sdp;
    }

    public IceCandidate toIceCandidate() {
        return new IceCandidate(sdpMid,sdpMLineIndex,sdp);
    }

    public String getSdpMid() {
        return sdpMid;
    }

    public int getSdpMLineIndex() {
        return sdpMLineIndex;
    }

    public String getSdp() {
        return sdp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateServer that = (CandidateServer) o;

        if (sdpMLineIndex != that.sdpMLineIndex) return false;
        if (sdpMid != null ? !sdpMid.equals(that.sdpMid) : that.sdpMid != null) return false;
        return sdp != null ? sdp.equals(that.sdp) : that.sdp == null;

    }

    @Override
    public int hashCode() {
        int result = sdpMid != null ? sdpMid.hashCode() : 0;
        result = 31 * result + sdpMLineIndex;
        result = 31 * result + (sdp != null ? sdp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CandidateServer{" +
                "sdpMid='" + sdpMid + '\'' +
                ", sdpMLineIndex=" + sdpMLineIndex +
                ", sdp='" + sdp + '\'' +
                '}';
    }
}
