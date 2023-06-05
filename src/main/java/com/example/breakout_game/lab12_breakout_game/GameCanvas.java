package com.example.breakout_game.lab12_breakout_game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameCanvas extends Canvas {
    private GraphicsContext graphicsContext;
    private Paddle paddle;
    public Ball ball;
    private boolean gameRunning = false;
    List<Brick> bricks = new ArrayList<>();

    private final AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate;

        @Override
        public void handle(long now) {
            double diff = (now - lastUpdate) / 1_000_000_000.;
            lastUpdate = now;
            ball.updatePosition(diff);
            if (shouldBallBounceVertically()) {
                ball.bounceVertically();
            }
            if (shouldBallBounceHorizontally()) {
                ball.bounceHorizontally();
            }
            if (shouldBallBounceFromPaddle()) {
                ball.bounceVertically();
            }
            for (Brick brick : bricks) {
                Point2D[] point = ball.borderPoints();
                Brick.CrushType crushType = brick.crushType(point[0], point[1], point[2], point[3]);
                if (crushType == Brick.CrushType.HorizontalCrush) {
                    ball.bounceHorizontally();
                    bricks.remove(brick);
                    break;
                }
                if (crushType == Brick.CrushType.VerticalCrush) {
                    ball.bounceVertically();
                    bricks.remove(brick);
                    break;
                }
            }
            draw();
        }

        @Override
        public void start() {
            super.start();
            lastUpdate = System.nanoTime();
        }
    };

    public GameCanvas() {
        super(640, 700);
        this.setOnMouseMoved(mouseEvent -> {
            paddle.setPosition(mouseEvent.getX());
            if (!gameRunning)
                ball.setPosition(new Point2D(mouseEvent.getX(), paddle.getY() - ball.getWidth() / 2));
            draw();
        });

        this.setOnMouseClicked(mouseEvent -> {
            gameRunning = true;
            animationTimer.start();
        });
    }

    public void initialize() {
        graphicsContext = this.getGraphicsContext2D();
        GraphicsItem.setCanvasSize(getWidth(), getHeight());
        paddle = new Paddle();
        ball = new Ball();
        this.loadLevel();
    }

    public void draw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());
        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);
        for (Brick b : bricks) {
            b.draw(graphicsContext);
        }
    }

    private boolean shouldBallBounceVertically() {
        return ball.lastY > 0 && ball.y <= 0;

    }

    private boolean shouldBallBounceHorizontally() {
        return (ball.lastX > 0 && ball.x <= 0) ||
                (ball.lastX < this.getWidth() && ball.x <= this.getWidth()) &&
                        ball.x >= this.getWidth() - ball.getWidth();
    }

    private boolean shouldBallBounceFromPaddle() {
        return (ball.lastY < paddle.y) &&
                (ball.y >= paddle.y) && (ball.lastX >= paddle.x) &&
                (ball.lastX <= (paddle.x + paddle.getWidth())) && ball.lastX <= paddle.getX() + paddle.getWidth();
    }

    public void loadLevel() {
        Color[] colors = new Color[]{Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN, Color.VIOLET};
        Brick.setGridRowsAndColumns(10, 20);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks.add(new Brick(j, i + 2, colors[i]));
            }
        }
    }

}



