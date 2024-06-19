package com.mygdx.game;

import static com.mygdx.game.GameSettings.BULLET_VELOCITY;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BulletObject extends GameObject {
    boolean wasHit;
    public BulletObject(
            int x,
            int y,
            int width,
            int height,
            String texturePath,
            short bulletBit, World world) {
        super(GameResources.BULLET_IMG_PATH, x, y, width, height, GameSettings.BULLET_BIT, world);

        body = createBody(x, y, world);
        body.setLinearVelocity(new Vector2(0, BULLET_VELOCITY));
        body.setBullet(true);
    }
    public boolean hasToBeDestroyed() {
        return wasHit || (getY() - height / 2 > GameSettings.SCR_HEIGHT);
    }
    @Override
    public void hit() {
        wasHit = true;
    }
}