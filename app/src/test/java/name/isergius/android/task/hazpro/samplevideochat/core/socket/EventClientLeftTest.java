package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;

import static org.junit.Assert.*;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventClientLeftTest {

    @Mock
    private ClientStore clientStore;

    private JSONObject arg;

    private String JSON = "{\"clientId\":\"5746768e-ddda-4189-86e8-ac687cdc0de3\"}";
    private EventClientLeft eventClientLeft;

    @Before
    public void setUp() throws Exception {
        this.arg = new JSONObject(JSON);
        this.eventClientLeft = new EventClientLeft(clientStore);
    }

    @Test
    public void call() throws Exception {
        String clientId = "5746768e-ddda-4189-86e8-ac687cdc0de3";
        eventClientLeft.call(arg);
        Mockito.verify(clientStore).delete(clientId);
    }

}