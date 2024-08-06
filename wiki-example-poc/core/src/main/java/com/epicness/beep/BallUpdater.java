package com.epicness.beep;

import static com.epicness.beep.Constants.BALL_COUNT;
import static com.epicness.beep.Constants.VOLUME;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class BallUpdater {

    private final BeepingBall[] balls;
    private final Sound beep;

    public BallUpdater(BeepingBall[] balls) {
        this.balls = balls;
        beep = Gdx.audio.newSound(Gdx.files.internal("ballBeep.ogg"));
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        if (delta >= 0.1f) return;

        for (int i = 0; i < BALL_COUNT; i++) {

            BeepingBall ball = balls[i];
            float speedModifier = MathUtils.map(0, BALL_COUNT - 1, 1f, 0.75f, i);
            if (ball.forward) {
                ball.angle += delta * 180f * speedModifier;
                if (ball.angle > 180f) {
                    ball.angle = 180f - (ball.angle - 180f);
                    ball.forward = false;

                    beep.play(VOLUME, ball.pitch, 0f);
                }
            } else {
                ball.angle -= delta * 180f * speedModifier;
                if (ball.angle < 0f) {
                    ball.angle = -ball.angle;
                    ball.forward = true;
                    beep.play(VOLUME, ball.pitch, 0f);
                }
            }
            float x = MathUtils.map(0f, 180f, ball.startingX, ball.finalX, ball.angle);
            float y = MathUtils.sinDeg(ball.angle) * (100f - i * 6f);
            y += ball.startingY;
            balls[i].setPosition(x, y);
        }
    }
}
