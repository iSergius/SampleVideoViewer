package name.isergius.android.task.hazpro.samplevideochat.data;

import org.webrtc.PeerConnection;

import java.io.Serializable;

/**
 * @author Sergey Kondratyev
 */

public class Server implements Serializable {

    private static final long serialVersionUID = -8249234420408936173L;
    private String credential;
    private String username;
    private String url;

    public Server(String credential, String username, String url) {
        this.credential = credential;
        this.username = username;
        this.url = url;
    }

    public PeerConnection.IceServer toIceServer() {
        return new PeerConnection.IceServer(this.url, this.username, this.credential);
    }

    public String getCredential() {
        return credential;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;

        if (credential != null ? !credential.equals(server.credential) : server.credential != null)
            return false;
        if (username != null ? !username.equals(server.username) : server.username != null)
            return false;
        return url != null ? url.equals(server.url) : server.url == null;

    }

    @Override
    public int hashCode() {
        int result = credential != null ? credential.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Server{" +
                "credential='" + credential + '\'' +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
