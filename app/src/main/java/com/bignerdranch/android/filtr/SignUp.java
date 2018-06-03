package com.bignerdranch.android.filtr;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private Button signUpButton;
    private EditText passwordSignUp, emailSignUp;
    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Assign variables
        signUpButton = findViewById(R.id.button_signUp_signUp);
        emailSignUp = findViewById(R.id.editText_email_signUp);
        passwordSignUp = findViewById(R.id.editText_password_signUp);
        mAuth = FirebaseAuth.getInstance();

        //On sign up button clicked, check fields and send new user to database //
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Make sure fields are not empty
                if (emailSignUp.getText().toString().isEmpty() || passwordSignUp.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();

                } else {
                    //If fields are filled, attempt to create new user in Firebase
                    mAuth.createUserWithEmailAndPassword(emailSignUp.getText().toString(), passwordSignUp.getText().toString())
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });


    }


}
