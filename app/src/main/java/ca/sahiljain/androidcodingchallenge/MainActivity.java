package ca.sahiljain.androidcodingchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    MyListAdapter adapter;
    SocketManager socketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.list_view);
        adapter = new MyListAdapter(this, lv, new ArrayList<Command>());
        lv.setAdapter(adapter);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        socketManager = new SocketManager();

    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView tv = (TextView) findViewById(R.id.text_view);
        socketManager.createSocketConnectionAndBeginRunnable(IPManager.getIP(), tv, adapter);
    }

    @Override
    protected void onPause() {
        socketManager.onPause();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
                Dialog dialog = Dialog.class.cast(dialogInterface);
                EditText et = (EditText) dialog.findViewById(R.id.ip_edit);
                IPManager.setIP(et.getText().toString());
                socketManager.onPause();
                final TextView tv = (TextView) findViewById(R.id.text_view);
                socketManager.createSocketConnectionAndBeginRunnable(IPManager.getIP(), tv, adapter);
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
