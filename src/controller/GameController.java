package controller;

import model.Bird;
import model.Pipe;
import repository.PipeRepository;
import service.GameService;

import java.util.List;

public class GameController {
    private final Bird bird;
    private final GameService service;
    private final PipeRepository repo;

    public GameController(Bird bird, GameService service, PipeRepository repo) {
        this.bird = bird;
        this.service = service;
        this.repo = repo;
    }

    public void updateGame() {
        service.applyGravity(bird);
        service.movePipes(repo.getPipes(), bird);
    }

    public void jump() {
        service.jump();
    }

    public void restartGame(int birdInitialY) {
        repo.resetPipes();
        service.resetBird(bird, birdInitialY);
    }

    public boolean isGameOver() {
        return service.isGameOver();
    }

    public double getScore() {
        return service.getScore();
    }

    public List<Pipe> getPipes() {
        return repo.getPipes();
    }
}
