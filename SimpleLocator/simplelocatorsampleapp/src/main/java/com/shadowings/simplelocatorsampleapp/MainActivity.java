package com.shadowings.simplelocatorsampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shadowings.core.MainViewModel;
import com.shadowings.simplelocator.SimpleLocator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = SimpleLocator.get(MainViewModel.class);

        TextView messageTextView = findViewById(R.id.message_textView);
        messageTextView.setText(mainViewModel.getMessage());

        TextView namedOneTextView = findViewById(R.id.namedOne_textView);
        namedOneTextView.setText(mainViewModel.getNamedOne());

        TextView namedTwoTextView = findViewById(R.id.namedTwo_textView);
        namedTwoTextView.setText(mainViewModel.getNamedTwo());
    }
}
