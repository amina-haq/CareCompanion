package com.example.carecompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Medical_Id extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medical_id);
    }

    // Method for back button to navigate to Homepage
    public void launchHomepage(View view) {
        Intent intent = new Intent(Medical_Id.this, Homepage.class);
        startActivity(intent);
    }

    // Method for settings button to navigate to Settings Activity
    public void launchSettingsPage(View view) {
        Intent intent = new Intent(Medical_Id.this, Settings.class);
        startActivity(intent);
    }
}
