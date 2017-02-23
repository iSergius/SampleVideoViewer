package name.isergius.android.task.hazpro.samplevideochat.data;

import org.webrtc.IceCandidate;
import org.webrtc.PeerConnection;
import org.webrtc.SessionDescription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Sergey Kondratyev
 */

public class Client implements Serializable {

    public static final String PROPERTY = "name.isergius.android.task.hazpro.samplevideochat.data.Client";
    private static final long serialVersionUID = 3846554733168063404L;
    private String id;
    private String deviceId;
    private String userId;
    private String name;
    private Set<Server> servers = new HashSet<>();
    private List<CandidateServer> candidateServers = new LinkedList<>();
    private boolean ready;

    private String socketId;
    private boolean isVideoEnabled;
    private boolean isAudioEnabled;
    private String spd;
    private SDescription sessionDescription;
    private SDescription localDescription;

    public Client(String id, String deviceId, String userId, String name) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.name = name;
    }

    public List<PeerConnection.IceServer> getIceServers() {
        List<PeerConnection.IceServer> result = new ArrayList<PeerConnection.IceServer>() {{
            add(new PeerConnection.IceServer("stun:stun.l.google.com:19302"));
        }};
        for (Server server : servers) {
            result.add(server.toIceServer());
        }
        return result;
    }

    public List<IceCandidate> getIceCandidates() {
        List<IceCandidate> result = new ArrayList<>();
        for (CandidateServer candidateServer : candidateServers) {
            result.add(new IceCandidate(candidateServer.getSdpMid(), candidateServer.getSdpMLineIndex(), candidateServer.getSdp()));
        }
        return result;
    }

    public SessionDescription getSessionDescription() {
        return sessionDescription.toSessionDescription();
    }

    public void add(Server server) {
        this.servers.add(server);
        this.ready = true;
    }

    public void add(CandidateServer candidateServer) {
        this.candidateServers.add(candidateServer);
    }

    public String getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Set<Server> getServers() {
        return servers;
    }

    public List<CandidateServer> getCandidateServers() {
        return candidateServers;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public boolean isVideoEnabled() {
        return isVideoEnabled;
    }

    public void setVideoEnabled(boolean videoEnabled) {
        isVideoEnabled = videoEnabled;
    }

    public boolean isAudioEnabled() {
        return isAudioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        isAudioEnabled = audioEnabled;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }

    public SDescription getSDescription() {
        return sessionDescription;
    }

    public void setSDescription(SDescription sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public SDescription getLocalDescription() {
        return localDescription;
    }

    public void setLocalDescription(SDescription localDescription) {
        this.localDescription = localDescription;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (isVideoEnabled != client.isVideoEnabled) return false;
        if (isAudioEnabled != client.isAudioEnabled) return false;
        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (deviceId != null ? !deviceId.equals(client.deviceId) : client.deviceId != null)
            return false;
        if (userId != null ? !userId.equals(client.userId) : client.userId != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (servers != null ? !servers.equals(client.servers) : client.servers != null)
            return false;
        if (candidateServers != null ? !candidateServers.equals(client.candidateServers) : client.candidateServers != null)
            return false;
        if (socketId != null ? !socketId.equals(client.socketId) : client.socketId != null)
            return false;
        return spd != null ? spd.equals(client.spd) : client.spd == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (servers != null ? servers.hashCode() : 0);
        result = 31 * result + (candidateServers != null ? candidateServers.hashCode() : 0);
        result = 31 * result + (socketId != null ? socketId.hashCode() : 0);
        result = 31 * result + (isVideoEnabled ? 1 : 0);
        result = 31 * result + (isAudioEnabled ? 1 : 0);
        result = 31 * result + (spd != null ? spd.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", servers=" + servers +
                ", candidateServers=" + candidateServers +
                ", socketId='" + socketId + '\'' +
                ", isVideoEnabled=" + isVideoEnabled +
                ", isAudioEnabled=" + isAudioEnabled +
                ", spd='" + spd + '\'' +
                '}';
    }
}
