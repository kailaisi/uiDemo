package com.kailaisi.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.kailaisi.uidemo.cai.ColorTrackTextView;
import com.kailaisi.uidemo.cai.QQStepView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* QQStepView view = findViewById(R.id.step);
        view.setMaxStep(4000);
        ValueAnimator valueAnimator= ObjectAnimator.ofInt(0,3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setCurrentStep((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();*/
        ColorTrackTextView view = findViewById(R.id.step);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 50);
        valueAnimator.setDuration(100000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                view.setRevert(value / 2 > 1);
                view.setProcess(value % 1);
            }
        });
        valueAnimator.start();

    }
}