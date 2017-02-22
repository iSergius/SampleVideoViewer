package name.isergius.android.task.hazpro.samplevideochat.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 *
 * thread-safe
 */

public class MemoryClientStore implements ClientStore {

    private Map<String, Client> clients = new HashMap<>();
    private Client self;
    private RoomConfig roomConfig;

    MemoryClientStore() {
    }

    public MemoryClientStore(List<Client> clients) {
        for (Client client : clients) {
            this.clients.put(client.getId(), client);
        }
    }

    @Override
    public synchronized Client read(String clientId) throws StoreException {
        Client client = clients.get(clientId);
        isNonNull(client);
        return client;
    }

    @Override
    public synchronized List<Client> readAll() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public synchronized List<Client> readAllReadyClients() {
        List<Client> result = new ArrayList<>();
        for (Client client : clients.values()) {
            if (client.isReady()) {
                result.add(client);
            }
        }
        return result;
    }

    @Override
    public synchronized void save(Client client) throws StoreException {
        try {
            clients.put(client.getId(), client);
        } catch (Exception e) {
            throw new StoreException(e);
        }
    }

    @Override
    public synchronized void delete(String clientId) throws StoreException {
        Client client = clients.remove(clientId);
        isNonNull(client);
    }

    @Override
    public synchronized void saveSelf(Client self) throws StoreException {
        isNonNull(self);
        this.self = self;
    }

    @Override
    public synchronized Client readSelf() throws StoreException {
        return self;
    }

    @Override
    public synchronized RoomConfig readRoomConfig() throws StoreException {
        if (this.roomConfig == null) throw new StoreException("RoomConfig is not initialized");
        return roomConfig;
    }

    @Override
    public synchronized void saveRoomConfig(RoomConfig roomConfig) throws StoreException {
        this.roomConfig = roomConfig;
    }

    private void isNonNull(Client client) throws StoreException {
        if (client == null) throw new StoreException("Client is not found");
    }


}
