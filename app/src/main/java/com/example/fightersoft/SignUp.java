package com.example.fightersoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    // create variables for components in the sign up activity
    EditText editTextEmail, editTextUsername, editTextPassword;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    // create a variable to allow for DB usage
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize all components on activity
        this.editTextUsername = (EditText)findViewById(R.id.username);
        this.editTextPassword = (EditText)findViewById(R.id.password);
        this.editTextEmail = (EditText)findViewById(R.id.email);
        this.buttonSignUp = findViewById(R.id.signupButton);
        this.textViewLogin = findViewById(R.id.loginText);
        this.progressBar = findViewById(R.id.progress);

        // initialize this instance of the database
        mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signUp();

            }

        });

        // if user selects the login option
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToLogin();

            }
        });

    }

    // Method used to sign up a user
    protected void signUp() {

        // get the values of the components in terms of Strings
        String username, password, email;
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();

        // if user left the username space empty
        if(username.isEmpty())
        {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
        }

        // if the password space is less than 6 characters (firebase doesn't accept passwords under 6 characters)
        if(password.isEmpty())
        {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
        }

        // if user left email space empty
        if(email.isEmpty())
        {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
        }

        // if user's email isn't compatible with app
        if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches()))
        {
            editTextEmail.setError("Please provide a valid email");
            editTextEmail.requestFocus();
        }

        // if the password space is less than 6 characters (firebase doesn't accept passwords under 6 characters)
        if(password.length() < 6)
        {
            editTextPassword.setError("Password must be at least 6 characters long");
            editTextPassword.requestFocus();
        }

        // if all fields were inputted in properly, put in the info into the database
        else
        {

            progressBar.setVisibility(View.VISIBLE);                        // show loading

            /* WHERE INPUT INTO DATABASE IS DONE */
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    // check if the creation was successful
                    if(task.isSuccessful())
                    {
                        // create a new user with info to send to database
                        User user = new User(username, password, email);

                        // create a firebase object and store the user in it
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Failed to Register. Try again.", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed to Register. Try again.", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });

        }

    }

    // Method for moving to the login page
    protected void moveToLogin() {

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();

    }

}