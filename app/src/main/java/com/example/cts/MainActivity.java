package com.example.cts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText name, email, password, age;
    CheckBox male, female, other;
    Button register;
    private String gender;
    FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebase = FirebaseAuth.getInstance();
        name = findViewById(R.id.edtusername);
        email = findViewById(R.id.emailAddress);
        password = findViewById(R.id.editTextTextPassword);
        age = findViewById(R.id.editTextNumber);
        register = findViewById(R.id.button);
        male = findViewById(R.id.checkBox);
        female = findViewById(R.id.checkBox2);
        other = findViewById(R.id.checkBox3);


        register.setOnClickListener(v -> {
            String username = name.getText().toString().trim();
            String emailId = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String ag = age.getText().toString().trim();

            if(male.isChecked()){
                gender = "Male";
            }
            else if (female.isChecked()){
                gender = "Female";
            }
            else if(other.isChecked()){
                gender = "Other";
            }

            if (username.isEmpty()){
                name.setError("Enter Username");
                name.requestFocus();
            }
            else if(emailId.isEmpty()){
                email.setError("Enter Email");
                email.requestFocus();
            }
            else if(pass.isEmpty()){
                password.setError("Enter Password");
                password.requestFocus();
            }
            else if(ag.isEmpty()){
                age.setError("Enter age");
                age.requestFocus();
            }
            else {

                firebase.createUserWithEmailAndPassword(emailId, pass).addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Registratin unsuccessfull", Toast.LENGTH_LONG).show();
                    }
                    else{


                        //Calendar calendar = Calendar.getInstance();
                        //String dt = DateFormat.getInstance().format(calendar.getTime());
                        //users user = new users(username, ag, gender, dt, status);
                        //database = FirebaseDatabase.getInstance();
                        //ref = database.getReference("users");
                        //FirebaseUser use = FirebaseAuth.getInstance().getCurrentUser();

                        //ref.push().child(use.getUid()).setValue(user);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        String dt = String.valueOf(cal.getTime());

                        name.setText("");
                        email.setText("");
                        password.setText("");
                        age.setText("");
                        male.setChecked(false);
                        female.setChecked(false);
                        other.setChecked(false);

                        Intent intent = new Intent(MainActivity.this, Symptoms.class);
                        intent.putExtra("uname", username);
                        intent.putExtra("uage", ag);
                        intent.putExtra("ugender", gender);
                        intent.putExtra("ddt", dt);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, Homepage.class));
            finish();
        }
    }
}