package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventReadyToReceiveOfferTest {
    @Mock
    private MessageConsumer messageConsumer;

    private JSONObject arg;

    private String JSON = "{\"iceServers\":{\"iceServers\":[{\"url\":\"turn:turn.appear.in:443?transport=udp\",\"urls\":\"turn:turn.appear.in:443?transport=udp\",\"username\":\"appearin:1487381191\",\"credential\":\"j/1YtwTvE6yqlb4l/sbYWW1oA64=\"},{\"url\":\"turn:turn.appear.in:443?transport=tcp\",\"urls\":\"turn:turn.appear.in:443?transport=tcp\",\"username\":\"appearin:1487381191\",\"credential\":\"j/1YtwTvE6yqlb4l/sbYWW1oA64=\"},{\"url\":\"turns:turn.appear.in:443?transport=tcp\",\"urls\":\"turns:turn.appear.in:443?transport=tcp\",\"username\":\"appearin:1487381191\",\"credential\":\"j/1YtwTvE6yqlb4l/sbYWW1oA64=\"}]},\"clientId\":\"6236007a-6651-453b-8701-fe127541e048\",\"deviceId\":\"6a5c7759-0c7b-4ccd-a076-8a4155e58ee3\"}";
    private EventReadyToReceiveOffer eventReadyToReceiveOffer;

    @Before
    public void setUp() throws Exception {
        this.arg = new JSONObject(JSON);
        this.eventReadyToReceiveOffer = new EventReadyToReceiveOffer(messageConsumer);
    }

    @Test
    public void call() throws Exception {
        String clientId = "6236007a-6651-453b-8701-fe127541e048";
        eventReadyToReceiveOffer.call(arg);
        Mockito.verify(messageConsumer).clientServer(clientId, new Server("j/1YtwTvE6yqlb4l/sbYWW1oA64=", "appearin:1487381191", "turn:turn.appear.in:443?transport=udp"));
    }

}