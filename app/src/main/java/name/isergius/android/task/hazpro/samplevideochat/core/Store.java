package name.isergius.android.task.hazpro.samplevideochat.core;

import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.data.ChannelConfig;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;

/**
 * @author Sergey Kondratyev
 * Contract for this store does not allow returns null
 * Implementations must thred-safe
 */

public interface Store {

    Client read(String clientId) throws StoreException;

    void save(Client expectedClient) throws StoreException;

    void delete(String clientId) throws StoreException;

    List<Client> readAll();

    List<Client> readAllReadyClients();

    void saveSelf(Client selfClient) throws StoreException;

    Client readSelf() throws StoreException;

    RoomConfig readRoomConfig() throws StoreException;

    void saveRoomConfig(RoomConfig roomConfig) throws StoreException;

    ChannelConfig readChannelConfig() throws StoreException;

    void saveChannelConfig(ChannelConfig channelConfig) throws StoreException;
}
