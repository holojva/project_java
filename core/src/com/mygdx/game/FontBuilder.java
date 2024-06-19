package com.mygdx.game;

import static com.mygdx.game.GameScreen.myGdxGame;
import static com.mygdx.game.GameSettings.SCR_HEIGHT;
import static com.mygdx.game.GameSettings.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontBuilder {
    int size;
    Color color;
    BitmapFont font;
    public FontBuilder(int size, Color color) {
        this.size = size;
        this.color = color;
    }
    public static BitmapFont generate(int size, Color color, String fontPath) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

// внутри метода onCreate()
//
//// внутри метода render()
//
    }
     BitmapFont getFont() {
        font = new BitmapFont();
        font.getData().scale(size);
        font.setColor(color);
        return font;
    }
    }