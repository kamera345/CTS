package com.example.cts;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class Homepage extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    TextView text,text3,text5;
    Button click;
    //private static final String TAG = Homepage.class.getSimpleName();
    private final ArrayList<String> arraylist = new ArrayList<>();//To store current day infected
    private final ArrayList<String>arraylist1 = new ArrayList<>();//To store current day suspected
    private final ArrayList<String>arraylist2 = new ArrayList<>();//To store current day recovered people
    private final ArrayList<String>arraylist3 = new ArrayList<>();//TO store the last day data suspected
    private final ArrayList<String>arraylist4 = new ArrayList<>();//To store the last day data infected
    private final ArrayList<String>arraylist5 = new ArrayList<>();//To find the last day recovered people
    public  int size;//Infected
    public int size1;//suspected
    public int size2;//recovered
    public int size3;//last suspected
    public int size4;//last infected
    public int size5;//last recovered
    private String dt;// To store current date
    private String user;//To store user id
    private String ddt1;//To store yesterday date
    private String hin;
    private  ArrayList<String> devicelist = new ArrayList<String>();// To store the scanned device details
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// To initializing the bluetooth

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicelist.add(device.getName());
                Log.i("BT", device.getName() + "\n" + device.getAddress());

                System.out.println("size of devices" +devicelist.size());
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bluetoothAdapter.startDiscovery();//To start the scanning

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentFilter);

        bluetoothAdapter.startDiscovery();


        text = findViewById(R.id.textView6);//user status
        text3 = findViewById(R.id.textView4);//name of the user
        //text4 = findViewById(R.id.textView7);
        text5 = findViewById(R.id.textView8);
        click = findViewById(R.id.button3);

         hin = getIntent().getStringExtra("hin");
        //String su = getIntent().getStringExtra("percent");

        FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
        assert us != null;
        user = us.getUid();

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        dt = String.valueOf(ca.getTime());

        System.out.println("tosay date:" + dt);


        // TO find the last date or yesterday
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -1);
        //To store last day date
        ddt1 = String.valueOf(cal.getTime());
        System.out.println("missing out " + ddt1);

        //To calculate the date for 15 days
        Calendar cc = Calendar.getInstance();
        cc.set(Calendar.HOUR_OF_DAY, 0);
        cc.set(Calendar.MINUTE, 0);
        cc.set(Calendar.SECOND, 0);
        cc.set(Calendar.MILLISECOND, 0);
        cc.add(Calendar.DATE, -15);

        //TO store the 15th day dates
        String fif = String.valueOf(cc.getTime());

        System.out.println("Last 15th day date" + fif);


        text5.setText(hin);


        //Initializing the method to find the current status of user
        userStatus();
        // Initializing method to find the current day data
        CurrentData();
        //Initializing the method to find the yesterday data
        yesterday();
        // initializing the method of calculations


        new Update(fif);

        click.setOnClickListener(v -> {
            Model model = new Model();
            model.Model(10, size1, size3, size, size4, size2, size5);
           /* text6.setVisibility(View.VISIBLE);
            text7.setVisibility(View.VISIBLE);
            text8.setVisibility(View.VISIBLE);*/
        });

    }
    public void userStatus(){
        // To get the user present status
        Query query5 = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(user);
        query5.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();

                assert newPost != null;
                String na = Objects.requireNonNull(newPost.get("username")).toString();
                String imp = Objects.requireNonNull(newPost.get("status")).toString();

                text3.setText(na);
                text.setText(imp);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    // This method is to get the current date  required data to perform calculations
    public void CurrentData(){

        Query query1 = FirebaseDatabase.getInstance().getReference("users").orderByChild("dt").equalTo(dt);

        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                //System.out.println("Status: " + newPost.get("status"));

                assert newPost != null;
                String imp = Objects.requireNonNull(newPost.get("status")).toString();

                if(imp.equals("NEGATIVE")){
                    arraylist1.add(imp);
                }

                if(imp.equals("POSITIVE")){
                    arraylist.add(imp);
                }
                if(imp.equals("RECOVERED")){
                    arraylist2.add(imp);
                }

                size1= arraylist1.size();//suspected
                size = arraylist.size();//infected
                size2 = arraylist2.size();//recovered


                System.out.println("infected  " +    size +    "suspected  " +    size1 +     "recovered" + size2 + ".");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //method to find the last day date
    public void yesterday(){
        // To get last day data from firebase
        Query query8 = FirebaseDatabase.getInstance().getReference("users").orderByChild("dt").equalTo(ddt1);

        query8.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                //System.out.println("Status: " + newPost.get("status"));

                assert newPost != null;
                String imp = Objects.requireNonNull(newPost.get("status")).toString();

                if(imp.equals("NEGATIVE")){
                    arraylist3.add(imp);
                }

                if(imp.equals("POSITIVE")){
                    arraylist4.add(imp);
                }
                if(imp.equals("RECOVERED")){
                    arraylist5.add(imp);
                }

                size3= arraylist3.size();//suspected
                size4 = arraylist4.size();//infected
                size5 = arraylist5.size();//recovered


               // System.out.println(" fifty suspected  " + size3 + "infected  " + size4 +"recovered" + size5 + "." + imp);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bluetoothAdapter == null) {
            Toast.makeText(Homepage.this, "Bluetooth is not supported", Toast.LENGTH_SHORT).show();
        }
        else if(!bluetoothAdapter.isEnabled()){
            Intent blue = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(blue, REQUEST_ENABLE_BT);
        }
        if(bluetoothAdapter.isEnabled()){
            Toast.makeText(Homepage.this, "Bluetooth is Enabled", Toast.LENGTH_SHORT).show();
        }

    }

}