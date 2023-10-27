
package com.example.worldofscoobydoo.viewModel;

import android.media.Image;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;
import com.example.worldofscoobydoo.model.Enemy;

public class EnemyViewModel extends ViewModel {
    private Enemy enemy;

    //Initialize the Enemy instance when the ViewModel is created
    /*public EnemyViewModel() {
        enemy = new Enemy();
    }
     */

    // Methods to access and manipulate the Enemy instance
    public String getEnemyType() {
        return enemy.getType();
    }

    public void setEnemyType(String type) {
        enemy.setType(type);
    }

    // Add more methods as needed to interact with the Enemy instance

    // Example method to get the movement strategy
    public MovementStrategy getMovementStrategy() {
        return enemy.getMvStrategy();
    }

    // Example method to set the movement strategy
    public void setMovementStrategy(MovementStrategy strategy) {
        enemy.setMvStrategy(strategy);
    }
    public void setImageView(ImageView img) {
        enemy.setImage(img);
    }
    public ImageView getImageView() {
        return enemy.getImage();
    }
}

