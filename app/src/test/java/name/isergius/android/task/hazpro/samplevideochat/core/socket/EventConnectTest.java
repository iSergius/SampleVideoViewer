package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.MessageConsumer;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventConnectTest {

    @Mock
    private MessageConsumer messageConsumer;

    private EventConnect eventConnect;

    @Before
    public void setUp() throws Exception {
        this.eventConnect = new EventConnect(messageConsumer);
    }

    @Test
    public void call() throws Exception {
        eventConnect.call(null);
        Mockito.verify(messageConsumer).selfConnected();
    }

}