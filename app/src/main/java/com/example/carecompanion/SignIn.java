package com.example.carecompanion;//package com.example.carecompanion;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//
//public class SignIn extends AppCompatActivity {
//
//    private FirebaseAuth auth;
//    private EditText loginEmail, loginPassword;
//    private Button loginButton;
//    private TextView signupRedirectText;
//    Button googleAuth;
//    FirebaseDatabase database;
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_sign_in);
//
//        auth = FirebaseAuth.getInstance();
//        loginEmail = findViewById(R.id.emailAddress);
//        loginPassword = findViewById(R.id.password);
//        loginButton = findViewById(R.id.btnLogin);
//        signupRedirectText = findViewById(R.id.register);
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = loginEmail.getText().toString();
//                String pass = loginPassword.getText().toString();
//
//                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                  if (!pass.isEmpty()){
//                      auth.signInWithEmailAndPassword(email, pass)
//                              .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                  @Override
//                                  public void onSuccess(AuthResult authResult) {
//                                      Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                      startActivity(new Intent(SignIn.this, Homepage.class));
//                                      finish();
//                                  }
//                              }).addOnFailureListener(new OnFailureListener() {
//                                  @Override
//                                  public void onFailure(@NonNull Exception e) {
//                                      Toast.makeText(SignIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                                  }
//                              });
//                  }
//                  else {
//                      loginPassword.setError("Password cannot be empty");
//                  }
//            }
//            else if(email.isEmpty()){
//                loginEmail.setError("Email cannot be empty");
//                }
//            else {
//                loginEmail.setError("Please enter valid email");
//                }
//            }
//        });
//
//                signupRedirectText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(SignIn.this, SignUp.class));
//                    }
//                });
//
//
//


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carecompanion.Homepage;
import com.example.carecompanion.R;
import com.example.carecompanion.SignUp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;
    private Button googleAuth;
    private FirebaseDatabase database;
    private GoogleSignInClient gsc;

    // Register for activity result
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Get the Google sign-in account from the Intent
                    Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    if (signInAccountTask.isSuccessful()) {
                        try {
                            GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                            if (googleSignInAccount != null) {
                                AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                                firebaseAuthWithGoogle(credential);
                            }
                        } catch (ApiException e) {
                            Toast.makeText(SignIn.this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.emailAddress);
        loginPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.btnLogin);
        signupRedirectText = findViewById(R.id.register);
        googleAuth = findViewById(R.id.googleLoginButton);

        database = FirebaseDatabase.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
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
                                        Toast.makeText(SignIn.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        loginPassword.setError("Password cannot be empty");
                    }
                }
                else if (email.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("438431947620-ecpi41uk3dhhf4mv8g8q993k3vs49ltm.apps.googleusercontent.com")
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(SignIn.this, gso);

        googleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = gsc.getSignInIntent();
                googleSignInLauncher.launch(signInIntent);
            }
        });
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignIn.this, Homepage.class));
                    finish();
                } else {
                    Toast.makeText(SignIn.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
