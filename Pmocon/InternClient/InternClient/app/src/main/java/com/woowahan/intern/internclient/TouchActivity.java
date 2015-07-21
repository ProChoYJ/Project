package com.woowahan.intern.internclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2015-06-18.
 */
public class TouchActivity extends AppCompatActivity {

    private Client cl;
    private DataInputStream in;
    private DataOutputStream out;
    public static Button btn;
    public static Button btn_d;
    public static Button btn_exit;
    public static Button btn_stop;
    private Socket socket;
    private ImageView ppt_page;
    //    private SenderUP senderUp;
    private Receiver receiver;
    private float x1, x2, y1, y2;
    private Toast toast;
    PhotoViewAttacher mAttacher;
    private String ip;
    private int port;
    private String user;
    private boolean master = false;
    private boolean stop = false;
    private long backKeyPressedTime = 0;
    private TelephonyManager telephony;
    private String myNumber;
    private ProgressDialog progressDialog;
    private boolean mStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn = (Button) findViewById(R.id.ok_btn);
        btn_d = (Button) findViewById(R.id.down_btn);
        btn_exit = (Button) findViewById(R.id.exit_btn);
        btn_stop = (Button) findViewById(R.id.stop_btn);
        ppt_page = (ImageView) findViewById(R.id.ppt_page);

        telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getLine1Number() == null) {
            myNumber = "0104696";
//            Log.d("mynum", myNumber);
        }else{
            myNumber = telephony.getLine1Number();
        }
        Intent it = getIntent();

        ip = it.getExtras().getString("ip");
        port = Integer.parseInt(it.getExtras().getString("port"));


        cl = new Client(ip, port);
        cl.execute();


    } // activity


    /////////// swipe
    //////////////////////////////////
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN: {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
//                Log.d("Touch", "touchDown");
//                if(btn_exit.getVisibility() == View.VISIBLE){
//                    btn_exit.setVisibility(View.INVISIBLE);
//                }else{
//                    btn_exit.setVisibility(View.VISIBLE);
//                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                //if left to right sweep event on screen
                if (y1 > y2) {

                    btn_d.performClick();
                }
                // if right to left sweep event on screen
                else if (y1 < y2) {
                    btn.performClick();
                } else {
                    if (btn_exit.getVisibility() == View.VISIBLE) {
                        btn_exit.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.INVISIBLE);
                        btn_d.setVisibility(View.INVISIBLE);
                        btn_stop.setVisibility(View.INVISIBLE);

                    } else {
                        btn_exit.setVisibility(View.VISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        btn_d.setVisibility(View.VISIBLE);
                        btn_stop.setVisibility(View.VISIBLE);


                    }

                }

                break;
            }
        }
        return false;
    }


    ////////////////// Client class
    ////////////////////////////////

    class Client extends AsyncTask {

        String cip;
        int cport;

        public Client(String ip, int port) {
            this.cip = ip;
            this.cport = port;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                socket = new Socket(cip, cport);
//                Log.d("tset", "connected");
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                String port = in.readUTF();
                String serverIp = in.readUTF();
                String serverName = in.readUTF();

                out.writeUTF("ACK");

                out.writeUTF(myNumber);

                user = in.readUTF();
                if (user.equals("1")) {
                    master = true;
                    stop = true;
                    btn_stop.setEnabled(false);

                }

//                Log.d("user", user);

            } catch (Exception e) {
            }
//                Log.d("testerror", e.toString());

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                            Log.d("test", "ok");
                    if (stop) {
                        if (System.currentTimeMillis() > backKeyPressedTime + 500) {
                            backKeyPressedTime = System.currentTimeMillis();
                            try {
                                out.writeUTF(user);
                                out.writeUTF("501");
                                out.flush();
                                //                        out.close();
                            } catch (Exception e) {
                                Log.d("testerror", e.toString());
                            }
                        }
                        if (System.currentTimeMillis() <= backKeyPressedTime + 500) {
                            return;
                        }

                    }
                }
            });
            btn_d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stop) {
                        if (System.currentTimeMillis() > backKeyPressedTime + 500) {
                            backKeyPressedTime = System.currentTimeMillis();

                            try {
                                out.writeUTF(user);
                                out.writeUTF("502");
                                out.flush();
                                //                        out.close();
                            } catch (Exception e) {
                                Log.d("testerror", e.toString());
                            }
                        }
                        if (System.currentTimeMillis() <= backKeyPressedTime + 500) {
                            return;
                        }

                    }
                }
            });

            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        in.close();
                        out.close();
                        socket.close();
                        finish();
                    } catch (IOException e) {
                        Log.d("exiterror", e.toString());
                    }
                }
            });

            btn_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stop = !stop;
