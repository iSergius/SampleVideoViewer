package name.isergius.android.task.hazpro.samplevideochat.core;

import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 * Contract for this store does not allow returns null
 * Implementations must thred-safe
 */

public interface ClientStore {

    Client read(String clientId) throws StoreException;

    void save(Client expectedClient) throws StoreException;

    void delete(String clientId) throws StoreException;

    List<Client> readAll();

    void addCandidateServer(String clientId, CandidateServer candidateServer) throws StoreException;

    void addIceServer(String clientId, Server server) throws StoreException;

    void saveSelf(Client selfClient) throws StoreException;

    Client readSelf() throws StoreException;

    void addSDescription(String clientId, SDescription sDescription) throws StoreException;

    void connected();
}
