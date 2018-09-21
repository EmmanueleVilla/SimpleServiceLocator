package com.shadowings.simplelocatorsampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shadowings.core.MainViewModel;
import com.shadowings.core.MySampleInterface;
import com.shadowings.simplelocator.IObjectFactory;
import com.shadowings.simplelocator.SimpleLocator;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new MainViewModel();

        messageTextView = findViewById(R.id.message_textView);
        messageTextView.setText(mainViewModel.getMessage());
    }
}
