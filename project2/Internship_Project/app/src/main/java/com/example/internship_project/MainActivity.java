package com.example.internship_project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner categorySpinner;
    private Spinner unitSpinner;
    private Button convertButton;
    private Button clearButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        categorySpinner = findViewById(R.id.categorySpinner);
        unitSpinner = findViewById(R.id.unitSpinner);
        convertButton = findViewById(R.id.convertButton);
        clearButton = findViewById(R.id.clearButton);
        resultView = findViewById(R.id.resultView);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void updateUnitSpinner(int categoryPosition) {
        int unitsArrayId;
        switch (categoryPosition) {
            case 0: // Length
                unitsArrayId = R.array.length_units_array;
                break;
            case 1: // Weight
                unitsArrayId = R.array.weight_units_array;
                break;
            case 2: // Temperature
                unitsArrayId = R.array.temperature_units_array;
                break;
            case 3: // Volume
                unitsArrayId = R.array.volume_units_array;
                break;
            default:
                unitsArrayId = R.array.length_units_array;
                break;
        }

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this,
                unitsArrayId, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);
    }

    private void convertUnits() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(input);
        String selectedUnit = unitSpinner.getSelectedItem().toString();
        double result = 0.0;

        switch (selectedUnit) {
            case "Centimeters to Meters":
                result = value / 100;
                break;
            case "Meters to Centimeters":
                result = value * 100;
                break;
            case "Grams to Kilograms":
                result = value / 1000;
                break;
            case "Kilograms to Grams":
                result = value * 1000;
                break;
            case "Celsius to Fahrenheit":
                result = (value * 9/5) + 32;
                break;
            case "Fahrenheit to Celsius":
                result = (value - 32) * 5/9;
                break;
            case "Milliliters to Liters":
                result = value / 1000;
                break;
            case "Liters to Milliliters":
                result = value * 1000;
                break;
        }

        resultView.setText(String.valueOf(result));
    }

    private void clearFields() {
        inputValue.setText("");
        resultView.setText("");
    }
}
