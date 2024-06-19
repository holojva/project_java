package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;
    TextView musicSettingView;
    TextView soundSettingView;
    TextView clearSettingView;
    TextView levelView;
    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(myGdxGame.largeWhiteFont, 256, 956, "Settings");
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        musicSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 717, "music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
        soundSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 658, "sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
        clearSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 599, "clear records");
        levelView = new TextView(myGdxGame.commonWhiteFont, 173, 540, "level: " + MemoryManager.loadLevel());
        returnButton = new ButtonView(
                280, 400,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_IMG_PATH,
                "return"
        );
    }
    private String translateStateToText(boolean state) {
        if (state) {
            return "ON";
        } else {
            return "OFF";
        }
    }
    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleTextView.draw(myGdxGame.batch);
        blackoutImageView.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        musicSettingView.draw(myGdxGame.batch);
        levelView.draw(myGdxGame.batch);
        soundSettingView.draw(myGdxGame.batch);
        clearSettingView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (clearSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
                clearSettingView.setText("clear records (cleared)");
            }
            if (musicSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());

                System.out.println("music");
                musicSettingView.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                myGdxGame.audio.updateMusicFlag();
            }
            if (soundSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                System.out.println("sound");
                soundSettingView.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                myGdxGame.audio.updateSoundFlag();
            }
            if (soundSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                System.out.println("sound");
                soundSettingView.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                myGdxGame.audio.updateSoundFlag();
            }
            if (levelView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveLevel(((MemoryManager.loadLevel())%3)+1);
                System.out.println("sound");
                levelView.setText("level: " + MemoryManager.loadLevel());
                myGdxGame.audio.updateSoundFlag();
            }
        }
    }
}
