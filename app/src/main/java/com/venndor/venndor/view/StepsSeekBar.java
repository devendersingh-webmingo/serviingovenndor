package com.venndor.venndor.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class StepsSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    int mSteps;
    int mCurrentStep;
    int mStepWidth;

    public StepsSeekBar(Context context, AttributeSet attrs, int steps) {
        super(context, attrs);
        mSteps = steps;
        mCurrentStep = 0;
        mStepWidth = 100 / mSteps;
    }

  /*  public nextStep() {
        // Animate progress to next step
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", mStepWidth * mCurrentStep, mStepWidth * (++mCurrentStep));
        animator.setDuration(*//* Duration *//*);
        animator.start();
    }*/
}
