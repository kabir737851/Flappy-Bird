package repository;

import model.Pipe;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PipeRepository {
    private final List<Pipe> pipes = new ArrayList<>();
    private final Random random = new Random();

    private final int boardHeight;
    private final int pipeWidth;
    private final int pipeHeight;
    private final int pipeX;
    private final int pipeY;

    public PipeRepository(int boardHeight, int pipeX, int pipeY, int pipeWidth, int pipeHeight) {
        this.boardHeight = boardHeight;
        this.pipeX = pipeX;
        this.pipeY = pipeY;
        this.pipeWidth = pipeWidth;
        this.pipeHeight = pipeHeight;
    }

    public void placePipes(Image topPipeImg, Image bottomPipeImg) {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        pipes.add(new Pipe(pipeX, randomPipeY, pipeWidth, pipeHeight, topPipeImg));
        pipes.add(new Pipe(pipeX, randomPipeY + pipeHeight + openingSpace, pipeWidth, pipeHeight, bottomPipeImg));
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public void resetPipes() {
        pipes.clear();
    }
}
