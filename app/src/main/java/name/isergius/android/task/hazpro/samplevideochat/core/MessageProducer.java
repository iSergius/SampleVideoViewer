package name.isergius.android.task.hazpro.samplevideochat.core;

import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;

/**
 * @author Sergey Kondratyev
 */

public interface MessageProducer {

    void sendJoinRoom(RoomConfig roomConfig);
}
