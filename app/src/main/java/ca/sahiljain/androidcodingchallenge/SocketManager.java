package ca.sahiljain.androidcodingchallenge;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

import ca.sahiljain.androidcodingchallenge.models.Command;
import ca.sahiljain.androidcodingchallenge.models.CommandType;

class SocketManager {
    Socket socket = null;
    boolean isActive = false;

    public void createSocketConnectionAndBeginRunnable(final String ip, final TextView headerTextView, final MyListAdapter adapter) {
        final Handler handler = new Handler();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                isActive = true;
                Log.d("sahil", "starting task");
                while(socket == null || socket.isClosed()) {
                    try {
                        socket = new Socket(ip, 1234);
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
                    while (isActive && socket != null && !socket.isClosed()) {
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
                        final Command fc = c;
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                adapter.addCommand(fc);
                                headerTextView.setText(adapter.getCommandSet().getResultColorString());
                                headerTextView.setBackgroundColor(adapter.getCommandSet().getResultColor());
                            }
                        });
                        Log.d("sahil", "r: " + r + " g: " + g + " b: " + b);

                    }
                } catch (EOFException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(headerTextView.getContext(), "END OF FILE", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SocketException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            headerTextView.setText("Socket Closed");
                            adapter.getCommandSet().clear();
                        }
                    });
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

    public void onPause() {
        isActive = false;
        if(socket!=null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
