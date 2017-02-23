package name.isergius.android.task.hazpro.samplevideochat.core;

import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.data.Client;

/**
 * @author Sergey Kondratyev
 */
public interface StoreListener {

    void updateClients(List<Client> clients);
}
