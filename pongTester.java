import java.awt.event.KeyEvent;

public class pongTester {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        runTest("ball update moves coordinates", pongTester::testBallUpdateMoves);
        runTest("wall bounce flips vertical velocity", pongTester::testWallBounce);
        runTest("paddle bounce flips horizontal velocity", pongTester::testPaddleBounce);
        runTest("score increments when ball passes right paddle", pongTester::testScoreRightIncrements);
        runTest("score increments when ball passes left paddle", pongTester::testScoreLeftIncrements);
        runTest("game ends when player score reaches 10", pongTester::testGameEndsAtTen);
        runTest("up/down arrow key moves paddle A", pongTester::testArrowKeyMovesPaddle);
        runTest("round reset preserves scores", pongTester::testRoundResetPreservesScores);

        System.out.println();
        System.out.println("Tests passed: " + passed + ", failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void runTest(String name, Runnable body) {
        try {
            body.run();
            System.out.println("PASS: " + name);
            passed++;
        } catch (AssertionError e) {
            System.out.println("FAIL: " + name + " - " + e.getMessage());
            failed++;
        } catch (Exception e) {
            System.out.println("ERROR: " + name + " - " + e);
            failed++;
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void testBallUpdateMoves() {
        GameModel model = new GameModel();
        model.setBallPosition(50, 75);
        model.setBallVelocity(7, 3);
        model.updateBall();
        assertTrue(model.getBallX() == 57, "expected ballX 57, got " + model.getBallX());
        assertTrue(model.getBallY() == 78, "expected ballY 78, got " + model.getBallY());
    }

    private static void testWallBounce() {
        GameModel model = new GameModel();
        model.setBallVelocity(4, -5);
        model.bounceBallVertical();
        assertTrue(model.getBallVelocityY() == 5, "expected velocityY 5, got " + model.getBallVelocityY());
        assertTrue(model.getBallVelocityX() == 4, "expected velocityX unchanged at 4");
    }

    private static void testPaddleBounce() {
        GameModel model = new GameModel();
        model.setBallVelocity(6, 2);
        model.bounceBallHorizontal();
        assertTrue(model.getBallVelocityX() == -6, "expected velocityX -6, got " + model.getBallVelocityX());
        assertTrue(model.getBallVelocityY() == 2, "expected velocityY unchanged at 2");
    }

    private static void testScoreRightIncrements() {
        GameController controller = new GameController();
        GameModel model = controller.getModel();
        model.resetRound();
        model.setBallPosition(605, 200);
        model.setBallVelocity(5, 0);
        controller.tick();
        assertTrue(model.getScoreA() == 1, "expected scoreA 1, got " + model.getScoreA());
        assertTrue(model.getGameState() == GameModel.GameState.PLAYING, "expected game still playing");
    }

    private static void testScoreLeftIncrements() {
        GameController controller = new GameController();
        GameModel model = controller.getModel();
        model.resetRound();
        model.setBallPosition(-5, 200);
        model.setBallVelocity(-5, 0);
        controller.tick();
        assertTrue(model.getScoreB() == 1, "expected scoreB 1, got " + model.getScoreB());
        assertTrue(model.getGameState() == GameModel.GameState.PLAYING, "expected game still playing");
    }

    private static void testGameEndsAtTen() {
        GameController controller = new GameController();
        GameModel model = controller.getModel();
        model.resetRound();
        model.setScoreA(9);
        model.setBallPosition(605, 200);
        model.setBallVelocity(5, 0);
        controller.tick();
        assertTrue(model.getScoreA() == 10, "expected scoreA 10, got " + model.getScoreA());
        assertTrue(model.getGameState() == GameModel.GameState.WON, "expected game state WON");

        controller = new GameController();
        model = controller.getModel();
        model.resetRound();
        model.setScoreB(9);
        model.setBallPosition(-5, 200);
        model.setBallVelocity(-5, 0);
        controller.tick();
        assertTrue(model.getScoreB() == 10, "expected scoreB 10, got " + model.getScoreB());
        assertTrue(model.getGameState() == GameModel.GameState.LOST, "expected game state LOST");
    }

    private static void testArrowKeyMovesPaddle() {
        GameController controller = new GameController();
        GameModel model = controller.getModel();
        int original = model.getPaddleAY();
        controller.handleKeyCode(KeyEvent.VK_UP);
        assertTrue(model.getPaddleAY() < original, "expected paddle A to move up");
        controller.handleKeyCode(KeyEvent.VK_DOWN);
        assertTrue(model.getPaddleAY() == original, "expected paddle A to return to original position");
    }

    private static void testRoundResetPreservesScores() {
        GameModel model = new GameModel();
        model.setScoreA(2);
        model.setScoreB(3);
        model.resetRound();
        assertTrue(model.getScoreA() == 2, "expected scoreA 2 after round reset");
        assertTrue(model.getScoreB() == 3, "expected scoreB 3 after round reset");
    }
}
