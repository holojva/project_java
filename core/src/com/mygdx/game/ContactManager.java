package com.mygdx.game;

import static com.mygdx.game.GameSettings.BULLET_BIT;
import static com.mygdx.game.GameSettings.SHIP_BIT;
import static com.mygdx.game.GameSettings.TRASH_BIT;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Objects;

public class ContactManager {
    World world;

    public ContactManager(World world) {
        this.world = world;
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                if (cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.BULLET_BIT
                        || cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.SHIP_BIT){
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
            }

            @Override
            public void endContact(Contact contact) {
                // код, выполняющийся после завершения контакта
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // код, выполняющийся перед вычислением всех контактоа
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // код, выполняющийся сразу после вычислений контактов
            }
        });
    }
}
