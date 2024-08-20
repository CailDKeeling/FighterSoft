package com.example.fightersoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonSet;
    private Button buttonLog;
    private Button buttonRegister;
    private Button buttonGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSet = (Button) findViewById(R.id.button1);
        buttonSet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSettings();
            }
        });

        buttonLog = (Button) findViewById(R.id.button5);
        buttonLog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View n)
            {
                openLog();

            }
        });

        buttonRegister = (Button) findViewById(R.id.button6);
        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View r)
            {
                openRegister();

            }
        });

        buttonGame = (Button) findViewById(R.id.button2);
        buttonGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View p)
            {
                openGame();

            }
        });
    }
    public void openSettings(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void openLog(){
        Intent intention = new Intent(this, Login.class);
        startActivity(intention);
    }
    public void openRegister(){
        Intent impartial = new Intent(this, SignUp.class);
        startActivity(impartial);
    }
    public void openGame(){
        Intent imperative = new Intent(this, Game.class);
        startActivity(imperative);
    }
}
