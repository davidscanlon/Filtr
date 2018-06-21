package com.bignerdranch.android.filtr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signUpButton, signInButton;
    private EditText email, password;
    private int MY_PERMISSIONS_ACCESS_LOCATION;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        mAuth = FirebaseAuth.getInstance();

        //Identify buttons and text fields
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        signUpButton = findViewById(R.id.button_signUp);
        signInButton = findViewById(R.id.button_signIn);

        //Check for location permission. If not granted, request permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request permission
            //-----CURRENTLY WILL PROBABLY BREAK IF USER SAYS NO-----
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_LOCATION);
        }

        //On Sign-in validate user credentials and move to map
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make sure fields are not empty
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Make sure no fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent k = new Intent(MainActivity.this, MapsActivity.class);
                                        startActivity(k);
                                    } else {
                                        //Login error
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //On Sign-up, open account creator
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });

    }
}
