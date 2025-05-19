package service;

import model.Bird;
import model.Pipe;
import helper.CollisionHelper;

import java.util.List;

public class GameService {
    private int velocityY = 0;  // move bird up or down speed.
    private final int gravity = 1;
    private final int velocityX = -4; // move pipes to left speed (Stimulates the bird moving right)
    private final int boardHeight;

    private double score = 0;
    private boolean gameOver = false;

    public GameService(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public void applyGravity(Bird bird) {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
    }

    public void movePipes(List<Pipe> pipes, Bird bird) {
        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5;
                pipe.passed = true;
            }

            if (CollisionHelper.collision(bird, pipe)) {
                gameOver = true;
            }
        }
        // It removes any pipe from the list that has completely moved off the left side of the screen.
        pipes.removeIf(pipe -> pipe.x + pipe.width < 0);


        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public void jump() {
        velocityY = -9;
    }

    public void resetBird(Bird bird, int initialY) {
        bird.y = initialY;
        velocityY = 0;
        score = 0;
        gameOver = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean val) {
        this.gameOver = val;
    }

    public double getScore() {
        return score;
    }
}
