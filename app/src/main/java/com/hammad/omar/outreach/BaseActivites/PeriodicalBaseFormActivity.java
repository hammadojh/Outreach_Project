package com.hammad.omar.outreach.BaseActivites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.hammad.omar.outreach.Adapters.ListImageAdapter;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.R;

import java.util.ArrayList;

public abstract class PeriodicalBaseFormActivity extends AppCompatActivity {

    private static final String TAG = PeriodicalBaseFormActivity.class.getSimpleName();

    //model
    protected String[] listItems;

    // UI
    protected ListImageAdapter listAdapter;
    protected GridView gridView;
    private Button nextBtn;
    private TextView subtitle;

    //flags
    private int numOfSelected = 0;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();
    private int maxAllowedSelection = 2;

    //instance vars
    protected int numOfItems;
    protected String currentSelectionText;


    //abstract methods
    protected abstract String getScreenTitle();
    public abstract String getTitleQuestion();
    protected abstract String[] getTextsArray();
    protected abstract String[] getFacesArray();
    protected abstract Intent getNextIntent();
    protected abstract void addItemToModel(String text);
    protected abstract void removeItemFromModel();
    protected boolean showSubtitle(){return false;}
    protected void specificCode() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_1);

        // step specific code

        specificCode();

        // max allowed selection

        maxAllowedSelection = getMaxAllowedSelection();

        // setup ui

        setTitle(getScreenTitle());
        TextView titleTV = findViewById(R.id.textView);
        titleTV.setText(getTitleQuestion());
        nextBtn = findViewById(R.id.button);
        nextBtn.setVisibility(View.INVISIBLE);
        subtitle = findViewById(R.id.subTitle);
        subtitle.setVisibility(showSubtitle() ? View.VISIBLE : View.GONE);

        //data

        listItems = getTextsArray();
        String[] images = getFacesArray();

        gridView = (GridView) findViewById(R.id.gridView);
        listAdapter = new ListImageAdapter(this, listItems, images);
        gridView.setAdapter(listAdapter);

        // num of items
        numOfItems = listAdapter.getCount();

        //selectiing
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected(view, position);
            }
        });


    }

    protected void itemSelected(View view, int position) {
        setItemSelected(view, position);
    }

    protected void setItemSelected(View view, int position) {

        if (isSelected(position)) {
            Log.d(TAG,"already selected");
            unselect(position, view);
            removeItemFromModel();
        } else {

            Log.d(TAG,"new");

            // get item text
            currentSelectionText = ((TextView) view.findViewById(R.id.textView_text)).getText().toString();
            Log.d(TAG,currentSelectionText);

            // if other is selected
            if (currentSelectionText.equalsIgnoreCase("other")) {
                promptUserWithInput(view, position);
            } else {
                // getting the item clicked
                select(position, view);
                addItemToModel(currentSelectionText);
                if (numOfSelected == maxAllowedSelection) {
                    navigateToNextScreen();
                }
            }
        }

    }

    private void promptUserWithInput(final View view, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.whatOther);

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setPadding(16, 0, 0, 16);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                okClicked(input.getText().toString(), view, position);
            }
        });

        builder.show();

    }

    private void okClicked(String input, final View view, final int position) {

        if (input != "") {
            currentSelectionText = input;
        } else {
            currentSelectionText = "Other";
        }

        // delay for the dialog animation

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                select(position, view);
                addItemToModel(currentSelectionText);
                if (numOfSelected == maxAllowedSelection) {
                    navigateToNextScreen();
                }
            }
        }, 500);

    }

    protected void hideButton() {
        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setEnabled(true);
    }

    protected void showButton() {

        if (maxAllowedSelection < 2) {
            return;
        }

        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setEnabled(false);
    }

    protected int getMaxAllowedSelection() {
        return 2;
    }

    protected boolean isSelected(int position) {
        return selectedItems.contains(position);
    }

    protected void select(int position, View view) {
        selectedItems.add(position);
        numOfSelected++;
        view.setSelected(true);
        view.setBackgroundResource(R.drawable.cell_background_selected);
        hideButton();
    }

    protected void unselect(int position, View view) {
        selectedItems.remove(0);
        numOfSelected--;
        view.setBackgroundResource(R.drawable.cell_background);
        showButton();
    }

    protected void navigateToNextScreen() {

        Intent intent = getNextIntent();
        startActivity(intent);

    }

    protected void nextClicked(View view) {
        navigateToNextScreen();
    }

}