package ca.sahiljain.androidcodingchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ca.sahiljain.androidcodingchallenge.models.CommandSet;


public class MainActivity extends Activity {

    private MyListAdapter adapter;
    private TextView mainTextView;
    private SocketManager socketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);
        mainTextView = (TextView) findViewById(R.id.text_view);
        adapter = new MyListAdapter(this, listView, new CommandSet());
        listView.setAdapter(adapter);
        socketManager = new SocketManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String ip = IPManager.getIP(this);
        if (TextUtils.isEmpty(ip)) {
            showIPDialog();
        } else {
            socketManager.createSocketConnectionAndBeginRunnable(ip, mainTextView, adapter);
        }
    }

    @Override
    protected void onPause() {
        socketManager.onPause();
        adapter.getCommandSet().clear();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showIPDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showIPDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle(R.string.action_settings);
        builder.setView(inflater.inflate(R.layout.dialog_ip, null));
        builder.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.getCommandSet().clear();
                Dialog dialog = Dialog.class.cast(dialogInterface);
                EditText et = (EditText) dialog.findViewById(R.id.ip_edit);
                String ip = et.getText().toString();
                IPManager.setIP(ip, MainActivity.this);
                socketManager.onPause();
                socketManager.createSocketConnectionAndBeginRunnable(ip, mainTextView, adapter);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
