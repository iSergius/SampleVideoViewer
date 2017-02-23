package name.isergius.android.task.hazpro.samplevideochat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import name.isergius.android.task.hazpro.samplevideochat.R;
import name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService;

/**
 * @author Sergey Kondratyev
 */
public class EnterActivity extends AppCompatActivity {

    public static final String PROPERTY_ROOM_NAME = "roomName";

    private RoomNameEdit roomNameEdit;
    private OpenRoomButton openRoomButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        this.roomNameEdit = new RoomNameEdit();
        this.openRoomButton = new OpenRoomButton(this);

    }

    private class RoomNameEdit {
        private AppCompatEditText roomNameEdit;

        RoomNameEdit() {
            roomNameEdit = (AppCompatEditText) findViewById(R.id.room_name_edit);
        }

        String getValue() {
            return roomNameEdit.getText().toString();
        }

        boolean isValid() {
            return true;
        }
    }

    private class OpenRoomButton implements View.OnClickListener {
        private AppCompatButton openRoomButton;
        private Context context;

        OpenRoomButton(Context context) {
            this.context = context;
            openRoomButton = (AppCompatButton) findViewById(R.id.open_room_btn);
            openRoomButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (roomNameEdit.isValid()) {
                Intent intent = new Intent(context, PeerListActivity.class);
                intent.putExtra(PROPERTY_ROOM_NAME, roomNameEdit.getValue());
                startActivity(intent);
            }
        }
    }
}
