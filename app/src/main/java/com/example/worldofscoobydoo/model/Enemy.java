package com.example.worldofscoobydoo.model;
import android.widget.ImageView;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;

public abstract class Enemy {
    //Type of enemy
    private String type;
    //How fast the enemy is moving
    private MovementStrategy mvStrategy;
    private ImageView image;
    public Enemy(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MovementStrategy getMvStrategy() {
        return mvStrategy;
    }

    public void setMvStrategy(MovementStrategy strategy) {
        this.mvStrategy = strategy;
    }
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView img) {
        this.image = img;
    }

    public void createMultipleEnemies(int numEnemies) {
    }
    public void movement(int movementCount, ImageView enemy, ImageView box) { }
    public int setCount(int movementCount) {
        return movementCount;
    }
    public static boolean enemyIsValid(String sprite) {
        return sprite == "Basic" || sprite == "Tank" || sprite == "Giant" || sprite == "Boss";
    }
}
