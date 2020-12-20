package com.example.cts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Symptoms extends AppCompatActivity {

    Button submit;
    CardView one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen;
    RadioButton positive, negative;
    final float x = 2.25f;
    int i = 0;
    float sum = 0;
    String status = "";
    String hint;
    String date;
    ArrayList<Float> arr = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        database =  FirebaseDatabase.getInstance();
        one = findViewById(R.id.c1);
        two = findViewById(R.id.c2);
        three = findViewById(R.id.c3);
        four = findViewById(R.id.c4);
        five = findViewById(R.id.c5);
        six = findViewById(R.id.c6);
        seven = findViewById(R.id.c7);
        eight = findViewById(R.id.c8);
        nine = findViewById(R.id.c9);
        ten = findViewById(R.id.c10);
        eleven = findViewById(R.id.c11);
        twelve = findViewById(R.id.c12);
        thirteen = findViewById(R.id.c13);
        submit = findViewById(R.id.button);
        positive = findViewById(R.id.radioButton);
        negative = findViewById(R.id.radioButton2);


       /* one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        ten.setOnClickListener(this);
        eleven.setOnClickListener(this);
        twelve.setOnClickListener(this);
        thirteen.setOnClickListener(this);
        */

        one.setOnClickListener(v -> {

            float item = 2*x;
            if(arr.add(item)){
                one.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        two.setOnClickListener(v -> {
            float item = 2*x;
            if(arr.add(item)){
                two.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        three.setOnClickListener(v -> {
            float item = 3*x;
            if(arr.add(item)){
                three.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        four.setOnClickListener(v -> {
            float item = 3*x;
            if(arr.add(item)){
                four.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        five.setOnClickListener(v -> {
            float item = 4*x;
            if(arr.add(item)){
                five.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        six.setOnClickListener(v -> {
            float item = 4*x;
            if(arr.add(item)){
                six.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        seven.setOnClickListener(v -> {
            float item = 2*x;
            if(arr.add(item)){
                seven.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        eight.setOnClickListener(v -> {
            float item = 2*x;
            if(arr.add(item)){
                eight.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        nine.setOnClickListener(v -> {
            float item = 4*x;
            if(arr.add(item)){
                nine.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        ten.setOnClickListener(v -> {
            float item = 4*x;
            if(arr.add(item)){
                ten.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        eleven.setOnClickListener(v -> {

            float item = 4*x;
            if(arr.add(item)){
                eleven.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        twelve.setOnClickListener(v -> {
            float item = 3*x;
            if(arr.add(item)){
                twelve.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });

        thirteen.setOnClickListener(v -> {
            float item = 3*x;
            if(arr.add(item)){
                thirteen.setCardBackgroundColor(Color.parseColor("#a86032"));
            }
        });
        positive.setOnClickListener(v -> status = "POSITIVE");

        negative.setOnClickListener(v -> status = "NEGATIVE");
        submit.setOnClickListener(v -> {


            for (i = 0; i < arr.size(); i++) {
                sum = sum + arr.get(i);
            }
            if (sum >= 25) {
                hint = "Please Consider the Doctor";
            }
            else if(sum<25 && status.equals("NEGATIVE")){
                hint="you are safe";
            }else if(sum<25 && status.equals("POSITIVE")){
                hint = "Please contact doctor and maintain social distance";
            }

            System.out.println(sum);

            String username  = getIntent().getStringExtra("uname");
            String age = getIntent().getStringExtra("uage");
            String gender = getIntent().getStringExtra("ugender");
            date = getIntent().getStringExtra("ddt");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("users");
            FirebaseUser use = FirebaseAuth.getInstance().getCurrentUser();

            assert use != null;
            String id = use.getUid();

            users u = new users(username, age, gender,date, status, id);

            ref.push().setValue(u);

            Intent intent = new Intent(Symptoms.this, Homepage.class);
            intent.putExtra("percent", sum);
            intent.putExtra("hin", hint);
            //intent.putExtra("dat", date);
            startActivity(intent);
            finish();

        });
    }
}