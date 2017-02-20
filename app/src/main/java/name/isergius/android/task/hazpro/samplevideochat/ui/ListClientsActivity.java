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
import java.util.Collection;
import java.util.List;

import name.isergius.android.task.hazpro.samplevideochat.R;
import name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService;
import name.isergius.android.task.hazpro.samplevideochat.data.Client;

import static name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService.ClientListListener;
import static name.isergius.android.task.hazpro.samplevideochat.core.SignalingChannelService.PROPERTY_ROOM_NAME;

/**
 * @author Sergey Kondratyev
 */

public class ListClientsActivity extends AppCompatActivity {

    private static final String TAG = ListClientsActivity.class.getSimpleName();

    private SignalingChannelService.SignalingChanelBinder binder;
    private ClientsListView clientsListViewAdapter;
    private String roomName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clients);
        this.clientsListViewAdapter = new ClientsListView(this);
        roomName = getIntent().getExtras().getString(SignalingChannelService.PROPERTY_ROOM_NAME);
        bindService();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binder.resetConnection();
        binder.register(clientsListViewAdapter);
    }

    private void unbindService() {
        unbindService(clientsListViewAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binder.unregister(clientsListViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, SignalingChannelService.class);
        intent.putExtra(PROPERTY_ROOM_NAME, roomName);
        bindService(intent, clientsListViewAdapter, BIND_AUTO_CREATE);
    }

    private class ClientsListView extends BaseAdapter implements ServiceConnection, ClientListListener {
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
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra(Client.PROPERTY,client);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (SignalingChannelService.SignalingChanelBinder) service;
            binder.register(this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void updateClients(final Collection<Client> clientsCollection) {
            Log.v(TAG,"updateClients "+clientsCollection.size());
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

        @Override
        public void lock() {
            view.setVisibility(View.GONE);
        }

        @Override
        public void unlock() {
            view.setVisibility(View.VISIBLE);
        }

    }
}
