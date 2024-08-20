package com.example.fightersoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Settings extends AppCompatActivity {

    // declare the components in the activity
    EditText editTextEmail, editTextPassword;
    TextView textViewSettings;
    Button updateButton, colorButton, deleteButton;
    int colorCount = 0;

    // create a variable to allow for DB usage
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        // function that plays when deleteButton is clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                deleteUser();

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                resetPassword();

            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            private Activity onClick;

            @Override
            public void onClick(View view) {

                if(colorCount == 3) colorCount = 0;

                colorCount++;

                if(colorCount == 1) Utils.changeTheme(this.onClick, Utils.THEME_DEFAULT);
                if(colorCount == 2) Utils.changeTheme(this.onClick, Utils.THEME_WHITE);
                if(colorCount == 3) Utils.changeTheme(this.onClick, Utils.THEME_BLUE);

            }
        });

    }

    // Function for deleting a user
    private void deleteUser() {

        String email, password;
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("Enter email to delete account");
            editTextEmail.requestFocus();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(email != user.getEmail())
        {
            editTextEmail.setError("Incorrect email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editTextPassword.setError("Enter correct password");
            editTextPassword.requestFocus();
            return;
        }

        else
        {
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            if(user != null)
            {
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    Toast.makeText(getApplicationContext(), "Deleted User Successfully", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }
                });
            }
        }

    }

    // Function for resetting a user's password
    private void resetPassword() {

        // get the values of the components in terms of Strings
        String email;
        email = editTextEmail.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("Email required to change password");
            editTextEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Check email to reset password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect email", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}