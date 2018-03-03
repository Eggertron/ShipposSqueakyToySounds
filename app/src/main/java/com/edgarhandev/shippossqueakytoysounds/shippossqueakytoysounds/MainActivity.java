package com.edgarhandev.shippossqueakytoysounds.shippossqueakytoysounds;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final String YOUR_ADMOB_APP_ID = "ca-app-pub-5445092696369420~5862136706";
    Button stopButton;
    ImageView robinToyImg, shippoBegImg, boneToyImg, squeakerToyImg;
    MediaPlayer mp;
    Context context = this;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    int width, height, shippoImgSize;
    Random random;
    Thread shippoThread;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the display's width and height
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


        // initialize the ads
        MobileAds.initialize(this, YOUR_ADMOB_APP_ID);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mp = MediaPlayer.create(this, R.raw.bird_whistle);

        random = new Random();

        // move shippo image outside of screen
        shippoBegImg = findViewById(R.id.shippo_beg);
        shippoBegImg.setX(width);
        shippoBegImg.setY(height);
        shippoImgSize = shippoBegImg.getHeight();

        stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlayer();
            }
        });

        robinToyImg = findViewById(R.id.robin_toy_img);
        robinToyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlayer();
                showShippo();
                mp = MediaPlayer.create(context, R.raw.bird_whistle);
            }
        });

        boneToyImg = findViewById(R.id.bone_toy_img);
        boneToyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlayer();
                showShippo();
                mp = MediaPlayer.create(context, R.raw.squeaky_toy_sound_01);
            }
        });

        squeakerToyImg = findViewById(R.id.squeaker_toy_img);
        squeakerToyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlayer();
                showShippo();
                mp = MediaPlayer.create(context, R.raw.squeaky_toy_sound_02);
            }
        });
    }

    void stopPlayer() {
        try {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Function that makes shippo images pop up
     */
    void showShippo() {

        if (shippoThread == null)
            shippoThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //shippoBegImg.setX(random.nextInt(width - shippoImgSize));
                    shippoBegImg.setY(random.nextInt(height - shippoImgSize));
                    shippoBegImg.setX(-shippoBegImg.getWidth());
                    int speed = 20;
                    int fpms = 30; // frames per milliseconds.
                    boolean isRunning = true;
                    // popping out
                    while(isRunning) {
                        if (shippoBegImg.getX() > 0)
                            isRunning = false;
                        else
                            shippoBegImg.setX(shippoBegImg.getX() + speed);
                        SystemClock.sleep(fpms); // 0.03 of a second to try and get 30 fps
                    }
                    SystemClock.sleep(fpms * 20);
                    isRunning = true;
                    // returning out of screen
                    while (isRunning) {
                        if (shippoBegImg.getX() < -shippoBegImg.getWidth())
                            isRunning = false;
                        else
                            shippoBegImg.setX(shippoBegImg.getX() - speed);
                        SystemClock.sleep(fpms);
                    }
                }
            });

        if (!shippoThread.isAlive()) {
            shippoThread.start();
        }
    }
}
