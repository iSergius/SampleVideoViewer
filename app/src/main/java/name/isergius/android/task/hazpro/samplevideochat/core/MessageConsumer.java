package name.isergius.android.task.hazpro.samplevideochat.core;

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

    void clientIceCandidateServer(String clientId, CandidateServer candidateServer);

    void clientIceServer(String clientId, Server server);

    void clientSDescription(String clientId, SDescription sDescription);

    void selfData(Client client);

    void selfConnected();
}
