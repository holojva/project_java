package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;






public class MenuScreen extends ScreenAdapter {
    TextView titleView;
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;
    ButtonView smallButtonView;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        FontBuilder whiteFontBuild = new FontBuilder(3, Color.WHITE);
        FontBuilder blackFontBuild = new FontBuilder(3, Color.BLACK);
        BitmapFont whiteFont = whiteFontBuild.getFont();
        BitmapFont blackFont = blackFontBuild.getFont();
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(myGdxGame.largeWhiteFont, 180, 960, "Space Cleaner");
        startButtonView = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70,  myGdxGame.commonBlackFont, GameResources.BUTTON_IMG_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70,  myGdxGame.commonBlackFont, GameResources.BUTTON_IMG_PATH, "exit");
        smallButtonView = new ButtonView(100, 100, 10, 10,  GameResources.BUTTON_IMG_PATH);
    }
    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        smallButtonView.draw(myGdxGame.batch);

        titleView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        startButtonView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {

                myGdxGame.setScreen(myGdxGame.gameScreen);
            }

            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.settingsScreen);
            }
            if (smallButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.audio.buttonClicked();
            }
        }
    }
}

