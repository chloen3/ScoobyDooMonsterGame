package com.example.worldofscoobydoo.viewModel;
import android.os.CountDownTimer;


public class CountDownTimerUtil {
    public static CountDownTimer startCountdownTimer(int initialScore,
                                                     CountdownTimerCallback callback) {
        return new CountDownTimer(initialScore * 1000, 1000) {
            private int score = initialScore;

            @Override
            public void onTick(long millisUntilFinished) {
                score -= 1;
                callback.onTick(score);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        }.start();
    }
}
