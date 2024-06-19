package com.mygdx.game;

import static com.mygdx.game.GameScreen.myGdxGame;
import static com.mygdx.game.GameSettings.SCR_HEIGHT;
import static com.mygdx.game.GameSettings.SCR_WIDTH;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	int width;
	public BitmapFont largeWhiteFont;
	public BitmapFont commonWhiteFont;
	public BitmapFont commonBlackFont;
	int height;
	Texture texture;

	Texture img;
	public World world;
	float accumulator = 0;
	public static final float STEP_TIME = 1f / 60;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 6;
	OrthographicCamera camera;
	public Vector3 touch;
	ShipObject ship;
	GameScreen gameScreen;
	GameSession gameSession;
	TextView scoreTextView;
	ButtonView pauseButton;
	public MenuScreen menuScreen;
	public SettingsScreen settingsScreen;
	AudioManager audio;
	float delta;
	ScreenAdapter screen;
	@Override
	public void create () {
		Box2D.init();
		FontBuilder fontBuilder = new FontBuilder(1, Color.WHITE);
		BitmapFont font = fontBuilder.getFont();
		largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);
		scoreTextView = new TextView(commonWhiteFont, 50, 1240, "Score: 5");
		pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
		audio = new AudioManager();
		world = new World(new Vector2(0,0), true);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);

		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);

		setScreen(menuScreen);
	}
	public void stepWorld() {
		delta = Gdx.graphics.getDeltaTime();
		accumulator += delta;

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}
	@Override
	public void render () {
		screen.render(delta);

	}
	public void setScreen(ScreenAdapter screen) {
		if (screen==gameScreen) {
			audio.startGame();
			gameScreen.restartGame();
		}
		if (screen==menuScreen) {
			audio.startMenu();

		}
		this.screen=screen;
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
