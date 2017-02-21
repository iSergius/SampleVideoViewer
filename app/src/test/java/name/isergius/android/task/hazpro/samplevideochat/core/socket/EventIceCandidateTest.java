package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;
import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventIceCandidateTest {

    @Mock
    private ClientStore clientStore;

    private EventIceCandidate eventIceCandidate;

    private JSONObject arg;
    private String JSON = "{\"clientId\":\"6236007a-6651-453b-8701-fe127541e048\",\"message\":{\"sdpMid\":\"audio\",\"sdpMLineIndex\":0,\"candidate\":\"candidate:316526476 1 udp 2122260223 192.168.0.20 37695 typ host generation 0\"}}";

    @Before
    public void setUp() throws Exception {
        arg = new JSONObject(JSON);
        eventIceCandidate = new EventIceCandidate(clientStore);
    }

    @Test
    public void call() throws Exception {
        String expectedClientId = "6236007a-6651-453b-8701-fe127541e048";
        CandidateServer expectedCandidateServer = new CandidateServer("audio", 0, "candidate:316526476 1 udp 2122260223 192.168.0.20 37695 typ host generation 0");
        eventIceCandidate.call(arg);
        Mockito.verify(clientStore).addCandidateServer(expectedClientId, expectedCandidateServer);
    }

}