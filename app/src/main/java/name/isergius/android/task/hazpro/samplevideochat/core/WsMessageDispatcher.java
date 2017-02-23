package name.isergius.android.task.hazpro.samplevideochat.core;

import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

public class WsMessageDispatcher implements MessageConsumer {

    private static final String TAG = WsMessageDispatcher.class.getSimpleName();
    private final ClientStore clientStore;
    private final MessageProducer messageProducer;
    private Map<String, PeerListener> peerListeners = new HashMap<>();
    private Set<StoreListener> storeListeners = new HashSet<>();

    public WsMessageDispatcher(ClientStore clientStore, MessageProducer messageProducer) {
        this.clientStore = clientStore;
        this.messageProducer = messageProducer;
    }

    public void registerPeerListener(PeerListener peerListener) {
        Log.v(TAG,"registerPeerListener "+peerListener);
        peerListeners.put(peerListener.clientId(), peerListener);
    }

    public void unregisteredPeerListener(PeerListener peerListener) {
        Log.v(TAG,"unregisteredPeerListener "+peerListener);
        peerListeners.remove(peerListener.clientId());
    }

    public void registerStoreListener(StoreListener storeListener) {
        Log.v(TAG,"registerStoreListener "+storeListener);
        storeListeners.add(storeListener);
    }

    public void unregisteredStoreListener(StoreListener storeListener) {
        Log.v(TAG,"unregisteredStoreListener "+storeListener);
        storeListeners.remove(storeListener);
    }

    @Override
    public void clientData(Client client) {
        try {
            clientStore.save(client);
        } catch (StoreException e) {
            Log.e(TAG, "Error", e);
        }
    }

    @Override
    public void clientLogout(String clientId) {
        try {
            clientStore.delete(clientId);
            PeerListener listener = peerListeners.get(clientId);
            if (listener != null) {
                listener.disconnect();
            }
            notifyStoreListeners();
        } catch (StoreException e) {
            Log.e(TAG, "Error", e);
        }
    }

    @Override
    public void clientCandidateServer(String clientId, CandidateServer candidateServer) {
        PeerListener listener = peerListeners.get(clientId);
        listener.candidateServer(candidateServer);
    }

    @Override
    public void clientServer(String clientId, Set<Server> servers) {
        synchronized (clientStore) {
            try {
                Client client = clientStore.read(clientId);
                for (Server server : servers) {
                    client.add(server);
                }
                notifyStoreListeners();
            } catch (StoreException e) {
                Log.e(TAG, "Error", e);
            }
        }
    }

    @Override
    public void selfData(Client client) {
        try {
            clientStore.saveSelf(client);
        } catch (StoreException e) {
            Log.e(TAG, "Error", e);
        }
    }

    @Override
    public void clientSDescription(String clientId, SDescription sDescription) {
        PeerListener peerListener = peerListeners.get(clientId);
        peerListener.sessionDescription(sDescription);
    }

    @Override
    public void selfConnected() {
        try {
            messageProducer.sendJoinRoom(clientStore.readRoomConfig());
        } catch (StoreException e) {
            Log.e(TAG, "Error", e);
        }
    }

    private void notifyStoreListeners() {
        for (StoreListener listener : storeListeners) {
            listener.updateClients(clientStore.readAllReadyClients());
        }
    }
}
