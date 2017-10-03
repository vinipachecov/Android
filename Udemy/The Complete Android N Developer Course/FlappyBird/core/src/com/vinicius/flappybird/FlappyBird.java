package com.vinicius.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.Random;

//About

//  Gdx.graphics.getWidth() /2 - birds[flapState].getWidth() / 2 ->
// half of the screeen height and half of the sprite height
// get the x coordinate for drawing in the center of the screen
//  Gdx.graphics.getHeight() /2 - birds[flapState].getHeight() /2 -> get the y coordinate for drawing in the center of the screen
// half of the screen width and half of the sprite width


public class FlappyBird extends ApplicationAdapter {

//	Score
	int score = 0;
	int scoringTube = 0;

//	Font
	BitmapFont font;


//	Sprites
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	int flatState = 0;
	float birdY = 0;
	Texture gameOver;

//	colission detection
	Circle birdCircle;
	ShapeRenderer shapeRenderer;

//	how fast the bird will be moving
	float velocity = 0;

//	game control
	int gamestate = 0;

//	speed up the fall of the bird;
	float gravity = 2;

//	Tubes
	Texture topTube;
	Texture bottomTube;

//	gap between the tubes
	float gap = 400;

//	Tubes settings
	float maxTubeOffset;
	Random randomGenarator;

	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;




	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		gameOver = new Texture("gameover.png");

//		score font
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY =  Gdx.graphics.getHeight() /2 - birds[0].getHeight() /2;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		randomGenarator = new Random();

//		tube settings
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;


//		Collisions
		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];


		for (int i = 0; i < numberOfTubes ; i++) {

			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() +  i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}

	}


	public void startGame() {

		birdY =  Gdx.graphics.getHeight() /2 - birds[0].getHeight() /2;

		for (int i = 0; i < numberOfTubes ; i++) {

			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() +  i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}

	}
//			half the gap up and half the width of the tube from the center
	public void drawTubes(){

		for (int i = 0; i < numberOfTubes ; i++) {

//			off the edge of the screen
			if (tubeX[i] < - topTube.getWidth()) {

				tubeX[i] += numberOfTubes * distanceBetweenTubes;
				tubeOffset[i] = (randomGenarator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

			} else {

				tubeX[i] -=  tubeVelocity;

//				less than the middle of the screen
				if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

					score++;

					Gdx.app.log("score", String.valueOf(score));

					if (scoringTube < numberOfTubes - 1) {

						scoringTube++;

					} else {
						scoringTube = 0;
					}

				}
			}

			batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
			batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

			topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
			bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gamestate == 1) {


//			bird jump
			if (Gdx.input.justTouched()) {

				velocity = -30;

			}



			drawTubes();

//			avoid bird falling beyond the bottom of the screen
			if ( birdY  > 0  ) {

				velocity += gravity;
				birdY -= velocity;

			} else {

				gamestate = 2;
			}


		}else if (gamestate == 0){

			if (Gdx.input.justTouched()) {

				gamestate = 1;

			}

		} else if (gamestate == 2){

			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() /2 - gameOver.getHeight() /2);

			if (Gdx.input.justTouched()) {

//				restart the game
				gamestate = 1;
				startGame();
				score = 0;
				scoringTube = 0;
				velocity = 0;




			}


		}

		if (flatState == 0) {

			flatState = 1;
		} else {
			flatState = 0;
		}


		batch.draw(birds[flatState], Gdx.graphics.getWidth() / 2 - birds[flatState].getWidth() / 2, birdY);
		font.draw(batch,"Score " + String.valueOf(score), 50, Gdx.graphics.getHeight() - 50);
		batch.end();



//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.GOLD);
		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flatState].getHeight() / 2, birds[flatState].getWidth() / 2);
//		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for (int i = 0; i < numberOfTubes ; i++) {

//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

//			check if bird collides with the tubes
			if (Intersector.overlaps(birdCircle,topTubeRectangles[i]) || Intersector.overlaps(birdCircle,bottomTubeRectangles[i])){

				gamestate = 2;


			}

		}
		shapeRenderer.end();
	}
}
