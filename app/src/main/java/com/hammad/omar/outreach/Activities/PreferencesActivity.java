package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Fragments.PreferencesFragment;
import com.hammad.omar.outreach.R;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //title
        setTitle("Settings");

        //init fragment
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pref, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout_item) {

            boolean signedOut = App.authManager.signout();

            if (signedOut) {
                // go to auth screen
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

}