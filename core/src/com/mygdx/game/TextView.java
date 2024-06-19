package com.mygdx.game;

import static com.mygdx.game.GameScreen.myGdxGame;
import static com.mygdx.game.GameSettings.SCR_HEIGHT;
import static com.mygdx.game.GameSettings.SCR_WIDTH;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView extends View{

    protected BitmapFont font;
    protected String text;

    public TextView(BitmapFont font, float x, float y) {
        super(x, y);
        this.font = font;
    }
    public TextView(BitmapFont font, float x, float y, String text) {
        this(font, x, y);
        this.text = text;

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }
    public boolean isHit(float tx, float ty) {
        return (tx >= x && tx <= x + width && ty >= y && ty <= y + height);
    }
    public void setText(String text) {
        this.text = text;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;

    }
    @Override
    public void draw(SpriteBatch batch) {
        font.draw(myGdxGame.batch, text, x, y);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}