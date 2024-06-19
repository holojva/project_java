package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class AudioManager {
    public Music backgroundMenuMusic;
    public Music backgroundGameMusic;
    public Music secretBackgroundMenuMusic;
    public Sound shootSound;
    public Sound explosionSound;
    public boolean isSoundOn;
    public boolean isMusicOn;

    public AudioManager() {
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));
        backgroundGameMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.GAME_BACKGROUND_MUSIC_PATH));
        backgroundMenuMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.MENU_BACKGROUND_MUSIC_PATH));
        secretBackgroundMenuMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.SECRET_MENU_BACKGROUND_MUSIC_PATH));
        isMusicOn = true;
        isSoundOn = true;
        backgroundGameMusic.setVolume(0.2f);
        backgroundGameMusic.setLooping(true);
        backgroundMenuMusic.setVolume(0.2f);
        backgroundMenuMusic.setLooping(true);
        updateSoundFlag();
        updateMusicFlag();
    }
    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();
        if (isMusicOn) backgroundMenuMusic.play();
        else backgroundMenuMusic.stop();
    }
    public void setIsMusicOn() {

    }
    public void startGame() {
        if (isMusicOn)  {
            backgroundGameMusic.stop();
            backgroundMenuMusic.stop();
            backgroundGameMusic.play();
        }
    }
    public void buttonClicked() {
        backgroundGameMusic.stop();
        backgroundMenuMusic.stop();
        secretBackgroundMenuMusic.play();
    }
    public void startMenu() {
        if (isMusicOn)  {
            backgroundGameMusic.stop();
            backgroundMenuMusic.stop();
            backgroundMenuMusic.play();
        }
    }
    public void updateSoundFlag() {
        isSoundOn = MemoryManager.loadIsSoundOn();
    }
}