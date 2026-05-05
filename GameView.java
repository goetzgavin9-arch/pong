import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GameView extends JPanel {
    private final GameModel model;
    private boolean endlessMode = false;
    private int endlessHighScore = 0;

    public GameView(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
    }

    public void setEndlessMode(boolean endlessMode) {
        this.endlessMode = endlessMode;
    }

    public void setEndlessHighScore(int endlessHighScore) {
        this.endlessHighScore = endlessHighScore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBall(g);
        drawPaddles(g);
        drawScores(g);
        drawEndlessHighScore(g);
        drawGameState(g);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        int size = 16;
        for (GameModel.Ball ball : model.getBalls()) {
            g.fillOval(ball.x, ball.y, size, size);
        }
    }

    private void drawPaddles(Graphics g) {
        int paddleWidth = 16;
        int paddleHeight = 80;
        int brickHeight = 16;
        int brickCount = paddleHeight / brickHeight;

        for (int i = 0; i < brickCount; i++) {
            int yA = model.getPaddleAY() + i * brickHeight;
            g.setColor(i % 2 == 0 ? Color.GREEN.darker() : Color.GREEN);
            g.fillRect(20, yA, paddleWidth, brickHeight);
            g.setColor(Color.BLACK);
            g.drawRect(20, yA, paddleWidth, brickHeight);
        }

        for (int i = 0; i < brickCount; i++) {
            int yB = model.getPaddleBY() + i * brickHeight;
            g.setColor(i % 2 == 0 ? Color.RED.darker() : Color.RED);
            g.fillRect(560, yB, paddleWidth, brickHeight);
            g.setColor(Color.BLACK);
            g.drawRect(560, yB, paddleWidth, brickHeight);
        }
    }

    private void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        String text = model.getScoreA() + " : " + model.getScoreB();
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (getWidth() - width) / 2, 40);
    }

    private void drawEndlessHighScore(Graphics g) {
        if (!endlessMode) {
            return;
        }

        g.setColor(Color.CYAN);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));
        String text = "High score: " + endlessHighScore;
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (getWidth() - width) / 2, 70);
    }

    private void drawGameState(Graphics g) {
        if (model.getGameState() != GameModel.GameState.PLAYING) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));
            String message = model.getGameState() == GameModel.GameState.WON ? "You Win" : "Game Over";
            int width = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (getWidth() - width) / 2, getHeight() / 2);
            g.setFont(new Font("SansSerif", Font.PLAIN, 18));
            String prompt = "Press R to reset";
            int promptWidth = g.getFontMetrics().stringWidth(prompt);
            g.drawString(prompt, (getWidth() - promptWidth) / 2, getHeight() / 2 + 30);
        }
    }
}
