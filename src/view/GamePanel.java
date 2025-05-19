package view;

import controller.GameController;
import model.Bird;
import repository.PipeRepository;
import service.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Pipe;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int birdWidth = 34;
    int pipeWidth = 64;
    int boardHeight = 640;
    int birdHeight = 24;
    int pipeHeight = 512;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    Bird bird;
    GameController controller;

    Timer gameLoop;
    Timer pipeTimer;

    public GamePanel() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("/assets/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/assets/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/assets/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/assets/bottompipe.png")).getImage();

        int birdX = boardWidth / 8;
        int birdY = boardHeight / 2;

        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImg);
        GameService service = new GameService(boardHeight);
        PipeRepository repo = new PipeRepository(boardHeight, boardWidth, 0, pipeWidth, pipeHeight);
        controller = new GameController(bird, service, repo);

        pipeTimer = new Timer(1500, e -> repo.placePipes(topPipeImg, bottomPipeImg));
        pipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        for (Pipe pipe : controller.getPipes()) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        if (controller.isGameOver()) {
            // Display crash message at center
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            String crashMsg = "Oh no! Not again....";
            FontMetrics fm = g.getFontMetrics();
            int msgWidth = fm.stringWidth(crashMsg);
            g.drawString(crashMsg, (boardWidth - msgWidth) / 2, boardHeight / 2);

            // Also show score below "Oh No!"
            g.setFont(new Font("Arial", Font.PLAIN, 28));
            String scoreMsg = "Game Over: " + (int) controller.getScore();
            msgWidth = g.getFontMetrics().stringWidth(scoreMsg);
            g.drawString(scoreMsg, (boardWidth - msgWidth) / 2, boardHeight / 2 + 40);
        } else {
            // Normal score display
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 32));
            g.drawString(String.valueOf((int) controller.getScore()), 10, 35);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.updateGame();
        repaint();

        if (controller.isGameOver()) {
            pipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controller.jump();

            if (controller.isGameOver()) {
                controller.restartGame(boardHeight / 2);
                gameLoop.start();
                pipeTimer.start();
            }
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
