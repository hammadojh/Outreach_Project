package com.hammad.omar.outreach.Activities;

import android.content.Intent;
import android.util.Log;

import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.BaseActivites.RegistrationBaseActivity;
import com.hammad.omar.outreach.Helping.FormEntries.DropDownFormEntry;
import com.hammad.omar.outreach.Helping.FormEntries.FormEntry;
import com.hammad.omar.outreach.Helping.FormEntries.ShortTextFormEntry;
import com.hammad.omar.outreach.Helping.FormEntries.YesNoFormEntry;
import com.hammad.omar.outreach.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OneTimeForm_4 extends RegistrationBaseActivity {


    // ui
    private YesNoFormEntry fresh_air;
    private FormEntry life_satisfied;
    private FormEntry all_things;
    private FormEntry standard;
    private FormEntry concerns;
    private FormEntry connected;
    private YesNoFormEntry asthma;
    private FormEntry helpless;
//    private ShortTextFormEntry env_exp;
//    private ShortTextFormEntry env_prob;

    public void nextButtonClicked(){

        //check form
        if (!App.checkForm(this.formEntries,this))
            return;

        // do these
        App.user.setAirFresh(fresh_air.getTrueOrFalse());
        App.user.setLifeSatisfaction(life_satisfied.getValue());
        App.user.setHowHappy(all_things.getValue());
        App.user.setStandardSatisfaction(standard.getValue());
        App.user.setPollutionImpact(concerns.getValue());
        App.user.setConnectedToCommunity(connected.getValue());
        App.user.setAsthmaDiagnosed(asthma.getTrueOrFalse());
        App.user.setHelpless(helpless.getValue());
//        App.user.set_env_exp(env_exp.getValue());
//        App.user.set_env_prob(env_prob.getValue());

        Log.d("DB",App.user.toString());

        // navigate to next screen

        Intent intent = new Intent(this,OneTimeFormCompletedActivity.class);
        startActivity(intent);

    }

    @Override
    protected String getScreenTitle() {
        return getResources().getString(R.string.well_title);
    }

    @Override
    protected String getActionButtonText() {
        return ACTION_BUTTON_TEXT_DONE;
    }

    @Override
    protected ArrayList<FormEntry> getFormElements() {


        ArrayList<FormEntry> entries = new ArrayList<>();

        // fresh air

        fresh_air = new YesNoFormEntry(getResources().getString(R.string.fresh_air_question),this);
        entries.add(fresh_air);

        // Env Exposure

//        env_exp = new ShortTextFormEntry(getString(R.string.env_exp_quesiton),this);
//        entries.add(env_prob);

        // Env Prob

//        env_prob = new ShortTextFormEntry(getString(R.string.env_problem_question),this);
//        entries.add(env_prob);

        // life satisfied

        life_satisfied = new DropDownFormEntry(getResources().getString(R.string.life_satisfied_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(life_satisfied);

        // all things satisfied

        all_things = new DropDownFormEntry(getResources().getString(R.string.all_things_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(all_things);

        // present standard

        standard = new DropDownFormEntry(getResources().getString(R.string.standard_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(standard);

        // concerns

        concerns = new DropDownFormEntry(getResources().getString(R.string.concerns_question)
                ,this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.agreement_scale))));
        entries.add(concerns);

        // connected

        connected = new DropDownFormEntry(getResources().getString(R.string.connected_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.satisfaction_scale))));
        entries.add(connected);

        // asthma

        asthma = new YesNoFormEntry(getResources().getString(R.string.asthma_question),this);
        entries.add(asthma);

        // helpless

        helpless = new DropDownFormEntry(getResources().getString(R.string.helpless_question),this,new ArrayList(Arrays.asList(getResources().getStringArray(R.array.helpless_choices))));
        entries.add(helpless);

        return entries;
    }

}