//                    Log.d("user stop", "" + stop);
                    if (stop) {
                        btn_stop.setBackgroundResource(R.drawable.stop_btn_effect);
                    } else {
                        btn_stop.setBackgroundResource(R.drawable.exit_btn_effect);
                    }
                }
            });
//

            receiver = new Receiver(socket);
            receiver.start();



            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(TouchActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Object o) {
//            try {
//                networkWriter.writeUTF("1");
//
//            }catch(Exception e){
//                Log.d("testerror",e.toString());
//            }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
//            try {
//                socket.close();
//            }catch(IOException e){}
        }


    }

    /////////////////// button event
    public boolean onKeyDown(int keycode, KeyEvent event) {

        switch (keycode) {
            case KeyEvent.KEYCODE_BACK:
                mStop = true;
//                    receiver.stopReceiver();
                    finish();

            case KeyEvent.KEYCODE_VOLUME_DOWN:
//                Log.d("test", "down");
                if (master) {
                    btn_d.performClick();
                }
                break;

            case KeyEvent.KEYCODE_VOLUME_UP:
//                Log.d("test", "up");
                if (master) {
                    btn.performClick();
                }
                break;
        }
        return true;
    }


    ///////// receiver
    class Receiver extends Thread {
        Socket rsocket;
        DataInputStream rin;
        DataOutputStream rout;
        byte[] fileArray;

        BufferedInputStream bufStream;
        byte[] readBytes = new byte[1024];
        ByteArrayInputStream bais;

        Receiver(Socket socket) {
            try {
                rsocket = socket;
                Log.d("receiver", "receiver conneceted");
                rin = new DataInputStream(rsocket.getInputStream());
                rout = new DataOutputStream((rsocket.getOutputStream()));

            } catch (Exception e) {
            }
        }

        public void stopReceiver(){
            this.rin = null;
        }

        public void run() {


            while (rin != null) {

                try {
                    if(mStop) {
                        return;
                    }

                    //// new
                    final String requestName = rin.readUTF();
//                    Log.d("user request", requestName);
                    String fileSize = rin.readUTF();
//                    Log.d("receiver class", "" + fileSize);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.show();
                        }
                    });

                    File f = new File("/mnt/sdcard/test7.png");
                    FileOutputStream fos = new FileOutputStream(f);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
                    //// if(stop false, requestName == 1
                    //// else
                    int len;
                    int count = 0;
                    int size = Integer.parseInt(fileSize);
                    byte[] data = new byte[8192];
                    while ((len = in.read(data)) != -1) {
                        baos.write(data, 0, len);
                        count += len;
                        if (count == size)
                            break;
//                        Log.d("receive len", "" + len);
                    }

                    fileArray = baos.toByteArray();
                    baos.flush();


//                    Log.d("receive count totoal", "" + count);
//                    Log.d("receive data totoal", "" + fileArray.length);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (!stop && requestName.equals("1")) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(fileArray, 0, fileArray.length);
                                ppt_page.refreshDrawableState();
                                ppt_page.invalidate();
                                ppt_page.setImageBitmap(bitmap);

                            } else if (stop && requestName.equals(user)) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(fileArray, 0, fileArray.length);
                                ppt_page.refreshDrawableState();
                                ppt_page.invalidate();
                                ppt_page.setImageBitmap(bitmap);
                            } else if (!stop && requestName.equals(user)) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(fileArray, 0, fileArray.length);
                                ppt_page.refreshDrawableState();
                                ppt_page.invalidate();
                                ppt_page.setImageBitmap(bitmap);

                            }
                            if (!master) {
                                mAttacher = new PhotoViewAttacher(ppt_page);
                                mAttacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                            }


                            progressDialog.dismiss();


                        }

                    });

                } catch (IOException e) {
//                    Log.d("receiver error", e.toString());
                    try {
                        rsocket.close();
                        socket.close();
                    } catch (IOException e1) {
                        Log.d("receiver error1", e1.toString());
                    }
                }
            }
        }

    }


    ////////////// backpress toast



    @Override
    public void onBackPressed() {

//        Toast.makeText(this, "Test", Toast.LENGTH_SHORT);

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
