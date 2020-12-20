package com.example.cts;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class splash extends AppCompatActivity {

    ImageView one, two, three, four;
    TextView first;

    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        one = findViewById(R.id.image1);
        two = findViewById(R.id.image2);
        three = findViewById(R.id.imageView3);
        four = findViewById(R.id.imageView4);
        first = findViewById(R.id.first);

        //To set the full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TO initialize top animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_image);

        // TO start the animation
        one.setAnimation(animation);
        //Initializing animation object
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                two, PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );

        // to set duration
        objectAnimator.setDuration(500);
        //To repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        // To start animation
        objectAnimator.start();

        // set animation text

        animationText("CTS-V01 ");

        // to load gif
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b")
                .asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(four);

        // TO initialize bottom animation
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bottom_image);

        // TO start bottom animation
        three.setAnimation(animation1);

        // initialize haldler

        boolean handler1= new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                // To finish the activity
                finish();
            }
        }, 4000);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // set text when runnable

            first.setText(charSequence.subSequence( 0, index++));

            if(index<charSequence.length()){
                // when index is equal to text length

                handler.postDelayed(runnable, delay);
            }
        }
    };

    // This methos is to create animated text

    public void animationText(CharSequence cs){
        // set text
        charSequence = cs;
        // clear index
        index=0;

        //clear text
        first.setText("");

        // to remove callbacks
        handler.removeCallbacks(runnable);

        //to run handler
        handler.postDelayed(runnable, delay);
    }
}