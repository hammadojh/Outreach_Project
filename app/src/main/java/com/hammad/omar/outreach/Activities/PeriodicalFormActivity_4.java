package com.hammad.omar.outreach.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Helping.FormEntries.*;
import com.hammad.omar.outreach.Helping.FormEntries.ScaleFormEntry;
import com.hammad.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalFormActivity_4 extends AppCompatActivity {


    private static LinearLayout formLayout;
    private ArrayList<FormEntry> formEntries;

    // ui

    private FormEntry air_quality;
    private FormEntry noise;
    private FormEntry transportation;
    private FormEntry active;

    // consts

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_4);

        // form layout

        formLayout = findViewById(R.id.formLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 24;

        //TITLE

        TextView tvTitle = findViewById(R.id.textView);
        tvTitle.setText(R.string.answerTheFollowing);
        setTitle(getString(R.string.env));

        // Form model

        formEntries = new ArrayList<>();

        // form entries

        air_quality = new ScaleFormEntry(getResources().getString(R.string.air_quality_question),this,5, getString(R.string.unacceptable),getString(R.string.highlyAccept));
        air_quality = new ImageFormEntryDecorator(air_quality,R.id.airEmojie);
        formEntries.add(air_quality);

        noise = new ScaleFormEntry(getResources().getString(R.string.noise_question),this,5,  getString(R.string.unacceptable),getString(R.string.highlyAccept));
        formEntries.add(noise);

        transportation = new ScaleFormEntry(getResources().getString(R.string.transportation_question),this,5,getString(R.string.unacceptable),getString(R.string.highlyAccept));
        formEntries.add(transportation);

        active = new ScaleFormEntry(getResources().getString(R.string.active_question),this,5, getString(R.string.notActive),getString(R.string.veryActive));
        formEntries.add(active);

        // ADD entries
        addEntries(formEntries);

        // Add Next Button

        Button nextButton = new Button(this);
        nextButton.setText(R.string.next);
        nextButton.setTag(getString(R.string.button));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonClicked();
            }
        });

        formLayout.addView(nextButton);
    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(this,PeriodicalFormActivity_5.class);
        startActivity(intent);
    }

    private void nextButtonClicked() {

        if (!App.checkForm(this.formEntries,this))
            return;

        App.inputEntry.setOdor(""+air_quality.getValue().substring(0,1));
        App.inputEntry.setNoise(""+noise.getValue().substring(0,1));
        App.inputEntry.setTransportation(""+transportation.getValue().substring(0,1));
        App.inputEntry.setActive(""+active.getValue().substring(0,1));

        navigateToNextScreen();
    }

    private void addEntries(List<FormEntry> formEntries) {
        for (int i = 0 ; i < formEntries.size() ; i++){
            FormEntry entry = formEntries.get(i);
            formLayout.addView(entry.getView());
        }
    }

}
