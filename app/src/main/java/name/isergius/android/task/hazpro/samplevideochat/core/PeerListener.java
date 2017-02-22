package name.isergius.android.task.hazpro.samplevideochat.core;

import name.isergius.android.task.hazpro.samplevideochat.data.CandidateServer;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;

/**
 * @author Sergey Kondratyev
 */
public interface PeerListener {

    String clientId();

    void candidateServer(CandidateServer iceCandidate);

    void sessionDescription(SDescription sessionDescription);

    void disconnect();
}
