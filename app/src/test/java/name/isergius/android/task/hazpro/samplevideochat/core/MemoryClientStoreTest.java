package name.isergius.android.task.hazpro.samplevideochat.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.Credentials;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */
public class MemoryClientStoreTest {

    private String expectedClientId = "6236007a-6651-453b-8701-fe127541e048";
    private String expectedDeviceId = "3f0c1829-31c8-4d5c-a01a-34b75043b393";
    private String expectedUserId = null;
    private String expectedName = "Anonymous 6236007a-6651-453b-8701-fe127541e048";
    private Client expectedClient = new Client(expectedClientId, expectedDeviceId, expectedUserId, expectedName);
    private MemoryClientStore memoryClientStore =new MemoryClientStore(Arrays.asList(expectedClient));

    @Test
    public void read() throws Exception {
        Client actualClient = memoryClientStore.read(expectedClientId);
        Assert.assertEquals(expectedClient, actualClient);
    }

    @Test(expected = StoreException.class)
    public void readNotContain() throws Exception {
        String clientId = "dca3ba16-9853-4c5e-ad04-c553f816f4b9";
        memoryClientStore.read(clientId);
    }

    @Test
    public void readAll() throws Exception {
        List<Client> allClients = memoryClientStore.readAll();
        Assert.assertEquals(Arrays.asList(expectedClient),allClients);
    }

    @Test
    public void save() throws Exception {
        String clientId = "dca3ba16-9853-4c5e-ad04-c553f816f4b9";
        Client expectedClient = new Client(clientId,
                "6a5c7759-0c7b-4ccd-a076-8a4155e58ee3",
                null,
                "Anonymous dca3ba16-9853-4c5e-ad04-c553f816f4b9");
        memoryClientStore.save(expectedClient);
        Client actualClient = memoryClientStore.read(clientId);
        Assert.assertEquals(expectedClient,actualClient);
    }

    @Test
    public void delete() throws Exception {
        memoryClientStore.delete(expectedClientId);
        Assert.assertTrue(memoryClientStore.readAll().isEmpty());
    }

    @Test
    public void addCandidateServer() throws Exception {
        CandidateServer expectedCandidateServer = new CandidateServer("audio", 0, "candidate:316526476 1 udp 2122260223 192.168.0.20 37695 typ host generation 0");
        memoryClientStore.addCandidateServer(expectedClientId, expectedCandidateServer);
        Client client = memoryClientStore.read(expectedClientId);
        Assert.assertEquals(Arrays.asList(expectedCandidateServer), client.getCandidateServers());
    }

    @Test
    public void addIceServer() throws Exception {
        String clientId = "6236007a-6651-453b-8701-fe127541e048";
        Server expectedServer = new Server("j/1YtwTvE6yqlb4l/sbYWW1oA64=", "appearin:1487381191", "turn:turn.appear.in:443?transport=udp");
        memoryClientStore.addIceServer(clientId, expectedServer);
        Client client = memoryClientStore.read(clientId);
        Assert.assertEquals(Arrays.asList(expectedServer),client.getServers());
    }

    @Test
    public void saveSelf() throws Exception {
        Client expectedClient = new Client("82c7cd22-6b7d-4866-ab7d-4a1aa2c85403", null, null, null);
        memoryClientStore.saveSelf(expectedClient);
        Client actualClient = memoryClientStore.readSelf();
        Assert.assertEquals(expectedClient,actualClient);
    }
}