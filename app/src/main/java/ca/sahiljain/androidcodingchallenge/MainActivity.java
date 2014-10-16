package ca.sahiljain.androidcodingchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;


public class MainActivity extends Activity {

    MyListAdapter adapter;

    void handleNewCommand(Command command) {
        adapter.addCommand(command);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.text_view);
        final ListView lv = (ListView) findViewById(R.id.list_view);
        adapter = new MyListAdapter(this, lv, new ArrayList<Command>());
        lv.setAdapter(adapter);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        final Handler handler = new Handler();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Log.d("sahil", "starting task");
                Socket socket = null;
                while(socket == null) {
                    try {
                        socket = new Socket("192.168.1.131", 1234);
                    } catch (IOException e) {
                        Log.d("sahil", "socket connection failed...");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                try {
                    InputStream inputStream = socket.getInputStream();
                    DataInputStream dis = new DataInputStream(inputStream);
                    int r = 127;
                    int g = 127;
                    int b = 127;
                    while(true) {
                        int in = dis.readByte();
                        Command c = null;
                        if (in == 1) {
                            int dr = dis.readShort();
                            int dg = dis.readShort();
                            int db = dis.readShort();
                            r = (r + dr) % 255;
                            g = (g + dg) % 255;
                            b = (b + db) % 255;
                            c = new Command(dr, dg, db, CommandType.Relative);
                        } else if (in == 2) {
                            r = dis.readUnsignedByte();
                            g = dis.readUnsignedByte();
                            b = dis.readUnsignedByte();
                            c = new Command(r,g,b, CommandType.Absolute);
                        }
                        final int fr = r;
                        final int fg = g;
                        final int fb = b;
                        final Command fc = c;
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                tv.setText(ColorUtils.getColorString(adapter.commandList));
                                handleNewCommand(fc);
                            }
                        });
                        Log.d("sahil", "r: " + r + " g: " + g + " b: " + b);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        thread.start();
    }

    @Override
    protected void onPause() {
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
