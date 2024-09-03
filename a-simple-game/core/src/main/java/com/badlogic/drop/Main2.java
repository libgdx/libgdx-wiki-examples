package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main2 implements ApplicationListener {
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sprite bucketSprite;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    ScreenViewport screenViewport;
    Array<Sprite> dropSprites;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    Vector3 touchPos;
    Sound dropSound;
    Music music;
    private long lastDropTime;
    public boolean clickedSplash;
    Texture splashTexture;

    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");

        viewport = new FitViewport(8, 5);
        screenViewport = new ScreenViewport();
        spriteBatch = new SpriteBatch();

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);

        touchPos = new Vector3();

        dropSprites = new Array<>();

        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        lastDropTime = TimeUtils.millis();
        splashTexture = new Texture("splash.png");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        screenViewport.update(width, height, true);
    }

    @Override
    public void render() {
        if (!clickedSplash) splashRender();
        else gameRender();
    }

    private void gameRender() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //input
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            bucketSprite.setX(bucketSprite.getX() - 4f * delta);
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            bucketSprite.setX(bucketSprite.getX() + 4f * delta);
        }

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 1);
            viewport.unproject(touchPos);
            bucketSprite.setX(touchPos.x);
        }

        //logic
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();
        if (bucketSprite.getX() < 0) bucketSprite.setX(0);
        else if (bucketSprite.getX() > worldWidth - bucketWidth) bucketSprite.setX(worldWidth - bucketWidth);

        long time = TimeUtils.millis();
        if (time - lastDropTime > 1000) {
            lastDropTime = time;
            float dropWidth = 1;
            float dropHeight = 1;

            Sprite dropSprite = new Sprite(dropTexture);
            dropSprite.setSize(dropWidth, dropHeight);
            dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
            dropSprite.setY(worldHeight);
            dropSprites.add(dropSprite);
        }

        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.setY(dropSprite.getY() - 2f * delta);
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (bucketRectangle.overlaps(dropRectangle)) {
                dropSprites.removeIndex(i);
                dropSound.play();
            }
        }

        //rendering
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        bucketSprite.draw(spriteBatch);

        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    private void splashRender() {
        if (Gdx.input.isTouched()) {
            clickedSplash = true;

            music.play();
        }

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        spriteBatch.flush();

        screenViewport.apply();
        spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
        spriteBatch.draw(splashTexture, MathUtils.round(screenViewport.getWorldWidth() / 2f - splashTexture.getWidth() / 2f), MathUtils.round(screenViewport.getWorldHeight() / 2f - splashTexture.getHeight() / 2f));

        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
