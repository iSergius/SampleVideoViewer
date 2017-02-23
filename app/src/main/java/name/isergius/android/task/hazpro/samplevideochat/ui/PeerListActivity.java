package name.isergius.android.task.hazpro.samplevideochat.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.R;
import name.isergius.android.task.hazpro.samplevideochat.core.ChannelService;
import name.isergius.android.task.hazpro.samplevideochat.core.StoreListener;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;

/**
 * @author Sergey Kondratyev
 */

public class PeerListActivity extends AppCompatActivity {

    public static final String PROPERTY_CLIENT_ID = "clientId";
    private static final String TAG = PeerListActivity.class.getSimpleName();
    private ChannelService.Facade facade;
    private ClientsListView clientsListView;
    private RoomConfig roomConfig;
    private SrvConn srvConn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clients);
        clientsListView = new ClientsListView(this);
        if (getIntent().hasExtra(EnterActivity.PROPERTY_ROOM_NAME)) {
            String roomName = getIntent().getExtras().getString(EnterActivity.PROPERTY_ROOM_NAME);
            roomConfig = new RoomConfig(roomName,false,false);
        }
        bindService();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        facade.disconnect();
        unbindService(srvConn);
    }

    private void bindService() {
        Intent intent = new Intent(this, ChannelService.class);
        srvConn = new SrvConn();
        bindService(intent, srvConn, BIND_AUTO_CREATE);
    }

    private class SrvConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            facade = (ChannelService.Facade) service;
            facade.connect(roomConfig);
            facade.register(clientsListView);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            facade.disconnect();
        }
    }

    private class ClientsListView extends BaseAdapter implements StoreListener {
        private volatile List<Client> clients = new ArrayList<>();

        private ListView view;
        private Context context;

        ClientsListView(Context context) {
            this.context = context;
            view = (ListView) findViewById(R.id.clients_list);
            view.setAdapter(this);
        }

        @Override
        public int getCount() {
            return clients.size();
        }

        @Override
        public Object getItem(int position) {
            return clients.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            final Client client = clients.get(position);
            TextView text = (TextView) convertView.findViewById(android.R.id.text1);
            text.setText(client.getName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PeerVideoActivity.class);
                    intent.putExtra(PROPERTY_CLIENT_ID, client.getId());
                    Log.v(TAG,"setOnClickListener "+client.getId());
                    startActivity(intent);
                }
            });
            return convertView;
        }

        @Override
        public void updateClients(final List<Client> clientsCollection) {
            Log.v(TAG, "updateClients " + clientsCollection.size());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetInvalidated();
                    synchronized (clients) {
                        clients = new ArrayList<>(clientsCollection);
                    }
                    notifyDataSetChanged();
                }
            });

        }

    }
}
