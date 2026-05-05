import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameController {
    private final GameModel model;
    private final GameView view;
    private final Timer timer;
    private final Random random;
    private final File highScoreFile = new File("endless_highscore.txt");
    private JFrame frame;
    private boolean endlessMode;
    private int endlessHighScore;
    private JPanel debugActionPanel;
    private boolean debugControlsUsed;
    private int aiDelayCounter;
    private int aiTargetY;

    public GameController() {
        model = new GameModel();
        view = new GameView(model);
        random = new Random();
        endlessHighScore = loadHighScore();
        view.setEndlessHighScore(endlessHighScore);
        aiDelayCounter = 0;
        aiTargetY = model.getBallY();
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTick();
            }
        });
    }

    public void start() {
        frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(createMenuPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(12, 12, 12, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("PONG", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 72));
        title.setForeground(Color.WHITE);
        c.gridy = 0;
        menuPanel.add(title, c);

        JButton firstToTen = new JButton("First to 10");
        firstToTen.setFont(new Font("SansSerif", Font.PLAIN, 24));
        firstToTen.addActionListener(e -> startGame(false));
        c.gridy = 1;
        menuPanel.add(firstToTen, c);

        JButton endless = new JButton("Endless");
        endless.setFont(new Font("SansSerif", Font.PLAIN, 24));
        endless.addActionListener(e -> startGame(true));
        c.gridy = 2;
        menuPanel.add(endless, c);

        JLabel highScoreLabel = new JLabel("High score: " + endlessHighScore, JLabel.CENTER);
        highScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        highScoreLabel.setForeground(Color.WHITE);
        c.gridy = 3;
        menuPanel.add(highScoreLabel, c);

        return menuPanel;
    }

    private void startGame(boolean endlessMode) {
        this.endlessMode = endlessMode;
        debugControlsUsed = false;
        model.resetGame();
        view.setEndlessMode(endlessMode);
        view.setEndlessHighScore(endlessHighScore);

        if (frame != null) {
            JPanel gamePanel = new JPanel(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(Color.BLACK);

            JPanel leftControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftControls.setOpaque(false);
            JButton quitButton = new JButton("Quit");
            quitButton.addActionListener(e -> returnToMenu());
            leftControls.add(quitButton);
            topPanel.add(leftControls, BorderLayout.WEST);

            if (endlessMode) {
                JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                rightControls.setOpaque(false);
                JButton debugButton = new JButton("Debug Controls");
                debugButton.addActionListener(e -> {
                    toggleDebugControls();
                    if (frame != null) {
                        frame.requestFocusInWindow();
                    }
                });
                rightControls.add(debugButton);
                topPanel.add(rightControls, BorderLayout.EAST);

                debugActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
                debugActionPanel.setOpaque(false);
                debugActionPanel.setVisible(false);
                JButton addBallButton = new JButton("Add Ball");
                addBallButton.addActionListener(e -> {
                    model.addBall();
                    view.repaint();
                    if (frame != null) {
                        frame.requestFocusInWindow();
                    }
                });
                JButton speedUpButton = new JButton("Speed Up");
                speedUpButton.addActionListener(e -> {
                    model.speedUpBalls();
                    view.repaint();
                    if (frame != null) {
                        frame.requestFocusInWindow();
                    }
                });
                JButton slowDownButton = new JButton("Slow Down");
                slowDownButton.addActionListener(e -> {
                    model.slowDownBalls();
                    view.repaint();
                    if (frame != null) {
                        frame.requestFocusInWindow();
                    }
                });
                debugActionPanel.add(addBallButton);
                debugActionPanel.add(speedUpButton);
                debugActionPanel.add(slowDownButton);
            } else {
                debugActionPanel = null;
            }

            JPanel northPanel = new JPanel(new BorderLayout());
            northPanel.setBackground(Color.BLACK);
            northPanel.add(topPanel, BorderLayout.NORTH);
            if (debugActionPanel != null) {
                northPanel.add(debugActionPanel, BorderLayout.SOUTH);
            }

            gamePanel.add(northPanel, BorderLayout.NORTH);
            gamePanel.add(view, BorderLayout.CENTER);

            gamePanel.setFocusable(true);
            frame.setContentPane(gamePanel);
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    handleKeyPress(e);
                }
            });
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.requestFocusInWindow();
            timer.start();
        }
    }

    private void returnToMenu() {
        if (frame != null) {
            timer.stop();
            endlessMode = false;
            frame.setContentPane(createMenuPanel());
            frame.revalidate();
            frame.repaint();
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
    }

    private void toggleDebugControls() {
        if (debugActionPanel == null) {
            return;
        }
        debugControlsUsed = true;
        debugActionPanel.setVisible(!debugActionPanel.isVisible());
        if (frame != null) {
            frame.pack();
            frame.revalidate();
            frame.repaint();
        }
    }

    private void handleKeyPress(KeyEvent e) {
        handleKeyCode(e.getKeyCode());
    }

    void handleKeyCode(int key) {
        if (key == KeyEvent.VK_UP) {
            model.movePaddleAUp();
        } else if (key == KeyEvent.VK_DOWN) {
            model.movePaddleADown();
        } else if (key == KeyEvent.VK_R) {
            if (model.getGameState() != GameModel.GameState.PLAYING) {
                model.resetGame();
            }
        }
        view.repaint();
    }

    void tick() {
        onTick();
    }

    GameModel getModel() {
        return model;
    }

    private void onTick() {
        if (model.getGameState() != GameModel.GameState.PLAYING) {
            return;
        }

        updateOpponent();
        model.updateBall();
        checkCollisions();
        checkPoints();
        view.repaint();
    }

    private void updateOpponent() {
        if (aiDelayCounter > 0) {
            aiDelayCounter--;
            return;
        }

        aiDelayCounter = 2 + random.nextInt(3); // slower reaction: 4-8 ticks

        int ballY = model.getBallY();
        int ballVelocityX = model.getBallX() < 300 ? -1 : 1;
        int predictedY = ballY;
        if (ballVelocityX > 0) {
            predictedY += 20;
        }

        int noise = random.nextInt(61) - 30; // less precise tracking
        aiTargetY = Math.max(0, Math.min(360, predictedY + noise));

        if (model.getBallX() > 460 && random.nextInt(100) < 25) {
            // occasional complete miss when the ball is close to the right edge
            return;
        }

        if (random.nextInt(100) < 15) {
            // occasional hesitation or mistake
            return;
        }

        if (model.getPaddleBY() + 40 < aiTargetY) {
            model.movePaddleBSlowDown();
        } else if (model.getPaddleBY() + 40 > aiTargetY) {
            model.movePaddleBSlowUp();
        }
    }

    private void checkCollisions() {
        java.util.List<GameModel.Ball> balls = model.getBalls();
        for (GameModel.Ball ball : balls) {
            if (ball.y <= 0 || ball.y >= 384) {
                ball.vy = -ball.vy;
            }

            if (ball.x <= 36 && ball.y + 16 >= model.getPaddleAY() && ball.y <= model.getPaddleAY() + 80) {
                ball.vx = -ball.vx;
            }

            if (ball.x >= 548 && ball.y + 16 >= model.getPaddleBY() && ball.y <= model.getPaddleBY() + 80) {
                ball.vx = -ball.vx;
            }
        }
    }

    private void checkPoints() {
        java.util.List<GameModel.Ball> balls = model.getBalls();
        for (int i = 0; i < balls.size(); i++) {
            GameModel.Ball ball = balls.get(i);
            if (ball.x < 0) {
                model.scorePointForB();
                if (!endlessMode && model.getScoreB() >= 10) {
                    model.setGameState(GameModel.GameState.LOST);
                } else {
                    model.resetBall(i);
                }
            } else if (ball.x > 600) {
                model.scorePointForA();
                if (endlessMode && model.getScoreA() > endlessHighScore && !debugControlsUsed) {
                    endlessHighScore = model.getScoreA();
                    view.setEndlessHighScore(endlessHighScore);
                    saveHighScore();
                }
                if (!endlessMode && model.getScoreA() >= 10) {
                    model.setGameState(GameModel.GameState.WON);
                } else {
                    model.resetBall(i);
                }
            }
        }
    }

    private int loadHighScore() {
        if (!highScoreFile.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    private void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            writer.write(String.valueOf(endlessHighScore));
        } catch (IOException e) {
            // ignore save failures for now
        }
    }

    public static void main(String[] args) {
        new GameController().start();
    }
}
