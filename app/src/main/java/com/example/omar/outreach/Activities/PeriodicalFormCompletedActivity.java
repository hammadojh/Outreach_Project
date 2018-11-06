package com.example.omar.outreach.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.CallBackLocation;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.Managers.RewardManager;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.R;

public class PeriodicalFormCompletedActivity extends AppCompatActivity implements CallBackLocation{

    private Location location;
    private EntriesDataSource entriesDataSource;

    //consts
    private int WAIT_TIME_MILLIS = 2500;

    //ui
    TextView totalTv;
    TextView youEarnedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_completed);

        entriesDataSource = new EntriesDataSource(this);

        //init ui
        totalTv = findViewById(R.id.rewardTextView);
        youEarnedTv = findViewById(R.id.youEarnedTextView);

        // set creation date
        App.inputEntry.setCreationDate(App.getCurrentDateString());

        // set location
        if(LocationManager.isLocationEnabled(this)){
            new LocationManager(this,this).getCurrentLocation();
        }else{
            showDialog();
        }
    }

    private void setupRewardViews() {

        Double totalReward = RewardManager.calculateReward(entriesDataSource.getNumOfItems());
        Double entryReward = RewardManager.REWARD_PER_ENTRY;

        youEarnedTv.setText("👍 You earned +¢"+entryReward);
        totalTv.setText("$"+totalReward);

    }

    private void showDialog() {
        // notify user
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Please Enable you location");

        dialog.setPositiveButton("Enable Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });

        dialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                userDidNotEnableLocation();
            }
        });

        dialog.show();
    }

    private void userDidNotEnableLocation() {

    }

    private void navigateToMain() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void callbackCurrentLocation(Object object) {

        if(object instanceof Location){
            location = (Location) object;
            App.inputEntry.setLatLng(location.getLatitude()+"",location.getLongitude()+"");
            backFromLocaion();
        }else{
            backFromLocaion();
        }
    }

    private void backFromLocaion(){

        entriesDataSource.insertItem(App.inputEntry);

        // change UI
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                setupRewardViews();
            }
        });

        navigateToNextScreen();


    }

    private void navigateToNextScreen() {

        // go back after 2 secs from the main thread
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMain();
            }
        }, WAIT_TIME_MILLIS);

    }
}