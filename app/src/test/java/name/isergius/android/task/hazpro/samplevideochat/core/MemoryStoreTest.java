package name.isergius.android.task.hazpro.samplevideochat.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.data.ChannelConfig;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;

/**
 * @author Sergey Kondratyev
 */
public class MemoryStoreTest {

    private String expectedClientId = "6236007a-6651-453b-8701-fe127541e048";
    private String expectedDeviceId = "3f0c1829-31c8-4d5c-a01a-34b75043b393";
    private String expectedUserId = null;
    private String expectedName = "Anonymous 6236007a-6651-453b-8701-fe127541e048";
    private Client expectedClient = new Client(expectedClientId, expectedDeviceId, expectedUserId, expectedName);
    private MemoryStore memoryClientStore = new MemoryStore(Arrays.asList(expectedClient));

    @Test
    public void read() throws Exception {
        Client actualClient = memoryClientStore.readClient(expectedClientId);
        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test(expected = StoreException.class)
    public void readNotContain() throws Exception {
        String clientId = "dca3ba16-9853-4c5e-ad04-c553f816f4b9";
        memoryClientStore.readClient(clientId);
    }

    @Test
    public void readAll() throws Exception {
        List<Client> allClients = memoryClientStore.readAllClients();
        Assert.assertEquals(Arrays.asList(expectedClient), allClients);
    }

    @Test
    public void readAllReadyClients() throws Exception {
        memoryClientStore.saveSelf(new Client("1",null,null,null));
        List<Client> allClients = memoryClientStore.readAllReadyClients();
        System.out.println(allClients);
        Assert.assertTrue(allClients.isEmpty());
    }

    @Test
    public void save() throws Exception {
        String clientId = "dca3ba16-9853-4c5e-ad04-c553f816f4b9";
        Client expectedClient = new Client(clientId,
                "6a5c7759-0c7b-4ccd-a076-8a4155e58ee3",
                null,
                "Anonymous dca3ba16-9853-4c5e-ad04-c553f816f4b9");
        memoryClientStore.saveClient(expectedClient);
        Client actualClient = memoryClientStore.readClient(clientId);
        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void delete() throws Exception {
        memoryClientStore.deleteClient(expectedClientId);
        Assert.assertTrue(memoryClientStore.readAllClients().isEmpty());
    }

    @Test
    public void saveSelf() throws Exception {
        Client expectedClient = new Client("82c7cd22-6b7d-4866-ab7d-4a1aa2c85403", null, null, null);
        memoryClientStore.saveSelf(expectedClient);
        Client actualClient = memoryClientStore.readSelf();
        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void saveRoomConfig() throws Exception {
        RoomConfig expectedRoomConfig = new RoomConfig("t", true, true);
        memoryClientStore.saveRoomConfig(expectedRoomConfig);
        RoomConfig actualRoomConfig = memoryClientStore.readRoomConfig();
        Assert.assertEquals(expectedRoomConfig, actualRoomConfig);
    }

    @Test
    public void saveChannelConfig() throws Exception {
        ChannelConfig expectedChannelConfig = new ChannelConfig();
        memoryClientStore.saveChannelConfig(expectedChannelConfig);
        ChannelConfig actualChannelConfig = memoryClientStore.readChannelConfig();
        Assert.assertEquals(expectedChannelConfig, actualChannelConfig);
    }
}