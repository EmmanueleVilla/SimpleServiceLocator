package com.shadowings.simplelocatorsampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shadowings.core.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private TextView messageTextView;
    private TextView namedOneTextView;
    private TextView namedTwoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new MainViewModel();

        messageTextView = findViewById(R.id.message_textView);
        messageTextView.setText(mainViewModel.getMessage());

        namedOneTextView = findViewById(R.id.namedOne_textView);
        namedOneTextView.setText(mainViewModel.getNamedOne());

        namedTwoTextView = findViewById(R.id.namedTwo_textView);
        namedTwoTextView.setText(mainViewModel.getNamedTwo());
    }
}
