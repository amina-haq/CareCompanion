package com.example.carecompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.emailAddress);
        loginPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.btnLogin);
        signupRedirectText = findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                  if (!pass.isEmpty()){
                      auth.signInWithEmailAndPassword(email, pass)
                              .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                  @Override
                                  public void onSuccess(AuthResult authResult) {
                                      Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                      startActivity(new Intent(SignIn.this, Homepage.class));
                                      finish();
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(SignIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                  }
                              });
                  }
                  else {
                      loginPassword.setError("Password cannot be empty");
                  }
            }
            else if(email.isEmpty()){
                loginEmail.setError("Email connot be empty");
                }
            else {
                loginEmail.setError("Please enter valid email");
                }
            }
        });

                signupRedirectText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SignIn.this, SignUp.class));
                    }
                });
    }
}
