package com.woowahan.intern.internclient;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

import static com.woowahan.intern.internclient.AutoSearchServerDialogFragment.*;


public class MainActivity extends AppCompatActivity {


    private Button btn;
    private EditText ipEditText;
    private EditText portEditText;
    private Button searchBtn;
    private String searchIp = "";
    private StringTokenizer st;
    private InetAddress ip;
    private String ipv4;
    private NetworkSearch mNetworkSearch;
    private ProgressDialog progressDialog;
    private SearchThread th;
    private SearchThread th2;
    private SearchThread th3;
    private ListView mListView;
//    private ArrayList<SearchInfo> mArrayList;
//    private SearchListAdapter mSearchListAdapter;
    private AutoSearchServerDialogFragment mServerListDialogFragment;
    private ArrayList<SearchInfo> mArrayList;
    private SearchListAdapter mSearchListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("P모콘");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custombar);

        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        Log.d("mynum", "" + telephony.getLine1Number());

        btn = (Button) findViewById(R.id.connect_btn);
        ipEditText = (EditText) findViewById(R.id.ip_edittext);
        portEditText = (EditText) findViewById(R.id.port_edittext);
        searchBtn = (Button) findViewById(R.id.server_search_btn);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpIp = ipEditText.getText().toString();
                String tmpPort = portEditText.getText().toString();

                if(!tmpIp.equals("") && !tmpPort.equals("")){
                    Intent it = new Intent(getApplicationContext(), TouchActivity.class);
                    it.putExtra("ip", ipEditText.getText().toString());
                    it.putExtra("port", portEditText.getText().toString());
                    //                Log.d("input address", "ip: " + ipEditText.getText().toString() + " - port : " + portEditText.getText().toString());
                    if(th != null) {
                        th.stopSearch();
                        th2.stopSearch();
                        th3.stopSearch();
                        mNetworkSearch.cancel(true);
                    }
                    startActivity(it);
                }
            }
        });

        mArrayList = new ArrayList<>();
        mSearchListAdapter = new SearchListAdapter(this, mArrayList);


        mNetworkSearch = new NetworkSearch();
        mNetworkSearch.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(mServerListDialogFragment != null && mServerListDialogFragment.isAdded()) {
            mServerListDialogFragment.dismiss();
        }
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


    class NetworkSearch extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] params) {


            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArrayList.clear();

                    th = new SearchThread();
                    th2 = new SearchThread();
                    th3 = new SearchThread();
                    th.setsStart(0);
                    th.setsEnd(80);
                    th2.setsStart(80);
                    th2.setsEnd(160);
                    th3.setsStart(160);
                    th3.setsEnd(255);
                    th.start();
                    th2.start();
                    th3.start();

                    createSearchServerDialog();

                }
            });


            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading...");
//            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

//            progressDialog.dismiss();
            super.onPostExecute(o);
        }
    }
//
    public ArrayList<SearchInfo> getSearchInfoList() {
        return mArrayList;
    }
    public SearchListAdapter getSearchListAdapter() { return mSearchListAdapter; }


    private void createSearchServerDialog() {
        mServerListDialogFragment = AutoSearchServerDialogFragment.newInstance();
        mServerListDialogFragment.setOnThreadListener(new OnThreadListener() {
            @Override
            public void onStop() {

                th.stopSearch();
                th2.stopSearch();
                th3.stopSearch();
                mNetworkSearch.cancel(true);
            }
        });
    }

    private void showSearchServerDialog() {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            mSearchListAdapter.notifyDataSetChanged();
        } else {
            mServerListDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
        ft.addToBackStack(null);
    }

    private void dismissSearchServerDialog() {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }


    }


    public static String getIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipAddress = inetAddress.getHostAddress().toString();
                        Log.e("IP address", "" + ipAddress);
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        Log.d("searchIp", "Exit");
        progressDialog.dismiss();
        mNetworkSearch.cancel(true);
        if (th != null) {
            th.stopSearch();
            th2.stopSearch();
            th3.stopSearch();
        }
        super.onBackPressed();
    }

    class SearchThread extends Thread {

        private boolean stop = false;
        Socket socket;
        int i = 0;
        int sStart = 0;
        int sEnd = 0;

        public void setsStart(int sStart) {
            this.sStart = sStart;
        }

        public void setsEnd(int sEnd) {
            this.sEnd = sEnd;
        }

        @Override
        public void run() {
            try {
                ip = InetAddress.getLocalHost();
                st = new StringTokenizer(getIpAddress(), ".");
                searchIp = "";

                for (int i = 0; i < 3; i++) {
                    searchIp += st.nextToken() + ".";
                }
//                Log.d("searchIp", searchIp);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.show();
                    }
                });
                for (i = sStart; i < sEnd; i++) {
                    try {
                        socket = new Socket();
                        SocketAddress sa = new InetSocketAddress("10.10.0." + i, 9000);

                        Log.d("searchIp", "num: " + searchIp + i);
                        socket.connect(sa, 200);
                        socket.setSoTimeout(100);

                        DataOutputStream cout = new DataOutputStream(socket.getOutputStream());
                        DataInputStream cin = new DataInputStream(socket.getInputStream());


                        final String ack = cin.readUTF();
                        final String ackip = cin.readUTF();
                        final String serverName = cin.readUTF();
                        if (socket.isConnected()) {

                            Log.d("searchIp", "num: " + searchIp + i);
                        }


                        Log.d("searchIp", "ack: " + ack + ", ackip: " + ackip);
                        if (ackip.equals("10.0.2.15")) {
                            cout.writeUTF("NAK");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
                                    mArrayList.add(new SearchInfo(serverName, ack, "10.10.0." + i));
//                                    ipEditText.setText(searchIp + i);
//                                    portEditText.setText(ack);
//                                }
//                            });
                            break;
                        }
                    } catch (Exception ie) {
//                        Log.d("searchIp", ie.toString());
                        socket.close();
                    }


                    if (mNetworkSearch.isCancelled())
                        break;
                    if (stop)
                        break;

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showSearchServerDialog();
                }
            });

            Log.d("search", "1");

        }

        public void stopSearch() {
            this.stop = true;
        }
    }

    private void DialogSelectOption() {
        final String items[] = {"item1", "item2", "item3"};
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Title");
        ab.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 각 리스트를 선택했을때
                        Log.d("positive", "" + whichButton);
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
//                        Toast.makeText(MainActivity.this, "" + whichButton, Toast.LENGTH_SHORT);
                        Log.d("positive", "" + whichButton);
                    }

                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                    }
                });
        ab.show();
    }

}