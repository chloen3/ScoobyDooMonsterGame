package com.example.worldofscoobydoo.viewModel;
import java.util.ArrayList;
import java.util.List;

public class MovementObservable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(float x, float y) {
        for (Observer observer : observers) {
            observer.onMovementChanged(x, y);
        }
    }
}
