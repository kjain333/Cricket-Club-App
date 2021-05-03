package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class LoginActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    Button updateBtn, switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBarWhite));

        }

        emailEditText =  (com.google.android.material.textfield.TextInputEditText )findViewById(R.id.loginEmailEditText);
        passwordEditText =  (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.loginPasswordEditText);
        switchButton = (Button) findViewById(R.id.switchButton);
        updateBtn = (Button) findViewById(R.id.updatePlayers);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            login();
        }

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     Toast.makeText(getBaseContext(),"Please contact admin for signup",Toast.LENGTH_LONG).show();
                Intent switchIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(switchIntent);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuctionAddPlayer.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser(View view){
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            login();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Login Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void goToAuctions(View view){
        Intent intent = new Intent(getApplicationContext(), Auctions.class);
        startActivity(intent);
    }

}