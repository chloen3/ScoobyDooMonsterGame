package com.example.worldofscoobydoo.viewModel;

import android.widget.TextView;

//Interface that movement observers will implement
public interface Observer {
    void onMovementChanged(float x, float y);
    void notifyObservers(TextView text);
}
