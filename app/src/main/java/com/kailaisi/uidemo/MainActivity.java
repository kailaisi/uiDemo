package com.kailaisi.uidemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kailaisi.uidemo.cai.LetterSideView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LetterSideView view = findViewById(R.id.step);
        TextView tv = findViewById(R.id.tv);
        view.setListener(new LetterSideView.TouchLetterListener() {
            @Override
            public void onTouch(String text) {
                tv.setText(text);
            }
        });
    }
}