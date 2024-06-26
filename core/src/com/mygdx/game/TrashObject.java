package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class TrashObject extends GameObject {
    private int livesLeft;
    Random rand = new Random();
    private static final int paddingHorizontal = 30;
    int x = width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCR_WIDTH - 2 * paddingHorizontal - width));
    int y = GameSettings.SCR_HEIGHT + height / 2;
    String type;

    public TrashObject(int width, int height, String texturePath, short cBits, World world, String type, int livesLeft) {

        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCR_WIDTH - 2 * paddingHorizontal - width)), GameSettings.SCR_HEIGHT + height / 2,
                width, height, cBits,
                world
        );
        this.type = type;
        body = createBody(x, y, world);
        this.livesLeft = livesLeft;
        if (type != "boss") {
            body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        } else {
            body.setLinearVelocity(new Vector2(rand.nextInt(20)-10, -GameSettings.TRASH_VELOCITY*2));
        }
    }
    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }
    @Override
    public void hit() {
       livesLeft-=1;
    }
    public boolean isAlive() {
        return livesLeft>0;
    }
}
