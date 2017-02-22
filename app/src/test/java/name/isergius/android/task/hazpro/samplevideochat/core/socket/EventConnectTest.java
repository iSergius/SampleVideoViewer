package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventConnectTest {

    @Mock
    private ClientStore clientStore;

    private EventConnect eventConnect;

    @Before
    public void setUp() throws Exception {
        this.eventConnect = new EventConnect(clientStore);
    }

    @Test
    public void call() throws Exception {
        eventConnect.call(null);
        Mockito.verify(clientStore).connected();
    }

}