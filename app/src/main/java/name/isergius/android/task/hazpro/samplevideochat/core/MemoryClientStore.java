package name.isergius.android.task.hazpro.samplevideochat.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.ChannelConfig;
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

    private static final String TAG = MemoryClientStore.class.getSimpleName();

    private Map<String, Client> clients = new ConcurrentHashMap<>();
    private Client self;
    private RoomConfig roomConfig;
    private ChannelConfig channelConfig = new ChannelConfig();

    MemoryClientStore() {
        Log.v(TAG, "Constructor");
    }

    public MemoryClientStore(List<Client> clients) {
        for (Client client : clients) {
            this.clients.put(client.getId(), client);
        }
    }

    @Override
    public synchronized Client read(String clientId) throws StoreException {
        Log.v(TAG, "read: "+clientId);
        Client client = clients.get(clientId);
        isNonNull(client);
        return client;
    }

    @Override
    public synchronized List<Client> readAll() {
        Log.v(TAG, "readAll "+clients);
        return new ArrayList<>(clients.values());
    }

    @Override
    public synchronized List<Client> readAllReadyClients() {
        List<Client> result = new ArrayList<>();
        for (Client client : clients.values()) {
            if (!client.getId().equals(self.getId()) & client.isReady()) {
                result.add(client);
            }
        }
        Log.v(TAG, "readAllReadyClients "+result);
        return result;
    }

    @Override
    public synchronized void save(Client client) throws StoreException {
        Log.v(TAG, "save "+client);
        try {
            clients.put(client.getId(), client);
        } catch (Exception e) {
            throw new StoreException(e);
        }
    }

    @Override
    public synchronized void delete(String clientId) throws StoreException {
        Log.v(TAG, "delete "+clientId);
        Client client = clients.remove(clientId);
        isNonNull(client);
    }

    @Override
    public synchronized void saveSelf(Client self) throws StoreException {
        Log.v(TAG, "saveSelf "+self);
        isNonNull(self);
        this.self = self;
    }

    @Override
    public synchronized Client readSelf() throws StoreException {
        Log.v(TAG, "readSelf "+self);
        return self;
    }

    @Override
    public synchronized RoomConfig readRoomConfig() throws StoreException {
        Log.v(TAG, "readRoomConfig "+roomConfig);
        if (this.roomConfig == null) throw new StoreException("RoomConfig is not initialized");
        return roomConfig;
    }

    @Override
    public synchronized void saveRoomConfig(RoomConfig roomConfig) throws StoreException {
        Log.v(TAG, "saveRoomConfig "+roomConfig);
        this.roomConfig = roomConfig;
    }

    @Override
    public synchronized ChannelConfig readChannelConfig() throws StoreException {
        Log.v(TAG, "readChannelConfig "+channelConfig);
        return channelConfig;
    }

    @Override
    public synchronized void saveChannelConfig(ChannelConfig channelConfig) throws StoreException {
        Log.v(TAG, "saveChannelConfig " + channelConfig);
        this.channelConfig = channelConfig;
    }

    private void isNonNull(Client client) throws StoreException {
        if (client == null) throw new StoreException("Client is not found");
    }


}
