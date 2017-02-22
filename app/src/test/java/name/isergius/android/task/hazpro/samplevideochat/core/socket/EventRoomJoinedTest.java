package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventRoomJoinedTest {

    @Mock
    private MessageConsumer messageConsumer;

    private EventRoomJoined eventRoomJoined;

    private JSONObject arg;
    private String JSON = "{\"room\":{\"clients\":[{\"id\":\"335386be-0a63-4d01-9476-759a54877b0e\",\"deviceId\":\"3f0c1829-31c8-4d5c-a01a-34b75043b393\",\"userId\":null,\"name\":\"Anonymous 335386be-0a63-4d01-9476-759a54877b0e\",\"streams\":[\"0\"],\"isOwner\":false,\"isAudioEnabled\":false,\"isVideoEnabled\":true,\"socketId\":\"7iEdwsJDeiW28RMUDFXV\",\"role\":{\"roleName\":\"visitor\"}},{\"id\":\"82c7cd22-6b7d-4866-ab7d-4a1aa2c85403\",\"deviceId\":\"6a5c7759-0c7b-4ccd-a076-8a4155e58ee3\",\"userId\":null,\"name\":\"Anonymous 82c7cd22-6b7d-4866-ab7d-4a1aa2c85403\",\"streams\":[\"0\"],\"isOwner\":false,\"isAudioEnabled\":true,\"isVideoEnabled\":true,\"socketId\":\"XElhT6i2yZVrvNd7DmH6\",\"role\":{\"roleName\":\"visitor\"}}],\"name\":\"/t\",\"owners\":[],\"sfuServer\":null,\"iceServers\":{\"iceServers\":[{\"url\":\"turn:turn.appear.in:443?transport=udp\",\"urls\":\"turn:turn.appear.in:443?transport=udp\",\"username\":\"appearin:1487733517\",\"credential\":\"Cbi9jjvM/pWOcOsfeGxLOeQbcOU=\"},{\"url\":\"turn:turn.appear.in:443?transport=tcp\",\"urls\":\"turn:turn.appear.in:443?transport=tcp\",\"username\":\"appearin:1487733517\",\"credential\":\"Cbi9jjvM/pWOcOsfeGxLOeQbcOU=\"},{\"url\":\"turns:turn.appear.in:443?transport=tcp\",\"urls\":\"turns:turn.appear.in:443?transport=tcp\",\"username\":\"appearin:1487733517\",\"credential\":\"Cbi9jjvM/pWOcOsfeGxLOeQbcOU=\"}]},\"isClaimed\":true,\"type\":\"free\",\"isFollowEnabled\":true,\"isLocked\":false,\"knockers\":[],\"knockPage\":{}},\"selfId\":\"82c7cd22-6b7d-4866-ab7d-4a1aa2c85403\"}";

    @Before
    public void setUp() throws Exception {
        arg = new JSONObject(JSON);
        eventRoomJoined = new EventRoomJoined(messageConsumer);
    }


    @Test
    public void call() throws Exception {
        Client expectedClient = new Client("82c7cd22-6b7d-4866-ab7d-4a1aa2c85403", null, null, null);
        expectedClient.add(new Server("Cbi9jjvM/pWOcOsfeGxLOeQbcOU=","appearin:1487733517","turn:turn.appear.in:443?transport=udp"));
        expectedClient.add(new Server("Cbi9jjvM/pWOcOsfeGxLOeQbcOU=","appearin:1487733517","turn:turn.appear.in:443?transport=tcp"));
        expectedClient.add(new Server("Cbi9jjvM/pWOcOsfeGxLOeQbcOU=","appearin:1487733517","turns:turn.appear.in:443?transport=tcp"));
        eventRoomJoined.call(arg);
        Mockito.verify(messageConsumer).selfData(expectedClient);
        Mockito.verify(messageConsumer).clientData(ArgumentMatchers.any(Client.class));
    }

}