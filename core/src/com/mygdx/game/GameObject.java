package com.mygdx.game;

import static com.mygdx.game.GameScreen.myGdxGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameObject {
	int width;
	int height;
	Texture texture;
	Texture img;
	Body body;
	public short cBits;
	public int livesLeft;
	public static final float SCALE = 0.05f;
	String type;
	GameObject() {}
	GameObject(String texturePath, int x, int y, int width, int height, short cBits, World world) {
		this.width = width;
		this.height = height;
		texture = new Texture(texturePath);
		body = createBody(x, y, world);
		this.cBits = cBits;
		this.type = "other";
	}
	public void hit() {}
	Body createBody(float x, float y, World world) {
		BodyDef def = new BodyDef(); // def - defenition (определение) это объект, который содержит все данные, необходимые для посторения тела

		def.type = BodyDef.BodyType.DynamicBody; // тип тела, который имеет массу и может быть подвинут под действием сил
		def.fixedRotation = true; // запрещаем телу вращаться вокруг своей оси
		Body body = world.createBody(def); // создаём в мире world объект по описанному нами определению

		CircleShape circleShape = new CircleShape(); // задаём коллайдер в форме круга
		circleShape.setRadius(Math.max(width, height) * SCALE / 2f); // определяем радиус круга коллайдера

		FixtureDef fixtureDef = new FixtureDef();

		fixtureDef.filter.categoryBits = cBits;
		fixtureDef.shape = circleShape; // устанавливаем коллайдер
		fixtureDef.density = 0.1f; // устанавливаем плотность тела
		fixtureDef.friction = 1f; // устанвливаем коэффициент трения

		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData(this);// создаём fixture по описанному нами определению
		circleShape.dispose(); // так как коллайдер уже скопирован в fixutre, то circleShape может быть отчищена, чтобы не забивать оперативную память.

		body.setTransform(x * SCALE, y * SCALE, 0); // устанавливаем позицию тела по координатным осям и угол поворота
		return body;
	}
	public void draw(SpriteBatch batch) {
		batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
	}
	public int getX() {
		return (int) (body.getPosition().x / SCALE);
	}

	public int getY() {
		return (int) (body.getPosition().y / SCALE);
	}

	public void setX(int x) {
		body.setTransform(x * SCALE, body.getPosition().y, 0);
	}

	public void setY(int y) {
		body.setTransform(body.getPosition().x, y * SCALE, 0);
	}
	public void dispose() {
		texture.dispose();
	}

}
