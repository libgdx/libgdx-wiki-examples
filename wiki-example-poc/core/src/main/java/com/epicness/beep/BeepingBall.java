package com.epicness.beep;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BeepingBall {

    private float x, y;
    private final Color color;
    public float startingX, finalX, startingY, angle, pitch, radius;
    public boolean forward;

    public BeepingBall(Color color) {
        this.color = color;
        forward = true;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
