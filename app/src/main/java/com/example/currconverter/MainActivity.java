package com.example.currconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.currconverter.repository.ServiceLocator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceLocator.init(getApplication());
    }
}