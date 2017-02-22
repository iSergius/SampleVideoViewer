package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventNewClientTest {

    @Mock
    private ClientStore clientStore;

    private JSONObject arg;
    private String JSON = "{\"client\": {\"id\": \"6236007a-6651-453b-8701-fe127541e048\",\"deviceId\": \"3f0c1829-31c8-4d5c-a01a-34b75043b393\",\"userId\": null,\"name\": \"Anonymous 6236007a-6651-453b-8701-fe127541e048\",\"streams\": [\"0\"],\"isOwner\": false,\"isAudioEnabled\": false,\"isVideoEnabled\": true,\"socketId\": \"vrr4acHsn5kxed-OCNi-\",\"role\": {\"roleName\": \"visitor\"}},\"room\": {\"clients\": [{\"id\": \"dca3ba16-9853-4c5e-ad04-c553f816f4b9\",\"deviceId\": \"6a5c7759-0c7b-4ccd-a076-8a4155e58ee3\",\"userId\": null,\"name\": \"Anonymous dca3ba16-9853-4c5e-ad04-c553f816f4b9\",\"streams\": [\"0\"],\"isOwner\": false,\"isAudioEnabled\": false,\"isVideoEnabled\": true,\"socketId\": \"4ZYwBkXYxGDXLFAbCC3L\",\"role\": {\"roleName\": \"visitor\"}},{\"id\": \"6236007a-6651-453b-8701-fe127541e048\",\"deviceId\": \"3f0c1829-31c8-4d5c-a01a-34b75043b393\",\"userId\": null,\"name\": \"Anonymous 6236007a-6651-453b-8701-fe127541e048\",\"streams\": [\"0\"],\"isOwner\": false,\"isAudioEnabled\": false,\"isVideoEnabled\": true,\"socketId\": \"vrr4acHsn5kxed-OCNi-\",\"role\": {\"roleName\": \"visitor\"}}],\"name\": \"/t\",\"owners\": []}}";

    private EventNewClient eventNewClient;

    @Before
    public void setUp() throws Exception {
        arg = new JSONObject(JSON);
        eventNewClient = new EventNewClient(clientStore);
    }

    @Test
    public void call() throws Exception {
        Client expectedClient = new Client(
                "6236007a-6651-453b-8701-fe127541e048",
                "3f0c1829-31c8-4d5c-a01a-34b75043b393",
                "null",
                "Anonymous 6236007a-6651-453b-8701-fe127541e048");
        eventNewClient.call(arg);
        Mockito.verify(clientStore).save(expectedClient);

    }

}