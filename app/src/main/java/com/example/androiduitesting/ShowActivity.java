package com.example.androiduitesting;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get the city name from the intent
        String cityName = getIntent().getStringExtra("CITY_NAME");

        // Display the city name
        TextView cityNameTextView = findViewById(R.id.cityNameTextView);
        cityNameTextView.setText(cityName);

        // Set up back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}