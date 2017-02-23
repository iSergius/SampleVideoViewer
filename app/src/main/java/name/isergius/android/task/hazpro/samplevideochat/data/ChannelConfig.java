package name.isergius.android.task.hazpro.samplevideochat.data;

import java.io.Serializable;

/**
 * @author Sergey Kondratyev
 */

public class ChannelConfig implements Serializable {

    private String path = "/protocol/socket.io/v1/";
    private String query = "socketType=room";
    private String uri = "https://appear.in";
    private String[] transports = {"polling", "websocket"};
    private String protocol = "TLS";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String[] getTransports() {
        return transports;
    }

    public void setTransports(String[] transports) {
        this.transports = transports;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
