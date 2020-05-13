package com.thunder146.travelcostcalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etDistance = null;
    private EditText etConsumption = null;
    private EditText etPrice = null;
    private EditText etPeople = null;
    private TextView tvResult = null;
    private String defaultText = "--- €";
    private String startUpDataFilePath;
    private String fileName = "startUp.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startUpDataFilePath = getCacheDir() + "/" + fileName;

        GetControls();
        RegisterOnEvents();
        setDefaults();
        restoreLastSessionData();
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
        if (id == R.id.action_reset) {
            resetText();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_HOME) {
            saveCurrentSession();
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveCurrentSession();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCurrentSession();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreLastSessionData();
    }

    // this method will be called, when the memory is low.
    // It's only can be called from the system
    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveCurrentSession();
    }

    private void GetControls() {
        etDistance = (EditText) findViewById(R.id.EditTextDistance);
        etConsumption = (EditText) findViewById(R.id.EditTextConsumption);
        etPrice = (EditText) findViewById(R.id.EditTextPrice);
        etPeople = (EditText) findViewById(R.id.EditTextPeople);
        tvResult = (TextView) findViewById(R.id.TextViewResult);
    }

    private void RegisterOnEvents() {
        // TextChanged EventHandler
        etDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });
        etConsumption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });
        etPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });
    }


    private void calculate() {
        try {
            float distance = 0;
            float consumption = 0;
            float price = 0;
            int people = 0;

            if (etDistance.getText().length() > 0) {
                distance = Float.parseFloat(etDistance.getText().toString());
            }
            if (etConsumption.getText().length() > 0) {
                consumption = Float.parseFloat(etConsumption.getText().toString());
            }
            if (etPrice.getText().length() > 0) {
                price = Float.parseFloat(etPrice.getText().toString());
            }
            if (etPeople.getText().length() > 0) {
                people = Integer.parseInt(etPeople.getText().toString());
            }

            if (Validator.validateValues(distance, consumption, price, people)) {
                double result = price * consumption / 100 * distance / people;
                result = Math.round(result * 100.0) / 100.0;
                tvResult.setText(String.valueOf(result) + " €");

                setTextColor(tvResult, Color.rgb(0, 222, 85));
            } else {
                setDefaults();
            }
        } catch (Exception ex) {
            // TODO write log
            resetText();
        }
    }

    private void setTextColor(TextView textView, Integer color) {
        textView.setTextColor(color);
    }

    private void resetText() {
        etDistance.setText("");
        etConsumption.setText("");
        etPrice.setText("");
        etPeople.setText("");
        tvResult.setText(defaultText);
    }

    private void setDefaults() {
        tvResult.setText(String.valueOf(defaultText));
        setTextColor(tvResult, Color.rgb(255, 170, 0));
    }

    private void saveCurrentSession() {
        try {
            TripData data = new TripData();
            data.setDistance(etDistance.getText().toString());
            data.setConsumption(etConsumption.getText().toString());
            data.setPrice(etPrice.getText().toString());
            data.setPeople(etPeople.getText().toString());

            FileAccess.writeTripDataToFile(data, startUpDataFilePath);

        } catch (IOException ex) {
            // TODO write log
        }
    }

    private void restoreLastSessionData() {
        File f = new File(startUpDataFilePath);

        if (f.exists()) {
            TripData data = FileAccess.readTripDataFromFile(startUpDataFilePath);

            if (data != null) {
                // Fill the textboxes
                etDistance.setText(data.getDistance());
                etConsumption.setText(data.getConsumption());
                etPrice.setText(data.getPrice());
                etPeople.setText(data.getPeople());
                // result will be calculated ...
            }
        }
    }

    private void ShowInfo() {
        // TODO Show Info
    }
}
