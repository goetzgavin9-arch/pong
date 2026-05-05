public class GameModel {
    public enum GameState {
        PLAYING,
        WON,
        LOST
    }

    public static class Ball {
        public int x;
        public int y;
        public int vx;
        public int vy;

        public Ball(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
    }

    private int scoreA;
    private int scoreB;
    private GameState gameState;
    private int ballX;
    private int ballY;
    private int ballVelocityX;
    private int ballVelocityY;
    private int paddleAY;
    private int paddleBY;
    private final java.util.List<Ball> balls = new java.util.ArrayList<>();

    public GameModel() {
        resetGame();
    }

    public void resetGame() {
        scoreA = 0;
        scoreB = 0;
        resetRound();
    }

    public void resetRound() {
        gameState = GameState.PLAYING;
        ballX = 300;
        ballY = 200;
        ballVelocityX = 5;
        ballVelocityY = 3;
        paddleAY = 180;
        paddleBY = 180;
        balls.clear();
        balls.add(new Ball(ballX, ballY, ballVelocityX, ballVelocityY));
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getBallX() {
        if (!balls.isEmpty()) {
            return balls.get(0).x;
        }
        return ballX;
    }

    public int getBallY() {
        if (!balls.isEmpty()) {
            return balls.get(0).y;
        }
        return ballY;
    }

    public int getBallVelocityX() {
        if (!balls.isEmpty()) {
            return balls.get(0).vx;
        }
        return ballVelocityX;
    }

    public int getBallVelocityY() {
        if (!balls.isEmpty()) {
            return balls.get(0).vy;
        }
        return ballVelocityY;
    }

    public java.util.List<Ball> getBalls() {
        return balls;
    }

    public int getPaddleAY() {
        return paddleAY;
    }

    public int getPaddleBY() {
        return paddleBY;
    }

    void setBallPosition(int x, int y) {
        ballX = x;
        ballY = y;
    }

    void setBallVelocity(int vx, int vy) {
        ballVelocityX = vx;
        ballVelocityY = vy;
    }

    void setPaddleAY(int y) {
        paddleAY = Math.max(0, Math.min(360, y));
    }

    void setPaddleBY(int y) {
        paddleBY = Math.max(0, Math.min(360, y));
    }

    void setScoreA(int score) {
        scoreA = score;
    }

    void setScoreB(int score) {
        scoreB = score;
    }

    public void movePaddleAUp() {
        paddleAY = Math.max(0, paddleAY - 20);
    }

    public void movePaddleADown() {
        paddleAY = Math.min(360, paddleAY + 20);
    }

    public void movePaddleBUp() {
        paddleBY = Math.max(0, paddleBY - 20);
    }

    public void movePaddleBDown() {
        paddleBY = Math.min(360, paddleBY + 20);
    }

    public void movePaddleBSlowUp() {
        paddleBY = Math.max(0, paddleBY - 15);
    }

    public void movePaddleBSlowDown() {
        paddleBY = Math.min(360, paddleBY + 15);
    }

    public void updateBall() {
        for (Ball ball : balls) {
            ball.x += ball.vx;
            ball.y += ball.vy;
        }
        if (!balls.isEmpty()) {
            Ball first = balls.get(0);
            ballX = first.x;
            ballY = first.y;
            ballVelocityX = first.vx;
            ballVelocityY = first.vy;
        }
    }

    public void addBall() {
        int vx = ballVelocityX >= 0 ? 5 : -5;
        int vy = ballVelocityY >= 0 ? 3 : -3;
        if (balls.size() % 2 == 0) {
            vx = -vx;
        }
        balls.add(new Ball(300, 200, vx, vy));
    }

    public void resetBall(int index) {
        if (index < 0 || index >= balls.size()) {
            return;
        }
        Ball ball = balls.get(index);
        ball.x = 300;
        ball.y = 200;
        ball.vx = 5;
        ball.vy = 3;
        if (index == 0) {
            ballX = ball.x;
            ballY = ball.y;
            ballVelocityX = ball.vx;
            ballVelocityY = ball.vy;
        }
    }

    public void speedUpBalls() {
        for (Ball ball : balls) {
            ball.vx = increaseSpeed(ball.vx);
            ball.vy = increaseSpeed(ball.vy);
        }
    }

    public void slowDownBalls() {
        for (Ball ball : balls) {
            ball.vx = decreaseSpeed(ball.vx);
            ball.vy = decreaseSpeed(ball.vy);
        }
    }

    private int increaseSpeed(int velocity) {
        int sign = Integer.signum(velocity);
        int abs = Math.max(1, Math.abs(velocity));
        return sign * (abs + 1);
    }

    private int decreaseSpeed(int velocity) {
        int sign = Integer.signum(velocity);
        int abs = Math.max(1, Math.abs(velocity) - 1);
        return sign * abs;
    }

    public void bounceBallVertical() {
        ballVelocityY = -ballVelocityY;
    }

    public void bounceBallHorizontal() {
        ballVelocityX = -ballVelocityX;
    }

    public void scorePointForA() {
        scoreA++;
    }

    public void scorePointForB() {
        scoreB++;
    }

    public void setGameState(GameState state) {
        gameState = state;
    }
}
