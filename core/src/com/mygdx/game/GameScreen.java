package com.mygdx.game;

import static com.mygdx.game.GameSettings.BULLET_BIT;
import static com.mygdx.game.GameSettings.SHIP_BIT;
import static com.mygdx.game.GameSettings.TRASH_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {

    ArrayList<TrashObject> trashArray = new ArrayList<TrashObject>();
    ArrayList<BulletObject> bulletArray = new ArrayList<BulletObject>();
    static MyGdxGame myGdxGame;
    GameSession gameSession;
    ShipObject shipObject;
    ContactManager contactManager;
    MovingBackgroundView backgroundView;
    LiveView liveView;
    Random rand = new Random();
    FontBuilder font = new FontBuilder(3, Color.WHITE);
    FontBuilder buttonFont = new FontBuilder(1, Color.BLACK);
    ImageView BlackoutView = new ImageView(0, 1180,GameResources.PAUSE_BACKGROUND_IMG_PATH);

    ImageView topBlackoutView = new ImageView(0, 1180,GameResources.BLACKOUT_TOP_IMG_PATH);
    ButtonView pauseButton;
    ButtonView homeButton;
    TextView pauseTextView;
    ButtonView continueButton;
    // ENDED state UI
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;
    public GameScreen(MyGdxGame myGdxGame) {
        liveView = new LiveView(305, 1215);
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();
        gameSession.startGame();
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        homeButton = new ButtonView(200, 700, 150, 70,myGdxGame.commonBlackFont,  GameResources.BUTTON_IMG_PATH, "Home");
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, 280, 1000, "Pause");
        continueButton = new ButtonView(380, 700, 150, 70,myGdxGame.commonBlackFont, GameResources.BUTTON_IMG_PATH, "Resume");
        shipObject = new ShipObject(GameResources.SHIP_IMG_PATH, GameSettings.SCR_WIDTH / 2, 150, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, SHIP_BIT, myGdxGame.world);
        contactManager = new ContactManager(myGdxGame.world);
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_IMG_PATH,
                "Home"
        );

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
    }

    public void restartGame() {
        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }
        shipObject = new ShipObject(
                GameResources.SHIP_IMG_PATH,GameSettings.SCR_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, SHIP_BIT,
                myGdxGame.world
        );
        for (int i = 0; i < trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }



        bulletArray.clear();
        gameSession.startGame();
    }

    @Override
    public void render(float delta) {

        handleInput();
        draw();

        if (gameSession.state == GameState.PLAYING) {
            myGdxGame.stepWorld();
            gameSession.updateScore();
            myGdxGame.scoreTextView.setText("Score: " + gameSession.getScore());
            updateTrash();
            updateBullet();
            liveView.setLeftLives(shipObject.getLiveLeft());
            backgroundView.move();
            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            if (gameSession.shouldSpawnTrash()) {
                int randNum = rand.nextInt(40);
                if (randNum >= 10) {
                    TrashObject trashObject = new TrashObject(
                            GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                            GameResources.TRASH_IMG_PATH, TRASH_BIT,
                            myGdxGame.world, "trash", 1
                    );
                    trashArray.add(trashObject);
                }
                else if (randNum>4) {
                    TrashObject trashObject = new TrashObject(
                            GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                            GameResources.BONUS_IMG_PATH, TRASH_BIT,
                            myGdxGame.world, "bonus", 1
                    );
                    trashArray.add(trashObject);
                } else {
                    TrashObject trashObject = new TrashObject(
                            GameSettings.TRASH_WIDTH*2, GameSettings.TRASH_HEIGHT*2,
                            GameResources.BOSS_IMG_PATH, TRASH_BIT,
                            myGdxGame.world, "boss", 3
                    );
                    trashArray.add(trashObject);
                }

            }
            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH, BULLET_BIT,
                        myGdxGame.world
                );
                if (myGdxGame.audio.isSoundOn) myGdxGame.audio.shootSound.play();
                bulletArray.add(laserBullet);
            }
        }
    }

    private void handleInput() {
        switch (gameSession.state) {
            case PLAYING:
                if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    gameSession.pauseGame();
                }
                shipObject.move(myGdxGame.touch);
                break;

            case PAUSED:
                if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    gameSession.resumeGame();
                }
                if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                }
                break;
            case ENDED:
                if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                    gameSession.endGame();
                }
                break;
        }

    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();

            backgroundView.draw(myGdxGame.batch);

            for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
            for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
            topBlackoutView.draw(myGdxGame.batch);
            shipObject.draw(myGdxGame.batch);
            liveView.draw(myGdxGame.batch);
            pauseButton.draw(myGdxGame.batch);
            myGdxGame.scoreTextView.draw(myGdxGame.batch);
        if (gameSession.state == GameState.PAUSED) {
            BlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            BlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }



        myGdxGame.batch.end();
    }
    private void updateTrash() {

        for (int i = 0; i < trashArray.size(); i++) {
            boolean hasToBeDestroyed = !trashArray.get(i).isAlive() || !trashArray.get(i).isInFrame();

            if (!trashArray.get(i).isAlive()) {
                if (myGdxGame.audio.isSoundOn){
                    gameSession.destructionRegistration();
                    myGdxGame.audio.explosionSound.play(0.2f);
                }
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }
    private void updateBullet() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }
    @Override
    public void show() {
        restartGame();
    }
}
