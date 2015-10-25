package com.smiler.volleyball_scoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, View.OnLongClickListener {

    private SharedPreferences statePref, sharedPref;
    private boolean doubleBackPressedFirst;

    private TextView hScoreView, gScoreView;
    private TextView hNameView, gNameView, periodView;
    private short hScore, gScore, period;
    private String hName, gName;

    private int soundWhistleId, soundHornId;
    private SoundPool soundPool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initElements();
        int MAX_STREAMS = 5;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(aa)
                    .build();
        } else {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 1);
        }
        soundWhistleId = soundPool.load(this, R.raw.whistle, 1);
        soundHornId = soundPool.load(this, R.raw.airhorn_short, 1);
    }

    private void initElements() {
        hScoreView = (TextView) findViewById(R.id.homeScoreView);
        gScoreView = (TextView) findViewById(R.id.guestScoreView);
        hNameView = (TextView) findViewById(R.id.homeNameView);
        gNameView = (TextView) findViewById(R.id.guestNameView);
        periodView = (TextView) findViewById(R.id.periodView);
//        ImageView whistleView = (ImageView) findViewById(R.id.whistleView);
//        ImageView startTimeoutView = (ImageView) findViewById(R.id.timeoutView);
//        ImageView startCameraView = (ImageView) findViewById(R.id.cameraView);
//        TextView homeScoreMinus1 = (TextView) findViewById(R.id.minus1HomeView);
//        TextView guestScoreMinus1 = (TextView) findViewById(R.id.minus1GuestView);

        hScoreView.setOnClickListener(this);
        gScoreView.setOnClickListener(this);
        periodView.setOnClickListener(this);

//        whistleView.setOnClickListener(this);
//        startTimeoutView.setOnClickListener(this);
//        startCameraView.setOnClickListener(this);

        hScoreView.setOnLongClickListener(this);
        gScoreView.setOnLongClickListener(this);
        hNameView.setOnLongClickListener(this);
        gNameView.setOnLongClickListener(this);
        periodView.setOnLongClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackPressedFirst) {
                super.onBackPressed();
                return;
            }
            this.doubleBackPressedFirst = true;
            Toast.makeText(this, getResources().getString(R.string.toast_confirm_exit), Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPressedFirst = false;
                }
            }, 3000);
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeScoreView:
                changeHomeScore(1);
                break;
            case R.id.guestScoreView:
                changeGuestScore(1);
                break;
/*
            case R.id.minus1HomeView:
                if (hScore > 0) { changeHomeScore(-1); }
                break;
            case R.id.minus1GuestView:
                if (gScore > 0) { changeGuestScore(-1); }
                break;
*/
            case R.id.periodView:
                newPeriod(true);
                break;
/*            case R.id.whistleView:
                playWhistle();
                break;
            case R.id.hornView:
                playHorn();
                break;

            case R.id.timeoutView:
//                showListDialog("timeout");
                break;
            case R.id.newPeriodIconView:
//                showListDialog("new_period");
                break;
*/
/*
            case R.id.homeTimeoutsView:
                timeout(0);
                break;
            case R.id.guestTimeoutsView:
                timeout(1);
                break;
*/
            case R.id.cameraView:
                if (checkCameraHardware(this)) { /*runCameraActivity();*/ }
                break;
            default:
                break;
        }
        
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.homeScoreView:
                hScore = 0;
                hScoreView.setText("00");
                return true;
            case R.id.guestScoreView:
                gScore = 0;
                gScoreView.setText("00");
                return true;
            case R.id.periodView:
                newPeriod(false);
                return true;
/*
            case R.id.homeTimeoutsView:
                nullTimeouts(0);
                return true;
            case R.id.guestTimeoutsView:
                nullTimeouts(1);
                return true;
*/
/*
            case R.id.homeNameView:
                chooseTeamNameDialog("home", hName);
                return true;
            case R.id.guestNameView:
                chooseTeamNameDialog("guest", gName);
                return true;
*/
            default:
                return false;
        }
    }

    public boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void playWhistle() {
        soundPool.play(soundWhistleId, 1, 1, 0, 0, 1);
    }

    private void playHorn() {
        soundPool.play(soundHornId, 1, 1, 0, 0, 1);
    }

    private void changeGuestScore(int value) {
        gScore += value;
        gScoreView.setText(String.format(Constants.FORMAT_TWO_DIGITS, gScore));
    }

    private void changeHomeScore(int value) {
        hScore += value;
        hScoreView.setText(String.format(Constants.FORMAT_TWO_DIGITS, hScore));
    }

    private void newPeriod(boolean next) {
        if (next) {
            period++;
        } else {
            period = 1;
        }
        periodView.setText(Short.toString(period));
//        setTimeouts();
//        saveResult();
//        scoreSaved = false;
    }

    private void setTeamNames(String home, String guest) {
        hNameView.setText(home);
        gNameView.setText(guest);
    }

    private void setTeamNames() {
        setTeamNames(hName, gName);
    }
}
