package com.example.carecompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    // Method to handle the launch of the Settings page
    public void launchSettingsPage(View view) {
        // Create an Intent to open the SettingsActivity
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void launchMedicalIDPage(View view) {
        // Create an intent to launch the Medical ID page
        Intent intent = new Intent(this, Medical_Id.class);
        startActivity(intent);
    }
}


