package com.example.carecompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, re_enterPassword, enterName, dateOfBirth;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.emailAddress);
        signupPassword = findViewById(R.id.password);
        re_enterPassword = findViewById(R.id.txtRepassword);
        enterName = findViewById(R.id.txtName);
        dateOfBirth = findViewById(R.id.txtDOB);
        signupButton = findViewById(R.id.btnSignUp);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String re_pass = re_enterPassword.getText().toString().trim();
                String name = enterName.getText().toString().trim();
                String DOB = dateOfBirth.getText().toString().trim();

                if (user.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                }
                if (re_pass.isEmpty()){
                    re_enterPassword.setError("Password cannot be empty");
                }
                if (name.isEmpty()){
                    enterName.setError("Section left empty");
                }
                if (DOB.isEmpty()){
                    dateOfBirth.setError("Section left empty");
                }
                else{
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, SignIn.class));
                            }
                            else{
                                Toast.makeText(SignUp.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}