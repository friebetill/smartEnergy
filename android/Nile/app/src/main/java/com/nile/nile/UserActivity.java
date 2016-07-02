package com.nile.nile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView textView = new TextView(this);
        textView.setTextSize(25);
        textView.setText("Amazon 15m");

        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);
        layout.addView(textView);
    }
}
