package com.example.mobileassignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    private Button kz_button_10, kz_button_15, kz_button_20;

    private EditText kz_input_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeListeners();
    }

    private void initializeViews() {
        kz_button_10 = findViewById(R.id.kz_button_10);
        kz_button_15 = findViewById(R.id.kz_button_15);
        kz_button_20 = findViewById(R.id.kz_button_20);

        kz_input_field = findViewById(R.id.kz_input_amount);
    }

    private void initializeListeners() {
        kz_button_10.setOnClickListener(v -> sameActivityResult("10"));
        kz_button_15.setOnClickListener(v -> sameActivityResult("15"));
        kz_button_20.setOnClickListener(v -> newActivityResult());
    }

    private void sameActivityResult(String percentage) {
        String kz_result = getResult(percentage);
        if (kz_result.isEmpty()) return;

        String kz_message = "Your tip is " + kz_result + "$";
        Toast.makeText(this, kz_message, Toast.LENGTH_SHORT).show();
    }

    private void newActivityResult() {
        String kz_result = getResult("20");
        if (kz_result.isEmpty()) return;

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("kz_result", kz_result);
        startActivity(intent);
    }

    private String getResult(String percentage) {
        String kz_user_input = kz_input_field.getText().toString();
        if (invalidInput(kz_user_input)) {
            kz_input_field.setError("Please enter a valid number");
            return "";
        }

        float kz_numeric_input = Float.parseFloat(kz_user_input);

        float kz_percentage_decimal = Float.parseFloat(percentage) / 100;
        return formatNumber(kz_numeric_input * kz_percentage_decimal);
    }

    private boolean invalidInput(String input) {
        if (input.isEmpty()) return true; // Empty input
        try {
            Float.parseFloat(input);
            return false; // Valid number
        } catch (NumberFormatException e) {
            return true; // Invalid number
        }
    }

    private String formatNumber(float number) {
        BigDecimal kz_big_decimal = new BigDecimal(number).setScale(2, RoundingMode.HALF_UP);
        return kz_big_decimal.stripTrailingZeros().toPlainString();

    }
}