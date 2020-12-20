package com.example.cts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class Update {

    String fif;

    public Update(String fif){
        this.fif = fif;

        Query query9 = FirebaseDatabase.getInstance().getReference("users").orderByChild("dt").equalTo(fif);

        query9.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();

                assert newPost != null;
                String imp = Objects.requireNonNull(newPost.get("status")).toString();

                if(imp.equals("POSITIVE")){
                    String idd = Objects.requireNonNull(newPost.get("id")).toString();

                    Query query10 = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(idd);
                    query10.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1: snapshot.getChildren()){
                                snapshot1.getRef().child("status").setValue("RECOVERED");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Error");

                        }
                    });
                    System.out.println("user id is this:" + idd);
                }


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
}
