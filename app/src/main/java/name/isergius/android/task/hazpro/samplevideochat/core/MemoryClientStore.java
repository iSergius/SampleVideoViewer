package name.isergius.android.task.hazpro.samplevideochat.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
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
    public synchronized void addCandidateServer(String clientId, CandidateServer candidateServer) throws StoreException {
        read(clientId).add(candidateServer);
    }

    @Override
    public synchronized void addIceServer(String clientId, Server server) throws StoreException {
        Client client = clients.get(clientId);
        isNonNull(client);
        client.add(server);
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
    public void addSDescription(String clientId, SDescription sDescription) {

    }

    @Override
    public void connected() {

    }

    private void isNonNull(Client client) throws StoreException {
        if (client == null) throw new StoreException("Client is not found");
    }


}
