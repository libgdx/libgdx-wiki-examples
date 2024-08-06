package com.epicness.beep;

import static com.epicness.beep.Constants.BALL_COLORS;
import static com.epicness.beep.Constants.BALL_COUNT;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class BeepApp extends Game {

    private ShapeRenderer shapeRenderer;
    private BeepingBall[] balls;
    private BallUpdater ballUpdater;
    private float width, height, ballSpacing, ballRadius;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        balls = new BeepingBall[BALL_COUNT];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new BeepingBall(BALL_COLORS[i]);
            balls[i].pitch = MathUtils.map(0, BALL_COUNT - 1, 1f, 0.5f, i);
        }
        setupBalls();
        ballUpdater = new BallUpdater(balls);
    }

    private void setupBalls() {
        for (int i = 0; i < balls.length; i++) {
            BeepingBall ball = balls[i];
            ball.startingX = width / 10f + i * ballSpacing;
            ball.startingY = height / 2f - i * ballSpacing;
            ball.finalX = width - width / 10f - i * ballSpacing;
            ball.radius = ballRadius;
            ball.setPosition(ball.startingX, ball.startingY);
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        ballUpdater.update();

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.line(
            width / 10f, height / 2f,
            width / 10f + BALL_COUNT * ballSpacing, 0
        );
        shapeRenderer.line(
            width - width / 10f, height / 2f,
            width - width / 10f - BALL_COUNT * ballSpacing, 0f
        );
        for (int i = 0; i < balls.length; i++) {
            balls[i].draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        ballSpacing = ((float) Math.min(width, height) / BALL_COUNT) / 2f;
        ballRadius = ((float) Math.min(width, height) / BALL_COUNT) / 4f;
        setupBalls();
        shapeRenderer.setProjectionMatrix(shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
    }
}
