package name.isergius.android.task.hazpro.samplevideochat.core;

import java.util.Set;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

public interface MessageConsumer {

    void clientData(Client client);

    void clientLogout(String clientId);

    void clientCandidateServer(String clientId, CandidateServer candidateServer);

    void clientServer(String clientId, Set<Server> servers);

    void clientSDescription(String clientId, SDescription sDescription);

    void selfData(Client client);

    void selfConnected();
}
